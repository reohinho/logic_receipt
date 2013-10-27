/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintSearchServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
package transaction.servlet;

import common.databean.DataFormatter;
import common.jsp.databean.GenericSearchFormData;
import transaction.databean.TransactionEnquiryData;
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

public class TransactionEnquiryServlet extends AppSearchServlet 
{
  public boolean getAcceptHttpGetMethod()  { return true ; }
  public String getMenuNo() { return "IE" ; } 
  public String getPageIdForResultPage()  { return ("TX_ENQU,1") ;  }
  public String getPageIdForSearchForm()  { return ("TX_ENQU,0") ; }

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
	ResultSet rs = null;
	
	String sql = "SELECT T1.NAME, T3.ITEMDESCRIPTION, IF(T1.PAYMENTTYPE='cash', T1.AMOUNT, 0), "+
				 "IF(T1.PAYMENTTYPE='cheque',T1.AMOUNT,0),T1.RECEIPTNO,T1.CREATEDON,OFFICERID,IF(T1.STATUS='READY','','Y') "+
				 "FROM RECEIPT T1, RECEIPT_ITEM T2, ITEM T3 "+
				 "WHERE T1.RECEIPTNO = T2.RECEIPTNO  "+
				 "AND T2.ITEMNO = T3.ITEMNO  ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(TransactionSearchFormData.txNo)))
		sql += "AND T1.RECEIPTNO = '"+iSearchData.getData(TransactionSearchFormData.txNo)+"'";
	if(!CircUtilities.isEmptyString(iSearchData.getData(TransactionSearchFormData.name)))
		sql += "AND LCASE(NAME) LIKE  LCASE('%"+iSearchData.getData(TransactionSearchFormData.name)+"%') ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(TransactionSearchFormData.officerId)))
		sql += "AND OFFICERID = '"+iSearchData.getData(TransactionSearchFormData.officerId)+"' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(TransactionSearchFormData.status)))
		sql += "AND T1.STATUS = '"+iSearchData.getData(TransactionSearchFormData.status)+"' ";
    try
	{
 System.out.println("TRANSACTION ENQUIRY="+sql);
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement(sql);
    	rs = ps.executeQuery();
    	while(rs.next())
    	{
    		TransactionEnquiryData iData = new TransactionEnquiryData(rs);
    		v.add((TransactionEnquiryData)iData);
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