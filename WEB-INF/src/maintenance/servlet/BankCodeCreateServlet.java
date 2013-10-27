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

import maintenance.databean.BankCodeData;
import jdbc.JdbcConnection;


public class BankCodeCreateServlet extends AppCreateServlet 
{
	public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
	
	protected String getPageIdForAddMain ()  { return "MA_BACO,2" ; }
    protected String getPageIdForAddConfirm ()  { return "MA_BACO,3" ; }
    protected String getPageIdForAddFinal () { return "DF_FINISH,0" ; }
    protected String getMenuNo () { return "MA_BACO"; }

    protected String getCreateSucceedMessage (GenericWebFormData wfd)
    {
        return "Bank Code is saved sucessfully." ;
    }

    protected String getCreateFailureMessage (GenericWebFormData wfd) 
    {
        return "Bank Code save is failed." ;
    }
        
    protected GenericWebFormData createAddForm (HttpServletRequest request)
    {
    	BankCodeData iData = new BankCodeData();
    	return iData;
    }
    protected boolean actionAdd (GenericWebFormData wfd )
    {
    	boolean result = false;
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String counterSQL = "SELECT COUNT(1) FROM BANKCODE";
    	String sql = "INSERT INTO BANKCODE (CODE,DESCRIPTION,STATUS,CREATEDON) "+
    				" VALUES (LPAD(?,10,'0'),?,'READY',sysdate())";
    	try
    	{
    		BankCodeData iData = (BankCodeData)wfd;
    		conn = JdbcConnection.getConnection();
    		ps = conn.prepareStatement(counterSQL);
    		rs = ps.executeQuery();
    		int itemCount = 1;
    		if(rs.next())
    		{
    			itemCount = rs.getInt(1)+1;
    		}
    		
    		ps = conn.prepareStatement(sql);
    		ps.setInt(1, itemCount);
    		ps.setString(2, iData.getData(BankCodeData.bankCodeDescription));
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
    	BankCodeData iData = (BankCodeData)form;
    	iData.setData(BankCodeData.bankCode, request.getParameter(iData.getFieldname(BankCodeData.bankCode)));
    	iData.setData(BankCodeData.bankCodeDescription, request.getParameter(iData.getFieldname(BankCodeData.bankCodeDescription)));
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