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

import maintenance.databean.UserData;
import jdbc.JdbcConnection;


public class UserCreateServlet extends AppCreateServlet 
{
	public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
	
	protected String getPageIdForAddMain ()  { return "MA_USER,2" ; }
    protected String getPageIdForAddConfirm ()  { return "MA_USER,3" ; }
    protected String getPageIdForAddFinal () { return "DF_FINISH,0" ; }
    protected String getMenuNo () { return "MA_USER"; }

    protected String getCreateSucceedMessage (GenericWebFormData wfd)
    {
        return "User record created sucessfully." ;
    }

    protected String getCreateFailureMessage (GenericWebFormData wfd) 
    {
        return "User Record update is failed." ;
    }
        
    protected GenericWebFormData createAddForm (HttpServletRequest request)
    {
    	UserData iData = new UserData();
    	return iData;
    }
    protected boolean actionAdd (GenericWebFormData wfd )
    {
    	boolean result = false;
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String sql = "INSERT INTO USER (USERID,USERNAME,STATUS,CREATEDON, TRANSACTION, ITEM, BANKCODE, COUNTRY, HOLIDAY, MESSAGE, REPORT, USER, PASSWORD, BACKUP) "+
    				" VALUES (?,?,'READY',sysdate(),?,?,?,?,?,?,?,?,?,?)";
    	try
    	{
    		UserData iData = (UserData)wfd;
    		conn = JdbcConnection.getConnection();
    		
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, iData.getData(UserData.userId));
    		ps.setString(2, iData.getData(UserData.userName));
    		ps.setString(3, iData.getData(UserData.transaction));
    		ps.setString(4, iData.getData(UserData.item));
    		ps.setString(5, iData.getData(UserData.bankCode));
    		ps.setString(6, iData.getData(UserData.country));
    		ps.setString(7, iData.getData(UserData.holiday));
    		ps.setString(8, iData.getData(UserData.message));
    		ps.setString(9, iData.getData(UserData.report));
    		ps.setString(10, iData.getData(UserData.user));
    		ps.setString(11, iData.getData(UserData.password));
    		ps.setString(12, iData.getData(UserData.backup));


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
    	iData.setData(UserData.user, request.getParameter(iData.getFieldname(UserData.user)));
    	iData.setData(UserData.password, request.getParameter(iData.getFieldname(UserData.password)));
		iData.setData(UserData.backup, request.getParameter(iData.getFieldname(UserData.backup)));

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