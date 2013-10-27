package transaction.servlet;

import common.servlet.MasterServlet;
import common.servlet.ServletShares;
import common.servlet.SessionDataControl;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;
import com.scmp.circ.utility.Log;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;   
import javax.print.*;   
import javax.print.attribute.*;    
import javax.print.attribute.standard.*;   

import common.jsp.databean.GenericWebFormData;
import generic.servlet.IBaseConstants;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Hashtable;
import java.text.SimpleDateFormat;

import transaction.databean.ReportEnquiryData;
import transaction.databean.TransactionData;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;

import jdbc.JdbcConnection;

public class ReceiptPrint extends MasterServlet implements IBaseConstants {


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

    TransactionData tData = (TransactionData)request.getAttribute(IBaseConstants.kGenericWebFormData);
    ReportEnquiryData reData = new ReportEnquiryData();
    String txNo = tData.getData(TransactionData.txNo);
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
	try {
		
	  if(txNo==null||txNo.equals(""))	
	  {
		  conn = JdbcConnection.getConnection();
		  ps = conn.prepareStatement("SELECT RECEIPTNO FROM RECEIPT WHERE NAME = ? ORDER BY CREATEDON DESC");
		  ps.setString(1, tData.getData(TransactionData.name));
		  rs = ps.executeQuery();
		  if(rs.next())
		  {
			  txNo = rs.getString(1);
		  }
	  }
		
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      JavaReportGenerate rptDrv = new JavaReportGenerate();
      ArrayList data = rptDrv.getReceiptPrintData(txNo);
      
      ReceiptPrintGenerate gen = new ReceiptPrintGenerate(data, "Customer Copy");
      baos = gen.getReportCode();
      
      OutputStream outStream = new FileOutputStream("c:\\logic_receipt\\"+txNo+"_A.pdf");
      baos.writeTo(outStream);
      
      gen = new ReceiptPrintGenerate(data, "Admin Copy");
      baos = gen.getReportCode();
      
      outStream = new FileOutputStream("c:\\logic_receipt\\"+txNo+"_B.pdf");
      baos.writeTo(outStream);
      
      gen = new ReceiptPrintGenerate(data, "Account Copy");
      baos = gen.getReportCode();
      
      outStream = new FileOutputStream("c:\\logic_receipt\\"+txNo+"_C.pdf");
      baos.writeTo(outStream);

	  PdfToImg conv = new PdfToImg("c:\\logic_receipt\\"+txNo+"_A.pdf","c:\\logic_receipt\\"+txNo+"_A.png");
	  conv = new PdfToImg("c:\\logic_receipt\\"+txNo+"_B.pdf","c:\\logic_receipt\\"+txNo+"_B.png");
	  conv = new PdfToImg("c:\\logic_receipt\\"+txNo+"_C.pdf","c:\\logic_receipt\\"+txNo+"_C.png");

      FileInputStream psStream_a = null;   
      FileInputStream psStream_b = null;   
      FileInputStream psStream_c = null;   

      try {   
    	  psStream_a = new FileInputStream("c:\\logic_receipt\\"+txNo+"_A.png");   
    	  psStream_b = new FileInputStream("c:\\logic_receipt\\"+txNo+"_B.png");   
    	  psStream_c = new FileInputStream("c:\\logic_receipt\\"+txNo+"_C.png");   
          } catch (FileNotFoundException ffne) {   
            ffne.printStackTrace();   
          }   
          if (psStream_a == null||psStream_b == null||psStream_c == null) {   
              return;   
          }         
       
System.out.println("Start Direct Print....");   
  
      DocFlavor psInFormat = DocFlavor.INPUT_STREAM.PNG;   
      Doc myDoc_a = new SimpleDoc(psStream_a, psInFormat, null);    
      Doc myDoc_b = new SimpleDoc(psStream_b, psInFormat, null); 
      Doc myDoc_c = new SimpleDoc(psStream_c, psInFormat, null); 
      PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();  
      aset.add(MediaSizeName.JAPANESE_POSTCARD);
     
      // this step is necessary because I have several printers configured
      PrintService myPrinter = null;
System.out.println("PrintService required:"+tData.getData(tData.printerName));   

      myPrinter = PrintServiceLookup.lookupDefaultPrintService();
System.out.println("PrintService default:"+myPrinter.getName());  

//Hardcode Printer name by remote address
	  String remoteAddr = request.getRemoteAddr();
	  String remoteHost = request.getRemoteHost();
	  if(remoteAddr!=null&&remoteAddr.equals("192.168.0.2"))
	  {
		tData.setData(tData.printerName, "\\\\COUNTERPC2\\Printer2");
	  }
	  else if(remoteAddr!=null&&remoteAddr.equals("192.168.0.3"))
	  {
		tData.setData(tData.printerName, "\\\\counterpc3\\Printer3");
	  }
System.out.println("REMOTE:"+remoteAddr+"||"+remoteHost+"||"+tData.getData(tData.printerName));	  

      if(!myPrinter.getName().equals(tData.getData(tData.printerName)))
      {
      	PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);
System.out.println("PrintService length:"+services.length);   

      	for (int i = 0; i < services.length; i++){
        	  String svcName = services[i].toString();
	          myPrinter = services[i];
System.out.println("PrintService is not null:"+svcName);
	          //if(myPrinter.getName().equals(tData.getData(tData.printerName)))
	          //if(myPrinter.getName().equals("Printer1")) 
			  if(myPrinter.getName().equals(tData.getData(tData.printerName))||myPrinter.getName().equals("Printer1"))
        	  {
	              break;
	          }
	          else
	          {
        		  myPrinter = null;
	          }
	}
      }
      if (myPrinter != null) {
  System.out.println("PrintService confirmed:"+myPrinter.getName());   
if (!myPrinter.isDocFlavorSupported(DocFlavor.INPUT_STREAM.POSTSCRIPT))
{
System.out.println("The printer does not support POSTSCRIPT");
}
if (!myPrinter.isDocFlavorSupported(DocFlavor.INPUT_STREAM.PDF))
{
System.out.println("The printer does not support PDF");
}
if (!myPrinter.isDocFlavorSupported(DocFlavor.INPUT_STREAM.PNG))
{
System.out.println("The printer does not support PNG");
}
if (!myPrinter.isDocFlavorSupported(DocFlavor.INPUT_STREAM.JPEG))
{
System.out.println("The printer does not support JPEG");
}

          DocPrintJob job = myPrinter.createPrintJob();   
          try {   
System.out.println("job.print(Customer Copy);   ");  
          job.print(myDoc_a, aset);   
          DocPrintJob jobb = myPrinter.createPrintJob();   
System.out.println("job.print(Account Copy);   ");  
  		  jobb.print(myDoc_b, aset); 
          DocPrintJob jobc = myPrinter.createPrintJob();   
System.out.println("job.print(Admin Copy);   ");  
		  jobc.print(myDoc_c, aset);   

          } catch (Exception pe) {pe.printStackTrace();}   
      } else {   
          System.out.println("no printer services found");   
      }   
	}catch(Exception e) {
		e.printStackTrace();
	}
	finally{
		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}
	String contextPath = (String)request.getSession().getAttribute("contextPath");
	response.sendRedirect(response.encodeRedirectURL(contextPath + "\\servlet\\shortcutServlet?shortcut=TX"));


  } 

  protected Object getFormRequest (HttpServletRequest request) {
      Vector v = new Vector(); 
      v.add((String)request.getParameter("start"));
      v.add((String)request.getParameter("end"));
      return v;
  }
  
  private void wait(int n)
  {
	  long t0,t1;
      t0=System.currentTimeMillis();
      do{
          t1=System.currentTimeMillis();
      }
      while ((t1-t0)<(n*1000));
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