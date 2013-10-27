/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintSearchServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
package maintenance.servlet;

import common.databean.DataFormatter;
import common.jsp.databean.GenericSearchFormData;
import maintenance.databean.CountryData;
import maintenance.databean.CountrySearchFormData;
import generic.servlet.AppSearchServlet;

import jdbc.JdbcConnection;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.http.HttpServletRequest;

public class CountrySearchServlet extends AppSearchServlet 
{
  public boolean getAcceptHttpGetMethod()  { return true ; }
  public String getMenuNo() { return "MA_COUN" ; } 
  public String getPageIdForResultPage()  { return ("MA_COUN,1") ;  }
  public String getPageIdForSearchForm()  { return ("MA_COUN,0") ; }

  public GenericSearchFormData createSearchForm(HttpServletRequest request)
  {
    return new CountrySearchFormData() ;
  }

  public Vector actionSearch(GenericSearchFormData o)
  {
	  CountrySearchFormData iSearchData = (CountrySearchFormData)o;  
	  
    Vector v = new Vector() ;
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	String sql = "SELECT * FROM COUNTRY WHERE STATUS = 'READY' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(CountrySearchFormData.countryCode)))
		sql += "AND CODE = '"+iSearchData.getData(CountrySearchFormData.countryCode)+"' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(CountrySearchFormData.countryDescription)))
		sql += "AND DESCRIPTION like '%"+iSearchData.getData(CountrySearchFormData.countryDescription)+"%' ";
    try
	{
 System.out.println("COUNTRY SEARCH="+sql);
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement(sql);
    	rs = ps.executeQuery();
    	while(rs.next())
    	{
    		CountryData iData = new CountryData(rs);
     		v.add((CountryData)iData);
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
	CountrySearchFormData iSearchData = (CountrySearchFormData)form;
	iSearchData.setData(CountrySearchFormData.countryCode, request.getParameter(iSearchData.getFieldname(CountrySearchFormData.countryCode)));
	iSearchData.setData(CountrySearchFormData.countryDescription, request.getParameter(iSearchData.getFieldname(CountrySearchFormData.countryDescription)));
	return iSearchData ;
  }

  protected Object datamapEntityToView (Object obj) throws CircException 
  { 
    return null;
  }
  
}