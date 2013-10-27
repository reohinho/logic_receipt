package maintenance.servlet;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.io.IOException;

import common.jsp.databean.GeneralDataInHtml;

public class BackupServlet extends HttpServlet 
{
	public void doGet (HttpServletRequest req,
            HttpServletResponse res)
	throws ServletException, IOException
	{
		String contextPath= (String)req.getSession().getAttribute("contextPath");
		
		//Timestamp now = System.getCurrentTimestamp();
		String filename = new SimpleDateFormat("yyyymmddHHmmss").format(Calendar.getInstance().getTime())+".sql";
		Process process = Runtime.getRuntime().exec("cmd /k c:\\mysql\\bin\\mysqldump --user=logic -plogic logic_receipt > z:\\"+filename);
		Process process2 = Runtime.getRuntime().exec("cmd /k c:\\mysql\\bin\\mysqldump --user=logic -plogic logic_receipt > d:\\mysql_backup\\"+filename);

		res.sendRedirect(res.encodeRedirectURL(contextPath + "/jsp/index.jsp"));
	}
	
	public void doPost (HttpServletRequest req,
            HttpServletResponse res)
	throws ServletException, IOException
	{
		doGet(req, res);
	}
}
