/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/generic/servlet/AppSearchServlet.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:16 $
  $Revision: 1.1.1.1 $
*/
package generic.servlet;

import common.jsp.databean.GenericSearchFormData;
import common.servlet.ServletShares;
import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.Log;

import java.io.IOException;

import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public abstract class AppSearchServlet extends BaseServlet implements ISearchWorkFlow
{

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
      super.doPost(request, response);

    HttpSession hs = request.getSession() ;
    ServletShares svInst = ServletShares.getInstance() ;
    String userId = getUserId(hs) ;
//    String fullPageId = getFormParameter(request, kPageId) ;
    if (requireBenchmark()) 
      Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doPost 1", svInst.getLogWebTraceStream()) ;
      
//    if (fullPageId==null || fullPageId.equals("")) {
//      int colonpos = getMenuNo ().indexOf(":") ;
//      fullPageId = (colonpos == -1) ? getMenuNo () : getMenuNo ().substring(0,colonpos) ;
//    }

    
    if (requireBenchmark()) 
      Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doPost 2", svInst.getLogWebTraceStream()) ;
      
    doAppSearch (request, response) ;

  }


  /** 
   * For ordinary "Search" workflow, no need to override this method. 
   * If you are in special case, use this as reference for "Search" process and then create your own doAppSearch method.
   */
  protected void doAppSearch (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    ServletShares svInst = ServletShares.getInstance() ;
    boolean isReqBenchmark = requireBenchmark() ;

    HttpSession hs = request.getSession() ;
    String userId   = getUserId(hs) ;

    if (isReqBenchmark) 
      Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 0", svInst.getLogWebTraceStream()) ;

    GenericSearchFormData sfd = this.createSearchForm(request) ;
    sfd.setSystemUserId(userId);

    if (isReqBenchmark) 
      Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 1", svInst.getLogWebTraceStream()) ;
      
    String inCmd = getCommand (request) ; // at least the command will be kSearchInit

    // **SPECIAL HANDLING** if child servlet return NULLs for search form pageID
    // We will do the following steps:
    // (1) create search form (no validation will be done)
    // (2) run the search with the default search form
    // (3) present the result page
    
    if (getPageIdForSearchForm() == null)
    {
      if (isReqBenchmark) 
        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 2.1", svInst.getLogWebTraceStream()) ;
      Vector resultObj = this.actionSearch(sfd) ;
      if (isReqBenchmark) 
        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 2.2", svInst.getLogWebTraceStream()) ;
      sfd.setResultVector(resultObj);
      if (isReqBenchmark) 
        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 2.3", svInst.getLogWebTraceStream()) ;
      request.setAttribute(kGenericWebFormData, sfd);
      forwardTo(this.getPageIdForResultPage(), request, response); // goto edit final page if no error or back to edit main page      
      return ;
    }


    // if sfd not found, create new one and go back to search main page
    if (getPageIdForSearchForm() != null && (inCmd.equals(ISearchWorkFlow.kSearchInit) || inCmd.equals(ISearchWorkFlow.kSearchBack)))
    {
      if (isReqBenchmark) 
        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 3", svInst.getLogWebTraceStream()) ;
      request.setAttribute(kGenericWebFormData, sfd);
      forwardTo(this.getPageIdForSearchForm(), request, response) ;
      return ;
    }

    if (inCmd.equals(ISearchWorkFlow.kSearch)) // search button in "Search" main page
    {
      sfd.setWithHttpRequest(request); // pre-fill form with data in HttpRequest
      if (isReqBenchmark) 
        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 4.1", svInst.getLogWebTraceStream()) ;
      sfd.validate() ;
      if (isReqBenchmark) 
        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 4.2", svInst.getLogWebTraceStream()) ;
      sfd = this.getWebFormRequest(sfd, request) ; // allow child servlet to further process data in form
      if (isReqBenchmark) 
        Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 4.3", svInst.getLogWebTraceStream()) ;
      request.setAttribute(kGenericWebFormData, sfd);
      if (sfd.hasErr() == true)
      { // if data is invalid, go back to search page
        if (isReqBenchmark) 
          Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 4.3.1", svInst.getLogWebTraceStream()) ;
        forwardTo(this.getPageIdForSearchForm(), request, response) ;
      }
      else
      {
        if (isReqBenchmark) 
          Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 4.3.2.1", svInst.getLogWebTraceStream()) ;
        Vector resultObj = this.actionSearch(sfd) ;
        if (isReqBenchmark) 
          Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 4.3.2.2", svInst.getLogWebTraceStream()) ;
System.out.println("Result: "+resultObj.size()+" record(s)");
        sfd.setResultVector(resultObj);
        if (isReqBenchmark) 
          Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, "BENCHMARK", "<" + userId + "> " + getChildServletName() + " doAppSearch 4.3.2.3", svInst.getLogWebTraceStream()) ;
        forwardTo(this.getPageIdForResultPage(), request, response); // goto edit final page if no error or back to edit main page
      }
      return ;
    }

    // redirect to search page if nothing happen so far
    
  
  }

  /**
   * Bound the command from form submit with the input commands
   * @param request HttpServletRequest
   * @return valid command from form submit or a new "Edit" command from beginning
   */
  protected String getCommand (HttpServletRequest request) 
  {
    String inCmd = getFormParameter (request, kCommand) ;
    if ( inCmd.equals(ISearchWorkFlow.kSearch) || 
         inCmd.equals(ISearchWorkFlow.kSearchBack) )
      return inCmd ;
    return ISearchWorkFlow.kSearchInit ;
    
  }

  /**
   * Define the page scope of the child servlet
   */
  protected String[] getPageScope () 
  {
    String s[] = { getPageIdForSearchForm() , getPageIdForResultPage() } ;
    return s;
  }

  // the following methods are not applicable in this servlet
  protected Object datamapViewToEntity (Object obj) throws CircException { return null ; }
  protected Object getFormRequest (HttpServletRequest request)  { return null ; }


  // abstract method for child servlet to implement
  protected abstract String getPageIdForSearchForm () ;
  protected abstract String getPageIdForResultPage () ;
  protected abstract String getMenuNo () ;
  
  protected abstract GenericSearchFormData createSearchForm(HttpServletRequest request) ;

  protected abstract Vector actionSearch (GenericSearchFormData wfd) ;
  // read in the data, to be saved, from Httprequest
  protected abstract GenericSearchFormData getWebFormRequest (GenericSearchFormData form, HttpServletRequest request) ;

}