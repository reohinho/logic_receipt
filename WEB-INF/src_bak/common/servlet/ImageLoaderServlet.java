
/** This servlet loads image by given session name and content-type. 
 * Then response that iamge and erase it in session if necessary
 **/

package common.servlet;

import java.io.IOException;

import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** This servlet loads image by given session name and content-type. 
 * Then response that iamge and erase it in session if necessary
 **/
public class

ImageLoaderServlet extends HttpServlet 
{

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
     HttpSession hs = request.getSession() ;

     String from = request.getParameter("from") ; 
     String contentType = (String)request.getAttribute("cType") ;
     byte[] img = (byte[]) request.getAttribute(from) ;
     if (from !=null && contentType==null && img==null) {
       from=from.trim() ;
       if (from.startsWith("CustomerUploadMapServlet")) {
          Vector vImageInfo = (Vector)hs.getAttribute("LOADIMG_"+from) ;
          contentType = (String)vImageInfo.elementAt(0) ;
          img = (byte[])vImageInfo.elementAt(1) ;
       }
     }
     hs.removeAttribute("LOADIMG_"+from);

     response.setContentType(contentType);
     java.io.OutputStream os = response.getOutputStream();
     os.write(img);
     os.flush();
     os.close();
     
  }
}