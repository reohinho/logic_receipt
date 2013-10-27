/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/servlet/DummyServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */

package common.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class

DummyServlet extends HttpServlet 
{

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String s = "" ;
    Enumeration enume = request.getHeaderNames() ;
    s += "<B>request.getHeaderNames()</B>" + "<BR>\n" ;
    while (enume.hasMoreElements()) {
      String h = (String) enume.nextElement() ;
      s += h + "->" + request.getHeader(h) + "<BR>\n" ;
    }
    s += "<B>request.getContextPath()</B> -> " + request.getContextPath()  + "<BR>\n" ;
    s += "<B>request.getMethod()</B> -> " + request.getMethod()  + "<BR>\n" ;
    s += "<B>request.getServletPath()</B> -> " + request.getServletPath()  + "<BR>\n" ;
    s += "<B>request.getPathInfo()</B> -> " + request.getPathInfo()  + "<BR>\n" ;
    s += "<B>request.getProtocol()</B> -> " + request.getProtocol()  + "<BR>\n" ;
    s += "<B>request.getQueryString()</B> -> " + request.getQueryString()  + "<BR>\n" ;
    s += "<B>request.getRemoteAddr()</B> -> " + request.getRemoteAddr()  + "<BR>\n" ;
    s += "<B>request.getRemoteHost()</B> -> " + request.getRemoteHost()  + "<BR>\n" ;
    s += "<B>request.getRemoteUser()</B> -> " + request.getRemoteUser()  + "<BR>\n" ;
    s += "<B>request.getServerName()</B> -> " + request.getServerName()  + "<BR>\n" ;
    s += "<B>request.getRequestURI()</B> -> " + request.getRequestURI()  + "<BR>\n" ;
    s += "<B>request.getRequestURL()</B> -> " + request.getRequestURL()  + "<BR>\n" ;
    s += "<B>request.getScheme()</B> -> " + request.getScheme()  + "<BR>\n" ;
    s += "<B>request.getSession().getId()</B> -> " + request.getSession().getId()  + "<BR>\n" ;

/*
    Vector v = (new MyHomeList()).getUserHomeList("1000000376");
    s+= v.toString();

MetaData md = new MetaData () ;
md.setUserID("UID123");

request.getSession().setAttribute((String)"XXX", (MetaData)md);
*/

    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>DummyServlet</title></head>");
    out.println("<body>");
    out.println(s);
    out.println("</body></html>");
    out.close();


  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
     doGet (request, response) ;
  }
}