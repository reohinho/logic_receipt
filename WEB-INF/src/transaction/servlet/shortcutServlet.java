package transaction.servlet;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;

import jdbc.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.jsp.databean.GeneralDataInHtml;

public class shortcutServlet extends HttpServlet 
{
	public void doGet (HttpServletRequest req,
            HttpServletResponse res)
	throws ServletException, IOException
	{
		String contextPath= (String)req.getSession().getAttribute("contextPath");
		if(contextPath==null||contextPath.equals(""))
		{
			contextPath = "localhost:8080/logic_receipt";
		}
		String uri = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String shortcutSQL = "select uri from page where lcase(shortcut) = lcase(?)";
		try
		{
			String userId = (String)req.getSession().getAttribute("userId");
			req.setAttribute("userId", userId);
			String shortcutKey = req.getParameter("shortcut");
			if(userId == null)
			{
				shortcutKey = "LL";
			}
			else if(shortcutKey==null||!GeneralDataInHtml.isAccessible(userId, shortcutKey))
			{
				shortcutKey = "MM"; //redirect to main menu 
			}
			conn = JdbcConnection.getConnection();
			ps = conn.prepareStatement(shortcutSQL);
			ps.setString(1, shortcutKey);
			rs = ps.executeQuery();
			if(rs.next())
			{
				uri = "/"+rs.getString(1);
			}

			if(uri==null||uri.equals(""))
			{
				uri = "/jsp/index.jsp";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null) try{rs.close();}catch(Exception e){e.printStackTrace();}
			if(ps!=null) try{ps.close();}catch(Exception e){e.printStackTrace();}
			if(conn!=null) try{conn.close();}catch(Exception e){e.printStackTrace();}
		}
		
		res.sendRedirect(res.encodeRedirectURL(contextPath + uri));
	  
	}
	
	public void doPost (HttpServletRequest req,
            HttpServletResponse res)
	throws ServletException, IOException
	{
		doGet(req, res);
	}
}
