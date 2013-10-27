/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintSearchServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
package maintenance.servlet;

import common.databean.DataFormatter;
import common.jsp.databean.GenericSearchFormData;
import maintenance.databean.UserData;
import maintenance.databean.UserSearchFormData;
import generic.servlet.AppSearchServlet;

import jdbc.JdbcConnection;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.http.HttpServletRequest;

public class UserSearchServlet extends AppSearchServlet 
{
  public boolean getAcceptHttpGetMethod()  { return true ; }
  public String getMenuNo() { return "UM" ; } 
  public String getPageIdForResultPage()  { return ("MA_USER,1") ;  }
  public String getPageIdForSearchForm()  { return ("MA_USER,0") ; }

  public GenericSearchFormData createSearchForm(HttpServletRequest request)
  {
    return new UserSearchFormData() ;
  }

  public Vector actionSearch(GenericSearchFormData o)
  {
	  UserSearchFormData iSearchData = (UserSearchFormData)o;  
	  
    Vector v = new Vector() ;
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	String sql = "SELECT USERID, USERNAME, TRANSACTION, ITEM, BANKCODE, COUNTRY, HOLIDAY, MESSAGE, "+
				 "REPORT, PASSWORD, BACKUP, USER FROM USER WHERE STATUS = 'READY' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(UserSearchFormData.userId)))
		sql += "AND USERID = '"+iSearchData.getData(UserSearchFormData.userId)+"' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(UserSearchFormData.userName)))
		sql += "AND LCASE(USERNAME) like LCASE('%"+iSearchData.getData(UserSearchFormData.userName)+"%') ";
    try
	{
 System.out.println("USER SEARCH="+sql);
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement(sql);
    	rs = ps.executeQuery();
    	while(rs.next())
    	{
    		UserData iData = new UserData(rs);
     		v.add((UserData)iData);
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
	UserSearchFormData iSearchData = (UserSearchFormData)form;
	iSearchData.setData(UserSearchFormData.userId, request.getParameter(iSearchData.getFieldname(UserSearchFormData.userId)));
	iSearchData.setData(UserSearchFormData.userName, request.getParameter(iSearchData.getFieldname(UserSearchFormData.userName)));
	return iSearchData ;
  }

  protected Object datamapEntityToView (Object obj) throws CircException 
  { 
    return null;
  }
  
}