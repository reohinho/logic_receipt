/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintEditServlet.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:15 $
  $Revision: 1.1.1.1 $
*/
package maintenance.servlet;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.AppEditServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import maintenance.databean.MessageData;
import jdbc.JdbcConnection;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

public class MessageEditServlet extends AppEditServlet
{
  protected String getPageIdForEditMain () 
  { 
    return "MA_MESS,4";
  }
  protected String getPageIdForEditConfirm ()  
  { 
    return "MA_MESS,5";
  }
  protected String getPageIdForEditFinal ()  {     return "DF_FINISH,0" ; }
  protected String getMenuNo ()  { return "MA_MESS"  ; }
  protected String getUpdateSucceedMessage (GenericWebFormData wfd) { return "Message edit saved successfully"  ; }
  protected String getUpdateFailureMessage (GenericWebFormData wfd)  { return "Message edit saved FAILED"  ; }
  
  protected GenericWebFormData actionLoadEditData (Object keyObjectForLoad) 
  {
    String code = (String) keyObjectForLoad ;   
    MessageData iData = null;
    
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "SELECT * FROM MESSAGE WHERE CODE = ?";
    try {
    	conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, code);
		rs = ps.executeQuery();
		if(rs.next())
		{
			iData = new MessageData(rs);
		}
    } 
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return iData ;
  }
  
  protected boolean actionUpdate (GenericWebFormData wfd) 
  {
    MessageData form = (MessageData) wfd ;
    
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "UPDATE MESSAGE SET DEFINEDMESSAGE = ?";
    try
    {
      conn = JdbcConnection.getConnection();
	  ps = conn.prepareStatement(sql);
	  ps.setString(1, form.getData(MessageData.definedMessage));

	  ps.executeUpdate();
	}
    catch (Exception e)
    {
      System.out.print(new java.util.Date()) ;
      System.out.println("Cannot update Message") ;
      return false ;
    }
    finally{
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}
    return true ;
  }
  
  // read in the data, to be saved, from Httprequest
  protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
  {
	MessageData iData = (MessageData)form;
	iData.setData(MessageData.definedMessage, request.getParameter("definedMessage"));
	
    return iData ;
  }
  
  // abstract methods from BaseServlet need to be implement in child Edit servlet
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected Object datamapViewToEntity (Object obj) throws CircException 
  {
    return null;
  }

  public Long zeroIfNull(Long input)
  {
    return ((input == null) ? new Long(0) : input) ;
  }
  
  // read in key element for load edit data from backend from Httprequest
  protected Object getFormRequest (HttpServletRequest request)
  {
    return "SM";
  }
  protected Object datamapEntityToView (Object obj) throws CircException
  {
    return null ;
  }

}