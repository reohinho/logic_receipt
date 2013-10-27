package generic.servlet;

import common.jsp.databean.GenericWebFormData;
import common.servlet.ServletShares;
import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.Log;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This servlet starts application level of generic workflow (Add function - 2 input pages + 1 confirm page + 1 result page) driving.
 */
public abstract class AppCreateWith3PagesServlet extends BaseServlet implements ICreateWorkFlow
{

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    // direct invoke (or no command) to this servlet means start a new "Add" request
    request.removeAttribute(IBaseConstants.kObjectIndex);
    doPost (request, response) ;
  }

  public final void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    super.doPost(request, response);

    HttpSession hs = request.getSession() ;
    ServletShares svInst = ServletShares.getInstance() ;
    String userId = getUserId(hs) ;
    String fullPageId = getFormParameter(request, kPageId) ;

    String inCmd = getCommand (request) ;

//    // no caller validation check when invoke the add main page
//    if (!inCmd.equals(kAddBack) && !inCmd.equals(kAdd)) {  
//      // Validate user privilege
//      if (!hasPrivilege(hs, AccessControl.ACCESS_ADD, fullPageId)) {
//        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, "NO_PRIVILEGE", "!<" + userId + "> " + getChildServletName() + " being called on " + new java.util.Date() , svInst.getLogWebTraceStream()) ;
//        svInst.setErrorPage(request, response, "10330", "", kPageIdUserDefault, "User Main Page") ;
//        forwardTo(svInst.JSPIdError, request, response);
//        return ;
//      }
//    }

    doAppCreate (request, response) ;
  }

  /** 
   * For ordinary "Add" workflow, no need to override this method. 
   * If you are in special case, use this as reference for "Add" process and then create your own doAppCreate method.
   */
  protected void doAppCreate (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession hs = request.getSession() ;
    ServletShares svInst = ServletShares.getInstance() ;
    String userId = getUserId(hs) ;

    String inCmd = getCommand (request) ;

    // object index reference must exist and go back to add main page 
    if (inCmd.equals(kAddBack)) {  // back button in 2nd page
      // same as kAdd action, but first preserve data in 2nd page
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      if (wfd!=null) { 
        wfd.setWithHttpRequest(request, 2);
        wfd = getWebFormRequestOnPage2( wfd, request) ;
      }
      inCmd = kAdd ;
//      forwardTo(svInst.getJSPbyId( getPageIdForAddMain() ), request, response); // goto add main page
    }
    else if (inCmd.equals(kAddBack2)) // back to page 2, i.e. back button on confirm page
    {
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      forwardTo(getPageIdForAddPage2(), request, response);         
      return ;
    }
    else if (inCmd.equals(kAddPage2))  // go to page 2, i.e. next button in input page 1
    {
      String objIdx = getFormParameter(request, kObjectIndex) ;

      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      if (wfd==null) { // if webformdata not found, sytem should abort operation
        wfd = createAddForm(request) ; 
        String newObjectId = sessDataCtrlNewObject(hs, getMenuNo(), (GenericWebFormData)wfd ) ;
        wfd.setSystemObjectId(newObjectId);
        wfd.setSystemUserId(userId);
      }
      wfd.setWithHttpRequest(request);
      wfd = getWebFormRequest( wfd, request) ;

      boolean formIsValid = wfd.validate(1) ; // try to validate all data on page 1
      
      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      if ( (formIsValid == true) && validateRequestParams ( (Object)wfd ) ) {
        forwardTo(getPageIdForAddPage2(), request, response); // goto add page 3
      }
      else {
        Log.logApplicationStatus( "!<" + userId + "> Cannot not passed object validation for the input values.", svInst.getLogUserActivityStream()) ;
        forwardTo(getPageIdForAddPage1(), request, response); // goto add main page when not passed validation on object level
      }
      return ;      
    }
    else if (inCmd.equals(kAddConfirm)) { // ok button in "Add" confirmation page
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // call backend for add action
      if (actionAdd(wfd)) {
        // if action suceed
        Log.logApplicationStatus( "<" + userId + "> " + getCreateSucceedMessage(wfd) , svInst.getLogUserActivityStream()) ;        
        sessDataCtrlRemoveObjectByIndex(hs, objIdx);
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo(getPageIdForAddFinal(), request, response); // goto add final page if no error or back to add main page
      }
      else {
        // if action failure, then back to add main page
        Log.logApplicationStatus( "!<" + userId + "> " + getCreateFailureMessage(wfd) , svInst.getLogUserActivityStream()) ;        
        sessDataCtrlRemoveObjectByIndex(hs, objIdx);
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo(getPageIdForAddPage1(), request, response); // goto add final page if no error or back to add main page
      }
      return ;
      
    }
    else if (inCmd.equals(kAddPage3)) 
    { // go to page 3, i.e. add button in page 2

      String objIdx = getFormParameter(request, kObjectIndex) ;

      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      if (wfd==null) {
        wfd = createAddForm(request) ; 
        String newObjectId = sessDataCtrlNewObject(hs, getMenuNo(), (GenericWebFormData)wfd ) ;
        wfd.setSystemObjectId(newObjectId);
        wfd.setSystemUserId(userId);
      }
      wfd.setWithHttpRequest(request, 2);
      wfd = getWebFormRequestOnPage2( wfd, request) ;
      boolean formIsValid = wfd.validate(2) ;
      
      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      if ( (formIsValid == true) && validateRequestParamsOnPage2 ( wfd ) ) {
        forwardTo(getPageIdForAddPage3(wfd), request, response); // goto add confirmation page  
      }
      else {
        Log.logApplicationStatus( "!<" + userId + "> Cannot not passed object validation (page 2) for the input values.", svInst.getLogUserActivityStream()) ;
        forwardTo(getPageIdForAddPage2(), request, response); // goto add page 2 when not passed validation on object level
      }
      return ;
    }
    else if (inCmd.equals(kAddBack3)) 
    { // back button in page 3
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      forwardTo(getPageIdForBackPage3(wfd), request, response);         
      return ;
    }
    // clear the obejct in session if available and close the window when onload
    else if (inCmd.equals(kAddDiscard)) { // close button in "Add" main page 
      String objIdx = getFormParameter(request, kObjectIndex) ;
      sessDataCtrlRemoveObjectByIndex(hs, objIdx);
      // client JSP should close the window automatically
      return ;
    }

    else if (inCmd.equals(kAddSave)) { // save button in "Add" page 3
      String objIdx = getFormParameter(request, kObjectIndex) ;

      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      if (wfd==null) {
        wfd = createAddForm(request) ; 
        String newObjectId = sessDataCtrlNewObject(hs, getMenuNo(), (GenericWebFormData)wfd ) ;
        wfd.setSystemObjectId(newObjectId);
        wfd.setSystemUserId(userId);
      }
      wfd.setWithHttpRequest(request);
      wfd = getWebFormRequestOnPage3( wfd, request) ;
      boolean formIsValid = wfd.validate() ;
      
      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      if ( (formIsValid == true) && validateRequestParamsOnPage3 ( wfd ) ) {
        forwardTo(getPageIdForAddConfirm(), request, response); // goto add confirmation page  
      }
      else {
        Log.logApplicationStatus( "!<" + userId + "> Cannot not passed object validation (page 3) for the input values.", svInst.getLogUserActivityStream()) ;
        forwardTo(getPageIdForAddPage3(wfd), request, response); // goto add page 2 when not passed validation on object level
      }
      return ;
    }
    else if (inCmd.equals(kReload1)) 
    {
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      if (wfd==null) { // if webformdata not found, sytem should abort operation
        wfd = createAddForm(request) ; 
        String newObjectId = sessDataCtrlNewObject(hs, getMenuNo(), (GenericWebFormData)wfd ) ;
        wfd.setSystemObjectId(newObjectId);
        wfd.setSystemUserId(userId);
        sessDataCtrlSetObjectByIndex(hs, newObjectId, (GenericWebFormData)wfd) ;
      }
      wfd.setWithHttpRequest(request);
      wfd = getWebFormRequest( wfd, request) ;

      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      forwardTo(getPageIdForAddPage1(), request, response); // goto add confirmation page  
      return ;      
    }



    // create a new add data object or load the one in session if index reference exists
    if (inCmd.equals(kAdd)) { // "Add" button from everywhere

      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // if no such reference in session, need to create a new one
      if (wfd == null) {
        wfd = createAddForm(request) ; 
        String newObjectId = sessDataCtrlNewObject(hs, getMenuNo(), (GenericWebFormData)wfd ) ;
        wfd.setSystemObjectId(newObjectId);
        wfd.setSystemUserId(userId);
        sessDataCtrlSetObjectByIndex(hs, newObjectId, (GenericWebFormData)wfd) ;
      }

      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      forwardTo(getPageIdForAddPage1(), request, response);  // goto add main page
      return ;
    }

  }

  /**
   * Bound the command from form submit with the input commands
   * @param request HttpServletRequest
   * @return valid command from form submit or a new "Add" command from beginning
   */
  protected String getCommand (HttpServletRequest request) 
  {
    String inCmd = getFormParameter (request, kCommand) ;
    if ( inCmd.equals(kAdd) || 
         inCmd.equals(kAddBack) || 
         inCmd.equals(kAddPage2) || 
         inCmd.equals(kAddBack2) || 
         inCmd.equals(kAddConfirm) || 
         inCmd.equals(kAddDiscard) || 
         inCmd.equals(kAddSave) || 
         inCmd.equals(kReload1) ||
         inCmd.equals(kAddPage3) ||
         inCmd.equals(kAddBack3))
      return inCmd ;
    return kAdd ;
    
  }

  /**
   * Define the page scope of the child servlet
   */
  protected String[] getPageScope () 
  {
    
    String s[] = { getPageIdForAddConfirm (), getPageIdForAddFinal(), getPageIdForAddPage2() } ;
    return s;
  }

  /**
   * Page ids are default vaild for page scope validation under a servlet
   */
  protected String[] getCallerJSPDefaultPageScopeAddition () 
  { 
    String s[] = { getPageIdForAddPage1() } ;
    return s ;
  }

  // abstract method for child servlet to implement
  protected abstract String getPageIdForAddPage1 () ;
  protected abstract String getPageIdForAddPage2 () ;
  // 14-09-2005 added by Wisley Lui for develop individual payment
  protected abstract String getPageIdForAddPage3 (GenericWebFormData form);
  protected abstract String getPageIdForBackPage3 (GenericWebFormData form);
  protected abstract GenericWebFormData getWebFormRequestOnPage3 (GenericWebFormData form, HttpServletRequest request) ;
  protected abstract boolean validateRequestParamsOnPage3 (GenericWebFormData wfd) ;
  // end added by Wisley Lui
  protected abstract String getPageIdForAddConfirm () ;
  protected abstract String getPageIdForAddFinal () ;
  protected abstract String getMenuNo () ;
  protected abstract String getCreateSucceedMessage (GenericWebFormData wfd) ;
  protected abstract String getCreateFailureMessage (GenericWebFormData wfd) ;
  protected abstract boolean validateRequestParams (GenericWebFormData wfd) ;
  protected abstract boolean validateRequestParamsOnPage2 (GenericWebFormData wfd) ;

  protected abstract GenericWebFormData createAddForm (HttpServletRequest request) ;
  protected abstract boolean actionAdd (GenericWebFormData wfd ) ;
  protected abstract GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) ;
  protected abstract GenericWebFormData getWebFormRequestOnPage2 (GenericWebFormData form, HttpServletRequest request) ;
  // abstract methods from BaseServlet need to be implement in child Create servlet
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected abstract Object datamapViewToEntity (Object obj) throws CircException ;
  
  // obsolate parent abstract methods in BaseServlet (no need on general "create" process).
  protected Object getFormRequest (HttpServletRequest request) {return null;}
  protected Object datamapEntityToView (Object obj) throws CircException {return null;}
  

  
}