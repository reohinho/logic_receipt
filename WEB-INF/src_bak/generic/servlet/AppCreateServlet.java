package generic.servlet;

import common.jsp.databean.GenericWebFormData;
import common.servlet.ServletShares;
import com.scmp.circ.utility.*;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * This servlet starts application level of generic workflow (Add function) driving.
 */
public abstract class AppCreateServlet extends BaseServlet implements ICreateWorkFlow
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
    if (fullPageId==null || fullPageId.equals("")) {
      int colonpos = getMenuNo ().indexOf(":") ;
      fullPageId = (colonpos == -1) ? getMenuNo () : getMenuNo ().substring(0,colonpos) ;
    }
    
    String inCmd = getCommand (request) ;

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
    if (inCmd.equals(kAddBack)) {  // back button in "Add" confirmation page
      // same as kAdd action
      inCmd = kAdd ;
//      forwardTo(svInst.getJSPbyId( getPageIdForAddMain() ), request, response); // goto add main page
    }
    
       else if (inCmd.equals(kAddConfirm)) { // ok button in "Add" confirmation page
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // call backend for add action
      if (actionAdd(wfd)) {
        // if action suceed
        Log.logApplicationStatus( "<" + userId + "> " + getCreateSucceedMessage(wfd) , svInst.getLogUserActivityStream()) ;        
        wfd.setMessage(this.getCreateSucceedMessage(wfd));
        sessDataCtrlRemoveObjectByIndex(hs, objIdx);
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo(getPageIdForAddFinal(), request, response); // goto add final page if no error or back to add main page
      }
      else {
        // if action failure, then back to add main page
        Log.logApplicationStatus( "!<" + userId + "> " + getCreateFailureMessage(wfd) , svInst.getLogUserActivityStream()) ;        
        wfd.setMessage(this.getCreateFailureMessage(wfd));
        sessDataCtrlRemoveObjectByIndex(hs, objIdx);
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo(getPageIdForAddMain(), request, response); // goto add final page if no error or back to add main page
      }
      return ;
      
    }

    // clear the obejct in session if available and close the window when onload
    else if (inCmd.equals(kAddDiscard)) { // close button in "Add" main page 
      String objIdx = getFormParameter(request, kObjectIndex) ;
      sessDataCtrlRemoveObjectByIndex(hs, objIdx);
      // client JSP should close the window automatically
      return ;
    }

    else if (inCmd.equals(kAddSave)) { // save button in "Add" main page
      String objIdx = getFormParameter(request, kObjectIndex) ;

      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      if (wfd==null) {
        wfd = createAddForm(request) ; 
        String newObjectId = sessDataCtrlNewObject(hs, getMenuNo(), (GenericWebFormData)wfd ) ;
        wfd.setSystemObjectId(newObjectId);
        wfd.setSystemUserId(userId);
      }
      wfd.setWithHttpRequest(request);
      wfd = getWebFormRequest( wfd, request) ;

      boolean formIsValid = wfd.validate() ;
      
      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      if ( (formIsValid == true) && validateRequestParams ( (Object) wfd ) ) {
        forwardTo(getPageIdForAddConfirm(), request, response); // goto add confirmation page  
      }
      else {
        Log.logApplicationStatus( "!<" + userId + "> Cannot not passed object validation for the input values.", svInst.getLogUserActivityStream()) ;
        forwardTo(getPageIdForAddMain(), request, response); // goto add main page when not passed validation on object level
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
      forwardTo(getPageIdForAddMain(), request, response); // goto add confirmation page  
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
      forwardTo(getPageIdForAddMain(), request, response);  // goto add main page
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
         inCmd.equals(kAddConfirm) || 
         inCmd.equals(kAddDiscard) || 
         inCmd.equals(kAddSave) || 
         inCmd.equals(kReload1))
      return inCmd ;
    return kAdd ;
    
  }

  /**
   * Define the page scope of the child servlet
   */
  protected String[] getPageScope () 
  {
    String s[] = { getPageIdForAddConfirm (), getPageIdForAddFinal() } ;
    return s;
  }

  /**
   * Page ids are default vaild for page scope validation under a servlet
   */
  protected String[] getCallerJSPDefaultPageScopeAddition () 
  {
    String s[] = { getPageIdForAddMain() } ;
    return s ;
  }


  // abstract method for child servlet to implement
  protected abstract String getPageIdForAddMain () ;
  protected abstract String getPageIdForAddConfirm () ;
  protected abstract String getPageIdForAddFinal () ;
  protected abstract String getMenuNo () ;
  protected abstract String getCreateSucceedMessage (GenericWebFormData wfd) ;
  protected abstract String getCreateFailureMessage (GenericWebFormData wfd) ;
  
  protected abstract GenericWebFormData createAddForm (HttpServletRequest request) ;
  protected abstract boolean actionAdd (GenericWebFormData wfd ) ;
  protected abstract GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) ;
  // abstract methods from BaseServlet need to be implement in child Create servlet
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected abstract Object datamapViewToEntity (Object obj) throws CircException ;
  
  // obsolate parent abstract methods in BaseServlet (no need on general "create" process).
  protected Object getFormRequest (HttpServletRequest request) {return null;}
  protected Object datamapEntityToView (Object obj) throws CircException {return null;}
  

  
}