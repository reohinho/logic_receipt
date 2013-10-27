/**
 * extends from AppImageLoaderServlet
 * load image from databean which set in session, remove databean from session if necessary (from view and delete servlet)
 */
package common.servlet;

import common.jsp.databean.GenericImageFormData;
import generic.servlet.AppImageLoaderServlet;

import generic.servlet.IUploadWorkFlow;

import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * extends from AppImageLoaderServlet
 * load image from databean which set in session, remove databean from session if necessary (from view and delete servlet)
 */
public class

LoadImageFromDatabeanServlet extends AppImageLoaderServlet 
{
  public void init(ServletConfig config) throws ServletException{
        super.init(config);
  }

 public Vector getHttpParameter(HttpServletRequest request){
        Vector vec = new Vector();
        vec.addElement(request.getParameter(IUploadWorkFlow.OBJECT_INDEX));
        vec.addElement(request.getParameter(IUploadWorkFlow.UPLOAD_FILE_ID));
        vec.addElement(request.getParameter(IUploadWorkFlow.ACTION));
        vec.addElement(request);
        return vec;
  }
 public Vector process(Vector vec){
        GenericImageFormData gifd = null;
        Vector vecResult = new Vector();
        String objIdx = (String)vec.get(0);
        String imageId = (String)vec.get(1);
        String action = (String)vec.get(2);
        HttpServletRequest request = (HttpServletRequest)vec.get(3); 
        HttpSession ht = request.getSession();
        gifd = (GenericImageFormData)this.sessDataCtrlGetObjectByIndex(ht,objIdx);

        vecResult.addElement(gifd.getImage(imageId));
        vecResult.addElement(gifd.getImageType(imageId));

        if (action.equals("view")) { // check if need to delete databean from session , which from View and Delete servlet.
        this.sessDataCtrlRemoveObjectByIndex(ht,objIdx);
        }
        return vecResult;
 }


}