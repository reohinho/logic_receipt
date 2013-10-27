package transaction.servlet;

import common.servlet.MasterServlet;
import common.servlet.ServletShares;
import common.servlet.SessionDataControl;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;
import com.scmp.circ.utility.Log;

import javax.servlet.*;
import javax.servlet.http.*;

import transaction.databean.ReportEnquiryData;

import java.io.IOException;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.IBaseConstants;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Hashtable;
import java.text.SimpleDateFormat;

public class TransactionSummaryReport extends MasterServlet   {


  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doPost (request, response) ;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    super.doPost(request,response);

    ReportEnquiryData reData = (ReportEnquiryData)getFormRequest(request);    
	try {
	  response.setContentType("application/pdf");
	  response.setHeader("Content-Disposition", "attachment; filename=\"TransactionSummaryReport.pdf\"");
	  response.setHeader("Cache-control", "no-store, no-cache, must-revalidate");
	  response.setHeader("Pragma", "no-cache");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OutputStream out = response.getOutputStream();     
      JavaReportGenerate rptDrv = new JavaReportGenerate();
      System.out.println("receiptType="+reData.getData(reData.receiptType));

      ArrayList data = rptDrv.getTransactionSummaryReportData(reData);
      TransactionSummaryReportGenerate gen = new TransactionSummaryReportGenerate(data, reData);
      baos = gen.getReportCode();
      out.write(baos.toByteArray());
	}catch(Exception e) {
		e.printStackTrace();
	}
  } 

  protected Object getFormRequest (HttpServletRequest request) {
      ReportEnquiryData reData = new ReportEnquiryData();
      reData.setData(ReportEnquiryData.officerId, (String)request.getParameter("officerId"));
      reData.setData(ReportEnquiryData.periodStart, (String)request.getParameter("periodStart"));
      reData.setData(ReportEnquiryData.periodEnd, (String)request.getParameter("periodEnd"));
      System.out.println("receiptType="+request.getParameter("receiptType"));
      reData.setData(ReportEnquiryData.receiptType, (String)request.getParameter("receiptType"));
      System.out.println("receiptType="+reData.getData(reData.receiptType));
      return reData;
  }

  protected boolean validateRequestParams (Object obj) {return true;}
  
  /**
   * Workflow for the servlet.
   * Not necessary in this servlet because workfow just serve 1 task only. Thus, simple enough to define in doPost method
   */
  protected Object process (Object obj) throws CircException {
    Hashtable htProcess = (Hashtable) obj ;
    String cmd = (String)htProcess.get ((String)"cmd") ;
    return null;
  }
  

   /**
   * Converts frontend Bulk Order object to backend Orders object
   */
  protected Object datamapViewToEntity (Object obj) throws CircException {
    return null;
  }

  /**
   * Converts backend Orders object to frontend Bulk Order object
   */
  protected Object datamapEntityToView (Object obj) throws CircException { 
    return null;
  }
//

  
}