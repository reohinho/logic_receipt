/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/databean/ScreenProductionData.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
package common.jsp.databean;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/databean/ScreenProductionData.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
public class

ScreenProductionData 
{
  // Static definition
  /** Indicator for referencing the menu URI attribute */
  public static int ATTR_PAGE_URI= 1 ;

  /** Indicator for referencing the menu display name atribute */
  public static int ATTR_PAGE_DISPLAY_NAME= 2 ;

  /** Indicator for referencing the "ISPOPUP" attribute */
  public static int ATTR_IS_POPUP= 3 ;

  /** Indicator for referencing the display order attribute */
  public static int ATTR_DISPLAY_ORDER= 4 ;

  
  // ---------------------------------------------------------------------------
  

  // Data for this object
  /** The menu id to be request for screen production */
  protected String requestMenuId = "" ;

  /** Whole list for all allowed subpage under the request menu id */
  protected Hashtable htAllowedSubPages = new Hashtable () ;

  
  // ---------------------------------------------------------------------------


  /** Default Constructor (do nothing) */
  public ScreenProductionData() { }


  // ---------------------------------------------------------------------------

  // Define getters
  /** Return the menu id requested in this object 
   * @return the menu id to be request for screen production
   * */
  public String getRequestPageId () { return requestMenuId ; }

  /** Obtain the sub page attribute information as specified in the 2 parameters
   * @param menuId (String) specific menu id for its attribute
   * @param attrId (int) indication for a page's attribute reference
   * @return (String) A string representation for the attribute detail, null if not found under the specific parameteres
   */
  public String get (String menuId, int attrId) {
    if (attrId <1 || attrId >4) return null ;
    Vector vAllAttr = (Vector) htAllowedSubPages.get ((String) menuId) ;
    if (vAllAttr == null) return null ;
    String attr = (String)vAllAttr.elementAt(attrId-1) ;
    return new String (attr) ;
  }

  /** Obtain the sub page display order information as specified in the parameter
   * @param menuId (String) specific menu id for its attribute
   * @return (int) integer datatype order number (lowest order from 0), return -1 if not found
   */
  public int getDisplayOrder (String menuId) {
    String order = get (menuId, ATTR_DISPLAY_ORDER)  ;
    try {
      return Integer.parseInt (order) ;
    } catch (Exception e) { return -1; }
  }

  /** Obtain an ordered list of page attributed
   * @return (String[][]) 2-Dimension array of the menus id and attributes<BR>
   * list [n][0] => sub menu id <BR>
   * list [n][1] => ATTR_PAEG_URI  <BR>
   * list [n][2] => ATTR_PAGE_DISPLAY_NAME <BR>
   * list [n][3] => ATTR_IS_POPUP <BR>
   */
  public String [][] getFullOrderedList () {
    String retAttrs[][] = new String[htAllowedSubPages.size()][4] ;
    Enumeration enume = htAllowedSubPages.keys() ;
    while (enume.hasMoreElements()) {
      String menuId = (String) enume.nextElement() ;
      int order = getDisplayOrder(menuId) ; // order must starts from 0
      Vector vMenuIdAttrs = (Vector)htAllowedSubPages.get((String)menuId) ;
      retAttrs[order][0] = new String (menuId) ;
      retAttrs[order][ATTR_PAGE_URI] = new String ((String)vMenuIdAttrs.elementAt(ATTR_PAGE_URI-1)) ;
      retAttrs[order][ATTR_PAGE_DISPLAY_NAME] = new String ((String)vMenuIdAttrs.elementAt(ATTR_PAGE_DISPLAY_NAME-1)) ;
      retAttrs[order][ATTR_IS_POPUP] = new String ((String)vMenuIdAttrs.elementAt(ATTR_IS_POPUP-1)) ;
    }

// test
/*
System.out.println("getFullOrderedList:") ;
for (int x = 0 ; x < retAttrs.length ; x++) {
  for (int y  = 0 ; y < retAttrs[x].length ; y++) {
    System.out.print(retAttrs[x][y] + "==") ;
  }
  System.out.println ("") ;
}
*/
    return retAttrs ;
  }



  /** Obtain an unordered list of page attributed
   * @return (String[][]) 2-Dimension array of the menus id and attributes<BR>
   * list [n][0] => sub menu id <BR>
   * list [n][1] => ATTR_PAEG_URI  <BR>
   * list [n][2] => ATTR_PAGE_DISPLAY_NAME <BR>
   * list [n][3] => ATTR_IS_POPUP <BR>
   */
  public String [][] getFullUnorderedList () {
    String retAttrs[][] = new String[htAllowedSubPages.size()][4] ;
    Enumeration enume = htAllowedSubPages.keys() ;
    int insCount = 0 ;
    while (enume.hasMoreElements()) {
      String menuId = (String) enume.nextElement() ;
      int order = getDisplayOrder(menuId) ; // order must starts from 0
      Vector vMenuIdAttrs = (Vector)htAllowedSubPages.get((String)menuId) ;
      retAttrs[insCount][0] = new String (menuId) ;
      retAttrs[insCount][ATTR_PAGE_URI] = new String ((String)vMenuIdAttrs.elementAt(ATTR_PAGE_URI-1)) ;
      retAttrs[insCount][ATTR_PAGE_DISPLAY_NAME] = new String ((String)vMenuIdAttrs.elementAt(ATTR_PAGE_DISPLAY_NAME-1)) ;
      retAttrs[insCount][ATTR_IS_POPUP] = new String ((String)vMenuIdAttrs.elementAt(ATTR_IS_POPUP-1)) ;
      insCount++ ;
    }
// test
/*
System.out.println("getFullUnorderedList:") ;
for (int x = 0 ; x < retAttrs.length ; x++) {
  String s[] = retAttrs[x] ;
  for (int y  = 0 ; y < s.length ; y++) {
    System.out.print(s[y] + "==") ;
  }
  System.out.println ("") ;
}
*/    
    return retAttrs ;
  }

  /** Obtain the total number of sub menus for this menu id 
   * @return (int) the total number of sub menus for this menu id 
   */
  public int getTotalSubMenu () { return htAllowedSubPages.size() ; }


  /** Obtain an unordered list of page attributed with given prefix
   * @param prefix (String) prefix of a page (Eg. DF, IV, OR, etc)
   * @return (String[][]) 2-Dimension array of the menus id and attributes<BR>
   * list [n][0] => sub menu id <BR>
   * list [n][1] => ATTR_PAEG_URI  <BR>
   * list [n][2] => ATTR_PAGE_DISPLAY_NAME <BR>
   * list [n][3] => ATTR_IS_POPUP <BR>
   */
  public String [][] getFullUnorderedList (String pfx) {
    Vector vtmp = new Vector () ;

    Enumeration enume = htAllowedSubPages.keys() ;
    while (enume.hasMoreElements()) {
      String menuId = (String) enume.nextElement() ;
      int order = getDisplayOrder(menuId) ; // order must starts from 0
      Vector vMenuIdAttrs = (Vector)htAllowedSubPages.get((String)menuId) ;
      String strAttr[] = new String [4] ;
      strAttr[0] = new String (menuId) ;
      strAttr[ATTR_PAGE_URI] = new String ((String)vMenuIdAttrs.elementAt(ATTR_PAGE_URI-1)) ;
      strAttr[ATTR_PAGE_DISPLAY_NAME] = new String ((String)vMenuIdAttrs.elementAt(ATTR_PAGE_DISPLAY_NAME-1)) ;
      strAttr[ATTR_IS_POPUP] = new String ((String)vMenuIdAttrs.elementAt(ATTR_IS_POPUP-1)) ;
      vtmp.addElement((String[])strAttr);
    }
    String retAttrs[][] = new String[vtmp.size()][4] ;
    for (int i = 0 ; i < vtmp.size() ; i++) 
       retAttrs[i] = (String[]) vtmp.elementAt(i);

// test
/*
System.out.println("getFullUnorderedList:") ;
for (int x = 0 ; x < retAttrs.length ; x++) {
  String s[] = retAttrs[x] ;
  for (int y  = 0 ; y < s.length ; y++) {
    System.out.print(s[y] + "==") ;
  }
  System.out.println ("") ;
}
*/    
    return retAttrs ;

  }  



  /** Obtain an ordered list of page attributed with given prefix
   * @param prefix (String) prefix of a page (Eg. DF, IV, OR, etc)
   * @return (String[][]) 2-Dimension array of the menus id and attributes<BR>
   * list [n][0] => sub menu id <BR>
   * list [n][1] => ATTR_PAEG_URI  <BR>
   * list [n][2] => ATTR_PAGE_DISPLAY_NAME <BR>
   * list [n][3] => ATTR_IS_POPUP <BR>
   */
  public String [][] getFullOrderedList (String prefix) {
    String tmpAttrs[][] = new String[htAllowedSubPages.size()][4] ;
    Enumeration enume = htAllowedSubPages.keys() ;
    int cnt=0 ;
    while (enume.hasMoreElements()) {
      String menuId = (String) enume.nextElement() ;
      int order = getDisplayOrder(menuId) ; // order must starts from 0
      Vector vMenuIdAttrs = (Vector)htAllowedSubPages.get((String)menuId) ;
      if (menuId.startsWith(prefix)) { 
        tmpAttrs[order][0] = menuId ;
        tmpAttrs[order][ATTR_PAGE_URI] = (String)vMenuIdAttrs.elementAt(ATTR_PAGE_URI-1) ;
        tmpAttrs[order][ATTR_PAGE_DISPLAY_NAME] = (String)vMenuIdAttrs.elementAt(ATTR_PAGE_DISPLAY_NAME-1) ;
        tmpAttrs[order][ATTR_IS_POPUP] = (String)vMenuIdAttrs.elementAt(ATTR_IS_POPUP-1) ;
        cnt++ ;
      }
    }
    String retAttrs[][] = new String[cnt][4] ;
    int cntIdx = 0 ;
    for (int i = 0 ; i < tmpAttrs.length ; i++) {
       if (tmpAttrs[i][0] !=null) {
         retAttrs[cntIdx][0] = new String (tmpAttrs[i][0] ) ;
         retAttrs[cntIdx][ATTR_PAGE_URI] = new String (tmpAttrs[i][ATTR_PAGE_URI] ) ;
         retAttrs[cntIdx][ATTR_PAGE_DISPLAY_NAME] = new String (tmpAttrs[i][ATTR_PAGE_DISPLAY_NAME] ) ;
         retAttrs[cntIdx][ATTR_IS_POPUP] = new String (tmpAttrs[i][ATTR_IS_POPUP] ) ;
         cntIdx ++ ;
       }
    }

// test
/*
String retAttrs1[][] = { {"A","B"}, {"C", "D"} } ;

System.out.println("getFullOrderedList:") ;
for (int x = 0 ; x < retAttrs1.length ; x++) {
  String s[] = retAttrs1[x] ;
  for (int y  = 0 ; y < retAttrs1[x].length ; y++) {
    System.out.print(s[y] + "==") ;
  }
  System.out.println ("") ;
}
*/
    return retAttrs ;
    
  }
  
}