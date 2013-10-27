package maintenance.servlet;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.AppViewDeleteServlet;
import maintenance.databean.ItemData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import jdbc.JdbcConnection;
import javax.servlet.http.HttpServletRequest;

public class

ItemDeleteServlet extends AppViewDeleteServlet 
{

  protected String getPageIdForViewMain() 
  { 
      return "MA_ITEM,4" ;
  }
  protected  String getPageIdForDeleteConfirm() 
  { 
      return "MA_ITEM,6"  ;
  }
  protected  String getPageIdForDeleteFinal () {     return "DF_FINISH,0" ; }
  protected  String getMenuNo () {return "MA_ITEM" ; }
  protected  String getDeleteSucceedMessage (GenericWebFormData wfd)  { return "Item record deleted" ; }
  protected  String getDeleteFailureMessage (GenericWebFormData wfd) { return "Cannot delete Item record." ; } 
  // Delete data by given key object
  protected  boolean actionDelete (GenericWebFormData wfd ) 
  {
    boolean done = false ;
    ItemData form = (ItemData) wfd;
    Connection conn = null;
	PreparedStatement ps = null;
	String sql = "UPDATE ITEM SET STATUS = 'DELETED', DELETEDON = sysdate() WHERE ITEMNO = ?";
    try {
		conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, form.getData(ItemData.itemNo));
		ps.executeUpdate();
		
		done = true;
    }
    catch (Exception e)
    {
      done = false;
      form.setMessage(this.getDeleteFailureMessage(wfd));            
      e.printStackTrace();
    }
    finally{
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}
    form.setShouldRefresh("Y");
    return done ;
  }
  // Load data by given key object. If fails, NULL object will be returned
  protected GenericWebFormData actionLoad (Object keyObjectForLoad ) 
  {
	String itemNo = (String)keyObjectForLoad;
    ItemData iData = new ItemData();
    iData.setData(ItemData.itemNo, itemNo);
    iData.setShortcut("IS");
    return iData;
  }
  
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
  
  // abstract methods from BaseServlet need to be implement in child Create servlet
  // return key object for loading data from backend
  protected  Object getFormRequest (HttpServletRequest request) 
  {
    String itemNo = request.getParameter("itemNo") ;
    request.setAttribute("itemNo",itemNo);
    return itemNo ;
  }
  // validate key object format when necessary  
//  protected abstract boolean validateRequestParams (Object obj) ;
  protected Object datamapViewToEntity (Object obj) throws CircException 
  { // 
    return null ;
  }
  
  public boolean getAcceptHttpGetMethod() { return true ; } 
  
}