/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintSearchServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
package maintenance.servlet;

import common.databean.DataFormatter;
import common.jsp.databean.GenericSearchFormData;
import maintenance.databean.HolidayData;
import maintenance.databean.HolidaySearchFormData;
import generic.servlet.AppSearchServlet;

import jdbc.JdbcConnection;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.http.HttpServletRequest;

public class HolidaySearchServlet extends AppSearchServlet 
{
  public boolean getAcceptHttpGetMethod()  { return true ; }
  public String getMenuNo() { return "MA_HOLI" ; } 
  public String getPageIdForResultPage()  { return ("MA_HOLI,1") ;  }
  public String getPageIdForSearchForm()  { return ("MA_HOLI,0") ; }

  public GenericSearchFormData createSearchForm(HttpServletRequest request)
  {
    return new HolidaySearchFormData() ;
  }

  public Vector actionSearch(GenericSearchFormData o)
  {
	  HolidaySearchFormData iSearchData = (HolidaySearchFormData)o;  
	  
    Vector v = new Vector() ;
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	String sql = "SELECT DATE_FORMAT(DATE, '%d/%m/%Y') AS DATE, DESCRIPTION FROM HOLIDAY WHERE STATUS = 'READY' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(HolidaySearchFormData.holidayDescription)))
		sql += "AND LCASE(DESCRIPTION) like LCASE('%"+iSearchData.getData(HolidaySearchFormData.holidayDescription)+"%') ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(HolidaySearchFormData.holidayDate)))
		sql += "AND DATE = ? ";
	sql += "ORDER BY DATE_FORMAT(DATE, '%Y%m%d') ";
    try
	{
 System.out.println("HOLIDAY SEARCH="+sql);
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement(sql);
    	if(!CircUtilities.isEmptyString(iSearchData.getData(HolidaySearchFormData.holidayDate)))
    		ps.setTimestamp(1, iSearchData.getTimestampByField(HolidaySearchFormData.holidayDate));
    	rs = ps.executeQuery();
    	while(rs.next())
    	{
    		HolidayData iData = new HolidayData(rs);
     		v.add((HolidayData)iData);
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
	  HolidaySearchFormData iSearchData = (HolidaySearchFormData)form;
	iSearchData.setData(HolidaySearchFormData.holidayDate, request.getParameter(iSearchData.getFieldname(HolidaySearchFormData.holidayDate)));
	iSearchData.setData(HolidaySearchFormData.holidayDescription, request.getParameter(iSearchData.getFieldname(HolidaySearchFormData.holidayDescription)));
	return iSearchData ;
  }

  protected Object datamapEntityToView (Object obj) throws CircException 
  { 
    return null;
  }
  
}