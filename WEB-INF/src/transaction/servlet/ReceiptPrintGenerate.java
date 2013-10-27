package transaction.servlet;

import java.io.ByteArrayOutputStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.PageSize;
import com.lowagie.text.Chunk;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.Rectangle;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import common.databean.GenericTable;
import java.text.DecimalFormat;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import java.util.TreeSet;

import java.util.Calendar;
import java.sql.Timestamp;
import com.scmp.circ.utility.CircUtilities;

import java.sql.*;
import jdbc.JdbcConnection;
import common.jsp.databean.GeneralDataInHtml;

public class ReceiptPrintGenerate  {
  ArrayList data;
  String params;
  
  public ReceiptPrintGenerate(ArrayList data, String params) {
    try {
      this.data = data;
      this.params = params;
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
  	
	public ByteArrayOutputStream getReportCode() { 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.B4,40,200,120,20); 
		try{
			PdfWriter writer = PdfWriter.getInstance(document, baos);
      MyPageEvents events = new MyPageEvents();
      writer.setPageEvent(events);
      document.open();
      if(data.size() == 0) {
        PdfPTable table = new PdfPTable(1);
        table.addCell(printStr("",6,8,false,PdfPCell.NO_BORDER,0));
        document.add(table);
      }    
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);    

        int border = 0;
        
        //Receipt Infos
        String row[] = (String[]) data.get(0);      
        table.addCell(printStr("",1,18,false,border,1));        

        table.addCell(printStr(row[17],1,22,false,border,1));        
        table.addCell(printStr(" ",1,18,false,border,1));    
        table.addCell(printStr(" ",1,18,false,border,1));    
        
        table.addCell(printStr("Name: "+row[0],1,22,false,border,0)); 
	table.addCell(printStr("      "+row[13],1,22,false,border,0)); 
        table.addCell(printStr("Sex: "+row[14],1,22,false,border,0)); 
        table.addCell(printStr("DOB: "+row[15],1,22,false,border,0)); 

        if(row[12].equals("A"))
        	table.addCell(printStr("Nationality: "+GeneralDataInHtml.getCountryName(row[2]),1,22,false,border,0));    
        table.addCell(printStr("PPT/Ref. No.: "+row[1],1,22,false,border,0));    

        table.addCell(printStr(" ",1,18,false,border,1));    
        table.addCell(printStr(" ",1,18,false,border,1));  
        double cashTotal = 0;
        double chequeTotal = 0;
        table.addCell(printStr(row[3]+"  x  "+row[4]+"  @$"+row[5],1,22,false,border,0));           
        for(int i=1; i<data.size();i++) {
          String item[] = (String[]) data.get(i);             
          table.addCell(printStr(item[3]+"  x  "+item[4]+"  @$"+item[5],1,22,false,border,0));           
        } 
        table.addCell(printStr(" ",1,18,false,border,1));    
        table.addCell(printStr(" ",1,18,false,border,1));    

        double amt = Double.parseDouble(row[9]);
        if(row[7].equals("cash"))
        {        table.addCell(printStr("Cash Total: HK$"+amt+"0",1,22,false,border,0));    }
        else
        {
        		 table.addCell(printStr("Cheque: HK$"+amt+"0",1,22,false,border,0));
        		 table.addCell(printStr("--"+row[8],1,22,false,border,0));
        }
        table.addCell(printStr(" ",1,18,false,border,1));    

        //Count Collection Date
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int dayAdd = 2;
        int dayCount = 2;
        //int receiptDate = Integer.parseInt(row[18]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
        java.util.Date rDate = sdf.parse(row[18]); 
        java.sql.Timestamp receiptDate = new java.sql.Timestamp(rDate.getTime()); 
        // 86400000 milliseconds in a day
        long oneDay = 1 * 24 * 60 * 60 * 1000;
        
        try
        {
        	conn = JdbcConnection.getConnection();
        
        	for(int k=1;k<=dayCount;k++)
        	{
        		receiptDate.setTime(receiptDate.getTime() + oneDay);
      
        		//System.out.println("select 1 from holiday where date = "+(receiptDate));
        		ps = conn.prepareStatement("select 1 from holiday where date = ?");
        		ps.setTimestamp(1, receiptDate);
            	rs = ps.executeQuery();
            	if(rs.next())
            	{
            		dayCount +=1;
            		dayAdd += 1;
            	}
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
        
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(row[18].substring(0,4)), Integer.parseInt(row[18].substring(4,6))-1, Integer.parseInt(row[18].substring(6)));
        cal.add(cal.DATE, dayAdd);
        
        Date date = cal.getTime();
        SimpleDateFormat df =
            new SimpleDateFormat("dd-MMM-yyyy (EEE)",Locale.US);

        table.addCell(printStr(" ",1,18,false,border,1));   
        if(row[12].equals("A"))
        {
        	table.addCell(printStr("Collection Date: "+df.format(date),1,22,false,border,1));    
        	table.addCell(printStr("Collection Time: 4:00pm to 5:00pm",1,22,false,border,1));    
        	table.addCell(printStr(" ",1,22,false,border,1)); 
        	table.addCell(printStr(row[10]+" "+row[16],1,22,false,border,1)); 
        	table.addCell(printStr(" ",1,22,false,border,1)); 
        	table.addCell(printStr("APPLICATION IS SUBJECTED TO APPROVAL, PLEASE BRING THIS RECEIPT ON COLLECTION DAY",1,22,false,border,1));    
        	table.addCell(printStr(row[11],1,32,false,border,1));  
        	table.addCell(printStr("("+this.params+")",1,22,false,border,2));    

        }
        else
        {
        	table.addCell(printStr("THANK YOU",1,22,false,border,1));    
        	table.addCell(printStr(row[10]+"   "+row[16],1,22,false,border,1)); 
        	table.addCell(printStr(" ",1,22,false,border,1)); 
        	table.addCell(printStr(row[11],1,22,false,border,1));  
        	table.addCell(printStr("("+this.params+")",1,22,false,border,2));    
        }

       
        float[] widths4 = {10};
        table.setWidths(widths4);
        document.add(table);
			document.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return baos;
	}

  public PdfPCell printStr(String text,int colspan,int fontsize,boolean bold,int border,int align) {
        try {
          if(text == null)
            text = "";
            Paragraph p = new Paragraph();
            float padding = 3.0f;
            //set bold feature
            if(bold) {
                p.add(new Chunk(text, new Font(Font.getFamilyIndex("Arial"), fontsize,Font.BOLD)));
                padding = 3.0f;
            }else
                p.add(new Chunk(text, new Font(Font.getFamilyIndex("Arial"), fontsize)));
            PdfPCell cell =  new PdfPCell(p);
            //set colspan feature
            cell.setColspan(colspan);
            //set alignment feature
            if(align==0)
                cell.setHorizontalAlignment(cell.ALIGN_LEFT);
            else if(align==1)
                cell.setHorizontalAlignment(cell.ALIGN_CENTER);
            else  if(align==2)
                cell.setHorizontalAlignment(cell.ALIGN_RIGHT);
            //set border feature
                cell.setBorder(border);
            cell.setPaddingBottom(padding);  
            return cell;
        }catch(Exception e) {
            return null;
        }
    }

    public PdfPCell printStrTopLine(String text,int colspan,int fontsize,boolean bold,boolean border,int align) {
        try {
          if(text == null)
            text = "";
            Paragraph p = new Paragraph();
            float padding = 3.0f;
            //set bold feature
            if(bold) {
                p.add(new Chunk(text, new Font(Font.getFamilyIndex("Arial"), fontsize,Font.BOLD)));
                padding = 3.0f;
            }else
                p.add(new Chunk(text, new Font(Font.getFamilyIndex("Arial"), fontsize)));
            PdfPCell cell =  new PdfPCell(p);
            //set colspan feature
            cell.setColspan(colspan);
            //set alignment feature
            if(align==0)
                cell.setHorizontalAlignment(cell.ALIGN_LEFT);
            else if(align==1)
                cell.setHorizontalAlignment(cell.ALIGN_CENTER);
            else  if(align==2)
                cell.setHorizontalAlignment(cell.ALIGN_RIGHT);
            //set border feature
            cell.setBorder(cell.NO_BORDER);
            cell.setBorder(cell.TOP);
            cell.setPaddingBottom(padding);  
            return cell;
        }catch(Exception e) {
            return null;
        }
    }

    public PdfPCell printStrBoldUnderline(String text,int colspan,int fontsize,int align) {
        try {
            Paragraph p = new Paragraph();
            float padding = 3f;
            //set bold feature
                p.add(new Chunk(text, new Font(Font.getFamilyIndex("Arial"), fontsize,Font.BOLD)));
            PdfPCell cell =  new PdfPCell(p);
            //set colspan feature
            cell.setColspan(colspan);
            //set alignment feature
            if(align==0)
                cell.setHorizontalAlignment(cell.ALIGN_LEFT);
            else if(align==1)
                cell.setHorizontalAlignment(cell.ALIGN_CENTER);
            else  if(align==2)
                cell.setHorizontalAlignment(cell.ALIGN_RIGHT);
            //set border feature
            cell.disableBorderSide(cell.LEFT);
            cell.disableBorderSide(cell.RIGHT);
            cell.disableBorderSide(cell.TOP);
            cell.setPaddingBottom(padding);         
            return cell;
        }catch(Exception e) {
            return null;
        }
    }
    
    
    public PdfPCell printLine(int colspan) {
        try {
            Paragraph p = new Paragraph();
            PdfPCell cell =  new PdfPCell(p);
            
            cell.setColspan(colspan);
            //set alignment feature
            cell.setBorder(cell.NO_BORDER);
       
            cell.setBorder(cell.BOTTOM);
            return cell;
        }catch(Exception e) {
            return null;
        }
    }

    class Speaker implements Comparable {
        
        // name of the speaker
        private String name;
        
        // number of occurrances
        private int occurrance = 1;
        
        public Speaker(String name) {
            this.name = name;
        }
        
        public String name() {
            return name;
        }
        
        public int occurrance() {
            return occurrance;
        }
        
        public int compareTo(Object o) {
            Speaker otherSpeaker = (Speaker) o;
            if (otherSpeaker.name().equals(name)) {
                this.occurrance += otherSpeaker.occurrance();
                otherSpeaker.occurrance = this.occurrance;
                return 0;
            }
            return name.compareTo(otherSpeaker.name());
        }
    }
    
    class MyPageEvents extends PdfPageEventHelper { //Override iText's class to handle total page number.
        final int FONT_SIZE = 8;
        PdfContentByte cb;
        PdfTemplate template;
        BaseFont arial = null;
        
        TreeSet speakers = new TreeSet();
        
        float pageYPos = 0;
        float pageXPos = 0;
        float tableBottom1 = 0;
        float xAxis = 0;
        
        String companyCode = "";
        
        final private String IMAGE = "image";
        final int MAX_IMAGE_HEIGHT = 40;
        
        public MyPageEvents() {
            try {
                arial = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        public void onOpenDocument(PdfWriter writer, Document document) {
            try {
                cb = writer.getDirectContent();
                template = cb.createTemplate(50, 50);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        public void onCloseDocument(PdfWriter writer, Document document) {
            template.beginText();
            template.setFontAndSize(arial, FONT_SIZE);
            template.showText(String.valueOf(writer.getPageNumber() - 1));
            template.endText();
        }
        
        public TreeSet getSpeakers() {
            return speakers;
        }
        
        public void onStartPage(PdfWriter writer, Document document) {
          try {
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);

            PdfPTable tab1 = new PdfPTable(1);
           
            
          //sub table 2
            PdfPTable subtab2 = new PdfPTable(1);
            subtab2.setWidthPercentage(100);     
            subtab2.addCell(printStr("SINGAPORE CONSULATE-GENERAL HONG KONG",1,25,true,PdfPCell.NO_BORDER,1));
            subtab2.addCell(printStr("Tel:2527 2212",1,25,true,PdfPCell.NO_BORDER,1));
            PdfPCell subcel2 =  new PdfPCell(subtab2);
            subcel2.setColspan(1);
            subcel2.setHorizontalAlignment(subcel2.ALIGN_LEFT);
            subcel2.setBorder(subcel2.NO_BORDER);
            subcel2.setPaddingBottom(0.0f); 
            tab1.addCell(subcel2);    


            float[] widths = {80};
            tab1.setWidths(widths);
            PdfPCell cel1 =  new PdfPCell(tab1);
            cel1.setColspan(1);
            cel1.setHorizontalAlignment(cel1.ALIGN_LEFT);
            cel1.setBorder(cel1.NO_BORDER);
            cel1.setPaddingBottom(0.0f); 
            cel1.setFixedHeight(120f);
            table.addCell(cel1);
            //col headers
            float[] widths4 = {80};
            table.setWidths(widths4);
            Rectangle page = document.getPageSize();
            table.setTotalWidth(page.width() - document.leftMargin() - document.rightMargin());
            table.writeSelectedRows(0, -1, 20, page.height() - 20 + table.getTotalHeight()-90, cb);

            
          }catch(Exception e) {
            e.printStackTrace();
          }
        }
    }
}

