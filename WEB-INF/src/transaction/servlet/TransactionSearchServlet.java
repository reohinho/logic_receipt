/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintSearchServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
package transaction.servlet;

import common.databean.DataFormatter;
import common.jsp.databean.GenericSearchFormData;
import transaction.databean.TransactionData;
import transaction.databean.TransactionLineData;
import transaction.databean.TransactionSearchFormData;
import generic.servlet.AppSearchServlet;

import jdbc.JdbcConnection;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.http.HttpServletRequest;

public class TransactionSearchServlet extends AppSearchServlet 
{
  public boolean getAcceptHttpGetMethod()  { return true ; }
  public String getMenuNo() { return "IM" ; } 
  public String getPageIdForResultPage()  { return ("TX_INPUT,1") ;  }
  public String getPageIdForSearchForm()  { return ("TX_INPUT,0") ; }

  public GenericSearchFormData createSearchForm(HttpServletRequest request)
  {
    return new TransactionSearchFormData() ;
  }

  public Vector actionSearch(GenericSearchFormData o)
  {
	  TransactionSearchFormData iSearchData = (TransactionSearchFormData)o;  
	  
    Vector v = new Vector() ;
    Connection conn = null;
	PreparedStatement ps = null;
	PreparedStatement ps2 = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	
	String sql = "SELECT * FROM RECEIPT WHERE 1=1 ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(TransactionSearchFormData.txNo)))
		sql += "AND RECEIPTNO = '"+iSearchData.getData(TransactionSearchFormData.txNo)+"'";
	if(!CircUtilities.isEmptyString(iSearchData.getData(TransactionSearchFormData.name)))
		sql += "AND LCASE(NAME) LIKE  LCASE('%"+iSearchData.getData(TransactionSearchFormData.name)+"%') ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(TransactionSearchFormData.officerId)))
		sql += "AND OFFICERID = '"+iSearchData.getData(TransactionSearchFormData.officerId)+"' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(TransactionSearchFormData.status)))
		sql += "AND STATUS = '"+iSearchData.getData(TransactionSearchFormData.status)+"' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(TransactionSearchFormData.refNo)))
		sql += "AND REFNO = '"+iSearchData.getData(TransactionSearchFormData.refNo)+"' ";
    
	
	try
	{
 System.out.println("TRANSACTION SEARCH="+sql);
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement(sql);
    	rs = ps.executeQuery();
    	int counter = 0;
    	int pageMax = 100000;
    	while(rs.next()&&counter<pageMax)
    	{
    		counter++;
    		TransactionData iData = new TransactionData(rs);
     		/*
    		//Receipt Line Details
    		ps2 = conn.prepareStatement("SELECT LINENO, ITEMNO, QTY, ITEMDESCRIPTION, UNITPRICE, TOTAL FROM RECEIPT_ITEM WHERE RECEIPTNO = ?");
    		ps2.setString(1, iData.getData(TransactionData.txNo));
    		rs2 = ps2.executeQuery();
    		Vector lineVec = new Vector();
    		while(rs2.next())
    		{
    			TransactionLineData tlData = new TransactionLineData(rs2);
    			lineVec.add((TransactionLineData)tlData);
    		}        		
    		iData.setLineVec(lineVec);
    		*/
    		if(iData.getData(TransactionData.txType)!=null&&iData.getData(TransactionData.txType).equals("A"))
    		{
    			iData.setLabel(TransactionData.dob,"dob","Date of Birth", "REQUIRED");
    			iData.setLabel(TransactionData.nationality,"nationality","Nationality","REQUIRED");

    		}
    		else
    		{
    			iData.setLabel(TransactionData.dob,"dob","Date of Birth", "");
    			iData.setLabel(TransactionData.nationality,"nationality","Nationality","");
    		}

    		v.add((TransactionData)iData);
    	}
	}
    catch(Exception e)
    { 
    	e.printStackTrace();
    }
    finally
    {
    	if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}

    return v ;
  }
  
  /**
   * Read all screen form data from HTTP request
   */
  protected GenericSearchFormData getWebFormRequest (GenericSearchFormData form, HttpServletRequest request) 
  { 
	TransactionSearchFormData iSearchData = (TransactionSearchFormData)form;
	iSearchData.setData(TransactionSearchFormData.txNo, request.getParameter(iSearchData.getFieldname(TransactionSearchFormData.txNo)));
	iSearchData.setData(TransactionSearchFormData.name, request.getParameter(iSearchData.getFieldname(TransactionSearchFormData.name)));
	iSearchData.setData(TransactionSearchFormData.officerId, request.getParameter(iSearchData.getFieldname(TransactionSearchFormData.officerId)));
	iSearchData.setData(TransactionSearchFormData.refNo, request.getParameter(iSearchData.getFieldname(TransactionSearchFormData.refNo)));
	iSearchData.setData(TransactionSearchFormData.status, request.getParameter(iSearchData.getFieldname(TransactionSearchFormData.status)));
	return iSearchData ;
  }

  protected Object datamapEntityToView (Object obj) throws CircException 
  { 
    return null;
  }
  
}