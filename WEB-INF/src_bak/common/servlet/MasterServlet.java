/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/servlet/MasterServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */

package common.servlet;

import com.scmp.circ.utility.CircException;

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
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/servlet/MasterServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
public abstract class

MasterServlet extends HttpServlet 
{
//  private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

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

    // Validate user login
    // Validate user privilege
    // Validate caller page id
    // Determine initial step    
    // Read form parameter
    // Validate form parameter when necessary
    // Process according to command
    // Flow control: forward to next page, back, another servlet etc
    
  }


// Default functions

//  /**
//   * check if user login
//   */
//  protected boolean hasLogin (HttpSession hs) {
//    return hs.getAttribute((String)"METADATA") != null ;
//  }

  /**
   * Check if invkoe from valid caller page
   */
  protected boolean isValidCaller (String validPageIdList[], String callerPageId) {
    if (callerPageId==null) return false ;
    for (int i = 0 ; i < validPageIdList.length ; i++)
      if (validPageIdList[i].equals(callerPageId)) return true ;
    return false ;
  }

//  /**
//   * Get user id from session
//   */
//  protected String getUserId  (HttpSession hs) {
//    if (hs==null) return null ;
//    MetaData md = (MetaData)hs.getAttribute((String)"METADATA") ;
//    if (md==null) return null ;
//    return md.getUserID()  ;
//  }

  protected String windowCloseString() {
    return "<HTML><BODY onload=\"window.close();\"></body></HTML>" ;
  }


  /**
   * Get user data from session. Session contains "USERDATA" attribute as Hashtable. Inside the Hashtable, it contains key=userDataName and value is Object it corresponds
   * 
   */
  protected Object getUserData  (HttpSession hs, String userDataName) 
  {
    // "USERDATA" is set in seesion when user logins. 
    return (Object) ((java.util.Hashtable)hs.getAttribute((String)"USERDATA")).get ( (String) userDataName ) ;
  }

  /**
   * Set user data to session. Session contains "USERDATA" attribute as Hashtable. Inside the Hashtable, it contains key=userDataName and value is Object it corresponds.
   * 
   */
  protected void setUserData  (HttpSession hs, String userDataName, Object obj) 
  {
    // "USERDATA" is set in seesion when user logins. 
    java.util.Hashtable ht = (java.util.Hashtable)hs.getAttribute((String)"USERDATA") ;
    ht.put ((String)userDataName, (Object)obj) ;
    hs.setAttribute((String)"USERDATA", (java.util.Hashtable)ht);
  }

  protected String getFormParameter (HttpServletRequest request, String paramName) {
    String s = request.getParameter(paramName) ;
    return (s==null)?"": s ;
  }

// User implement functions


  /**
   * Read all form data and maybe do simple validation.
   */
  protected abstract Object getFormRequest (HttpServletRequest request) ;


  /**
   * Simple validate form submitted information.
   */
  protected abstract boolean validateRequestParams (Object obj) ;

  /**
   * Workflow for the servlet.
   */
  protected abstract Object process (Object obj) throws CircException ;

  /**
   * JSP data object maps to entity recognized object.
   */
  protected abstract Object datamapViewToEntity (Object obj) throws CircException ;


  /**
   * Entity data object maps to JSP recognized object.
   */
  protected abstract Object datamapEntityToView (Object obj) throws CircException ;

//  /**
//   * Check if user has privilege on this function
//   */
//  protected abstract boolean hasPrivilege (HttpSession hs) ;

  // BEGIN: Lite access control procedure on session object
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
  // END: Lite access control procedure on session object

}