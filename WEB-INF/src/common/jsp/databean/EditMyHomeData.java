/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/databean/EditMyHomeData.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:14 $
 * $Revision: 1.1.1.1 $
 */

package common.jsp.databean;

import java.util.Vector;

import javax.servlet.http.HttpSession;

public class

EditMyHomeData 
{
  // Static definition
  /** Indicator for referencing the menu display name atribute */
  public static int ATTR_LIST_DISPLAY_NAME= 0 ;

  // Data for this object
  /** Current list in My Home page */
  protected Vector vMyHomeIdList;
  protected Vector vMyHomeNameList = new Vector();

  /** List of pages which can be accessed by the user, exclude the links in My home page */
  protected Vector vMyAvailableIdList = new Vector();
  protected Vector vMyAvailableNameList = new Vector();

  /** constructor */
  protected HttpSession httpSession;
  
  public EditMyHomeData(HttpSession httpSess) {
    this.httpSession = httpSess;
    init();
  }

  // initialize the htMyHomeList and htMyAvailableList
  public void init() 
  {
//    setMyHomeList();
//    setMyAvailableList();
    
  }
  
  // define getters
  /** Return a String array for My home list
   *  @return String[][] : 2 dimension array for the Page id and display name of the my home list <br>
   *  list[n][0] => Page id <br>
   *  list[n][1] => Display Name <br>
   * */
   
//  public String[][] getMyHomeList() 
//  {
//    String arrMyHomeList[][] = new String[vMyHomeIdList.size()][2];
////System.out.println("~~~~~~~~~~~~~~~~~~~ get My Home List ~~~~~~~~~~~~~~~~~~~~~~~~");
//    if (!vMyHomeIdList.isEmpty()) {
//      for (int i=0;i<vMyHomeIdList.size();i++) 
//      {
//        arrMyHomeList[i][0] = (String)vMyHomeIdList.elementAt(i);
//        arrMyHomeList[i][1] = (String)vMyHomeNameList.elementAt(i);
////System.out.println(i+": " +arrMyHomeList[i][0] + " - " + arrMyHomeList[i][1]); 
//      }
////System.out.println("~~~~~~~~~~~~~~~~~~~ Bye get My Home List ~~~~~~~~~~~~~~~~~~~~~~~~");
//      return arrMyHomeList;
//    } else {
//      return null;
//    }
//  }
//
//  /** Return a 2 dimension array for Available list 
//   *  @return String[][] : 2 dimension array for page id and display name of available list<br>
//   *  list[n][0] => Page id <br>
//   *  list[n][1] => Display Name <br>
//   * */
//  public String[][] getMyAvailableList() 
//  {
//    String arrMyAvailableList[][] = new String[vMyAvailableIdList.size()][2];
//    if (!vMyAvailableIdList.isEmpty()) 
//    {
//      for (int i=0;i<vMyAvailableIdList.size();i++) 
//      {
//        arrMyAvailableList[i][0] = (String)vMyAvailableIdList.elementAt(i);
//        arrMyAvailableList[i][1] = (String)vMyAvailableNameList.elementAt(i);
//      }
//      return arrMyAvailableList;
//    } else {
//      return null;
//    }
//  }
  
//  // define setters
//  /** set the Vector vMyHomeIdList and vMyHomeNameList in this object
//   *  get the data from Metadata object
//   *  @return void
//   * */
//  public void setMyHomeList() 
//  {
//
////System.out.println("************************* SET Edit My Home Data bean ********************");
//    MetaData md = (MetaData)httpSession.getAttribute((String)"METADATA");
//    WebTierMappingCache wtmc = WebTierMappingCache.getInstance();
//    vMyHomeIdList = md.getUserMenuList();
////System.out.println(vMyHomeIdList);
//    if (vMyHomeIdList != null) 
//    {
//      String tmpMenuDetail[];
//      for (int i=0; i< vMyHomeIdList.size(); i++) 
//      {
//        tmpMenuDetail = wtmc.getPrimaryMenuDetail((String)vMyHomeIdList.elementAt(i));
//        vMyHomeNameList.addElement((String)tmpMenuDetail[ATTR_LIST_DISPLAY_NAME]);
////        htMyHomeList.put((String)vMyHomeIdList.elementAt(i), (String)tmpMenuDetail[ATTR_LIST_DISPLAY_NAME]);
////System.out.println(i+": " + vMyHomeIdList.elementAt(i) + " - " + tmpMenuDetail[ATTR_LIST_DISPLAY_NAME]);
//      }
//    }
////System.out.println(htMyHomeList.toString());
////System.out.println("************************* Bye SET Edit My Home Data bean ********************");
//  }

//  /** set the Vector vMyAvailableIdList and vMyAvailableNameList in this object 
//   *  get the data from the MetaData object
//   *  @return void
//   *  */
//  public void setMyAvailableList()
//  {
//    MetaData md = (MetaData)httpSession.getAttribute((String)"METADATA");
//    AccessControl aCtrl = md.getAccessController();
//    Enumeration eMyAvailableList = aCtrl.getAllProgID();
//    WebTierMappingCache wtmc = WebTierMappingCache.getInstance();
//    if (eMyAvailableList != null) 
//    {
//      String tmpProgId;
//      String tmpMenuId[];
//      String tmpMenuDetail[];
//      while (eMyAvailableList.hasMoreElements()) 
//      {
//        tmpProgId = (String) eMyAvailableList.nextElement();
///**
// * TOC
// * MenuIdList including programID
// * get all the menuList data in the menuList array
// */
//        tmpMenuId = wtmc.getMenuIdList(tmpProgId);
//        if (tmpMenuId != null && tmpMenuId.length > 0) {
//          for (int i=0; i<tmpMenuId.length; i++) 
//          {
//            if (!vMyHomeIdList.contains(tmpMenuId[i])) 
//            {
//              tmpMenuDetail = wtmc.getPrimaryMenuDetail(tmpMenuId[i]);
//              if (tmpMenuDetail != null) { // avoid the menu list stored in his selection bute removed in the menu list table
//                vMyAvailableNameList.addElement((String)tmpMenuDetail[ATTR_LIST_DISPLAY_NAME]);
//                vMyAvailableIdList.addElement(tmpMenuId[i]);
//              }
//                
////              htMyAvailableList.put(tmpMenuId[i], (String)tmpMenuDetail[ATTR_LIST_DISPLAY_NAME]);
//            }
//          }
//        }
//      }
//    }
//  }
  
}