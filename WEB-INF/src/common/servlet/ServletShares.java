/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/servlet/ServletShares.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */

package common.servlet;

//import common.databean.WebTierMappingCache;
//import generate.servlet.AutoGenerateProcess;
//import generate.servlet.CreditControlProcess;
import com.scmp.circ.utility.Log;
import jdbc.JdbcConnection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


/**
 * Class for collecting of commonly used methods 
 */
public class ServletShares implements Serializable
{
  public static ServletShares sharedObject = null ;

  /** Intran1et system reference id (site id) */
  public static String INTRANET_SITEID = "1000000011";

  private static String CONTENT_TYPE = "text/html; charset=UTF-8";
  
  public static String rootJSPURI = "../" ;
  public static String rootServletURI = "/servlet/" ;

  public static String JSPIdLogin = "DF_LOGIN,0" ;
  public static String JSPIdUserDefault = "DF_TOP,0" ;
  public static String JSPIdError = "DF_ERROR,0" ;
  public static String JSPIdMainMenu = "DF_MAIN,0" ;
  public static String JSPIdUserHome = "OT_EDHOME,0" ;
  public static String httpRoot = "http://rtl-server:8888" ;


  /** Obtain this object instance which shares across the servlet. 
   * @return   The instance of this object (If not exists before, then it will create one)
   * */
  public static synchronized ServletShares getInstance() {
     if ( sharedObject == null ) sharedObject = new ServletShares();
     return sharedObject;
  }

  /**
   * Default constructor just init this object .
   */
  public ServletShares () {
    //init () ;
    //getApplicationCacheMapping () ;
  }


  /** Circulation system log filename */
  private static String LOGWEBTRACE_FILENAME = "circwebtrace.log" ;

  /** Circulation system log filename */
  private static String LOGUSERACTIVITY_FILENAME = "circuserstatus.log" ;

  /** Circulation system program tracing output stream */
  private static FileOutputStream outDebug = null;

  /** Circulation system appliction status output stream */
  private static FileOutputStream outUserActi = null;

  /** Upload file tmp location */
  private static String uploadTempLocation = null ;

  /* Upload OF Cutomer Credit location */
  private static String uploadOFCustCreditLocation = null ;
  
  /* Upload OF Customer Credit Control File Location, Added by Seve */
  private static String uploadOFCustCreditLocationControlFile = null ;

  /* Upload OF Customer Credit Home Location, Added by Seve */
  private static String uploadOFCustCreditLocationHome = null ;

  /** Backup OF Customer Credit Location */
  private static String backupOFCustCreditLocation = null;

  /**
   * Load pre-defined information from data source.
   */
  private static synchronized void init () {
    try {
       // load resources bundle
       ResourceBundle pProperties = ResourceBundle.getBundle("web", Locale.getDefault());
System.out.println("ServletShares Locale.getDefault() " + Locale.getDefault());
       String tmp = null ;
       
       tmp = pProperties.getString("servlet.servletrooturi");
       if (tmp!=null) rootServletURI = tmp ;
//System.out.println(">" + tmp + "<" ) ;            
       tmp = pProperties.getString("servlet.jsprooturi");
       if (tmp!=null) rootJSPURI = tmp ;
//System.out.println(">" + tmp + "<" ) ;            
            
       tmp = pProperties.getString("servlet.cfssiteid");
       if (tmp!=null) INTRANET_SITEID = tmp ;
//System.out.println(">" + tmp + "<" ) ;            
            
       tmp = pProperties.getString("servlet.LOGWEBTRACE_FILENAME");
       if (tmp!=null) LOGWEBTRACE_FILENAME = tmp ;
//System.out.println(">" + tmp + "<" ) ;            

       tmp = pProperties.getString("servlet.LOGUSERACTIVITY_FILENAME");
       if (tmp!=null) LOGUSERACTIVITY_FILENAME = tmp ;
//System.out.println(">" + tmp + "<" ) ;            

       tmp = pProperties.getString("servlet.uploadtemplocation");
       if (tmp!=null) uploadTempLocation = tmp ;
//System.out.println(">" + tmp + "<" ) ;            

       tmp = pProperties.getString("servlet.httpRoot");
       if (tmp!=null) httpRoot = tmp ;
       
       tmp = pProperties.getString("servlet.ofCustCredit");     
       if (tmp!=null) uploadOFCustCreditLocation = tmp ;

       // Added by Seve
       tmp = pProperties.getString("servlet.ofCustCreditControl");     
       if (tmp!=null) uploadOFCustCreditLocationControlFile = tmp ;

       // Added by Seve   
       tmp = pProperties.getString("servlet.ofCustCreditHome");     
       if (tmp!=null) uploadOFCustCreditLocationHome = tmp ;

       tmp = pProperties.getString("servlet.ofCustCreditBackup");
       if (tmp!=null) backupOFCustCreditLocation = tmp ;

       // create log stream
        outDebug = new FileOutputStream(LOGWEBTRACE_FILENAME, true);
        outUserActi = new FileOutputStream(LOGUSERACTIVITY_FILENAME, true);

        // create auto process to process order event log, invoice generation and AR interface files
        //AutoGenerateProcess auto_process = new AutoGenerateProcess();
        //auto_process.start();

        // credit control process
        //CreditControlProcess creditControl = new CreditControlProcess();
        //creditControl.start();
    } catch (IOException e) {
      System.out.println("(ServletShares.java) Error for servlet init config: " + e.getMessage()) ;
    }
 
  }


  /**
   * Forward to error page without close window option
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @param errCode (String) error code for this error and this will print on screen
   * @param errMsg (String) error message for this error and this will print on screen
   * @param errReturnPageId (String) return page id, 
   * @param errReturnPageName (String) return page name
   */
  public static void setErrorPage (HttpServletRequest request, HttpServletResponse response, String errCode, String errMsg, String errReturnPageId, String errReturnPageName) {
    request.setAttribute ((String)"ERROR_CODE", (String)errCode) ;
    request.setAttribute ((String)"ERROR_MSG", (String)errMsg) ;
    request.setAttribute ((String)"ERROR_BACKID", (String)errReturnPageId) ;
    request.setAttribute ((String)"ERROR_BACKNAME", (String)errReturnPageName) ;
  }


  /**
   * Forward to error page with close window option
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @param errCode (String) error code for this error and this will print on screen
   * @param errMsg (String) error message for this error and this will print on screen
   * @param errReturnPageId (String) return page id, 
   * @param errReturnPageName (String) return page name
   * 
   */   
  public static void setCloseErrorPage (HttpServletRequest request, HttpServletResponse response, String errCode, String errMsg, String errReturnPageId, String errReturnPageName) {
    setErrorPage (request, response, errCode, errMsg, errReturnPageId, errReturnPageName) ;
/*
    request.setAttribute ((String)"ERROR_CODE", (String)errCode) ;
    request.setAttribute ((String)"ERROR_MSG", (String)errMsg) ;
    request.setAttribute ((String)"ERROR_BACKID", (String)errReturnPageId) ;
    request.setAttribute ((String)"ERROR_BACKNAME", (String)errReturnPageName) ;
*/    
    request.setAttribute ((String)"ERROR_CLOSEWINDOW", (String)"Y") ;
  }



  /** Obtain servlet application cache mapping object */
  //public WebTierMappingCache getApplicationCacheMapping () { return WebTierMappingCache.getInstance (); }

  /** Obtain log web tier trace stream */
  public FileOutputStream getLogWebTraceStream () { return outDebug; }

  /** Obtain log user status stream */
  public FileOutputStream getLogUserActivityStream () { return outUserActi ; }

  /** Obtain log user status stream */
  public String getTempFileUploadLocation () { return uploadTempLocation ; }

  /** Obtain OF Customer Credit stream */
  public String getOFCustCreditLocation () { return uploadOFCustCreditLocation ; }

  /** Obtain OF Customer Credit Control stream */
  public String getOFCustCreditLocationControl () { return uploadOFCustCreditLocationControlFile ; }

  /** Obtain OF Customer Credit Control Home Directory */
  public String getOFCustCreditLocationHome () { return uploadOFCustCreditLocationHome ; }

  public String getOFCustCreditBackup() {return backupOFCustCreditLocation;}

  /** User activities trial logging 
   * @params uid (String) User id 
   * @params menuId (String) Menu No. to be report
   * @params statusString (String) Description for the user activities on this menu
   **/
  public void logUserStatus (String uid, String menuId, String statusString) {
    Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION_STATUS, Log.LOG_ALL, uid+":"+menuId, statusString, getLogUserActivityStream()) ;
  }


  /**
   * Obtain jsp from cached object
   * @params menuNo (String) menuId
   * @params accessOrder (int) page id under the menuNo
   * @return the corresponding jsp starts from JSP root URI
   */
  public String getJSPURIbyId (String menuNo, int accessOrder) {
    return getJSPURIbyId (menuNo+","+accessOrder) ;
  }

  /**
   * Obtain menu full id by jsp URL from cached object 
   */
  public String getMenuNobyJspUrl (String jspUrl) {
    if (jspUrl == null) return null ;
    //Hashtable ht = (Hashtable)WebTierMappingCache.getInstance().getMenuNoAll() ;
    Hashtable ht = null;    
    Enumeration enume = ht.keys() ;
    while (enume.hasMoreElements()) {
      String key=(String)enume.nextElement() ;
      String menuDetail[] = (String[])ht.get ((String)key) ;
      if (jspUrl.indexOf(menuDetail[1]) != -1) // Check if contains relative jsp URI
        return key; // Then return full menu no.
    }
    return "" ;
  }
  
  /**
   * Obtain page id from cached object with url
   * @params url (String) page URL
   * @return the corresponding page id
   */
  public String getPageIdByUrl(String url) {
    if (url == null) return null ;
    while (url.charAt(0) == '/') 
    {
      url = url.substring(1, url.length());
    }
    //String pageId = (String)WebTierMappingCache.getInstance().getUrlPageIdMappingCache().get(url);
    String pageId = null;
    return pageId;
  }
  
  /**
   * Obtain jsp from cached object with full menu no
   * @params fullMenuNo (String) menuId
   * @return the corresponding jsp starts from JSP root URI
   */
  public String getJSPURIbyId (String fullMenuNo) {
    if (fullMenuNo==null) return null ;
    //String[] o =  (String[])WebTierMappingCache.getInstance().getMenuNoAll().get((String)(fullMenuNo)) ;
    String uri = "";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try
    {
    	String sql = "SELECT * from page where pageid = ?";
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement(sql);
    	ps.setString(1, fullMenuNo);
    	rs = ps.executeQuery();
    	if(rs.next())
    	{
    		uri = rs.getString("uri");
    	}
    	
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
    return (uri==null) ? getForwardErrorJSP () : uri;
  }


  /**
   * Obtain jsp from cached object for forwarding 
   * @params menuNo (String) menuId
   * @params accessOrder (int) page id under the menuNo
   * @return the corresponding jsp starts from JSP root URI
   */
  public String getJSPbyId (String menuNo, int accessOrder) 
  {
    return getJSPbyId (menuNo +","+ accessOrder) ;
  }

  /**
   * Obtain jsp from cached object for forwarding 
   * @params fullMenuNo (String) menuId
   * @params accessOrder (int) page id under the menuNo
   * @return the corresponding jsp starts from JSP root URI
   */
  public String getJSPbyId (String fullMenuNo) 
  {
    String s = getJSPURIbyId(fullMenuNo) ;
    return "/" + s;
  }

  /**
   * Obtain jsp from cached object for redirection
   * @params menuNo (String) menuId
   * @params accessOrder (int) page id under the menuNo
   * @return the corresponding jsp starts from JSP root URI
   */
  public String getRedirectJSPbyId (String menuNo, int accessOrder) 
  {
    return getRedirectJSPbyId (menuNo+","+accessOrder) ;
  }

  /**
   * Obtain jsp from cached object for redirection
   * @params fullMenuNo (String) menuId in format "XXXX,0"
   * @return the corresponding jsp starts from JSP root URI
   */
  public String getRedirectJSPbyId (String fullMenuNo) 
  {
    try {
      String s = getJSPURIbyId (fullMenuNo) ;
      return rootJSPURI + s;
    } catch (Exception e) { return "" ; }
  }


  /**
   * Object primary jsp from cached object
   * @params menuNo (String) menuId
   * @params accessOrder (int) page id under the menuNo
   * @return the corresponding jsp starts from JSP root URI
   */
  public String getPageDisplayNamebyId (String menuNo, int accessOrder) 
  {
    return getPageDisplayNamebyId ((String)(menuNo+","+accessOrder)) ;
  }

  /**
   * Obtain login JSP in full url
   * @return login JSP in full url
   */
  public String getFullUrlLoginJSP () 
  {
    return httpRoot + getRdirectLoginJSP() ;
  }


  /**
   * Object primary sp from cached object
   * @params menuNo,accessOrder (String) menuId
   * @return the corresponding jsp starts from JSP root URI
   */
  public String getPageDisplayNamebyId (String menuNoNaccessOrder) 
  {
    //String[] o =  (String[])WebTierMappingCache.getInstance().getMenuNoAll().get((String)(menuNoNaccessOrder)) ;
    String[] o = null;
	return (o != null && o.length==3) ?o[0]:null ;
  }


// Generic JSP for forwarding / redirection

  /**
   * Obtain Error jsp for forwarding
   */
  public String getForwardErrorJSP () { return getJSPbyId (JSPIdError) ; }

  /**
   * Obtain user Default jsp for forwarding
   */
  public String getForwardUserDefaultJSP () { return getJSPbyId (JSPIdUserDefault) ;}

  /**
   * Obtain user Default jsp for redirection
   */
  public String getRedirectUserDefaultJSP () { return getRedirectJSPbyId (JSPIdUserDefault) ;}

  /**
   * Obtain user Login jsp for forwarding
   */
  public String getForwardLoginJSP  () { return getJSPbyId (JSPIdLogin) ; }

  /**
   * Obtain user Login jsp for redirection
   */
  public String getRdirectLoginJSP  () { return getRedirectJSPbyId (JSPIdLogin) ; }

// End genreic JSP

  public String windowCloseHTML () {
    return "<HTML><BODY onload=\"window.close();\"></body></HTML>";
  }


  public static void main(String[] args)
  {
    ServletShares ss = ServletShares.getInstance() ;
  }
  
}