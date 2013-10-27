/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/databean/DataFormatter.java,v $
  $Author: leo.ching $
  $Date: 2008/08/23 06:41:15 $
  $Revision: 1.3 $
*/
package common.databean;


import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Vector;
import java.util.Locale;


public class DataFormatter 
{
  public DataFormatter() {}

  public static SimpleDateFormat frontendDateFormat = new SimpleDateFormat("dd/MMM/yy", Locale.US) ;
  public static SimpleDateFormat frontendDateFormatWithTime = new SimpleDateFormat("dd/MMM/yy HH:mm:ss", Locale.US) ;
  public static SimpleDateFormat frontendDateFormatddMMyyWithTime = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.US) ;  
  public static SimpleDateFormat frontendDateFormatInput = new SimpleDateFormat("dd/MM/yy", Locale.US) ;
  public static SimpleDateFormat frontendDateFormatYYYYMMDD = new SimpleDateFormat("yyyyMMdd", Locale.US);
  public static SimpleDateFormat frontendDateFormatYYYYMMDDWithTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.US);
  public static SimpleDateFormat backendDateFormatYYYYMMDDWithTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
  public static DecimalFormat frontendNumberFormat = new DecimalFormat ("0.0000;-0.0000") ;
  public static DecimalFormat frontendNumberFormat2DecPlc = new DecimalFormat ("0.00;-0.00") ;
  public static DecimalFormat frontendNumberFormat2DecPlcWithThs = new DecimalFormat ("#,###.00;-#,###.00") ;
  public static DecimalFormat frontendNumberFormatWithThs = new DecimalFormat ("#,###.0000;-#,###.0000") ;
  public static DecimalFormat frontendIntegerFormat = new DecimalFormat ("0;-0") ;
  public static DecimalFormat frontendExchangeRateFormat = frontendNumberFormat ;

  public static String Timestamp2ScreenFormat (Timestamp ts) {
    if (ts == null) return "" ;
    java.util.Date inDateFormat = (java.util.Date) ts ;
    return frontendDateFormat.format(inDateFormat) ;
  }
  public static String Timestamp2ScreenFormatInput (Timestamp ts) {
    if (ts == null) return "" ;
    java.util.Date inDateFormat = (java.util.Date) ts ;
    return frontendDateFormatInput.format(inDateFormat) ;
  }
  public static String Timestamp2ScreenFormatWithTime (Timestamp ts) {
    if (ts == null) return "" ;
    java.util.Date inDateFormat = (java.util.Date) ts ;
    return frontendDateFormatWithTime.format(inDateFormat) ;
  }
  
  public static String Timestamp2YYYYMMDDFormat (Timestamp ts) 
  {
    if (ts == null) return "";
    java.util.Date inDateFormat = (java.util.Date) ts;
    return frontendDateFormatYYYYMMDD.format(inDateFormat);
  }

  public static String Timestamp2YYYYMMDDWithTimeFormat (Timestamp ts) 
  {
    if (ts == null) return "";
    java.util.Date inDateFormat = (java.util.Date) ts;
    return frontendDateFormatYYYYMMDDWithTime.format(inDateFormat);
  }
  
  public static String Timestamp2YYYYMMDDWithTimeFormatBackEnd (Timestamp ts) 
  {
    if (ts == null) return "";
    java.util.Date inDateFormat = (java.util.Date) ts;
    return backendDateFormatYYYYMMDDWithTime.format(inDateFormat);
  }

  public static String Double2ScreenFormat (Double dbl) {
    if (dbl == null) return "" ;
    return frontendNumberFormat.format(dbl.doubleValue()) ;
  }
  public static String Double2ScreenFormat (double dbl) {
    return frontendNumberFormat.format(dbl) ;
  }

  public static String Double2ScreenFormatWithThs(double dbl) {
	   return frontendNumberFormatWithThs.format(dbl) ;
  }
  public static String Double2ScreenFormatWithThs(Double dbl) {
	  if (dbl==null ) return "";
	   return frontendNumberFormatWithThs.format(dbl) ;
  }
  public static String Double2ScreenFormat2DecPlc (Double dbl) {
    if (dbl == null) return "0.00" ;
    return frontendNumberFormat2DecPlc.format(dbl.doubleValue()) ;
  }
  
  public static String Double2ScreenFormat2DecPlc (double dbl) {
    return frontendNumberFormat2DecPlc.format(dbl) ;
  }
  
  public static String Double2ScreenFormat2DecPlcWithThs (double dbl) {
	   return frontendNumberFormat2DecPlcWithThs.format(dbl) ;
  }
  
  public static String Double2ScreenFormat2DecPlcWithThs (Double dbl) {
	  if (dbl==null ) return "";
	   return frontendNumberFormat2DecPlcWithThs.format(dbl) ;
  }

  public static String Double2ScreenFormatInteger (Double dbl) {
    if (dbl == null) return "" ;
    return frontendIntegerFormat.format(dbl.doubleValue()) ;
  }
  public static String Double2ScreenFormatInteger (double dbl) {
    return frontendIntegerFormat.format(dbl) ;
  }

  public static String Double2ScreenFormatExchangeRate (Double dbl) {
    if (dbl == null) return "" ;
    return frontendExchangeRateFormat.format(dbl.doubleValue()) ;
  }
  public static String Double2ScreenFormatExchangeRate (double dbl) {
    return frontendExchangeRateFormat.format(dbl) ;
  }


 

public static String displayErrorMsgVec(Vector vec) {
        StringBuffer str = new StringBuffer();
            if (vec != null && vec.size() > 0) {
                 for (int g=0; g< vec.size(); g++) {
                     if ( ((String)vec.get(g)).trim().equals("") == false) {
                         str.append((String)vec.get(g));
                         str.append("<br>");
                     }
                 }
                 if (str.length() > 0){
                   str.setLength(str.length() - 4);
                 }
            }
            return str.toString();
}


  private static String FormatIfNull(String s)
  {
    return (s == null? "" : s) ;
  }

  
  public static void main (String arg[]) 
  {

   //  double d = 1234.567 ;
    // print current timestamp in front end date format
/**
    System.out.println( DataFormatter.Timestamp2ScreenFormatWithTime ( new Timestamp(Calendar.getInstance().getTime().getTime())) );
    System.out.println( DataFormatter.Timestamp2ScreenFormatInput ( new Timestamp(Calendar.getInstance().getTime().getTime())) );
    System.out.println( "---" ) ;
    System.out.println( DataFormatter.Double2ScreenFormat (new Double (d)) ) ;
    System.out.println( DataFormatter.Double2ScreenFormat2DecPlc (new Double (d)) ) ;
    System.out.println( DataFormatter.Double2ScreenFormatInteger (new Double (d)) ) ;
    System.out.println( "---" ) ;
    System.out.println( DataFormatter.Double2ScreenFormat ( d ) ) ;
    System.out.println( DataFormatter.Double2ScreenFormat2DecPlc ( d ) ) ;
    System.out.println( DataFormatter.Double2ScreenFormatInteger ( d ) ) ;
    System.out.println( "---" ) ;
    System.out.println( DataFormatter.Double2ScreenFormat ( d ) ) ;
*/    
  }
  
}