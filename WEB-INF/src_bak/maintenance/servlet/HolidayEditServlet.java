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

import maintenance.databean.HolidayData;
import jdbc.JdbcConnection;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

public class HolidayEditServlet extends AppEditServlet
{
  protected String getPageIdForEditMain () 
  { 
    return "MA_HOLI,4";
  }
  protected String getPageIdForEditConfirm ()  
  { 
    return "MA_HOLI,5";
  }
  protected String getPageIdForEditFinal ()  {     return "DF_FINISH,0" ; }
  protected String getMenuNo ()  { return "MA_HOLI"  ; }
  protected String getUpdateSucceedMessage (GenericWebFormData wfd) { return "Holiday record edit saved successfully"  ; }
  protected String getUpdateFailureMessage (GenericWebFormData wfd)  { return "Holiday record edit saved FAILED"  ; }
  
  protected GenericWebFormData actionLoadEditData (Object keyObjectForLoad) 
  {
    String holidayDate = (String) keyObjectForLoad ;   
    HolidayData iData = new HolidayData();
    iData.setData(HolidayData.holidayDate,holidayDate);
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "SELECT DESCRIPTION FROM HOLIDAY WHERE DATE = ?";
    try {
    	conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setTimestamp(1, iData.getTimestampByField(HolidayData.holidayDate));
		rs = ps.executeQuery();
		if(rs.next())
		{
			iData.setData(HolidayData.holidayDescription, rs.getString(1));
		}
    } 
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally{
		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}
    return iData ;
  }
  
  protected boolean actionUpdate (GenericWebFormData wfd) 
  {
    HolidayData form = (HolidayData) wfd ;
    
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "UPDATE HOLIDAY SET DESCRIPTION = ?, MODIFIEDON = SYSDATE() WHERE DATE_FORMAT(DATE,'%d/%m/%Y') = ?";
    try
    {
      conn = JdbcConnection.getConnection();
	  ps = conn.prepareStatement(sql);
	  ps.setString(1, form.getData(HolidayData.holidayDescription));
	  ps.setString(2, form.getData(HolidayData.holidayDate));

	  ps.executeUpdate();
	}
    catch (Exception e)
    {
      System.out.print(new java.util.Date()) ;
      System.out.println("Cannot update Item:" + form.getData(form.holidayDescription)) ;
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
	HolidayData iData = (HolidayData)form;
	iData.setData(HolidayData.holidayDate, request.getParameter("holidayDate"));
	iData.setData(HolidayData.holidayDescription, request.getParameter("holidayDescription"));
	
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
    String holidayDate = request.getParameter("holidayDate") ;
    return holidayDate ;
  }
  protected Object datamapEntityToView (Object obj) throws CircException
  {
    return null ;
  }

}