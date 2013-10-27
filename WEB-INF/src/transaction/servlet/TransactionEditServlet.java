/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintEditServlet.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:15 $
  $Revision: 1.1.1.1 $
*/
package transaction.servlet;

import common.jsp.databean.GenericWebFormData;
import common.servlet.ServletShares;
import generic.servlet.AppEditServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;
import com.scmp.circ.utility.Log;

import maintenance.databean.BankCodeData;
import jdbc.JdbcConnection;
import java.sql.Timestamp;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import transaction.databean.TransactionData;
import transaction.databean.TransactionLineData;

public class TransactionEditServlet extends AppEditServlet
{
  protected String getPageIdForEditMain () 
  { 
    return "TX_INPUT,4";
  }
  protected String getPageIdForEditConfirm ()  
  { 
    return "TX_INPUT,5";
  }
  protected String getPageIdForEditFinal ()  {     return "DF_FINISH,0" ; }
  protected String getMenuNo ()  { return "TX_INPUT"  ; }
  protected String getUpdateSucceedMessage (GenericWebFormData wfd) { return "Receipt voided successfully"  ; }
  protected String getUpdateFailureMessage (GenericWebFormData wfd)  { return "Receipt void FAILED"  ; }
  
  protected GenericWebFormData actionLoadEditData (Object keyObjectForLoad) 
  {
    String txNo = (String) keyObjectForLoad ;   
    TransactionData iData = null;
    
    Connection conn = null;
	PreparedStatement ps = null;
	PreparedStatement ps2 = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	String sql = "SELECT * FROM RECEIPT WHERE RECEIPTNO = ?";
    try {
    	conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, txNo);
		rs = ps.executeQuery();
		if(rs.next())
		{
			iData = new TransactionData(rs);
     		
    		//Receipt Line Details
    		ps2 = conn.prepareStatement("SELECT LINENO, ITEMNO, QTY, ITEMDESCRIPTION, UNITPRICE, TOTAL FROM RECEIPT_ITEM WHERE RECEIPTNO = ?");
    		ps2.setString(1, iData.getData(TransactionData.txNo));
    		rs2 = ps2.executeQuery();
    		Vector lineVec = new Vector();
    		int counter = 0;
    		while(rs2.next()&&counter<=100)
    		{
    			TransactionLineData tlData = new TransactionLineData(rs2);
    			lineVec.add((TransactionLineData)tlData);
    			counter++;
    		}        		
    		iData.setLineVec(lineVec);
		}
    } 
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if(iData.getData(TransactionData.txType)!=null&&iData.getData(TransactionData.txType).equals("A"))
	{
		iData.setLabel(TransactionData.dob,"dob","Date of Birth", "REQUIRED");
		iData.setLabel(TransactionData.nationality,"nationality","Nationality","REQUIRED");

	}
	else
	{
		iData.setLabel(TransactionData.dob,"dob","Date of Birth", "");
		iData.setLabel(TransactionData.nationality,"nationality","Nationality","");
	}

    return iData ;
  }
  
  protected boolean actionUpdate (GenericWebFormData wfd) 
  {
	TransactionData form = (TransactionData) wfd ;
    
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	boolean isVoid = false;
	if(form.getData(TransactionData.isVoid).equals("Y"))
		isVoid = true;
	if(isVoid)
		sql = "UPDATE RECEIPT SET STATUS = 'VOIDED', VOIDON = SYSDATE(), VOIDBY = ?, VOIDREASON = ? WHERE RECEIPTNO = ?";
	else
		sql = "UPDATE RECEIPT SET REPRINTON = SYSDATE(), REPRINTBY = ?, REPRINTREASON = ? WHERE RECEIPTNO = ?";
	String historySQL = "INSERT INTO RECEIPT_HISTORY (RECEIPTNO, REPRINTON, REPRINTBY, REPRINTREASON, ISVOID) "+
						"VALUES (?,sysdate(),?,?,?)";
    try
    {
      conn = JdbcConnection.getConnection();
	  ps = conn.prepareStatement(sql);
	  ps.setString(1, form.getSystemUserId());
	  ps.setString(2, form.getData(TransactionData.voidReason));
	  ps.setString(3, form.getData(TransactionData.txNo));

	  ps.executeUpdate();
	  
	  
	  ps = conn.prepareStatement(historySQL);
	  ps.setString(1, form.getData(TransactionData.txNo));
	  ps.setString(2, form.getSystemUserId());
	  ps.setString(3, form.getData(TransactionData.voidReason));
	  ps.setString(4, form.getData(TransactionData.isVoid));  
	 
	  ps.executeUpdate();
	 }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.print(new java.util.Date()) ;
      System.out.println("Cannot void Receipt:" + form.getData(form.txNo)) ;
      return false ;
    }
    return true ;
  }
  
  // read in the data, to be saved, from Httprequest
  protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
  {
	TransactionData iData = (TransactionData)form;
	iData.setData(TransactionData.txNo, request.getParameter("txNo"));
	iData.setSystemUserId((String)request.getSession().getAttribute("userId"));
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
    String txNo = request.getParameter("txNo") ;
    return txNo ;
  }
  protected Object datamapEntityToView (Object obj) throws CircException
  {
    return null ;
  }

  /** 
   * For ordinary "Edit" workflow, no need to override this method. 
   * If you are in special case, use this as reference for "Edit" process and then create your own doAppEdit method.
   */
  protected void doAppEdit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession hs = request.getSession() ;
    ServletShares svInst = ServletShares.getInstance() ;
    String userId = getUserId(hs) ;

    String inCmd = getCommand (request) ;
    // object index reference must exist and go back to edit main page 
    if (inCmd.equals(getEditBackCommand())) {  // back button in "Edit" confirmation page
      // same as kEdit action
      inCmd = getEditCommand() ;
    }
    
    else if (inCmd.equals(getEditConfirmCommand())) { // ok button in "Edit" confirmation page
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // call backend for update action
      if (actionUpdate(wfd)) {
        // if action suceed
        wfd.setShouldRefresh("Y");
      	wfd.setPageID(getPageIdForEditMain());
      	wfd.setMessage(getUpdateSucceedMessage(wfd));
        Log.logApplicationStatus( "<" + userId + "> " + getUpdateSucceedMessage(wfd) , svInst.getLogUserActivityStream()) ;
        sessDataCtrlRemoveObjectByIndex(hs, objIdx);
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        if(wfd.getData(TransactionData.isVoid).equals("N"))
        {
        	forwardTo("RP_RECR", request, response); // goto edit final page if no error or back to edit main page
        }
        else
        {
        	forwardTo(getPageIdForEditFinal(), request, response); // goto edit final page if no error or back to edit main page
        }
      }
      else {
        // if action failure, then back to edit main page
        Log.logApplicationStatus( "!<" + userId + "> " + getUpdateFailureMessage(wfd) , svInst.getLogUserActivityStream()) ;
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo(getPageIdForEditMain(), request, response); // goto edit first page if error occurs.
      }
      return ;
      
    }

    else if (inCmd.equals(getEditSaveCommand())) { // save button in "Edit" main page
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // When reference in session
      if (wfd!=null) {
        wfd.setWithHttpRequest(request);
        wfd = getWebFormRequest(wfd, request) ; 
        wfd.validate() ;
        if (validateRequestParams ((Object)wfd)) {
          sessDataCtrlSetObjectByIndex(hs, objIdx, (GenericWebFormData)wfd) ;
          request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
          forwardTo(getPageIdForEditConfirm(), request, response); // goto edit confirmation page  
          return ;
        }
        else {  
          // if has error, back to edit main page
          inCmd = getEditCommand() ;
        }
        
      }
      else 
      {  
        // if edit object reference does not in the session, then assume it will create edit process by form submit key element
        inCmd = getEditCommand() ;
      }
    }

    // clear the obejct in session if available and close the window when onload
    else if (inCmd.equals(getEditDiscardCommand())) { // close button in "Edit" main page 
    
      String objIdx = getFormParameter(request, kObjectIndex) ;
      sessDataCtrlRemoveObjectByIndex(hs, objIdx);
      // client JSP should close the window automatically
    }

    // clear the obejct in session if available and perform the followup actions
    else if (inCmd.equals(getDiscardRedirctCommand())) { // close button in "Edit" main page 
    
      // create parameters for follow-up actions
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      java.util.Vector v = getParamsForDiscardRedirct (wfd) ;
      
      // remove session referenced data
      sessDataCtrlRemoveObjectByIndex(hs, objIdx);
      discardFollowUp (request, response, v) ;
    }


    else if (inCmd.equals(getReloadCommand())) 
    {
      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);

      // When reference in session
      if (wfd!=null) {
        wfd.setWithHttpRequest(request);
        wfd = getWebFormRequest(wfd, request) ; 
        sessDataCtrlSetObjectByIndex(hs, objIdx, (GenericWebFormData)wfd) ;
        request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
        forwardTo(getPageIdForEditMain(), request, response); // goto edit confirmation page  
        return ;
      } else {
        // if edit object reference does not in the session, then assume it will create edit process by form submit key element
        inCmd = getEditCommand() ;
      }
    }




    // Create a new edit data object or load the one in session if index reference exists
    // CAUTION: Under this mechanism will result in whenever a edit button is pressed, a new GenericWebFormData object will be creation in session data control object. 
    //          It will better edit button does not spwan another window to do its process (ie. one way edit process and force submit exit command to servlet when finish/discard edit process. 
    //          Anyway, house keep action on session data control should perform regularly to remove long time no access object.
    if (inCmd.equals(getEditCommand())) { // "Edit" button from "View" detail

      String objIdx = getFormParameter(request, kObjectIndex) ;
      GenericWebFormData wfd = (GenericWebFormData)sessDataCtrlGetObjectByIndex(hs, objIdx);
      // if no such reference in session, need to create a new one
      if (wfd == null) {

        Object keyForLoadingObject = getFormRequest (request) ;
        wfd = actionLoadEditData (keyForLoadingObject) ;
      
        if (wfd.hasErr()) {
          // if action failure, log failure message
          Log.logApplicationStatus( "!<" + userId + "> Load data for edit ["+getPageIdForEditMain()+"] failure." , svInst.getLogUserActivityStream()) ;
          Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, "GENERICEDIT", "<" + userId + "> Load data for view ["+getPageIdForEditMain()+"] failure.", svInst.getLogWebTraceStream()) ;
          // when error occurs, error object will set in the GenericWebFormData object in acctionLoad method
        }
        String newObjectId = sessDataCtrlNewObject(hs, getMenuNo(), (GenericWebFormData)wfd ) ;
        wfd.setSystemObjectId(newObjectId);
        wfd.setSystemUserId(userId);
        sessDataCtrlSetObjectByIndex(hs, newObjectId, (GenericWebFormData)wfd) ;
      }
      
      // Session alread has GenericWebFormData object referenced
      //  then no action  

      request.setAttribute(kGenericWebFormData, (GenericWebFormData)wfd);
      forwardTo( getPageIdForEditMain(), request, response);  // goto edit main page
    }

    return ;
  }

  
}