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
 * This servlet for View/Delete actions.
 * No session reference is required here as everytime need up-to-date data.
 */
public abstract class AppViewDeleteServlet extends BaseServlet implements IViewDeleteWorkFlow
{
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    // Http get is not allowed as it require at least key for loading the data and object index for session referencing
  }

  public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    super.doPost(request, response);
//
//    HttpSession hs = request.getSession() ;
//    ServletShares svInst = ServletShares.getInstance() ;
//    String userId = getUserId(hs) ;
//    String fullPageId = getFormParameter(request, kPageId) ;
//    if (fullPageId==null || fullPageId.equals("")) {
//      int colonpos = getMenuNo ().indexOf(":") ;
//      fullPageId = (colonpos == -1) ? getMenuNo () : getMenuNo ().substring(0,colonpos) ;
//    }
//
//    String inCmd = getCommand (request) ; 
//
//    // no caller validation check when invoke the view main page



    doAppAction (request, response) ;
  }

  /** 
   * For ordinary "Add" workflow, no need to override this method. 
   * If you are in special case, use this as reference for "Add" process and then create your own doAppCreate method.
   */
  protected void doAppAction (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession hs = request.getSession() ;
    ServletShares svInst = ServletShares.getInstance() ;
    String userId = getUserId(hs) ;

      String inCmd = getCommand (request) ; 

    // kView -> command to "View" detail by keys or 
    // kDelete -> "Delete" button click in "View" detail page
    // kDeleteBack -> "Back" button click in "Delete" confirmation page 
    if ( inCmd.equals(kView) || inCmd.equals(kDeleteBack) || 
         inCmd.equals(kDelete)) {  
      Object keyForLoadingObject = getFormRequest (request) ;

      GenericWebFormData wfd = actionLoad (keyForLoadingObject) ;
      wfd.setSystemUserId(userId);
      
      if (wfd.hasErr()) {
        // if action failure, log failure message
        String errorAtPage =  inCmd.equals(kDelete) ? getPageIdForViewMain () : 
                              inCmd.equals(kDeleteBack) ? getPageIdForDeleteConfirm() :
                              getPageIdForViewMain() ;
        Log.logApplicationStatus( "!<" + userId + "> Load data for view ["+errorAtPage+"] failure." , svInst.getLogUserActivityStream()) ;
        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, "GENERICVIEW", "<" + userId + "> Load data for view ["+errorAtPage+"] failure.", svInst.getLogWebTraceStream()) ;
        // when error occurs, error object will set in the GenericWebFormData object in acctionLoad method
      }


      if (inCmd.equals(kView) || inCmd.equals(kDeleteBack) || (wfd.hasErr())) {
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo( getPageIdForViewMain() , request, response); // goto "View" detail page
        return ;
      }
      else {
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo( getPageIdForDeleteConfirm() , request, response); // goto "Delete" confirmation page
        return ;
      }
      
    }

    // kViewDiscard -> case when "Close" button click in "View" detail page
    else if (inCmd.equals(kViewDiscard)) {  
      // clean up object index when available
      String objIdx = (String)getFormParameter(request, kObjectIndex) ;
      if (objIdx != null && !objIdx.equals("")) 
         sessDataCtrlRemoveObjectByIndex(hs, objIdx);
      return ;
    }


    // kDeleteConfirm -> case when "OK" button click in "Delete" confirmation page
    else if (inCmd.equals(kDeleteConfirm)) {  
      Object keyForLoadingObject = getFormRequest (request) ;
      GenericWebFormData wfd = actionLoad (keyForLoadingObject) ;
      wfd.setSystemUserId(userId);
      if (wfd.hasErr()) {
        // if action failure, log failure message
        Log.logApplicationStatus( "!<" + userId + "> " + getDeleteFailureMessage(wfd) , svInst.getLogUserActivityStream()) ;
        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, "GENERICDELETE", "<" + userId + "> Load data for view ["+getPageIdForViewMain ()+"] failure.", svInst.getLogWebTraceStream()) ;
        // when error occurs, error object will set in the GenericWebFormData object in acctionLoad method
      }
      else {
        if ( !actionDelete( wfd ) ) {
          // if delete failure, log failure message
          wfd.setMessage(getDeleteFailureMessage(wfd));    
          Log.logApplicationStatus( "!<" + userId + "> " + getDeleteFailureMessage(wfd) , svInst.getLogUserActivityStream()) ;
          Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, "GENERICDELETE", "<" + userId + "> Load data for view ["+getPageIdForViewMain ()+"] failure.", svInst.getLogWebTraceStream()) ;
          // when error occurs, error object will set in the GenericWebFormData object in acctionLoad method
        }
        else {
          // Log delete suceed
          wfd.setMessage(getDeleteSucceedMessage(wfd));    
          Log.logApplicationStatus( "<" + userId + "> " + getDeleteSucceedMessage(wfd) , svInst.getLogUserActivityStream()) ;
        }
      }

      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd) ;
      forwardTo( getPageIdForDeleteFinal(), request, response); // goto "View" detail page
      return ;
    }
    
  }

  /**
   * Bound the command from form submit with the input commands
   * @param request HttpServletRequest
   * @return command from form submit for "View" / "Delete" actions
   */
  protected String getCommand (HttpServletRequest request) 
  {
    String inCmd = getFormParameter (request, kCommand) ;
    if ( inCmd.equals(kView) || 
         inCmd.equals(kViewDiscard) || 
         inCmd.equals(kDelete) || 
         inCmd.equals(kDeleteBack) || 
         inCmd.equals(kDeleteConfirm) )
      return inCmd ;
    return "" ;
    
  }

  /**
   * Define the page scope of the child servlet
   */
  protected String[] getPageScope () 
  {
    // Understood that the array sequence:
    // [1] -> View Main page id
    // [2] -> Delete confirmation page id
    // [3] -> Delete final page id
    String s[] = { getPageIdForViewMain() , 
                   getPageIdForDeleteConfirm (), 
                   getPageIdForDeleteFinal() } ;
    return s;
  }


  // abstract method for child servlet to implement
  protected abstract String getPageIdForViewMain () ;
  protected abstract String getPageIdForDeleteConfirm () ;
  protected abstract String getPageIdForDeleteFinal () ;
  protected abstract String getMenuNo () ;
  protected abstract String getDeleteSucceedMessage (GenericWebFormData wfd) ;
  protected abstract String getDeleteFailureMessage (GenericWebFormData wfd) ;
  // Delete data by given key object
  protected abstract boolean actionDelete (GenericWebFormData wfd ) ;
  // Load data by given key object
  protected abstract GenericWebFormData actionLoad (Object keyObjectForLoad ) ;
  protected abstract GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) ;
  
  // abstract methods from BaseServlet need to be implement in child Create servlet
  // return key object for loading data from backend
  protected abstract Object getFormRequest (HttpServletRequest request) ;
  // validate key object format when necessary
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected abstract Object datamapViewToEntity (Object obj) throws CircException ;
  
  // obsolate parent abstract methods in BaseServlet (no need on general "create" process).
  protected Object datamapEntityToView (Object obj) throws CircException {return null;}
  
}


