package transaction.servlet;

import common.servlet.MasterServlet;
import common.servlet.ServletShares;
import common.servlet.SessionDataControl;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;
import com.scmp.circ.utility.Log;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.IBaseConstants;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Hashtable;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.JdbcConnection;

public class LoginServlet extends MasterServlet   {


  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doPost (request, response) ;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    super.doPost(request,response);

    Vector v = (Vector)getFormRequest(request);    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    HttpSession hs = null;
    boolean authenticated = false;
	try {
		String contextPath= request.getParameter("contextPath");
		String contextServletPath=request.getParameter("contextServletPath");
		String userId = (String)v.get(0);
		String password = (String)v.get(1);
		String userName = "";
		conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement("SELECT * from USER WHERE USERID = ? AND PASSWORD = ?");
		ps.setString(1, userId);
		ps.setString(2, password);
		rs = ps.executeQuery();
		if(rs.next())
		{
			userName = rs.getString("username");
			authenticated = true;
		}
		hs = request.getSession();
		if(hs!=null) {
		        hs.invalidate();
		}
	    // Create a new session for the user.
        hs = request.getSession(true); 
		if(authenticated)
		{
			hs.setAttribute("userId", (String)userId);
			hs.setAttribute("userName", (String)userName);
			hs.setAttribute("contextPath", (String)contextPath);
			hs.setAttribute("contextServletPath", (String)contextServletPath);
			response.sendRedirect(response.encodeRedirectURL(contextPath + "/jsp/index.jsp"));
		}
		else
		{
			response.sendRedirect(response.encodeRedirectURL(contextPath));
		}
	}
	catch(Exception e) 
	{
		e.printStackTrace();
	}
    finally{
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}
	 
  } 

  protected Object getFormRequest (HttpServletRequest request) {
      Vector v = new Vector(); 
      v.add((String)request.getParameter("userId"));
      v.add((String)request.getParameter("password"));
      return v;
  }

  protected boolean validateRequestParams (Object obj) {return true;}
  
  /**
   * Workflow for the servlet.
   * Not necessary in this servlet because workfow just serve 1 task only. Thus, simple enough to define in doPost method
   */
  protected Object process (Object obj) throws CircException {
    Hashtable htProcess = (Hashtable) obj ;
    String cmd = (String)htProcess.get ((String)"cmd") ;
    return null;
  }
  

   /**
   * Converts frontend Bulk Order object to backend Orders object
   */
  protected Object datamapViewToEntity (Object obj) throws CircException {
    return null;
  }

  /**
   * Converts backend Orders object to frontend Bulk Order object
   */
  protected Object datamapEntityToView (Object obj) throws CircException { 
    return null;
  }
//

  
}