/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintSearchServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
package maintenance.servlet;

import common.databean.DataFormatter;
import common.jsp.databean.GenericSearchFormData;
import maintenance.databean.BankCodeData;
import maintenance.databean.BankCodeSearchFormData;
import generic.servlet.AppSearchServlet;

import jdbc.JdbcConnection;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.http.HttpServletRequest;

public class BankCodeSearchServlet extends AppSearchServlet 
{
  public boolean getAcceptHttpGetMethod()  { return true ; }
  public String getMenuNo() { return "IM" ; } 
  public String getPageIdForResultPage()  { return ("MA_BACO,1") ;  }
  public String getPageIdForSearchForm()  { return ("MA_BACO,0") ; }

  public GenericSearchFormData createSearchForm(HttpServletRequest request)
  {
    return new BankCodeSearchFormData() ;
  }

  public Vector actionSearch(GenericSearchFormData o)
  {
	  BankCodeSearchFormData iSearchData = (BankCodeSearchFormData)o;  
	  
    Vector v = new Vector() ;
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	String sql = "SELECT * FROM BANKCODE WHERE STATUS = 'READY' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(BankCodeSearchFormData.bankCode)))
		sql += "AND CODE = '"+iSearchData.getData(BankCodeSearchFormData.bankCode)+"' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(BankCodeSearchFormData.bankCodeDescription)))
		sql += "AND DESCRIPTION = '"+iSearchData.getData(BankCodeSearchFormData.bankCodeDescription)+"' ";
    try
	{
 System.out.println("BANKCODE SEARCH="+sql);
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement(sql);
    	rs = ps.executeQuery();
    	while(rs.next())
    	{
    		BankCodeData iData = new BankCodeData(rs);
     		v.add((BankCodeData)iData);
    	}
	}
    catch(Exception e)
    { // ignore search error, just return empty vector
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
	BankCodeSearchFormData iSearchData = (BankCodeSearchFormData)form;
	iSearchData.setData(BankCodeSearchFormData.bankCode, request.getParameter(iSearchData.getFieldname(BankCodeSearchFormData.bankCode)));
	iSearchData.setData(BankCodeSearchFormData.bankCodeDescription, request.getParameter(iSearchData.getFieldname(BankCodeSearchFormData.bankCodeDescription)));
	return iSearchData ;
  }

  protected Object datamapEntityToView (Object obj) throws CircException 
  { 
    return null;
  }
  
}