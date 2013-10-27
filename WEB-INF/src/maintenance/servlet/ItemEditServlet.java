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

import maintenance.databean.ItemData;
import jdbc.JdbcConnection;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

public class ItemEditServlet extends AppEditServlet
{
  protected String getPageIdForEditMain () 
  { 
    return "MA_ITEM,4";
  }
  protected String getPageIdForEditConfirm ()  
  { 
    return "MA_ITEM,5";
  }
  protected String getPageIdForEditFinal ()  {     return "DF_FINISH,0" ; }
  protected String getMenuNo ()  { return "MA_ITEM"  ; }
  protected String getUpdateSucceedMessage (GenericWebFormData wfd) { return "Item edit saved successfully"  ; }
  protected String getUpdateFailureMessage (GenericWebFormData wfd)  { return "Item edit saved FAILED"  ; }
  
  protected GenericWebFormData actionLoadEditData (Object keyObjectForLoad) 
  {
    String itemNo = (String) keyObjectForLoad ;   
	ItemData iData = null;
    
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "SELECT * FROM ITEM WHERE ITEMNO = ?";
    try {
    	conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, itemNo);
		rs = ps.executeQuery();
		if(rs.next())
		{
			iData = new ItemData(rs);
		}
    } 
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally{
		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}
    return iData ;
  }
  
  protected boolean actionUpdate (GenericWebFormData wfd) 
  {
    ItemData form = (ItemData) wfd ;
    
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "UPDATE ITEM SET ITEMDESCRIPTION = ?, AMOUNT = ?, MAXQTY = ?, "+
				" ITEMTYPE = ?, PAYMENTTYPE = ?, MODIFIEDON = SYSDATE() WHERE ITEMNO = ?";
    try
    {
      conn = JdbcConnection.getConnection();
	  ps = conn.prepareStatement(sql);
	  ps.setString(1, form.getData(ItemData.itemDescription));
	  ps.setString(2, form.getData(ItemData.amount));
	  ps.setString(3, form.getData(ItemData.maxQty));
	  ps.setString(4, form.getData(ItemData.itemType));
	  ps.setString(5, form.getData(ItemData.paymentType));
	  ps.setString(6, form.getData(ItemData.itemNo));
	  ps.executeUpdate();
	}
    catch (Exception e)
    {
      System.out.print(new java.util.Date()) ;
      System.out.println("Cannot update Item:" + form.getData(form.itemNo)) ;
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
	ItemData iData = (ItemData)form;
	iData.setData(ItemData.itemNo, request.getParameter("itemNo"));
	iData.setData(ItemData.itemDescription, request.getParameter("itemDescription"));
	iData.setData(ItemData.amount, request.getParameter("amount"));
	iData.setData(ItemData.maxQty, request.getParameter("maxQty"));
	iData.setData(ItemData.itemType, request.getParameter("itemType"));
	iData.setData(ItemData.paymentType, request.getParameter("paymentType"));
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
    String itemNo = request.getParameter("itemNo") ;
    return itemNo ;
  }
  protected Object datamapEntityToView (Object obj) throws CircException
  {
    return null ;
  }

}