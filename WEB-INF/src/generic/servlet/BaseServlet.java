package generic.servlet;

import common.jsp.databean.GenericWebFormData;
import common.servlet.ServletShares;
//import security.ejb.object.UserVO;
import com.scmp.circ.utility.*;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class BaseServlet extends MasterServlet implements IBaseConstants
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
    super.doGet (request, response) ;

   if (getAcceptHttpGetMethod() == true)
      doPost(request, response) ;
    else {
      ServletShares svInst = ServletShares.getInstance() ;
      String userId = getUserId(request.getSession()) ;
      Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, "INVALID SERVLET ACCESS (GET)", "!<" + userId + "> " + getChildServletName() + " being called.", svInst.getLogWebTraceStream()) ;
      svInst.setErrorPage(request, response, "10360", "", kPageIdUserDefault, "Main Menu") ;
      forwardTo(svInst.getForwardErrorJSP(), request, response);
      return;
    }

    // override here (Suppose no GET method, except create function, in this system.)
  }


  /**
   * Process the HTTP doPost request.
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    super.doPost(request, response);

//    ServletShares svInst = ServletShares.getInstance() ;
//
//    HttpSession hs = request.getSession() ;
//
//    // Validate user login
//    if (!hasLogin(hs)) {
//      String invalidCaller = request.getRemoteAddr() + "," + request.getRemoteHost() + "," + request.getRequestURL() + "," + request.getQueryString();
//      Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, "NO_LOGIN", getChildServletName ()  + " being called: " + invalidCaller, svInst.getLogWebTraceStream()) ;
//      svInst.setErrorPage(request, response, "10320", "User session Expired. Please login again.", kPageIdLogin, "Login Page") ;
//      response.sendRedirect(svInst.getRdirectLoginJSP());  
//      return ;
//    }

      
  }

  
//  // Default functions for application level

//  
//  // General methods
//  /**
//   * check if user login
//   * @param hs HttpSession
//   */
//  protected boolean hasLogin (HttpSession hs) {
//    return hs.getAttribute((String)kUserMetaDataSessionId) != null ;
//  }

  /**
   * Check if invoke from valid caller page (ie. check if invoke within the action scope)
   * @param scopeOfValidPages Pages are valid under the child servlet
   * @param callerPageId full page id from form submit
   */
  protected boolean isValidCaller (String scopeOfValidPages[], String callerPageId) {
    ServletShares svInst = ServletShares.getInstance() ;
    if (callerPageId==null || scopeOfValidPages==null) return false ;
    // allow valid caller from default main page / user home page
    for (int i = 0 ; i < scopeOfValidPages.length ; i++)
      if (scopeOfValidPages[i].equals(callerPageId)) return true ;
    return false ;
  }

  /**
   * Check if invoke from valid caller page by given JSP URL (ie. check if invoke within the default valid page scope)
   * @param jspUrl url of a jsp from HttpServletRequest
   */
  protected boolean isValidCallerByJSP (String jspUrl) {
  if (jspUrl==null) return false ;
    String scopeOfDefaultPages[] = getCallerJSPDefaultPageScope () ;
    String callerJSPPageId = ServletShares.getInstance().getMenuNobyJspUrl (jspUrl) ;
    for (int j = 0 ; j < scopeOfDefaultPages.length ; j++)
      if ( callerJSPPageId.equals (scopeOfDefaultPages[j]) )
        return true ;
    return false ;    
  }

  /**
   * Get user id from given HttpSession 
   * @param hs HttpSession
   */
  protected String getUserId  (HttpSession hs) {
    //return ((UserVO)hs.getAttribute("UserVO")).getUserid()  ;
	  return "system";
  }

  /**
   * Obtain HTML string for window close immediate after returned fromthe outputstream
   */
  protected String windowCloseString() {
    return "<HTML><BODY onload=\"window.close();\"></body></HTML>" ;
  }

  /**
   * Obtain form parameter from front-end and return empty string when the request name not found too.
   * @param request HttpServletRequest
   * @param paramName String name of the form parameter
   */
  protected String getFormParameter (HttpServletRequest request, String paramName) {
    String s = request.getParameter(paramName) ;
    return (s==null)?"": s ;
  }

  /** Generic fucntion to foward HttpRequest to another JSP/servlet, list in menu table, by given:
   * @param pageName Id of a menu
   * @param pageId sequence of a menu under the specific Id
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   */
  public void forwardTo (String pageName, int pageId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    getServletContext().getRequestDispatcher(ServletShares.getInstance().getJSPbyId(pageName,pageId)).forward(request, response);
  }

  /** Generic fucntion to foward HttpRequest to another JSP/servlet, list in menu table, by given:
   * @param pageSet Full menu id (eg. "XXXX,0")
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   */
  public void forwardTo (String pageSet, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    getServletContext().getRequestDispatcher(ServletShares.getInstance().getJSPbyId(pageSet)).forward(request, response);
  }

  /** Obtain request on WebFormData object
   * @param form GenericWebFormData
   * @param request HttpServletRequest
   */

  protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
  { 
    form.setWithHttpRequest(request);
    return form ;
  }



  protected String getChildServletName () 
  {
    String childServletName = this.getClass().getName() ;
    return (childServletName.lastIndexOf('.')!= -1) ?
        childServletName.substring ( childServletName.lastIndexOf('.') + 1 ) : childServletName ;
  }

// Begin: Primary session object control for this application
// Most object require the following method for manipulating on session. 
// A unique index will be returned for referencing the object.

  /**
   * Reference for General data control object in user HttpSession
   */
  protected static String kMasterDataControl = "MasterDataControl" ; // reserved HttpSession id (keyword)

  /**
   * Deposit an object in session data control object
   * @param hs User HttpSession
   * @param refGroupName Grouping for similar object
   * @param obj user object to be deposit in session data control object
   * @return an unique id for this object
   */
  protected String sessDataCtrlNewObject (HttpSession hs, String refGroupName, Object obj) {
    SessionDataControl sdc = (SessionDataControl) sessionGetAttribute(hs, kMasterDataControl) ;
    if (sdc==null) 
      sdc = new SessionDataControl () ;
    int newObjIdx = sdc.newObject(refGroupName, (Object)obj) ; 
    sessionSetAttribute(hs, kMasterDataControl, (SessionDataControl)sdc);
    return ""+newObjIdx ;
  }

  /**
   * Obtain an existing object in session data control object by given unique index 
   * @param hs User HttpSession
   * @param idx Object on this unique index 
   * @return user object at the unique index referenced
   */
  protected Object sessDataCtrlGetObjectByIndex (HttpSession hs, String idx) {
    SessionDataControl sdc = (SessionDataControl) sessionGetAttribute(hs, kMasterDataControl) ;
    if (sdc==null) {
      sdc = new SessionDataControl () ;
      return null ;
    }
    return sdc.getObjectByIndex(idx) ;
  }

  /**
   * Replace an existing object in session data control object by given unique index 
   * @param hs User HttpSession
   * @param idx Object on this unique index to be replaced
   * @param obj User object to be replace
   * @return true if successful set the object (false maybe no such object in index)
   */
  protected boolean sessDataCtrlSetObjectByIndex (HttpSession hs, String idx, Object obj) {
    SessionDataControl sdc = (SessionDataControl) sessionGetAttribute(hs, kMasterDataControl) ;
    if (sdc==null) {
      sdc = new SessionDataControl () ;
      return false ;
    }
    if (sessDataCtrlGetObjectByIndex (hs, idx) == null) 
      return false ;
    sdc.setObjectToExistingIndex(obj, idx) ;
    return true ;
  }

  /**
   * Remove the existing object from session data control object by given index
   * @param hs User HttpSession
   * @param idx Object on this unique index to be removed
   */
  protected void sessDataCtrlRemoveObjectByIndex (HttpSession hs, String idx) {
    SessionDataControl sdc = (SessionDataControl) sessionGetAttribute(hs, kMasterDataControl) ;
    if (sdc!=null) {
      sdc.removeObjectFromExistingIndex(idx);
    }
  }
// End: Primary session object control for this application



// abstract method to be implement in child servlet

  /**
   * Simple validate form submitted information.
   */
  /**
   * Usually no need to validate the search criteriawith backend process.
   * However, frontend object level validation on search criteria fields may be required from GenericWebFormData validation methods.
   */
  protected boolean validateRequestParams (Object obj) { 
    GenericWebFormData wfd = (GenericWebFormData) obj ;
    return wfd.isValid() ;
  }

  /** 
   * Brenchmark for each step's processing timestamp.
   */
  protected boolean requireBenchmark () { return false ; }
  
  /**
   * JSP data object maps to entity recognized object.
   */
  protected abstract Object datamapViewToEntity (Object obj) throws CircException ;

  /**
   * Entity data object maps to JSP recognized object.
   */
  protected abstract Object datamapEntityToView (Object obj) throws CircException ;

  /**
   * Check if user has privilege on this function
   */
//  protected abstract boolean hasPrivilege (HttpSession hs) ;

  /**
   * Set the scope of pages at the child servlet
   */
  protected abstract String[] getPageScope () ;
  
  /**
   * List of pages which are default valid to any page. (Here we set user home and main menu pae id.)
   * Override this method when user wants his own set of page id.
   * If addition to the default 2, use method getCallerJSPDefaultPageScopeAddition.
   */
  protected String[] getCallerJSPDefaultPageScope () { 
    ServletShares svInst = ServletShares.getInstance() ;
    String sAdditionDefaultMenuNo[] = getCallerJSPDefaultPageScopeAddition () ;
    if (sAdditionDefaultMenuNo==null) sAdditionDefaultMenuNo = new String[0] ;
    String sPageScope[] = new String [sAdditionDefaultMenuNo.length + 2] ;
    for (int i = 0 ; i < sAdditionDefaultMenuNo.length ; i++) {
      sPageScope[i] = sAdditionDefaultMenuNo[i] ;
    }
    sPageScope[ sAdditionDefaultMenuNo.length ] = svInst.JSPIdMainMenu ;
    sPageScope[ sAdditionDefaultMenuNo.length + 1 ] = svInst.JSPIdUserHome  ;
    return sPageScope ;
  }
  /**
   * Addition page id scope is default valid against JSP URL checking.
   * Override this method when user suggests more page ids are default vaild for page scope validation under a servlet
   */
  protected String[] getCallerJSPDefaultPageScopeAddition () { return new String[0] ; }



  /**
   * Set the Http Get of pages at the child servlet
   */
  protected abstract boolean getAcceptHttpGetMethod () ; 




}