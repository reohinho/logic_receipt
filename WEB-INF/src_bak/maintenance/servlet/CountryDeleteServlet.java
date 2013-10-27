package maintenance.servlet;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.AppViewDeleteServlet;
import maintenance.databean.BankCodeData;
import maintenance.databean.CountryData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import jdbc.JdbcConnection;
import javax.servlet.http.HttpServletRequest;

public class

CountryDeleteServlet extends AppViewDeleteServlet 
{

  protected String getPageIdForViewMain() 
  { 
      return "MA_COUN,4" ;
  }
  protected  String getPageIdForDeleteConfirm() 
  { 
      return "MA_COUN,6"  ;
  }
  protected  String getPageIdForDeleteFinal () {     return "DF_FINISH,0" ; }
  protected  String getMenuNo () {return "MA_COUN" ; }
  protected  String getDeleteSucceedMessage (GenericWebFormData wfd)  { return "Country record deleted" ; }
  protected  String getDeleteFailureMessage (GenericWebFormData wfd) { return "Cannot delete Country record." ; } 
  // Delete data by given key object
  protected  boolean actionDelete (GenericWebFormData wfd ) 
  {
    boolean done = false ;
    CountryData form = (CountryData) wfd;
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "UPDATE COUNTRY SET STATUS = 'DELETED', DELETEDON = sysdate() WHERE CODE = ?";
    try {
		conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, form.getData(CountryData.countryCode));
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
	String countryCode = (String)keyObjectForLoad;
	CountryData iData = new CountryData();
    iData.setData(CountryData.countryCode, countryCode);
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try
    {
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement("SELECT DESCRIPTION FROM COUNTRY WHERE CODE = ?");
    	ps.setString(1, countryCode);
    	rs = ps.executeQuery();
    	if(rs.next())
    	{
    		iData.setData(CountryData.countryDescription, rs.getString(1));
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
    iData.setShortcut("CS");
    return iData;
  }
  
  protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
  {
	  CountryData iData = (CountryData)form;
	iData.setData(CountryData.countryCode, request.getParameter("countryCode"));
	iData.setData(CountryData.countryDescription, request.getParameter("countryDescription"));
	return iData ;
  }
  
  // abstract methods from BaseServlet need to be implement in child Create servlet
  // return key object for loading data from backend
  protected  Object getFormRequest (HttpServletRequest request) 
  {
    String countryCode = request.getParameter("countryCode") ;
    request.setAttribute("countryCode",countryCode);
    return countryCode ;
  }
  // validate key object format when necessary  
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected Object datamapViewToEntity (Object obj) throws CircException 
  { // 
    return null ;
  }
  
  public boolean getAcceptHttpGetMethod() { return true ; } 
  
}