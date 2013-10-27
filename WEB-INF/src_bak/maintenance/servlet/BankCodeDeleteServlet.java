package maintenance.servlet;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.AppViewDeleteServlet;
import maintenance.databean.BankCodeData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import jdbc.JdbcConnection;
import javax.servlet.http.HttpServletRequest;

public class

BankCodeDeleteServlet extends AppViewDeleteServlet 
{

  protected String getPageIdForViewMain() 
  { 
      return "MA_BACO,4" ;
  }
  protected  String getPageIdForDeleteConfirm() 
  { 
      return "MA_BACO,6"  ;
  }
  protected  String getPageIdForDeleteFinal () {     return "DF_FINISH,0" ; }
  protected  String getMenuNo () {return "MA_BACO" ; }
  protected  String getDeleteSucceedMessage (GenericWebFormData wfd)  { return "Bank Code record deleted" ; }
  protected  String getDeleteFailureMessage (GenericWebFormData wfd) { return "Cannot delete Bank Code record." ; } 
  // Delete data by given key object
  protected  boolean actionDelete (GenericWebFormData wfd ) 
  {
    boolean done = false ;
    BankCodeData form = (BankCodeData) wfd;
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "UPDATE BANKCODE SET STATUS = 'DELETED', DELETEDON = sysdate() WHERE CODE = ?";
    try {
		conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, form.getData(BankCodeData.bankCode));
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
	String bankCode = (String)keyObjectForLoad;
	BankCodeData iData = new BankCodeData();
    iData.setData(BankCodeData.bankCode, bankCode);
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try
    {
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement("SELECT DESCRIPTION FROM BANKCODE WHERE CODE = ?");
    	ps.setString(1, bankCode);
    	rs = ps.executeQuery();
    	if(rs.next())
    	{
    		iData.setData(BankCodeData.bankCodeDescription, rs.getString(1));
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
    iData.setShortcut("BS");
    return iData;
  }
  
  protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
  {
	  BankCodeData iData = (BankCodeData)form;
	iData.setData(BankCodeData.bankCode, request.getParameter("bankCode"));
	iData.setData(BankCodeData.bankCodeDescription, request.getParameter("bankCodeDescription"));
	return iData ;
  }
  
  // abstract methods from BaseServlet need to be implement in child Create servlet
  // return key object for loading data from backend
  protected  Object getFormRequest (HttpServletRequest request) 
  {
    String bankCode = request.getParameter("bankCode") ;
    request.setAttribute("bankCode",bankCode);
    return bankCode ;
  }
  // validate key object format when necessary  
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected Object datamapViewToEntity (Object obj) throws CircException 
  { // 
    return null ;
  }
  
  public boolean getAcceptHttpGetMethod() { return true ; } 
  
}