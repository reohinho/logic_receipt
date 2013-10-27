/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/servlet/EditMyHomeServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
package common.servlet;

import com.scmp.circ.utility.CircException;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class

EditMyHomeServlet extends MasterServlet 
{
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    super.doPost(request, response);
//    String selectedList[] = null;
//    MetaData md = (MetaData)request.getSession().getAttribute((String)"METADATA");
//    ServletShares svInst = ServletShares.getInstance() ;
//    
//    try
//    {
//      selectedList = request.getParameterValues("SelectedList");
//      // Call the EJB to update the database records
//      String userId = md.getUserID();
// 
//      Vector tmpVec;
//      try 
//      {
//        // Update database when selectetList not null
////        for (int j=0; j<selectedList.length; j++) 
////          myHomeList.refreshUserHomeList(userId, selectedList);
//
////  /**
////   * TOC
////   */
////        // Construct the vector for updating the Metadata
////        if (selectedList != null) {
////          com.scmp.circ.systemaccess.ejb.driver.MyHomeList myHomeList = new com.scmp.circ.systemaccess.ejb.driver.MyHomeList();
////          myHomeList.refreshUserHomeList(userId, selectedList);
////          tmpVec = new Vector(selectedList.length);
////          for (int i=0; i<selectedList.length; i++) 
////            tmpVec.addElement(new String(selectedList[i]));
////        } else {
////          tmpVec = new Vector();
////        }
//        tmpVec = null;
//        
//        // Set the My home list of Metadata object
//        md.setUserMenuList(tmpVec);
//
//        // redirect to the successful page
//        svInst.logUserStatus( md.getUserID(), "OT_EDHOME",  "User home list updated:" + tmpVec ) ;
//
//        request.setAttribute("finPageID","OT_EDHOME,0");
//        request.setAttribute("finMsg","My home updated!");
//        request.setAttribute("shouldRefresh", "Y");
//        getServletContext().getRequestDispatcher(svInst.getJSPbyId("DF_FINISH",0)).forward(request, response);
//        return;
//        
////        response.sendRedirect(svInst.rootJSPURI +  "editmyhomesuccess.jsp");  
//        
//      } catch (Exception e)
//      {
//        svInst.logUserStatus( md.getUserID(), "OT_EDHOME",  "User home list update failed: " + e.getMessage()) ;
//      }
//    }
//    catch(Exception e)
//    {
//      svInst.logUserStatus( md.getUserID(), "OT_EDHOME",  "User home list update failed: " + e.getMessage()) ;
//      e.printStackTrace();
//    }

  }

  /**
   * Read all form data and maybe do simple validation.
   */
  protected Object getFormRequest (HttpServletRequest request) {return null;};


  /**
   * Simple validate form submitted information.
   */
  protected boolean validateRequestParams (Object obj) {return true; };

  /**
   * Workflow for the servlet.
   */
  protected Object process (Object obj) throws CircException { return null;} ;

  /**
   * JSP data object maps to entity recognized object.
   */
  protected Object datamapViewToEntity (Object obj) throws CircException { return null;};


  /**
   * Entity data object maps to JSP recognized object.
   */
  protected Object datamapEntityToView (Object obj) throws CircException {return null;};

//  /**
//   * Check if user has privilege on this function
//   */
//  protected boolean hasPrivilege (HttpSession hs) {return true;};


  
}