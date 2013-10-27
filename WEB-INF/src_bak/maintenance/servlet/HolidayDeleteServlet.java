package maintenance.servlet;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.AppViewDeleteServlet;
import maintenance.databean.HolidayData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import jdbc.JdbcConnection;
import javax.servlet.http.HttpServletRequest;

public class

HolidayDeleteServlet extends AppViewDeleteServlet 
{

  protected String getPageIdForViewMain() 
  { 
      return "MA_HOLI,4" ;
  }
  protected  String getPageIdForDeleteConfirm() 
  { 
      return "MA_HOLI,6"  ;
  }
  protected  String getPageIdForDeleteFinal () {     return "DF_FINISH,0" ; }
  protected  String getMenuNo () {return "MA_HOLI" ; }
  protected  String getDeleteSucceedMessage (GenericWebFormData wfd)  { return "Holiday record deleted" ; }
  protected  String getDeleteFailureMessage (GenericWebFormData wfd) { return "Cannot delete Holiday record." ; } 
  // Delete data by given key object
  protected  boolean actionDelete (GenericWebFormData wfd ) 
  {
    boolean done = false ;
    HolidayData form = (HolidayData) wfd;
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "DELETE FROM HOLIDAY WHERE DATE_FORMAT(DATE,'%d/%m/%Y') = ?";
    try {
		conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, form.getData(HolidayData.holidayDate));
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
	String holidayDate = (String)keyObjectForLoad;
	HolidayData iData = new HolidayData();
    iData.setData(HolidayData.holidayDate, holidayDate);
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try
    {
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement("SELECT DESCRIPTION FROM HOLIDAY WHERE DATE = ?");
    	ps.setTimestamp(1, iData.getTimestampByField(HolidayData.holidayDate));
    	rs = ps.executeQuery();
    	if(rs.next())
    	{
    		iData.setData(HolidayData.holidayDescription, rs.getString(1));
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
    iData.setShortcut("HS");
    return iData;
  }
  
  protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
  {
	  HolidayData iData = (HolidayData)form;
	iData.setData(HolidayData.holidayDate, request.getParameter("holidayDate"));
	iData.setData(HolidayData.holidayDescription, request.getParameter("holidayDescription"));
	return iData ;
  }
  
  // abstract methods from BaseServlet need to be implement in child Create servlet
  // return key object for loading data from backend
  protected  Object getFormRequest (HttpServletRequest request) 
  {
    String holidayDate = request.getParameter("holidayDate") ;
    request.setAttribute("holidayDate",holidayDate);
    return holidayDate ;
  }
  // validate key object format when necessary  
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected Object datamapViewToEntity (Object obj) throws CircException 
  { // 
    return null ;
  }
  
  public boolean getAcceptHttpGetMethod() { return true ; } 
  
}