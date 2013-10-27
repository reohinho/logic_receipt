package maintenance.servlet;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.AppCreateServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import maintenance.databean.HolidayData;
import jdbc.JdbcConnection;


public class HolidayCreateServlet extends AppCreateServlet 
{
	public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
	
	protected String getPageIdForAddMain ()  { return "MA_HOLI,2" ; }
    protected String getPageIdForAddConfirm ()  { return "MA_HOLI,3" ; }
    protected String getPageIdForAddFinal () { return "DF_FINISH,0" ; }
    protected String getMenuNo () { return "MA_HOLI"; }

    protected String getCreateSucceedMessage (GenericWebFormData wfd)
    {
        return "Holiday is saved sucessfully." ;
    }

    protected String getCreateFailureMessage (GenericWebFormData wfd) 
    {
        return "Holiday save is failed." ;
    }
        
    protected GenericWebFormData createAddForm (HttpServletRequest request)
    {
    	HolidayData iData = new HolidayData();
    	return iData;
    }
    protected boolean actionAdd (GenericWebFormData wfd )
    {
    	boolean result = false;
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String sql = "INSERT INTO HOLIDAY (DATE,DESCRIPTION,STATUS,CREATEDON) "+
    				" VALUES (?,?,'READY',sysdate())";
    	try
    	{
    		HolidayData iData = (HolidayData)wfd;
    		conn = JdbcConnection.getConnection();
        		
    		ps = conn.prepareStatement(sql);
    		ps.setTimestamp(1, iData.getTimestampByField(HolidayData.holidayDate));
    		ps.setString(2, iData.getData(HolidayData.holidayDescription));
    		ps.executeUpdate();
    		
    		result = true;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		wfd.setMessage(this.getCreateFailureMessage(wfd));
    		result = false;
    	}
    	finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
    	return result;
    }
    protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
    {
    	HolidayData iData = (HolidayData)form;
    	iData.setData(HolidayData.holidayDate, request.getParameter(iData.getFieldname(HolidayData.holidayDate)));
    	iData.setData(HolidayData.holidayDescription, request.getParameter(iData.getFieldname(HolidayData.holidayDescription)));
    	return iData;
    }
    
    protected Object datamapViewToEntity (Object obj) throws CircException
    {
    	return null;
    }
    
    protected boolean getAcceptHttpGetMethod()
    {
    	return true;
    }

}