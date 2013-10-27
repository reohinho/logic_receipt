/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/generic/servlet/MasterServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:16 $
 * $Revision: 1.1.1.1 $
 */

package generic.servlet;

import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet for system base functions. 
 * This is for child application base servlet inheriting (eg. BaseServlet).
 */

public class MasterServlet extends HttpServlet 
{
  
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  /**
   * Process the HTTP doGet request.
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8") ;
    // override here (Suppose no GET method in this system.)
  }


  /**
   * Process the HTTP doPost request.
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8") ;
    // override here
   
  }

  /**
   * Obtain caller url
   */
  protected String getRefererUrl (HttpServletRequest request) {
    return request.getHeader("REFERER") ;
  }


// Session control begin

  protected void sessionSetAttribute (HttpSession hs, String key, Object obj) {
    hs.setAttribute(key, obj) ;
    Hashtable ht = (Hashtable)hs.getAttribute((String)"AttributeLastAccess") ;
    if (ht==null) ht = new Hashtable () ;
    ht.put((String)("AttributeLastAccess" + "_" + hs.getId() + "_" + key), (Date)Calendar.getInstance().getTime()) ;
    hs.setAttribute("AttributeLastAccess", (Hashtable)ht);
  }

  protected Object sessionGetAttribute (HttpSession hs, String key) {
    Object obj = hs.getAttribute(key) ;
    if (obj != null) {
      Hashtable ht = (Hashtable)hs.getAttribute((String)"AttributeLastAccess") ;
      if (ht==null) ht = new Hashtable () ;
      ht.put((String)("AttributeLastAccess" + "_" + hs.getId() + "_" + key), (Date)Calendar.getInstance().getTime()) ;
      hs.setAttribute("AttributeLastAccess", (Hashtable)ht);
    }
    return obj ;
  }

  protected void sessionRemoveAttribute (HttpSession hs, String key) {
    Object obj = hs.getAttribute(key) ;
    if (obj != null) {
      obj=null ;
      Hashtable ht = (Hashtable)hs.getAttribute((String)"AttributeLastAccess") ;
      if (ht==null) ht = new Hashtable () ;
      ht.remove((String)("AttributeLastAccess" + "_" + hs.getId() + "_" + key)) ;
      hs.setAttribute("AttributeLastAccess", (Hashtable)ht);
      hs.removeAttribute(key);
    }
  }

  protected void sessioCleanupAttribute (HttpSession hs, long time) {
    // Default 30 mins unused attributes will be clean up if called this method.
    
  }
 
// Session control end

}