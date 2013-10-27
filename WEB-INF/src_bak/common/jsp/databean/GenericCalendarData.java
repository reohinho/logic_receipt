package common.jsp.databean;

import java.text.NumberFormat;

import java.util.Calendar;


public class GenericCalendarData 
{
  public static int EMPTY_VALUE = Integer.MAX_VALUE ;
  private int dayValue[] ; // This array contains the entire month value
  private int dayStyle[] ;
  private String dayAnchor [] ;
  
  private String fontColorName [] = new String [20] ;
  private String [] dayStyleName = new String [20] ; // composition of <background style>-<foreground style>
  
  private Calendar dateBegin ;
  private Calendar dateEnd ;

  private String title ;
  private String titleStyle ;

  private boolean monthsHighlight[] ;
  private String monthHighlightStyle ;
  
  private String headStyle ;  // Mon, Tue ... Sun
  
  private String defaultStyle ; // for those not specified

  private final int weekdayCellWidth=50 ;
  private final int weekdayInputCellWidth=60 ;
  

  private String calDayStyle = "calendarDay" ;

  private int[] alternateDeliveryCounts_Total = new int[10];
  private int[] alternateDeliveryCounts_Monthly = new int[10];
  
  public GenericCalendarData() {}

  public Calendar getDateBegin () { return dateBegin ; }
  public Calendar getDateEnd () { return dateEnd ; }

  public int[] getValues () { return dayValue ; }
  public int[] getStyles () { return dayStyle ; }
  public String[] getAnchors () { return dayAnchor ; }

  public void setMonthStyle (boolean[] hlMonth, int style) {
    monthsHighlight = hlMonth ;
    monthHighlightStyle = getDayStyleName(style) ;  
  }

  public void setTitleDetail (String inTitle, int inTitStyle, int inHeadStyle, int inDefStyle) {
    title = inTitle;
    titleStyle = getDayStyleName(inTitStyle) ;  
    headStyle = getDayStyleName(inHeadStyle) ;
    defaultStyle = getDayStyleName(inDefStyle) ;  
  }
  
  public void setDayBeginEnd (Calendar bg, Calendar en) {
    dateBegin = bg ; 
    dateEnd = en ;
  }


  public void setDayValueStyle (int val[], int sty[]) {
    dayValue = val ;
    dayStyle = sty ;
  }

  public void setDayValueStyle (int val, int sty, int pos) {
    if (pos >=0 && pos < dayValue.length) {
      dayValue[pos] = val ;
      dayStyle[pos] = sty ;
    }
  }

  public void setDayStyleName (int idx1To20, String styleName) {
    if (idx1To20 >=1 && idx1To20 <=20) {
      dayStyleName[idx1To20-1] = styleName ;
    }
  }

  public String getDayStyleName (int idx1To20) {
    if (idx1To20 >=1 && idx1To20 <=20) {
      return dayStyleName[idx1To20-1] ;
    }
    return null ;
  }

  public void setFontColorName (int idx1To20, String fontColor) {
    if (idx1To20 >=1 && idx1To20 <=20) {
      fontColorName[idx1To20-1] = fontColor ;
    }
  }

  public String getFontColorName (int idx1To20) {
    if (idx1To20 >=1 && idx1To20 <=20) {
      return fontColorName[idx1To20-1] ;
    }
    return null ;
  }  

  public int getStyleOn (int pos) 
  {
    if (pos >= 0 && pos < dayStyle.length)
      return dayStyle[pos] ;
    else return -1 ;
  }

  public String getHtml () {
     return getHtml (false,0);          
  } 

  public String getHtml (boolean dispalyMonthlyCount, int dispalyMonthlyCount_patten) 
  {
//System.out.println("GenericCalendarData.getHtml() =>" + (new Date()).toString()  );  
    StringBuffer s = new StringBuffer() ;
    Calendar tmpBegin = Calendar.getInstance()  ; // month level traversing from first month to last month
    tmpBegin.setTime(dateBegin.getTime());
    String allMonths = "<TABLE border=0 width=100% cellspacing=3 cellpadding=3>" ;
    int monthCount = 0;
    int arrarpos = 0 ;
    while (tmpBegin.getTime().before(dateEnd.getTime())) {
      monthCount ++ ;
      if (monthCount % 2 == 1) 
        allMonths += "<TR>" ;
      String monthBgStyle = "" ;
      if (monthsHighlight != null && monthsHighlight.length > monthCount && monthsHighlight[monthCount]) {
        monthBgStyle = " class='" + monthHighlightStyle + "' ";
      }
      allMonths += "<TD VALIGN=TOP" + monthBgStyle + ">" ;
      
      String htmlMonth = "";
      if (dispalyMonthlyCount) { // for display monthly alternate delivery count.
        htmlMonth = getOneMonth (tmpBegin, arrarpos,true, dispalyMonthlyCount_patten) ;
      }else {
        htmlMonth = getOneMonth (tmpBegin, arrarpos) ;
      }  
      
      allMonths += htmlMonth ;
      allMonths += "</TD>" ;
      arrarpos += tmpBegin.getActualMaximum(Calendar.DAY_OF_MONTH) ;
      if (tmpBegin.get(Calendar.MONTH) == Calendar.DECEMBER)
        tmpBegin.roll(Calendar.YEAR, 1) ;
      tmpBegin.roll(Calendar.MONTH, 1);
      if (monthCount % 2 == 0) {
        // the display shows 2 months in the same row
        allMonths += "</TR>" ;
      }
    }
    if (monthCount % 2 == 1) {
        allMonths += "<TD></TD></TR>" ;
    }
    allMonths += "</TABLE>";
//System.out.println("finish GenericCalendarData =>" + (new Date()).toString()  );      
    return allMonths ;
  }



  public String getHtml_input () {
     return getHtml_input (false,0);          
  } 

  public String getHtml_input (boolean dispalyMonthlyCount, int dispalyMonthlyCount_patten) 
  {
//System.out.println("GenericCalendarData.getHtml_input() " + ( new Date().toString()));  
    StringBuffer s = new StringBuffer() ;
    Calendar tmpBegin = Calendar.getInstance()  ; // month level traversing from first month to last month
    tmpBegin.setTime(dateBegin.getTime());
    String allMonths = "<TABLE border=0 width=100% cellspacing=3 cellpadding=3>" ;
    int monthCount = 0;
    int arrarpos = 0 ;
    while (tmpBegin.getTime().before(dateEnd.getTime())) {
      monthCount ++ ;
      if (monthCount % 2 == 1) 
        allMonths += "<TR>" ;
      String monthBgStyle = "" ;
      if (monthsHighlight != null && monthsHighlight.length > monthCount && monthsHighlight[monthCount]) {
        monthBgStyle = " class='" + monthHighlightStyle + "' ";
      }
      allMonths += "<TD VALIGN=TOP" + monthBgStyle + ">" ;
      
      String htmlMonth = "";
      if (dispalyMonthlyCount) { // for display monthly alternate delivery count.
        htmlMonth = getOneMonth_input (tmpBegin, arrarpos,true, dispalyMonthlyCount_patten) ;
      }else {
        htmlMonth = getOneMonth_input (tmpBegin, arrarpos) ;
      }  
      
      allMonths += htmlMonth ;
      allMonths += "</TD>" ;
      arrarpos += tmpBegin.getActualMaximum(Calendar.DAY_OF_MONTH) ;
      if (tmpBegin.get(Calendar.MONTH) == Calendar.DECEMBER)
        tmpBegin.roll(Calendar.YEAR, 1) ;
      tmpBegin.roll(Calendar.MONTH, 1);
      if (monthCount % 2 == 0) {
        // the display shows 2 months in the same row
        allMonths += "</TR>" ;
      }
    }
    if (monthCount % 2 == 1) {
        allMonths += "<TD></TD></TR>" ;
    }
    allMonths += "</TABLE>";
//System.out.println("end GenericCalendarData.getHtml_input() " + (new Date().toString()) );      
    return allMonths ;
  }



  private String getOneMonth (Calendar calStart, int valIdxStart) {
        return  getOneMonth (calStart, valIdxStart,false,0);
  }


  /** 
   * @param calStart first day of the month
   * @param valIdxStart position of the dayValue array
   * @return only 1 rendered month in HTML format
   */
  private String getOneMonth (Calendar calStart, int valIdxStart, boolean display_AlternateDeliveryCount, int patten) {
//System.out.println("start GenericCalendarData.getOneMonth" + (new Date()).toString() );
    // call methos to calculate the monthly alternate delivery.
    String altrnateDeliveryMonthlyCount = "";
    if (display_AlternateDeliveryCount) {
        resetMonthlyCountOfAlternateDelivery();
        setMonthlyCountForAlternateDelivery(calStart,valIdxStart);
        altrnateDeliveryMonthlyCount = getMonthlyCountOfAlternateDelivery(patten);
    }

    String monthTitleStr = getMonthString (calStart.get(Calendar.MONTH)) + " " + calStart.get(Calendar.YEAR);
    int dayCellCount = 0 ; // counter for a week

    StringBuffer s = new StringBuffer() ;
    
    // Month table begin
      s.append("<table border='1' cellspacing='0' style='border-collapse: collapse' bordercolor='#111111'>") ; s.append('\n') ;

    // Title row
      s.append("  <tr>") ; s.append('\n') ;
      s.append("    <td colspan='3'  class='"+titleStyle+"' width='"+(10+weekdayCellWidth*3)+"'>"+monthTitleStr+"</td>") ;
      s.append("    <td align='right' colspan='4' class='"+titleStyle+"' width='"+(10+weekdayCellWidth*4)+"'>"+altrnateDeliveryMonthlyCount+"</td>") ;
      s.append('\n') ;
      s.append("  </tr>") ; s.append('\n') ;
      
    // Week head row
      s.append("  <tr class='"+headStyle+"'> ") ; s.append('\n') ;
      s.append("    <td width='"+weekdayCellWidth+"'>Sun</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayCellWidth+"'>Mon</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayCellWidth+"'>Tue</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayCellWidth+"'>Wed</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayCellWidth+"'>Thu</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayCellWidth+"'>Fri</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayCellWidth+"'>Sat</td>") ; s.append('\n') ;
      s.append("  </tr>") ; s.append('\n') ;

    // first row of the month
      s.append("  <tr>") ; s.append('\n') ; // first row begin
      
      // print blank cell if beginning of the month is not sunday
      int dayOfWeek = calStart.get (Calendar.DAY_OF_WEEK) ;
      for (int i = Calendar.SUNDAY ; i < dayOfWeek ; i++) {
        dayCellCount ++ ;
        s.append("    <td>") ; s.append('\n') ;
        s.append(getCellBlank ()) ;s.append('\n') ;
        s.append("    </td>") ; s.append('\n') ;
      }
      // end print blank cell on the first row

      int dayEndOfMonth = calStart.getActualMaximum(Calendar.DAY_OF_MONTH) ;
      for (int j = 1 ; j <= dayEndOfMonth ; j++) {
        if (dayCellCount % 7 == 0) {  // case for end of week row
          s.append("  </tr>") ; s.append('\n') ;
        }
        dayCellCount ++ ;
        s.append("    <td>") ; s.append('\n') ;
        int refIdx = valIdxStart+j-1 ;
        //dayAnchor
        
        String cellStyle = getDayStyleName (dayStyle[refIdx] ) ;
        if (cellStyle==null) cellStyle = "" ;
        String cellBgStyle = cellStyle, 
               cellFgStyle = cellStyle ;

        if (cellStyle.indexOf('-')!=-1) {
          if (dayValue[refIdx] == EMPTY_VALUE)
            cellBgStyle = "" ;
          else
            cellBgStyle = cellStyle.substring(0,cellStyle.indexOf('-')) ;
          cellFgStyle = cellStyle.substring(cellStyle.indexOf('-')+1, cellStyle.length()) ; 
        }

        

        // day cell begin
          s.append("<TABLE border=0 width=100% cellspacing=0 cellpadding=0>") ;s.append('\n') ;
          s.append("<tr>") ;s.append('\n') ;
          s.append("<td width=12 align=left class='"+calDayStyle+"'> " + j + " </td>") ;s.append('\n') ;
          s.append("<td class='"+cellBgStyle+"'>&nbsp;</td>") ;s.append('\n') ;
          if (dayValue[refIdx] == EMPTY_VALUE)
            s.append("<td class='"+cellBgStyle+"'>&nbsp;</td>") ;
          else {
            s.append("<td align=right class='"+cellBgStyle+"'><font class='"+cellFgStyle+"'>"+dayValue[refIdx]+"</font></td>") ;
          }
          s.append("</tr>") ;s.append('\n') ;
          s.append("</TABLE>") ;s.append('\n') ;
        // day cell end

        s.append("    </td>") ; s.append('\n') ;

        // handle <tr> open event when it is Sat cell
        if (dayCellCount % 7 == 0 && j < dayEndOfMonth) // open <tr> for another row
          s.append("    <tr>") ; s.append('\n') ;
      }


      // print blank cell at the end of the month row
      if (dayCellCount % 7 != 0) {
        for (int k = dayCellCount % 7+1  ; k <= 6 ; k++) {
          dayCellCount ++ ;
          s.append("    <td>") ; s.append('\n') ;
          s.append(getCellBlank ()) ;s.append('\n') ;
          s.append("    </td>") ; s.append('\n') ;
        }
      }

    // close the last row
      s.append("  </tr>") ; s.append('\n') ;
      
    // End of the month table
      s.append("</table>") ; s.append('\n') ;
//System.out.println("finish GenericCalendarData.getOneMonth" + (new Date()).toString() );          
    return s.toString() ;
  }


  private String getOneMonth_input (Calendar calStart, int valIdxStart) {
        return  getOneMonth_input (calStart, valIdxStart,false,0);
  }


  /** 
   * @param calStart first day of the month
   * @param valIdxStart position of the dayValue array
   * @return only 1 rendered month in HTML format for input
   */
  private String getOneMonth_input (Calendar calStart, int valIdxStart, boolean display_AlternateDeliveryCount, int patten) {
    // call methos to calculate the monthly alternate delivery.
    String altrnateDeliveryMonthlyCount = "";
    if (display_AlternateDeliveryCount) {
        resetMonthlyCountOfAlternateDelivery();
        setMonthlyCountForAlternateDelivery(calStart,valIdxStart);
        altrnateDeliveryMonthlyCount = getMonthlyCountOfAlternateDelivery(patten);
    }

    String monthTitleStr = getMonthString (calStart.get(Calendar.MONTH)) + " " + calStart.get(Calendar.YEAR);
    int dayCellCount = 0 ; // counter for a week
   
    StringBuffer s = new StringBuffer() ;
    
    // Month table begin
      s.append("<table border='1' width='100%' cellspacing='0' style='border-collapse: collapse' bordercolor='#111111'>") ; s.append('\n') ;

    // Title row
      s.append("  <tr>") ; s.append('\n') ;
      s.append("    <td colspan='3'  class='"+titleStyle+"' width='"+(10+weekdayInputCellWidth*3)+"'>"+monthTitleStr+"</td>") ;
      s.append("    <td align='right' colspan='4' class='"+titleStyle+"' width='"+(10+weekdayInputCellWidth*4)+"'>"+altrnateDeliveryMonthlyCount+"</td>") ;
      s.append('\n') ;
      s.append("  </tr>") ; s.append('\n') ;
      
    // Week head row
      s.append("  <tr class='"+headStyle+"'> ") ; s.append('\n') ;
      s.append("    <td width='"+weekdayInputCellWidth+"'>Sun</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayInputCellWidth+"'>Mon</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayInputCellWidth+"'>Tue</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayInputCellWidth+"'>Wed</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayInputCellWidth+"'>Thu</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayInputCellWidth+"'>Fri</td>") ; s.append('\n') ;
      s.append("    <td width='"+weekdayInputCellWidth+"'>Sat</td>") ; s.append('\n') ;
      s.append("  </tr>") ; s.append('\n') ;

    // first row of the month
      s.append("  <tr>") ; s.append('\n') ; // first row begin
      
      // print blank cell if beginning of the month is not sunday
      int dayOfWeek = calStart.get (Calendar.DAY_OF_WEEK) ;
      for (int i = Calendar.SUNDAY ; i < dayOfWeek ; i++) {
        dayCellCount ++ ;
        s.append("    <td>") ; s.append('\n') ;
        s.append(getCellBlank ()) ;s.append('\n') ;
        s.append("    </td>") ; s.append('\n') ;
      }
      // end print blank cell on the first row

      int dayEndOfMonth = calStart.getActualMaximum(Calendar.DAY_OF_MONTH) ;
      String allowInput = "";
      for (int j = 1 ; j <= dayEndOfMonth ; j++) {
        if (dayCellCount % 7 == 0) {  // case for end of week row
          s.append("  </tr>") ; s.append('\n') ;
        }
        dayCellCount ++ ;
        s.append("    <td>") ; s.append('\n') ;
        int refIdx = valIdxStart+j-1 ;
        //dayAnchor

        String cellStyle = getDayStyleName (dayStyle[refIdx] ) ;
        if (cellStyle==null) cellStyle = "" ;
        String cellBgStyle = cellStyle, 
               cellFgStyle = cellStyle ;

        if (cellStyle.indexOf('-')!=-1) {
          if (dayValue[refIdx] == EMPTY_VALUE)
            cellBgStyle = "" ;
          else
            cellBgStyle = cellStyle.substring(0,cellStyle.indexOf('-')) ;
          cellFgStyle = cellStyle.substring(cellStyle.indexOf('-')+1, cellStyle.length()) ; 
        }

        // check if allow input new quantity.
        allowInput = dayAnchor[refIdx];  
        // day cell begin
          s.append("<TABLE border=0 width=100% cellspacing=0 cellpadding=0>") ;s.append('\n') ;
          s.append("<tr valign='middle' >") ;s.append('\n') ;
//          s.append("<tr>") ;s.append('\n') ;          
          s.append("<td width=18 align=left class='"+calDayStyle+"'> " + j + " </td>") ;s.append('\n') ;
          s.append("<td class='"+cellBgStyle+"'>&nbsp;</td>") ;s.append('\n') ;
          if (dayValue[refIdx] == EMPTY_VALUE){
              if (allowInput.equalsIgnoreCase("y")) {
                 s.append("<td> <input name='" + getFormat_Date(calStart.get(Calendar.YEAR),calStart.get(Calendar.MONTH),j) + "' type='text' size='5' value='' ></td>") ;
              } else{
                 s.append("<td class='"+cellBgStyle+"'>&nbsp;</td>") ;
              }
          } else {
            if (allowInput.equalsIgnoreCase("y")) {
//           s.append("<td align=right class='"+cellBgStyle+"'><font class='"+cellFgStyle+"'>"+dayValue[refIdx]+"</font></td>") ;
          s.append("<td> <input name='" + getFormat_Date(calStart.get(Calendar.YEAR),calStart.get(Calendar.MONTH),j) + "' type='text' size='5' style='color:" +this.getFontColorName(dayStyle[refIdx])+ "' value='" + dayValue[refIdx] + "' > </td>");
              }else {
                 s.append("<td align=right class='"+cellBgStyle+"'><font class='"+cellFgStyle+"'>"+dayValue[refIdx]+"</font></td>") ;
       s.append("<input type='hidden' name='" + getFormat_Date(calStart.get(Calendar.YEAR),calStart.get(Calendar.MONTH),j) + "' value='"+dayValue[refIdx]+"'>") ;
            }
          }
          s.append("</tr>") ;s.append('\n') ;
          s.append("</TABLE>") ;s.append('\n') ;
        // day cell end

        s.append("    </td>") ; s.append('\n') ;

        // handle <tr> open event when it is Sat cell
        if (dayCellCount % 7 == 0 && j < dayEndOfMonth) // open <tr> for another row
          s.append("    <tr>") ; s.append('\n') ;
      }


      // print blank cell at the end of the month row
      if (dayCellCount % 7 != 0) {
        for (int k = dayCellCount % 7+1  ; k <= 6 ; k++) {
          dayCellCount ++ ;
          s.append("    <td>") ; s.append('\n') ;
          s.append(getCellBlank ()) ;s.append('\n') ;
          s.append("    </td>") ; s.append('\n') ;
        }
      }

    // close the last row
      s.append("  </tr>") ; s.append('\n') ;
      
    // End of the month table
      s.append("</table>") ; s.append('\n') ;
        
    return s.toString() ;
  }


  // method to define cell format

  private String getMonthString (int month) 
  {
    switch (month) 
    {
      case Calendar.JANUARY : return "JANUARY" ;
      case Calendar.FEBRUARY : return "FEBRUARY" ;
      case Calendar.MARCH : return "MARCH" ;
      case Calendar.APRIL : return "APRIL" ;
      case Calendar.MAY : return "MAY" ;
      case Calendar.JUNE : return "JUNE" ;
      case Calendar.JULY : return "JULY" ;
      case Calendar.AUGUST : return "AUGUST" ;
      case Calendar.SEPTEMBER : return "SEPTEMBER" ;
      case Calendar.OCTOBER : return "OCTOBER" ;
      case Calendar.NOVEMBER : return "NOVEMBER" ;
      case Calendar.DECEMBER : return "DECEMBER" ;
      default : return "" ;
    }
      
  }
  
  /** 
   * @return return 1 row 4 column blank HTML table
   */
  private String getCellBlank() {
     return "<TABLE border=0 width='100%' cellspacing='0' cellpadding='0'>" +
            "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>" + 
            "</table>" ;
  }

/**
 * get total count of days for a alternative delivery
 */
public int getCountOfAlternateDelivery(int altDelv) {
     if (altDelv >=0 && altDelv <9) {
       return alternateDeliveryCounts_Total[altDelv];
     } else {
         return 0;
     }  
}

public void setCountForAlternateDelivery() {
      for (int s=0; s < dayStyle.length; s++) {
           switch (dayStyle[s]) {
               case 0 : alternateDeliveryCounts_Total[0]++ ; break;           
               case 1 : alternateDeliveryCounts_Total[1]++ ; break;
               case 2 : alternateDeliveryCounts_Total[2]++ ; break;
               case 3 : alternateDeliveryCounts_Total[3]++ ; break;
               case 4 : alternateDeliveryCounts_Total[4]++ ; break;               
               case 5 : alternateDeliveryCounts_Total[5]++ ; break;
               case 6 : alternateDeliveryCounts_Total[6]++ ; break;
               case 7 : alternateDeliveryCounts_Total[7]++ ; break;
               case 8 : alternateDeliveryCounts_Total[8]++ ; break;
               case 9 : alternateDeliveryCounts_Total[9]++ ; break;
           } 
       }   
}

/**
 * get predefined monthly count display of alternative delivery.
 */
public String getMonthlyCountOfAlternateDelivery(int patten) {
       String display_count = "";
       switch(patten) {
          case 1 : display_count = "HRP: " + (alternateDeliveryCounts_Monthly[2] + alternateDeliveryCounts_Monthly[4]) + 
                                   " / School: " + (alternateDeliveryCounts_Monthly[1] + alternateDeliveryCounts_Monthly[3]);
                   break;
                   
          case 2 : display_count = "HRP: " + (alternateDeliveryCounts_Monthly[2] + alternateDeliveryCounts_Monthly[4]);
                   break;
                   
          case 3 : display_count = " / School: " + (alternateDeliveryCounts_Monthly[1] + alternateDeliveryCounts_Monthly[3]);
                   break;
                   
          case 4 : display_count = "Non-publishing day(s): " + (alternateDeliveryCounts_Monthly[5]);
                   break;   
          // School Order Delivery Date with the days have to be updated
          case 5 : display_count = "School: "+(alternateDeliveryCounts_Monthly[1] + alternateDeliveryCounts_Monthly[3] + alternateDeliveryCounts_Monthly[9]);
                   break;
                          
       }
       return display_count;
}

public void resetMonthlyCountOfAlternateDelivery() {
       alternateDeliveryCounts_Monthly = new int[10];
}

public void setMonthlyCountForAlternateDelivery(Calendar calStart, int valIdxStart) {
      int dayEndOfMonth = calStart.getActualMaximum(Calendar.DAY_OF_MONTH) ;
      for (int j = 1 ; j <= dayEndOfMonth ; j++) {
              int refIdx = valIdxStart+j-1 ;
            switch (dayStyle[refIdx]) {
               case 0 : alternateDeliveryCounts_Monthly[0]++ ; break;           
               case 1 : alternateDeliveryCounts_Monthly[1]++ ; break;
               case 2 : alternateDeliveryCounts_Monthly[2]++ ; break;
               case 3 : alternateDeliveryCounts_Monthly[3]++ ; break;
               case 4 : alternateDeliveryCounts_Monthly[4]++ ; break;               
               case 5 : alternateDeliveryCounts_Monthly[5]++ ; break;
               case 6 : alternateDeliveryCounts_Monthly[6]++ ; break;
               case 7 : alternateDeliveryCounts_Monthly[7]++ ; break;
               case 8 : alternateDeliveryCounts_Monthly[8]++ ; break;
               case 9 : alternateDeliveryCounts_Monthly[9]++ ; break;
           } 
       }   
}

  public void setDayAnchor(String[] input_anchor){
        this.dayAnchor = input_anchor ;
  }

 public String getFormat_Date(int year, int month, int date){
  StringBuffer strBff = new StringBuffer();
  NumberFormat nf = NumberFormat.getInstance();
    nf.setGroupingUsed(false);
    nf.setMinimumIntegerDigits(2);
    strBff.append(year);
    strBff.append(nf.format(month +1));
    strBff.append(nf.format(date));
    strBff.append("01");
    return  strBff.toString();
 }

  public static void main (String arg[]) 
  {
     common.jsp.databean.GenericCalendarData gcd = new common.jsp.databean.GenericCalendarData () ;
     int val[] = {9,8,7,6,5,4,3,2,1,0,99,88,77,66,55,44,33,22,11,0,1,2,3,4,5,6,7,8,9,0,common.jsp.databean.GenericCalendarData.EMPTY_VALUE,    
                  119,118,117,116,115,114,113,112,111,110,1199,1188,1177,1166,1155,1144,1133,1122,1111,110,111,112,113,114,115,116,117,118,119,110,111,
                  9,8,7,6,5,4,3,2,1,0,1199,1188,1177,1166,1155,1144,1133,1122,1111,110,111,112,113,114,115,116,117,118,119,110
                  } ;
     int sty[] = {2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,   
                  2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,
                  2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1} ;
     gcd.setDayValueStyle(val, sty);
     java.util.Calendar c1 = java.util.Calendar.getInstance() ;
     c1.setTime(java.sql.Date.valueOf("2004-07-01"));  
     java.util.Calendar c2 = java.util.Calendar.getInstance() ;
     c2.setTime(java.sql.Date.valueOf("2004-09-30"));  
     gcd.setDayBeginEnd(c1, c2);
     gcd.setDayStyleName(1, "school-calendarDayFont");
     gcd.setDayStyleName(2, "hrp-calendarDayFont");
     gcd.setDayStyleName(3, "calendarHeader");
     gcd.setDayStyleName(4, "columnhead");
     gcd.setDayStyleName(5, "school");
     gcd.setTitleDetail("myMonth", 3,4,5);

     String sss = gcd.getHtml() ;

  }

  
  
}