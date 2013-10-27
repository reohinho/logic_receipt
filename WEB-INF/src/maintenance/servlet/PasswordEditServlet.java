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

public class PasswordEditServlet extends AppEditServlet
{
  protected String getPageIdForEditMain () 
  { 
    return "MA_PASS,4";
  }
  protected String getPageIdForEditConfirm ()  
  { 
    return "MA_PASS,5";
  }
  protected String getPageIdForEditFinal ()  {     return "DF_FINISH,0" ; }
  protected String getMenuNo ()  { return "MA_PASS"  ; }
  protected String getUpdateSucceedMessage (GenericWebFormData wfd) { return "Password edit saved successfully"  ; }
  protected String getUpdateFailureMessage (GenericWebFormData wfd)  { return "Password edit saved FAILED"  ; }
  
  protected GenericWebFormData actionLoadEditData (Object keyObjectForLoad) 
  {
    String code = (String) keyObjectForLoad ;   
    UserData iData = new UserData();
    
    /*Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "SELECT * FROM USER WHERE USERID = ?";
    try {
    	conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, code);
		rs = ps.executeQuery();
		if(rs.next())
		{
			iData = new UserData(rs);
		}
    } 
    catch (Exception e)
    {
      e.printStackTrace();
    }*/
    return iData ;
  }
  
  protected boolean actionUpdate (GenericWebFormData wfd) 
  {
    UserData form = (UserData) wfd ;
    form.setShortcut("MM");
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "UPDATE USER SET PASSWORD = ? WHERE USERID = ?";
    try
    {
System.out.println("sql="+sql);
      conn = JdbcConnection.getConnection();
	  ps = conn.prepareStatement(sql);
	  ps.setString(1, form.getData(UserData.password));
	  ps.setString(2, form.getData(UserData.userId));
	  ps.executeUpdate();
	}
    catch (Exception e)
    {
      System.out.print(new java.util.Date()) ;
      System.out.println("Cannot update Password") ;
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
    return "PE";
  }
  protected Object datamapEntityToView (Object obj) throws CircException
  {
    return null ;
  }

}