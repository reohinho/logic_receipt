
/** This template servlet loads image by given databean session id
 * put the image into response.
 * child class need to override getHttpParameter(HttpServletRequest request) & process(Vector vec) to meet their need
 **/

package generic.servlet;

import com.scmp.circ.utility.CircException;

import java.io.IOException;

import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** This template servlet loads image by given databean session id
 * put the image into response.
 * child class need to override getHttpParameter(HttpServletRequest request) & process(Vector vec) to meet their need
 **/
public abstract class

AppImageLoaderServlet extends BaseServlet 
{

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
   super.doPost(request,response);
   byte[] img = null;
   String contentType = null;
    try {
     Vector vec = process(getHttpParameter(request));
     img = (byte [])vec.get(0);
     contentType = (String)vec.get(1);     
    } catch (Exception e) {

    }

     response.setContentType(contentType);
     java.io.OutputStream os = response.getOutputStream();
     os.write(img);
     os.flush();
     os.close();
     
  }

public boolean getAcceptHttpGetMethod() {return true;} 
protected  String[] getPageScope () {return null;}
protected Object datamapEntityToView (Object obj) throws CircException {return null;}; 
protected Object datamapViewToEntity (Object obj) throws CircException {return null;};   

public abstract Vector getHttpParameter(HttpServletRequest request);
public abstract Vector process(Vector vec)throws Exception;
}