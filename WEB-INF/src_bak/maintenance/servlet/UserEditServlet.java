/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintEditServlet.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:15 $
  $Revision: 1.1.1.1 $
*/
package maintenance.servlet;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.AppEditServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import maintenance.databean.UserData;
import jdbc.JdbcConnection;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

public class UserEditServlet extends AppEditServlet
{
  protected String getPageIdForEditMain () 
  { 
    return "MA_USER,4";
  }
  protected String getPageIdForEditConfirm ()  
  { 
    return "MA_USER,5";
  }
  protected String getPageIdForEditFinal ()  {     return "DF_FINISH,0" ; }
  protected String getMenuNo ()  { return "MA_USER"  ; }
  protected String getUpdateSucceedMessage (GenericWebFormData wfd) { return "User record edit saved successfully"  ; }
  protected String getUpdateFailureMessage (GenericWebFormData wfd)  { return "User record edit saved FAILED"  ; }
  
  protected GenericWebFormData actionLoadEditData (Object keyObjectForLoad) 
  {
    String userId = (String) keyObjectForLoad ;   
    UserData iData = null;
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "SELECT USERID, USERNAME, TRANSACTION, ITEM, BANKCODE, COUNTRY, HOLIDAY, MESSAGE, "+
	 			 "REPORT, PASSWORD, BACKUP, USER FROM USER WHERE STATUS = 'READY' AND USERID = ?";
    try {
    	conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, userId);
		rs = ps.executeQuery();
		if(rs.next())
		{
			iData = new UserData(rs);
		}
    } 
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return iData ;
  }
  
  protected boolean actionUpdate (GenericWebFormData wfd) 
  {
    UserData form = (UserData) wfd ;
    
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "UPDATE USER SET USERNAME = ?, "+
				 " TRANSACTION = ?, "+
				 " ITEM = ?, "+
				 " BANKCODE = ?, "+
				 " COUNTRY = ?, "+
				 " HOLIDAY = ?, "+
				 " MESSAGE = ?, "+
				 " REPORT = ?, "+
				 " USER = ?, "+
				 " PASSWORD = ?, "+
				 " BACKUP = ? "+				 
				 " WHERE USERID = ?";
    try
    {
      conn = JdbcConnection.getConnection();
	  ps = conn.prepareStatement(sql);
	  ps.setString(1, form.getData(UserData.userName));
	  ps.setString(2, form.getData(UserData.transaction));
	  ps.setString(3, form.getData(UserData.item));
	  ps.setString(4, form.getData(UserData.bankCode));
	  ps.setString(5, form.getData(UserData.country));
	  ps.setString(6, form.getData(UserData.holiday));
	  ps.setString(7, form.getData(UserData.message));
	  ps.setString(8, form.getData(UserData.report));
	  ps.setString(9, form.getData(UserData.user));
	  ps.setString(10, form.getData(UserData.password));
	  ps.setString(11, form.getData(UserData.backup));	  
	  ps.setString(12, form.getData(UserData.userId));

	  ps.executeUpdate();
	}
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.print(new java.util.Date()) ;
      System.out.println("Cannot update User:" + form.getData(form.userName)) ;
      return false ;
    }
    finally{
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}
    return true ;
  }
  
  // read in the data, to be saved, from Httprequest
  protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
  {
	UserData iData = (UserData)form;
	iData.setData(UserData.userId, request.getParameter("userId"));
	iData.setData(UserData.userName, request.getParameter("userName"));
	iData.setData(UserData.transaction, request.getParameter("transaction"));
	iData.setData(UserData.item, request.getParameter("item"));
	iData.setData(UserData.bankCode, request.getParameter("bankCode"));
	iData.setData(UserData.country, request.getParameter("country"));
	iData.setData(UserData.holiday, request.getParameter("holiday"));
	iData.setData(UserData.message, request.getParameter("message"));
	iData.setData(UserData.report, request.getParameter("report"));
	iData.setData(UserData.user, request.getParameter("user"));
	iData.setData(UserData.backup, request.getParameter("backup"));
	iData.setData(UserData.password, request.getParameter("password"));
	
    return iData ;
  }
  
  // abstract methods from BaseServlet need to be implement in child Edit servlet
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected Object datamapViewToEntity (Object obj) throws CircException 
  {
    return null;
  }

  public Long zeroIfNull(Long input)
  {
    return ((input == null) ? new Long(0) : input) ;
  }
  
  // read in key element for load edit data from backend from Httprequest
  protected Object getFormRequest (HttpServletRequest request)
  {
    return (String)request.getParameter("userId") ;
  }
  protected Object datamapEntityToView (Object obj) throws CircException
  {
    return null ;
  }

}