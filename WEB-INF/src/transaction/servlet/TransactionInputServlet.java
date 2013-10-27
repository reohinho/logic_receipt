package transaction.servlet;

import common.jsp.databean.GenericWebFormData;
import common.jsp.databean.GeneralDataInHtml;
import common.jsp.HtmlHelper;
import generic.servlet.AppCreateServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.util.Vector;

import com.scmp.circ.utility.CircException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.JdbcConnection;

import transaction.databean.TransactionData;
import transaction.databean.TransactionLineData;


public class TransactionInputServlet extends AppCreateServlet 
{
	public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
	
	protected String getPageIdForAddMain ()  { return "TX_INPUT,2" ; }
    protected String getPageIdForAddConfirm ()  { return "TX_INPUT,3" ; }
    protected String getPageIdForAddFinal () { return "RP_RECP" ; }
    protected String getMenuNo () { return "TX_INPUT"; }

    protected String getCreateSucceedMessage (GenericWebFormData wfd)
    {
        return "Transaction is saved sucessfully." ;
    }

    protected String getCreateFailureMessage (GenericWebFormData wfd) 
    {
        return "Transaction failed." ;
    }
        
    protected GenericWebFormData createAddForm (HttpServletRequest request)
    {
    	TransactionData tData = new TransactionData();
    	tData.setData(TransactionData.txType, "A");
    	tData.setShortcut("RP");
    	return tData;
    }
    protected boolean actionAdd (GenericWebFormData wfd )
    {
    	boolean result = false;
    	
    	TransactionData tData = (TransactionData)wfd;
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String txNoSQL = "SELECT max(receiptno) FROM RECEIPT";
    	String sql = "INSERT INTO RECEIPT (RECEIPTNO, NAME, NAME2, GENDER, DOB, REFNO, RECEIPTTYPE, NATIONALITY, AMOUNT, PAYMENTTYPE, PAYMENTREF, OFFICERID, STATUS, CREATEDON)"+
    				"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'READY',sysdate())";
    	String txLineSQL = "INSERT INTO RECEIPT_ITEM (LINENO, RECEIPTNO, ITEMNO, QTY, UNITPRICE) VALUES "+
    						" (?,?,?,?,?)";
    	String itemDetailSQL = "UPDATE RECEIPT_ITEM T1, ITEM T2 "+
    							"SET T1.ITEMDESCRIPTION = T2.ITEMDESCRIPTION, "+
								//"T1.UNITPRICE = T2.AMOUNT, "+
								"T1.TOTAL = T1.QTY * T2.AMOUNT "+
								"WHERE T1.ITEMNO = T2.ITEMNO AND T1.RECEIPTNO = ?";
    	
    	String paymentAmtSQL = "SELECT * FROM RECEIPT_ITEM WHERE RECEIPTNO = ?";
    	String paymentAmtSQL2 = "UPDATE RECEIPT SET AMOUNT = ? WHERE RECEIPTNO = ?";
    	
    	int txCount = 0;
    	try
    	{
    		conn = JdbcConnection.getConnection();
    		
    		conn.setAutoCommit(false);
    		
    		ps = conn.prepareStatement(txNoSQL);
    		rs = ps.executeQuery();
    		if(rs.next())
    		{
    			txCount = rs.getInt(1)+1;
    		}
    		ps = conn.prepareStatement(sql);
    		int j=1;
    		ps.setInt(j++, txCount);
    		ps.setString(j++, tData.getData(TransactionData.name));
    		ps.setString(j++, tData.getData(TransactionData.name2));
    		ps.setString(j++, tData.getData(TransactionData.gender));
    		ps.setTimestamp(j++, tData.getTimestampByField(TransactionData.dob));
    		ps.setString(j++, tData.getData(TransactionData.refNo));
    		ps.setString(j++, tData.getData(TransactionData.txType));
    		ps.setString(j++, tData.getData(TransactionData.nationality));
    		ps.setString(j++, tData.getData(TransactionData.paymentAmt));
    		ps.setString(j++, tData.getData(TransactionData.paymentType));
    		ps.setString(j++, tData.getData(TransactionData.paymentRef));
    		ps.setString(j++, tData.getData(TransactionData.officerId));
    		ps.executeUpdate();
    		
    		Vector lineVec = tData.getLineVec();
    		
    		for(int i=0;i<lineVec.size();i++)
    		{
    			TransactionLineData tlData = (TransactionLineData)lineVec.get(i);
    			if(tlData.getData(TransactionLineData.itemNo)!=null&&!tlData.getData(TransactionLineData.itemNo).equals(""))
    			{
    				ps = conn.prepareStatement(txLineSQL);
	    			ps.setString(1, tlData.getData(TransactionLineData.lineNo));
	    			ps.setInt(2, txCount);
	    			ps.setString(3, tlData.getData(TransactionLineData.itemNo));
	    			ps.setString(4, tlData.getData(TransactionLineData.qty));
	    			ps.setString(5, tlData.getData(TransactionLineData.unitPrice));
	    			
	    			ps.executeUpdate();
    			}
    		}
    		
    		
    		ps = conn.prepareStatement(itemDetailSQL);
    		ps.setInt(1, txCount);
    		ps.executeUpdate();
    		
    		ps = conn.prepareStatement(paymentAmtSQL);
    		ps.setInt(1, txCount);
    		rs = ps.executeQuery();
    		double totalAmt = 0;
    		while(rs.next())
    		{
    			totalAmt += rs.getDouble("total");
    		}
    		
    		ps = conn.prepareStatement(paymentAmtSQL2);
    		ps.setDouble(1, totalAmt);
    		ps.setInt(2, txCount);
    		ps.executeUpdate();
    		
    		conn.commit();
    		result = true;
    	}
    	catch(Exception e)
    	{
    		try
    		{	conn.rollback(); }
    		catch(Exception ee) {ee.printStackTrace(); }
    		e.printStackTrace();
    		return false;
    	}
    	finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
    	return result;
    }
    protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
    {
    	TransactionData tData = (TransactionData)form;

    	//TransactionData
    	tData.setData(TransactionData.name, request.getParameter("name"));
    	tData.setData(TransactionData.name2, request.getParameter("name2"));
    	tData.setData(TransactionData.gender, request.getParameter("gender"));
    	tData.setData(TransactionData.dob, request.getParameter("dob"));
    	tData.setData(TransactionData.nationality, request.getParameter("nationality"));
    	tData.setData(TransactionData.txType, request.getParameter("txType"));
    	tData.setData(TransactionData.paymentAmt, request.getParameter("paymentAmt"));
    	tData.setData(TransactionData.paymentType, request.getParameter("paymentType"));
    	tData.setData(TransactionData.paymentRef, request.getParameter("paymentRef"));
    	tData.setData(TransactionData.officerId, request.getParameter("officerId"));
    	tData.setData(TransactionData.printerName, request.getParameter("printerName"));

    	//TransactionLineData
    	String itemNo[] = request.getParameterValues("itemNo");
    	String unitPrice[] = request.getParameterValues("price");
    	String qty[] = request.getParameterValues("qty");
    	String lineAmount[] = request.getParameterValues("total");

    	Vector lineVec = new Vector();
		
		int qtyValue = 0;
		int maxQty = 0;
    	if(qty!=null)
    	{
	    	for(int i=0;i<qty.length;i++)
	    	{
	    		if(qty[i]!=null) //For the transaction Line with qty input only
	    		{
		    		TransactionLineData tlData = new TransactionLineData();
		    		tlData.setData(TransactionLineData.lineNo, new Integer(i+1).toString());
		    		tlData.setData(TransactionLineData.itemNo, itemNo[i]);
		    		tlData.setData(TransactionLineData.unitPrice, unitPrice[i]);
					
					//Check max qty by Item No  2012-12-19 Leo
					if(!qty[i].equals("")&&HtmlHelper.isNumberic(qty[i])) {
						qtyValue = Integer.parseInt(qty[i]);
						maxQty = getMaxQty(itemNo[i]);
						if(maxQty < qtyValue) {
							qtyValue = maxQty;
						}	
					}
					else{
						qtyValue = 1;
					}
		    		tlData.setData(TransactionLineData.qty, (new Integer(qtyValue)).toString());
		    		tlData.setData(TransactionLineData.lineAmount, lineAmount[i]);
	    		
		    		lineVec.add((TransactionLineData)tlData);
	    		}
	    	}
    	}
    	tData.setLineVec(lineVec);
    	
    	if(tData.getData(TransactionData.txType)!=null&&tData.getData(TransactionData.txType).equals("A"))
		{
			tData.setLabel(TransactionData.dob,"dob","Date of Birth", "REQUIRED");
			tData.setLabel(TransactionData.nationality,"nationality","Nationality","REQUIRED");

		}
		else
		{
			tData.setLabel(TransactionData.dob,"dob","Date of Birth", "");
			tData.setLabel(TransactionData.nationality,"nationality","Nationality","");
		}
    	
    	return tData;
    }
    
    protected Object datamapViewToEntity (Object obj) throws CircException
    {
    	return null;
    }
    
    protected boolean getAcceptHttpGetMethod()
    {
    	return true;
    }
	
	protected int getMaxQty(String itemNo) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "Select maxqty from item where itemno = ?";
		int maxQty = 0;
		try {
			conn = JdbcConnection.getConnection();    		
    		ps = conn.prepareStatement(sql);
			ps.setString(1, itemNo);
    		rs = ps.executeQuery();
    		if(rs.next())
    		{
    			maxQty = rs.getInt(1);
    		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
		}
		
		return maxQty;
	}

}