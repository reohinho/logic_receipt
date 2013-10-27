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


public abstract class AppEditWith2PagesServlet extends BaseServlet implements IEditWorkFlow
{

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    super.doGet(request, response);
    // Http get is not allowed as it require at least key for loading the data and object index for session referencing
  }

  public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    super.doPost(request, response);

//    HttpSession hs = request.getSession() ;
//    ServletShares svInst = ServletShares.getInstance() ;
//    String userId = getUserId(hs) ;
//    String fullPageId = getFormParameter(request, kPageId) ;
//    if (fullPageId==null || fullPageId.equals("")) {
//      int colonpos = getMenuNo ().indexOf(":") ;
//      fullPageId = (colonpos == -1) ? getMenuNo () : getMenuNo ().substring(0,colonpos) ;
//    }
//    
//    // Validate user privilege
//    if (!hasPrivilege(hs, getPrivilege(request), fullPageId)) {
//      Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, "NO_PRIVILEGE", "!<" + userId + "> " + getChildServletName() + " being called", svInst.getLogWebTraceStream()) ;
//      svInst.setErrorPage(request, response, "10330", "", kPageIdUserDefault, "User Main Page") ;
//      forwardTo(svInst.JSPIdError, request, response);
//      return ;
//    }
//    
    doAppEdit (request, response) ;

  }

  /** 
   * For ordinary "Edit" workflow, no need to override this method. 
   * If you are in special case, use this as reference for "Edit" process and then create your own doAppEdit method.
   */
  protected void doAppEdit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession hs = request.getSession() ;
    ServletShares svInst = ServletShares.getInstance() ;
    String userId = getUserId(hs) ;

    String inCmd = getCommand (request) ;

    // object index reference must exist and go back to edit main page 
    if (inCmd.equals(getEditBackCommand())) {  // back button in "Edit" confirmation page
      // same as kEdit action
      inCmd = getEditCommand() ;
    }else if (inCmd.equals(getEditBack2Command())) {  // back button in "Edit" page 2

      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      if (wfd!=null) { 
        wfd.setWithHttpRequest(request, 2);
        wfd = getWebFormRequestOnPage2( wfd, request) ;
      }           
      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      forwardTo(getPageIdForEditPage1(), request, response);  // goto add main page
      return ;
    
    }
    else if (inCmd.equals(getEditConfirmCommand())) { // ok button in "Edit" confirmation page
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // call backend for update action
      if (actionUpdate(wfd)) {
        // if action suceed
        Log.logApplicationStatus( "<" + userId + "> " + getUpdateSucceedMessage(wfd) , svInst.getLogUserActivityStream()) ;
        sessDataCtrlRemoveObjectByIndex(hs, objIdx);
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo(getPageIdForEditFinal(), request, response); // goto edit final page if no error or back to edit main page
      }
      else {
        // if action failure, then back to edit main page
        Log.logApplicationStatus( "!<" + userId + "> " + getUpdateFailureMessage(wfd) , svInst.getLogUserActivityStream()) ;
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo(getPageIdForEditPage1(), request, response); // goto edit first page if error occurs.
      }
      return ;
      
    }

    else if (inCmd.equals(getEditSaveCommand())) { // save button in "Edit" main page
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // When reference in session
      if (wfd!=null) {
        wfd.setWithHttpRequest(request);
        wfd = getWebFormRequest(wfd, request) ; 
        wfd.validate() ;
        if (validateRequestParams ((Object)wfd)) {
          sessDataCtrlSetObjectByIndex(hs, objIdx, (GenericWebFormData)wfd) ;
          request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
          forwardTo(getPageIdForEditPage2(), request, response); // goto edit page 2  
          return ;
        }
        else {  
          // if has error, back to edit main page
          inCmd = getEditCommand() ;
        }
        
      }
      else 
      {  
        // if edit object reference does not in the session, then assume it will create edit process by form submit key element
        inCmd = getEditCommand() ;
      }
    }else if (inCmd.equals(getEditSave2Command())) {
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // When reference in session
      if (wfd!=null) {
        wfd.setWithHttpRequest(request,2);
        wfd = getWebFormRequestOnPage2(wfd, request) ; 
        wfd.validate(2) ;
        if (validateRequestParamsOnPage2 ((Object)wfd)) {
          sessDataCtrlSetObjectByIndex(hs, objIdx, (GenericWebFormData)wfd) ;
          request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
          forwardTo(getPageIdForEditConfirm(), request, response); // goto edit confirmation page  
          return ;
        } else {
          sessDataCtrlSetObjectByIndex(hs, objIdx, (GenericWebFormData)wfd) ;
          request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
          forwardTo(this.getPageIdForEditPage2(), request, response); // goto edit confirmation page  
        }
      } 

    }
    // clear the obejct in session if available and close the window when onload
    else if (inCmd.equals(getEditDiscardCommand())) { // close button in "Edit" main page 
      String objIdx = getFormParameter(request, kObjectIndex) ;
      sessDataCtrlRemoveObjectByIndex(hs, objIdx);
      // client JSP should close the window automatically
    }

    else if (inCmd.equals(getReloadCommand())) 
    {
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // When reference in session
      if (wfd!=null) {
        wfd.setWithHttpRequest(request);
        wfd = getWebFormRequest(wfd, request) ; 
        sessDataCtrlSetObjectByIndex(hs, objIdx, (GenericWebFormData)wfd) ;
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo(getPageIdForEditPage1(), request, response); // goto edit confirmation page  
        return ;
      } else {
        // if edit object reference does not in the session, then assume it will create edit process by form submit key element
        inCmd = getEditCommand() ;
      }
    }




    // Create a new edit data object or load the one in session if index reference exists
    // CAUTION: Under this mechanism will result in whenever a edit button is pressed, a new GenericWebFormData object will be creation in session data control object. 
    //          It will better edit button does not spwan another window to do its process (ie. one way edit process and force submit exit command to servlet when finish/discard edit process. 
    //          Anyway, house keep action on session data control should perform regularly to remove long time no access object.
    if (inCmd.equals(getEditCommand())) { // "Edit" button from "View" detail

      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // if no such reference in session, need to create a new one
      if (wfd == null) {

        Object keyForLoadingObject = getFormRequest (request) ;
        wfd = actionLoadEditData (keyForLoadingObject) ;
      
        if (wfd.hasErr()) {
          // if action failure, log failure message
          Log.logApplicationStatus( "!<" + userId + "> Load data for edit ["+getPageIdForEditPage1()+"] failure." , svInst.getLogUserActivityStream()) ;
          Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, "GENERICEDIT", "<" + userId + "> Load data for view ["+getPageIdForEditPage1()+"] failure.", svInst.getLogWebTraceStream()) ;
          // when error occurs, error object will set in the GenericWebFormData object in acctionLoad method
        }
        String newObjectId = sessDataCtrlNewObject(hs, getMenuNo(), (GenericWebFormData)wfd ) ;
        wfd.setSystemObjectId(newObjectId);
        wfd.setSystemUserId(userId);
        sessDataCtrlSetObjectByIndex(hs, newObjectId, (GenericWebFormData)wfd) ;
      }
      
      // Session alread has GenericWebFormData object referenced
      //  then no action  

      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      
      forwardTo( getPageIdForEditPage1(), request, response);  // goto edit main page
    }

    return ;
  }


  // Default value setting, may override at child servlet for action similar to "Edit" process
  protected boolean getAcceptHttpGetMethod() { return false ; }

  /** Default "Edit" command for command page loading/initiating. */
  protected String getEditCommand () { return kEdit ; }
  
  /** Default "EditBack" command for "Back" button in confirmation page */
  protected String getEditBackCommand () { return kEditBack ; }

  /** Default "EditConfirm" command for "OK" button in confirmation page */
  protected String getEditConfirmCommand () { return kEditConfirm ; }
  
  /** Default "EditDiscard" command for "Close" button in edit main page */
  protected String getEditDiscardCommand () { return kEditDiscard ; }

  /** Default "EditSave" command for "Save" button in edit main page */
  protected String getEditSaveCommand () { return kEditSave ; }

  /** Default "Reload" command in edit main page */
  protected String getReloadCommand() { return kReload; } 

  /** Default "EditBack2" command in edit main page */
  protected String getEditBack2Command() { return kEditBack2; } 

  
  /** Default "EditSave2" command in edit main page */
  protected String getEditSave2Command() { return kEditSave2; } 
  
  /**
   * Bound the command from form submit with the input commands
   * @param request HttpServletRequest
   * @return valid command from form submit or a new "Edit" command from beginning
   */
  protected String getCommand (HttpServletRequest request) 
  {
    String inCmd = getFormParameter (request, kCommand) ;
    if ( inCmd.equals(getEditCommand()) || 
         inCmd.equals(getEditBackCommand()) || 
         inCmd.equals(getEditConfirmCommand()) || 
         inCmd.equals(getEditDiscardCommand()) || 
         inCmd.equals(getEditSaveCommand()) ||
         inCmd.equals(getReloadCommand()) ||
         inCmd.equals(getEditSave2Command()) ||
         inCmd.equals(getEditBack2Command()) )
      return inCmd ;
    return getEditCommand() ;
    
  }

  /**
   * Define the page scope of the child servlet
   */
  protected String[] getPageScope () 
  {
    String s[] = { getPageIdForEditPage1() , getPageIdForEditConfirm (), getPageIdForEditFinal() } ;
    return s;
  }

  // abstract method for child servlet to implement
  protected abstract String getPageIdForEditPage1 () ;
  protected abstract String getPageIdForEditPage2 () ;
  protected abstract String getPageIdForEditConfirm () ;
  protected abstract String getPageIdForEditFinal () ;
  protected abstract String getMenuNo () ;
  protected abstract String getUpdateSucceedMessage (GenericWebFormData wfd) ;
  protected abstract String getUpdateFailureMessage (GenericWebFormData wfd) ;
  protected abstract GenericWebFormData actionLoadEditData (Object keyObjectForLoad) ;
  protected abstract boolean actionUpdate (GenericWebFormData wfd) ;
  // read in the data, to be saved, from Httprequest
  protected abstract GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) ;
  protected abstract GenericWebFormData getWebFormRequestOnPage2 (GenericWebFormData form, HttpServletRequest request) ;
  // abstract methods from BaseServlet need to be implement in child Edit servlet
  protected abstract boolean validateRequestParamsOnPage2 (Object obj);  
  protected abstract boolean validateRequestParams (Object obj) ;
  protected abstract Object datamapViewToEntity (Object obj) throws CircException ;
  // read in key element for load edit data from backend from Httprequest
  protected abstract Object getFormRequest (HttpServletRequest request) ;
  protected abstract Object datamapEntityToView (Object obj) throws CircException ;
  
}