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
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import common.databean.GenericTable;
import java.text.DecimalFormat;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import java.util.TreeSet;

import transaction.databean.ReportEnquiryData;

public class VisaSummaryReportGenerate  {
  ArrayList data;
  ReportEnquiryData reData;
  public VisaSummaryReportGenerate(ArrayList data, ReportEnquiryData reData) {
    try {
      this.data = data;
      this.reData = reData;
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
  	
	public ByteArrayOutputStream getReportCode() { 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4.rotate(),20,20,110,20); 
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
System.out.println("data size="+data.size());
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);    
        double totalCash = 0;
        double totalCheque = 0;
        double cashAmt = 0;
        double chequeAmt = 0;
        int border = 0;
        boolean cur = false;
        boolean lockbox = false;
        for(int i=0; i<data.size();i++) {
          String row[] = (String[]) data.get(i);
          int top=0;
          top = PdfPCell.TOP;

          if((i+1) < data.size()) {
              String nextrow[] = (String[]) data.get(i+1);
              //currency grouping
              if(row[0].equals(nextrow[0])) {
                  border = PdfPCell.LEFT+PdfPCell.RIGHT;
              } else {
                  border = PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.BOTTOM;   
              } 
          } else {
            border = PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.BOTTOM;   
          }
          border +=top;
          table.addCell(printStr(new Integer(i+1).toString(),1,10,false,border,0));        
          table.addCell(printStr(row[1]+" "+row[2],1,10,false,border,0));                         
          table.addCell(printStr(row[6]+"/"+row[5],1,10,false,border,0));                
          table.addCell(printStr(row[4],1,10,false,border,0));                
          table.addCell(printStr(row[3],1,10,false,border,0));                
          table.addCell(printStr(row[0],1,10,false,border,0));                  
          table.addCell(printStr(row[7],1,10,false,border,0));  
          table.addCell(printStr(row[8],1,10,false,border,0));                
          table.addCell(printStr(" ",1,10,false,border,0));         
         
        } 
        float[] widths4 = {8,22,16,11,13,
                              12,20,12,12};
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
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);

            PdfPTable tab1 = new PdfPTable(4);
            tab1.setWidthPercentage(100);
            //sub table 1
            PdfPTable subtab1 = new PdfPTable(1);
            subtab1.setWidthPercentage(100);     
            subtab1.addCell(printStr("",1,8,false,PdfPCell.NO_BORDER,0));
            SimpleDateFormat in = new SimpleDateFormat("yyyyMMddkkmmss");
            SimpleDateFormat out = new SimpleDateFormat("MM/dd/yyyy kk:mm:ss");
            
            Date startDate = in.parse(reData.getData(reData.periodStart));
            Date endDate = in.parse(reData.getData(reData.periodEnd));
            
            subtab1.addCell(printStr("Period Start Date: "+out.format(startDate),1,8,false,PdfPCell.NO_BORDER,0));
            subtab1.addCell(printStr("Period End Date: "+out.format(endDate),1,8,false,PdfPCell.NO_BORDER,0));
	      //subtab1.addCell(printStr("Period Start Date: "+reData.getData(reData.periodStart),1,8,false,PdfPCell.NO_BORDER,0));
   	      //subtab1.addCell(printStr("Period End Date: "+reData.getData(reData.periodEnd),1,8,false,PdfPCell.NO_BORDER,0));
            PdfPCell subcel1 =  new PdfPCell(subtab1);
            subcel1.setColspan(1);
            subcel1.setHorizontalAlignment(subcel1.ALIGN_LEFT);
            subcel1.setBorder(subcel1.NO_BORDER);
            subcel1.setPaddingBottom(0.0f); 
            tab1.addCell(subcel1);    
            
          //sub table 2
            PdfPTable subtab2 = new PdfPTable(1);
            subtab2.setWidthPercentage(100);     
            subtab2.addCell(printStr("VISA SUMMARY REPORT",1,12,true,PdfPCell.NO_BORDER,1));
            subtab2.addCell(printStr("CONSULATE-GENERAL SINGAPORE IN HONG KONG",1,14,true,PdfPCell.NO_BORDER,1));
            PdfPCell subcel2 =  new PdfPCell(subtab2);
            subcel2.setColspan(1);
            subcel2.setHorizontalAlignment(subcel2.ALIGN_LEFT);
            subcel2.setBorder(subcel2.NO_BORDER);
            subcel2.setPaddingBottom(0.0f); 
            tab1.addCell(subcel2);    

            tab1.addCell(printStr("",1,8,false,PdfPCell.NO_BORDER,0));
            
          //sub table 3  
            PdfPTable subtab3 = new PdfPTable(1);
            subtab3.setWidthPercentage(100);     
            subtab3.addCell(printStr("Print Date: "+new SimpleDateFormat("MM/dd/yyyy HH:mm aa").format(new Date()),1,8,false,PdfPCell.NO_BORDER,0));
            subtab3.addCell(printStr("\n",1,8,false,PdfPCell.NO_BORDER,0));
            subtab3.addCell(printStr("Page: ",1,8,false,PdfPCell.NO_BORDER,0)); 
            PdfPCell subcel3 =  new PdfPCell(subtab3);
            subcel3.setColspan(1);
            subcel3.setHorizontalAlignment(subcel3.ALIGN_LEFT);
            subcel3.setBorder(subcel3.NO_BORDER);
            subcel3.setPaddingBottom(0.0f); 
            tab1.addCell(subcel3);    

            float[] widths = {70,230,10,60};
            tab1.setWidths(widths);
            PdfPCell cel1 =  new PdfPCell(tab1);
            cel1.setColspan(16);
            cel1.setHorizontalAlignment(cel1.ALIGN_LEFT);
            cel1.setBorder(cel1.NO_BORDER);
            cel1.setPaddingBottom(0.0f); 
            cel1.setFixedHeight(50f);
            table.addCell(cel1);
            //col headers
            table.addCell(printStr("No.",1,10,true,PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.TOP+PdfPCell.BOTTOM,0));        
            table.addCell(printStr("Name of Applicant",1,10,true,PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.TOP+PdfPCell.BOTTOM,0));      
            table.addCell(printStr("Sex/DOB",1,10,true,PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.TOP+PdfPCell.BOTTOM,0)); 
            table.addCell(printStr("Nationality",1,10,true,PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.TOP+PdfPCell.BOTTOM,0));       
            table.addCell(printStr("Passport No",1,10,true,PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.TOP+PdfPCell.BOTTOM,0)); 
            table.addCell(printStr("Receipt No",1,10,true,PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.TOP+PdfPCell.BOTTOM,0)); 
            table.addCell(printStr("Transaction Date",1,10,true,PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.TOP+PdfPCell.BOTTOM,0)); 
            table.addCell(printStr("Officer",1,10,true,PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.TOP+PdfPCell.BOTTOM,0)); 
            table.addCell(printStr("Visa No.",1,10,true,PdfPCell.LEFT+PdfPCell.RIGHT+PdfPCell.TOP+PdfPCell.BOTTOM,0)); 
            float[] widths4 = {8,22,16,11,13,
                              12,20,12,12};
            table.setWidths(widths4);
            Rectangle page = document.getPageSize();
            table.setTotalWidth(page.width() - document.leftMargin() - document.rightMargin());
            table.writeSelectedRows(0, -1, 20, page.height() - 20 + table.getTotalHeight()-90, cb);

            int pageN = writer.getPageNumber();
            String text = pageN + " of ";
            pageYPos = page.height() - 80;
            
            xAxis = arial.getWidthPoint(text, FONT_SIZE);
            float len = arial.getWidthPoint(text, FONT_SIZE);
            pageXPos = document.right()-66;
            
            cb.beginText();
            cb.setFontAndSize(arial, FONT_SIZE);
            cb.setTextMatrix(pageXPos - len , pageYPos-tableBottom1);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(template, pageXPos, pageYPos-tableBottom1);
            cb.beginText();
            cb.setFontAndSize(arial, FONT_SIZE);
            cb.setTextMatrix(280, 820);
            cb.endText();
          }catch(Exception e) {
            e.printStackTrace();
          }
        }
    }
}

