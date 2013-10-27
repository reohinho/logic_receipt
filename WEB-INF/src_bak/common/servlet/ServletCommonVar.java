/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/servlet/ServletCommonVar.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */

package common.servlet;

/**
 * Class for representing pre-defined or text file stored common variables
 */
public class ServletCommonVar 
{
  // ** Common variables defines here
  /** Intranet system reference id (site id) */
  public static final String INTRANET_SITEID = "1000000011";

  /** Fixed content type */
  public static final String CONTENT_TYPE = "text/html; charset=UTF-8";
  // **

  public static ServletCommonVar commonVar = null ;

  /** Obtain the shared instance for this object. */
  public static synchronized ServletCommonVar getInstance() {
     if ( commonVar == null ) commonVar = new ServletCommonVar();
     return commonVar;
  }

  public ServletCommonVar () { 
    loadVars();
  }

  /**
   * Load variables from file system when necessary
   */
  public static void loadVars () 
  {
    // Load action performs here
  }
}