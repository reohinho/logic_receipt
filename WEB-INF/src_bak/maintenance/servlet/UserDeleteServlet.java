package maintenance.servlet;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.AppViewDeleteServlet;
import maintenance.databean.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import jdbc.JdbcConnection;
import javax.servlet.http.HttpServletRequest;

public class

UserDeleteServlet extends AppViewDeleteServlet 
{

  protected String getPageIdForViewMain() 
  { 
      return "MA_USER,4" ;
  }
  protected  String getPageIdForDeleteConfirm() 
  { 
      return "MA_USER,6"  ;
  }
  protected  String getPageIdForDeleteFinal () {     return "DF_FINISH,0" ; }
  protected  String getMenuNo () {return "MA_USER" ; }
  protected  String getDeleteSucceedMessage (GenericWebFormData wfd)  { return "User record is deleted" ; }
  protected  String getDeleteFailureMessage (GenericWebFormData wfd) { return "Cannot delete User record." ; } 
  // Delete data by given key object
  protected  boolean actionDelete (GenericWebFormData wfd ) 
  {
    boolean done = false ;
    UserData form = (UserData) wfd;
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "UPDATE USER SET STATUS = 'DELETED', DELETEDON = sysdate() WHERE USERID = ?";
    try {
		conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, form.getData(UserData.userId));
		ps.executeUpdate();
		
		done = true;
    }
    catch (Exception e)
    {
      done = false;
      form.setMessage(this.getDeleteFailureMessage(wfd));            
      e.printStackTrace();
    }
    finally{
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}
    form.setShouldRefresh("Y");
    return done ;
  }
  // Load data by given key object. If fails, NULL object will be returned
  protected GenericWebFormData actionLoad (Object keyObjectForLoad ) 
  {
	String userId = (String)keyObjectForLoad;
	UserData iData = null;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try
    {
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement("SELECT USERID, USERNAME, TRANSACTION, ITEM, "+
    								"BANKCODE, COUNTRY, HOLIDAY, MESSAGE, REPORT FROM USER WHERE USERID = ?");
    	ps.setString(1, userId);
    	rs = ps.executeQuery();
    	if(rs.next())
    	{
    		iData = new UserData(rs);
    	}
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
    finally{
		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}
    return iData;
  }
  
  protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
  {
	UserData iData = (UserData)form;
	iData.setData(UserData.userId, request.getParameter(iData.getFieldname(UserData.userId)));
	iData.setData(UserData.userName, request.getParameter(iData.getFieldname(UserData.userName)));
	iData.setData(UserData.transaction, request.getParameter(iData.getFieldname(UserData.transaction)));
	iData.setData(UserData.item, request.getParameter(iData.getFieldname(UserData.item)));
	iData.setData(UserData.bankCode, request.getParameter(iData.getFieldname(UserData.bankCode)));
	iData.setData(UserData.country, request.getParameter(iData.getFieldname(UserData.country)));
	iData.setData(UserData.holiday, request.getParameter(iData.getFieldname(UserData.holiday)));
	iData.setData(UserData.message, request.getParameter(iData.getFieldname(UserData.message)));
	iData.setData(UserData.report, request.getParameter(iData.getFieldname(UserData.report)));
	return iData;
  }
  
  // abstract methods from BaseServlet need to be implement in child Create servlet
  // return key object for loading data from backend
  protected  Object getFormRequest (HttpServletRequest request) 
  {
    return (String)request.getParameter("userId") ;
  }
  // validate key object format when necessary  
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected Object datamapViewToEntity (Object obj) throws CircException 
  { // 
    return null ;
  }
  
  public boolean getAcceptHttpGetMethod() { return true ; } 
  
}