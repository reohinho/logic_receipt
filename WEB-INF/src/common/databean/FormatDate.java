package common.databean;

import com.scmp.circ.utility.*;

import java.sql.Timestamp;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FormatDate 
{

private static DateFormatSymbols dfs = new DateFormatSymbols() ;

/**
 * repeat the character
 * @param charater
 * @param repeat count
 * @return final string
 * 
 */

public final static String repeatChar(char c, int count) {
   char[] chars = new char[count];
   while(count > 0 ) chars[--count] = c; 
   return new String(chars);
}



/**
 * check the input string conform database constrain.
 * @param test value
 * @param database field size
 * @param database field scale (decimal point)
 */
public static boolean checkDBFormat(String input,int real,int dec ) {
        
        String strInput = input.trim();
        boolean isVaild = true;
        double d = 0.0;
        int length = strInput.length();
        int idx = strInput.indexOf(".");
      if( (length > (real - dec) && idx == -1) || (length > (real + 1) && idx != -1) ) { 
            isVaild = false;
      }else {

        try {
             d = Double.parseDouble(strInput);
             if(idx != -1) { // input is positive
                    if ( length - (idx + 1) > dec  ){
                        isVaild = false;
                     }
             }       
        } catch (NumberFormatException nfe)  {
           isVaild = false;
        }
      }   
    return isVaild; 
}


/**
 * @param Date String in dd-MM-yy or yyyyMMdd format
 * @return dd-MMM-yy for fornt end dispaly.
 */
public static String shortMonth (String input) {
          String str = input;
          if(input.indexOf("-") == 2 && input.lastIndexOf("-") == 5) {
             str = (input.substring(0,3) + getShortMonth(input.substring(3,5)) + input.substring(5) );
          }else if( input.length() == 8 && input.indexOf("-") < 0) {
             str =  (input.substring(6) + "-" + getShortMonth(input.substring(4,6)) + "-" + input.substring(2,4) );                            
          }
          return str;
}

/**
 * @param the month in number format.
 * @return the name of month in short format. 
 */
public static String getShortMonth(String month) {
        try {
            return dfs.getShortMonths()[Integer.parseInt(month) -1 ];           
        } catch(NumberFormatException nfe) {
            return month;
        } catch (ArrayIndexOutOfBoundsException aiob) {
            return month;            
        }
}

/** 
 *  get the array of the months' name in short format
 * @return String[]
 */ 
public static String[] getShortMonthArray() 
{
    String arr[] = new String[12];
    try 
    {
        String tmp[] = dfs.getShortMonths();
        for (int i=0; i< 12; i++) 
            arr[i] = tmp[i];
        return arr;
    } catch (Exception e) 
    {
        return arr;
    }
}

/** 
 *  get the array of the months' fullname
 * @return String[] 
 */
public static String[] getMonthArray() 
{

    String arr[] = new String[12];
    try 
    {
        String tmp[] = dfs.getMonths();
        for (int i=0; i< 12; i++) 
            arr[i] = tmp[i];
        return arr;
    } catch (Exception e) 
    {
        return arr;
    }
}

/**
 * @param Date String
 * @param Format of the Date String
 * @return Date String in dd-MMM-yy format
 */
public static String getShortMonth(String input, String inputFormat) {
               String format = "dd-MMM-yy"; 
               SimpleDateFormat sdf = new SimpleDateFormat(); 
               sdf.setLenient(false);
               String str = input;
           try {
                  sdf.applyPattern(inputFormat);
                  Date date = sdf.parse(input);
                  sdf.applyPattern(format);
                  str = sdf.format(date);
           }catch(ParseException pe) {
              return str;
           }
           return str;
}

public static String getMonth(String month) {
        try {
            return dfs.getMonths()[Integer.parseInt(month) -1 ];           
        } catch(NumberFormatException nfe) {
            return month;
        } catch (ArrayIndexOutOfBoundsException aiob) {
            return month;            
        }
}

public static String getKey(String input) {
        try {
          int year = Integer.parseInt(input.substring(6));  
          int month = Integer.parseInt(input.substring(3,5));  
          int date = Integer.parseInt(input.substring(0,2));            

             if(input.indexOf("-") == 2 && input.lastIndexOf("-") == 5 ) {
                 return com.scmp.circ.utility.CircUtilities.getStringFromTimestamp_yyyymmdd(com.scmp.circ.utility.CircUtilities.getTimestampByString(input));                     
              }else {
                 return input;
              }  
        } catch (NumberFormatException nfe) {
          return input;
        }
}

/**
 * @param Date String in dd-MM-yy or dd-MMM-yy format
 * @return yyyyMMdd format
 */

public static String get_yyyyMMdd(String input) {
       SimpleDateFormat sdf = new SimpleDateFormat();
        try {
             if(input.indexOf("-") == 2 && input.lastIndexOf("-") == 5 ) {
                  sdf.applyPattern("dd-MM-yy");
                  java.util.Date dt = sdf.parse(input);
                  sdf.applyPattern("yyyyMMdd"); 
                  input = sdf.format(dt);
             }else if (input.indexOf("-") == 2 && input.lastIndexOf("-") == 6) {
                  sdf.applyPattern("dd-MMM-yy");
                  java.util.Date dt = sdf.parse(input);
                  sdf.applyPattern("yyyyMMdd"); 
                  input = sdf.format(dt);
              }
                
        } catch (Exception e) {
             return input;
        }
       return input;
}

/**
 * @param Date String 
 * @param Format of Date String
 * @return yyyyMMdd format
 */

public static String get_yyyyMMdd(String input, String inputFormat) {
       SimpleDateFormat sdf = new SimpleDateFormat();
       sdf.setLenient(false);
        try {
                  sdf.applyPattern(inputFormat);
                  java.util.Date dt = sdf.parse(input);
                  sdf.applyPattern("yyyyMMdd"); 
                  input = sdf.format(dt);
        } catch (Exception e) {
             System.out.println(e.getMessage());
             return input;
        }
       return input;
}

/** 
 * generate timestamp
 * @param time string
 * @param time string pattern
 * @return timestamp or null
 */

public static Timestamp get_Timestamp(String input,String pattern) {
       SimpleDateFormat sdf = new SimpleDateFormat();
       sdf.setLenient(false);
       Timestamp ts = null;
        try {
            sdf.applyPattern(pattern);
             java.util.Date dt = sdf.parse(input);
             ts = new Timestamp(dt.getTime()); 
             
        }catch (ParseException pe) {
        }
        return ts;
}

/** 
 * @param string in yyyymmdd or dd-MM-yy or dd-MMM-yy format
 * @return timestamp
 */

public static Timestamp get_Timestamp(String input) {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
       sdf.setLenient(false);
       Timestamp ts = null;
        try {
             if(input.indexOf("-") == 2 && input.lastIndexOf("-") == 5 ) {
                  sdf.applyPattern("dd-MM-yy");
                  java.util.Date dt = sdf.parse(input);
                  ts = new Timestamp(dt.getTime());                  
             }else if (input.indexOf("-") == 2 && input.lastIndexOf("-") == 6) {
                  sdf.applyPattern("dd-MMM-yy");
                  java.util.Date dt = sdf.parse(input);
                  ts = new Timestamp(dt.getTime());                  
             }else if (input.indexOf("-") == 4 && input.lastIndexOf("-") == 7) {
                  sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
                  java.util.Date dt = sdf.parse(input);
                  ts = new Timestamp(dt.getTime());                                    
              }else {
                  sdf.applyPattern("yyyyMMdd");
                  java.util.Date dt = sdf.parse(input);
                  ts = new Timestamp(dt.getTime());                            
              }    
        } catch (Exception e) {
             return ts;
        }
       return ts;
}

/** 
 * 
 *  @return current timestamp
 */

public static Timestamp current_Timestamp() {
    return get_Timestamp( get_currenyDate("dd-MM-yy"));
}

/**
 * @param Date String in yyyy-mm-dd hh:mm:ss 
 * @return Date String in dd-MMM-yy format
 */
//public static String get_Date_Dispaly_ddMMyy(java.sql.Date date) {
//              return get_Timestamp_Dispaly_ddMMMyy( date.toString() );
//}
/**
 * @param Date String in yyyy-mm-dd hh:mm:ss 
 * @return Date String in dd-MMM-yy format
 */
public static String get_Timestamp_Dispaly_ddMMMyy(String input) {
               String format = "dd-MMM-yy"; 
               SimpleDateFormat sdf = new SimpleDateFormat(); 
               sdf.setLenient(false);
               String str = input;
           try {
              if (input.indexOf("-") == 4 && input.lastIndexOf("-") == 7 && input.length() > 10) {
                  sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
                  Date date = sdf.parse(input);
                  sdf.applyPattern(format);
                  str = sdf.format(date);
              }
           }catch(ParseException pe) {
              return str;
           }
           return str;
}


/**
 * @param Date String in yyyy-mm-dd hh:mm:ss 
 * @return Date String in dd-MMM-yy hh:mm:ss format
 */
public static String get_Timestamp_Dispaly(String input) {
               String format = "dd-MMM-yy hh:mm:ss"; 
               SimpleDateFormat sdf = new SimpleDateFormat();
               sdf.setLenient(false);
               String str = input;
           try {
              if (input.indexOf("-") == 4 && input.lastIndexOf("-") == 7 && input.length() > 10) {
                  sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
                  Date date = sdf.parse(input);
                  sdf.applyPattern(format);
                  str = sdf.format(date);
              }
           }catch(ParseException pe) {
              return str;
           }
           return str;
}

/**
 * @param Timestamp 
 * @return Date String in dd-MMM-yy hh:mm:ss format
 */
public static String get_Timestamp_Dispaly(Timestamp timestamp) {
              return get_Timestamp_Dispaly(timestamp.toString());    
}

/**
 * @param Timestamp 
 * @return Date String in dd-MMM-yy hh:mm:ss format
 */
public static String get_Timestamp_Dispaly(Timestamp timestamp,String format) {
               SimpleDateFormat sdf = new SimpleDateFormat(); 
               sdf.setLenient(false);
               
              if (timestamp == null ) {
                  return null;                    
              }else {
               String str = timestamp.toString();
           try {
                  sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
                  Date date = sdf.parse(str);
                  sdf.applyPattern(format);
                  str = sdf.format(date);
           }catch(ParseException pe) {
              return str;
           }
           return str;
              }
}

/**
 * @param Date String in yyyymmdd or dd-MMM-yy format
 * @return Date String in dd-MM-yy format
 */
public static String get_ddMMyy(String input) {
               String format = "dd-MM-yy"; 
               SimpleDateFormat sdf = new SimpleDateFormat(); 
               sdf.setLenient(false);
               String str = input;
           try {
            if (input.indexOf("-") == 2 && input.lastIndexOf("-") == 6 && input.length() == 9) {
                  sdf.applyPattern("dd-MMM-yy");
                  Date date = sdf.parse(input);
                  sdf.applyPattern(format);
                  str = sdf.format(date);
            }else if (input.length() == 8 && input.indexOf("-") < 0) {
                  sdf.applyPattern("yyyyMMdd");
                  Date date = sdf.parse(input);
                  sdf.applyPattern(format);
                  str = sdf.format(date);
            }
           }catch (ParseException pe){
             return str;
           }
            return str;
                
}

/**
 * @param Date String, Date Format 
 * @return Date String in dd-MM-yy format
 */
public static String get_ddMMyy(String input, String format) {
               String out_format = "dd-MM-yy"; 
               SimpleDateFormat sdf = new SimpleDateFormat(); 
               sdf.setLenient(false);
               String str = input;
           try {
                  sdf.applyPattern(format);
                  Date date = sdf.parse(input);
                  sdf.applyPattern(out_format);
                  str = sdf.format(date);
           }catch (ParseException pe){
             return str;
           }
            return str;
}


/**
 * @param result date String format
 * @return String of date in specified format
 */
public static String get_currenyDate(String format) {

      Date current = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat (format);
     return sdf.format(current);
}

/**
 * @param original date
 * @param number of month add
 * @retur date with new month and date set to 01.
 */
   public static String addMonths(String origDate, int months){
       Calendar osd = Calendar.getInstance();
       String newDate = origDate;
       osd.clear(Calendar.YEAR);
       osd.clear(Calendar.MONTH);
       osd.clear(Calendar.DATE);
       osd.clear(Calendar.HOUR);
       osd.clear(Calendar.MINUTE);
       osd.clear(Calendar.SECOND);
       
         
         if (origDate.length() == 8 && origDate.indexOf("-") < 0) {
              osd.set(Integer.parseInt( origDate.substring(0,4)) , Integer.parseInt(origDate.substring(4,6)) -1, osd.getActualMinimum(Calendar.DATE));             
           try {
              osd.set(Calendar.MONTH, (osd.get(Calendar.MONTH) + months));
              newDate = osd.get(Calendar.YEAR) + "" + format_WithLeadingZero(osd.get(Calendar.MONTH) +1) + "" +   format_WithLeadingZero(osd.get(Calendar.DATE));
            }catch (NumberFormatException nfe) {
             // return orig date if error             
            }

         }

        return newDate;
   }

public static String format_WithLeadingZero(long input) {
    NumberFormat nf;
    nf = NumberFormat.getInstance();
    nf.setGroupingUsed(false);
    nf.setMinimumIntegerDigits(2);
    return nf.format(input);
    
}


public static Calendar getStartDateOfMonth(String inputDate) {
        Calendar calStart = null;
        if (inputDate != null && !inputDate.equals("")) {
            Timestamp st = get_Timestamp(inputDate);
             if (st != null) {
                   calStart = Calendar.getInstance();
                   calStart.setTime(new java.sql.Date(st.getTime()));
                   calStart.set(Calendar.DAY_OF_MONTH,1);
             }      
        }
        return calStart;
} 

public static Calendar getEndDateOfMonth(String inputDate) {
        Calendar calEnd = null;
        if (inputDate != null && !inputDate.equals("")) {
            Timestamp et = get_Timestamp(inputDate);
             if (et != null) {
                   calEnd = Calendar.getInstance();
                   calEnd.setTime(new java.sql.Date(et.getTime()));
                   calEnd.set(Calendar.DAY_OF_MONTH, calEnd.getActualMaximum(Calendar.DAY_OF_MONTH)  );
             }      
        }
        return calEnd;
} 


    public static void main(String[] args)  {
    System.out.println(getEndDateOfMonth("20040731").toString() );
    }
}