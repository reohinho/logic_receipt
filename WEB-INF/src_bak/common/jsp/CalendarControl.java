/**
* $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/CalendarControl.java,v $
* $Author: scmp $
* $Date: 2008/04/28 02:41:14 $
* $Revision: 1.1.1.1 $
*/
package common.jsp;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class CalendarControl 
{
  private static final String[] weekname = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
  private static final String[] monthname = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
  private static final int months_per_row = 4;

  private boolean readWrite = true;

  // 08-09-2005 changed by Wisley Lui for extend canlender from 12 months to 16 months
  //private int duration = 365;
  //private int duration = 489 ; // 1 year (365 days) + 4 months (31 days * 4)
  private int duration = 1219; // 3 year (365days) + 4months (31 dys*4)
  // end changed by Wisley

  private GregorianCalendar startDate;
  private int[] year;
  private int[] month;
  private int[] day;
  private int[] weekday;
  private int[] status;

  // add by charlie
  private Hashtable htCount = new Hashtable();
  // end add by charlie
  
  private int currentWeekday = 0;
  private int endOfControl = duration;

  public static String removeHyphen (String newDate)
  {
    char[] temp = new char[newDate.length()];
    String output = "";
    temp = newDate.toCharArray();
    for (int i = 0; i < temp.length; i++) 
    {
      if (temp[i] != '-') 
      {
        output += temp[i];
      }
    }
    return output;
  }

  public static String ddmmyy_to_yyyymmdd (String ddmmyy) 
  {
    ddmmyy = removeHyphen(ddmmyy);
    if (ddmmyy.length() < 6) 
    {
      return "";
    }
    String day = ddmmyy.substring(0,2);
    String month = ddmmyy.substring(2,4);
    String year = ddmmyy.substring(4,6);
    if (Integer.parseInt(year) < 71) 
    {
      year = "20" + year;
    } else 
    {
      year = "19" + year;
    }
    return year + month + day;
  }

  public static String yyyymmdd_to_ddmmyy (String yyyymmdd)
  {
    if (yyyymmdd.length() < 8)
    {
      return "";
    }
    String day = yyyymmdd.substring(6,8);
    String month = yyyymmdd.substring(4,6);
    String year = yyyymmdd.substring(2,4);
    return day + "-" + month + "-" + year;
  }

  public static String yyyymmdd_to_ddmmmyy (String yyyymmdd)
  {
    if (yyyymmdd.length() < 8)
    {
      return "";
    }
    int day = Integer.parseInt(yyyymmdd.substring (6,8));
    String month = monthname[Integer.parseInt(yyyymmdd.substring (4, 6)) - 1];
    String year = yyyymmdd.substring(0,4);
    return day + " " + month + " " + year;
  }

  public static String timestamp_to_yyyymmdd (Timestamp ts)
  {
    String temp = ts.toString();
    StringTokenizer st = new StringTokenizer (temp, " ");
    String temp2 = st.nextToken();
    st = new StringTokenizer (temp2, "-");
    String year = st.nextToken();
    String month = st.nextToken();
    String day = st.nextToken();
    return year + month + day;
  }

  public static Timestamp yyyymmdd_to_timestamp (String yyyymmdd)
  {
    String day = yyyymmdd.substring(6,8);
    String month = yyyymmdd.substring(4,6);
    String year = yyyymmdd.substring(0,4);
    return Timestamp.valueOf(year + "-" + month + "-" + day + " 00:00:00.000");
  }
  
  /** Default constructor creates a control of a duration of 365 days from the current date.
   *  All status will be blank by default.
   */
  public CalendarControl()
  {
    startDate = new GregorianCalendar();
    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();

    startDate.setFirstDayOfWeek(Calendar.SUNDAY);
    for (int i = 0; i < duration; i++)
    {
      status[i] = 1;
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      if (weekday[i] == 0) 
      {
        weekday[i] = 7;
      }
      year[i] = currDate.get(Calendar.YEAR);
      currDate.add(Calendar.DATE, 1);
    }
  }

  /** This constructor takes a new duration for the calendar control.
   *  It is otherwise the same as the default constructor
   */
  public CalendarControl(int newDuration)
  {
    duration = newDuration;
    startDate = new GregorianCalendar();
    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();

    startDate.setFirstDayOfWeek(Calendar.SUNDAY);
    for (int i = 0; i < duration; i++)
    {
      status[i] = 1;
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      if (weekday[i] == 0) 
      {
        weekday[i] = 7;
      }
      year[i] = currDate.get(Calendar.YEAR);
      currDate.add(Calendar.DATE, 1);
    }
  }

  /** This constructor takes a string which length will be the duration of the calendar control
   *  All characters must be an element of {0, 1, 2, 3}
   */
  public CalendarControl(String newStatus)
  {
    duration = newStatus.length();
    startDate = new GregorianCalendar();
    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();

    startDate.setFirstDayOfWeek(Calendar.SUNDAY);
    for (int i = 0; i < duration; i++)
    {
      status[i] = newStatus.charAt(i);
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      if (weekday[i] == 0) 
      {
        weekday[i] = 7;
      }
      year[i] = currDate.get(Calendar.YEAR);
      currDate.add(Calendar.DATE, 1);
    }
  }

  /** This constructor takes an integer array as the status string.
   *  The length of the array will be the duration of the calendar control
   *  Each element in the array must be {0, 1, 2, 3}
   */
  public CalendarControl(int[] newStatus)
  {
    duration = newStatus.length;
    startDate = new GregorianCalendar();
    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();

    startDate.setFirstDayOfWeek(Calendar.SUNDAY);
    for (int i = 0; i < duration; i++)
    {
      status[i] = newStatus[i];
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      if (weekday[i] == 0) 
      {
        weekday[i] = 7;
      }
      year[i] = currDate.get(Calendar.YEAR);
      currDate.add(Calendar.DATE, 1);
    }
  }

  /** This constructor takes a vector of string which conatain the start date and status 
   *  All characters must be an element of {0, 1, 2, 3}
   *  It also takes a date in the form of yyyymmdd
   *  the vale of HRP & School date remain unchanged even before today..
   *  
   */
  public CalendarControl(Vector vec)
  {
    String newStartDate = (String)vec.get(0);
    String newStatus = (String)vec.get(1);
    if (newStatus.equals("") || newStatus == null) 
    {
      newStatus = this.randomStatus ();
    }
    duration = newStatus.length();
    if (newStartDate.length() == 8) 
    {
      startDate = new GregorianCalendar(
                        Integer.parseInt(newStartDate.substring(0,4)), 
                        Integer.parseInt(newStartDate.substring(4,6)) - 1, 
                        Integer.parseInt(newStartDate.substring(6,8))
                      );
    } else if (newStartDate.length() == 6)
    {
      startDate = new GregorianCalendar(
                        Integer.parseInt(newStartDate.substring(4,6)) + 2000,
                        Integer.parseInt(newStartDate.substring(0,2)) - 1,
                        Integer.parseInt(newStartDate.substring(2,4))
                      );
    } else {
      startDate = new GregorianCalendar();
    }

    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    startDate.setFirstDayOfWeek(Calendar.SUNDAY);
    startDate.setMinimalDaysInFirstWeek(1);

    
    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();
    GregorianCalendar today = new GregorianCalendar();
    int todayDate = today.get(Calendar.DAY_OF_MONTH);
    int todayMonth = today.get(Calendar.MONTH);
    int todayYear = today.get(Calendar.YEAR);

    for (int i = 0; i < duration; i++)
    {
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      int tmpV = Character.getNumericValue(newStatus.charAt(i));
      if (weekday[i] == 0) 
      {
        weekday[i] = 7;
      }
      year[i] = currDate.get(Calendar.YEAR);

      
    // add by charlie to calculate the count of status 2,3 for each month
       if (tmpV == 2 || tmpV == 3) {
           setCount(year[i],month[i]);  
       }
    // end add by charlie   
      

      if ( year[i] > todayYear ||  
          (year[i] == todayYear && month[i] > todayMonth) ||
          (year[i] == todayYear && month[i] == todayMonth && day[i] >= todayDate)
         ) 
      {
        status[i] = tmpV;
      } else 
      {
      // add by charlie 24-02-2004 check if the value is -3 or -4 for HRP & scholl day which before today
      switch (tmpV) {
          case 3 : status[i] = -3;
                   break;
          case 2 : status[i] = -4;
                   break;
          default : status[i] = 0;         
      }
      // ens add by charlie
      }
      currDate.add(Calendar.DATE, 1);
    }
  }



  /** This constructor takes a string which length will be the duration of the calendar control
   *  All characters must be an element of {0, 1, 2, 3}
   *  It also takes a date in the form of yyyymmdd
   */
  public CalendarControl(String newStartDate, String newStatus)
  {
    if (newStatus.equals("") || newStatus == null) 
    {
      newStatus = this.randomStatus ();
    }
    duration = newStatus.length();
    if (newStartDate.length() == 8) 
    {
      startDate = new GregorianCalendar(
                        Integer.parseInt(newStartDate.substring(0,4)), 
                        Integer.parseInt(newStartDate.substring(4,6)) - 1, 
                        Integer.parseInt(newStartDate.substring(6,8))
                      );
    } else if (newStartDate.length() == 6)
    {
      startDate = new GregorianCalendar(
                        Integer.parseInt(newStartDate.substring(4,6)) + 2000,
                        Integer.parseInt(newStartDate.substring(0,2)) - 1,
                        Integer.parseInt(newStartDate.substring(2,4))
                      );
    } else {
      startDate = new GregorianCalendar();
    }

    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    startDate.setFirstDayOfWeek(Calendar.SUNDAY);
    startDate.setMinimalDaysInFirstWeek(1);

    
    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();
    GregorianCalendar today = new GregorianCalendar();
    int todayDate = today.get(Calendar.DAY_OF_MONTH);
    int todayMonth = today.get(Calendar.MONTH);
    int todayYear = today.get(Calendar.YEAR);

    for (int i = 0; i < duration; i++)
    {
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      if (weekday[i] == 0) 
      {
        weekday[i] = 7;
      }
      year[i] = currDate.get(Calendar.YEAR);

      if ( year[i] > todayYear ||  
          (year[i] == todayYear && month[i] > todayMonth) ||
          (year[i] == todayYear && month[i] == todayMonth && day[i] >= todayDate)
         ) 
      {
        status[i] = Character.getNumericValue(newStatus.charAt(i));
      } else 
      {
        status[i] = 0; 
      }

    // add by charlie to calculate the count of status 2,3 for each month
       if (status[i] == 2 || status[i] == 3) {
           setCount(year[i],month[i]);  
       }
    // end add by charlie   
      
      currDate.add(Calendar.DATE, 1);
    }
  }

  // add by charlie
  private void setCount(int year, int month) {
     String countKey = Integer.toString(year) + Integer.toString(month); 
      if(this.htCount.containsKey(countKey) ) {
          ((InnerCount)htCount.get(countKey)).add(1);

      }else {
         InnerCount a = new InnerCount(1);
         this.htCount.put(countKey,a);
      }  

  }

private class InnerCount {
  int tmpCount;

  InnerCount(int initValue) {
     tmpCount = initValue;
  }     

  public void add(int count) {
    tmpCount = tmpCount + count ;
   }

  public int getCount(){
      return tmpCount;
  }
  
}


 private String genTotalDays (int year,int month)
  {
    String output = "";
   try {
       String countKey = Integer.toString(year) + Integer.toString(month); 
       output = " (" + ((InnerCount)this.htCount.get(countKey)).getCount() + " Days)";
    } catch (Exception e) {
      return output;  
    }
    
   return output;
  }

// end add by charlie.



  
  public CalendarControl (String[] newStartDate, String newStatus)
  {
    StringTokenizer st = new StringTokenizer (newStartDate[0], "-");
    String start_day = st.nextToken();
    String start_month = st.nextToken();
    String start_year = st.nextToken();
    
    startDate = new GregorianCalendar (Integer.parseInt(start_year), Integer.parseInt(start_month)-1, Integer.parseInt(start_day));
    // 08-09-2005 changed by Wisley Lui for extend calender from 12 months to 16 months
    //duration = 365;
    //duration =489;
    duration = 1219;
    // end changed by Wisley Lui

    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();
    
    startDate.setFirstDayOfWeek(Calendar.SUNDAY);
    int j = 0;

    for (int i = 0; i < duration; i++)
    {
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      year[i] = currDate.get(Calendar.YEAR);

      if (j < newStartDate.length) {
        StringTokenizer temp_st = new StringTokenizer (newStartDate[j], "-");
        int curr_day = Integer.parseInt(temp_st.nextToken());
        int curr_month = Integer.parseInt(temp_st.nextToken());
        int curr_year = Integer.parseInt(temp_st.nextToken());

        if (curr_day == day[i] && curr_month == month[i]+1 && curr_year == year[i])
        {
          status[i] = Character.getNumericValue(newStatus.charAt(j));
          j++;
        } else 
        {
          status[i] = 0;
        }
      } else 
      {
        status[i] = 0;
      }

      currDate.add(Calendar.DATE, 1);
    }  
  }

  public CalendarControl (String newStartDate, String[] nonPublicationDate) 
  {
    String start_day = newStartDate.substring(6,8);
    String start_month = newStartDate.substring(4,6);
    String start_year = newStartDate.substring(0,4);
    
    startDate = new GregorianCalendar (Integer.parseInt(start_year), Integer.parseInt(start_month)-1, Integer.parseInt(start_day));
    
    // 08-09-2005 changed by Wisley Lui for extend calender from 12 months to 16 months
    //duration = 365;
    //duration =489;
    duration = 1219; // change from 16months to 40months
    // end changed by Wisley Lui

    startDate.setFirstDayOfWeek(Calendar.SUNDAY);

    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    int[] npdDay = new int[nonPublicationDate.length];
    int[] npdMonth = new int[nonPublicationDate.length];
    int[] npdYear = new int[nonPublicationDate.length];

    for (int i = 0; i < nonPublicationDate.length; i++)
    {
      StringTokenizer st = new StringTokenizer (nonPublicationDate[i], "-");
      npdDay[i] = Integer.parseInt(st.nextToken());
      npdMonth[i] = Integer.parseInt(st.nextToken());
      npdYear[i] = Integer.parseInt(st.nextToken());
    }

    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();
    GregorianCalendar today = new GregorianCalendar();
    int todayDate = today.get(Calendar.DAY_OF_MONTH);
    int todayMonth = today.get(Calendar.MONTH);
    int todayYear = today.get(Calendar.YEAR);

    for (int i = 0; i < duration; i++) 
    {
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      if (weekday[i] == 0) 
      {
        weekday[i] = 7;
      }
      year[i] = currDate.get(Calendar.YEAR);

      boolean published = true;
      
      for (int j = 0; j < nonPublicationDate.length; j++)
      {
        if (day[i] == npdDay[j] && month[i]+1 == npdMonth[j] && year[i] == npdYear[j]) 
        {
          published = false;
          break;
        }
      }

      
      if (published && ( year[i] > todayYear ||  
          (year[i] == todayYear && month[i] > todayMonth) ||
          (year[i] == todayYear && month[i] == todayMonth && day[i] >= todayDate))
         ) 
      {
        status[i] = 1;
      } else 
      {
        System.out.println("year = " + year[i]+" month = "+ month[i]+" day = "+ day[i]);
        status[i] = 0; 
      }
      
      currDate.add(Calendar.DATE, 1);
    }
    
  }


  /** Constructor takes a string of dd-mm-yyyy and an int array for status 
   *  Zero status will be skipped. 
   */
  public CalendarControl (String[] newStartDate, int[] newStatus, boolean viewOriginal) 
  {

      createCalendarControl (newStartDate, newStatus, viewOriginal);
  }

  /** Constructor takes a string of dd-mm-yyyy and an int array for status 
   *  Zero status will be skipped. 
   */
  public CalendarControl (String[] newStartDate, int[] newStatus) 
  {

      createCalendarControl (newStartDate, newStatus, false);
/**    StringTokenizer st = new StringTokenizer (newStartDate[0], "-");
    String start_day = st.nextToken();
    String start_month = st.nextToken();
    String start_year = st.nextToken();
    
    startDate = new GregorianCalendar (Integer.parseInt(start_year), Integer.parseInt(start_month)-1, Integer.parseInt(start_day));
    duration = 365;

    startDate.setFirstDayOfWeek(Calendar.SUNDAY);

    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    int[] pdDay = new int[newStartDate.length];
    int[] pdMonth = new int[newStartDate.length];
    int[] pdYear = new int[newStartDate.length];

    for (int i = 0; i < newStartDate.length; i++)
    {
      st = new StringTokenizer (newStartDate[i], "-");
      pdDay[i] = Integer.parseInt(st.nextToken());
      pdMonth[i] = Integer.parseInt(st.nextToken());
      pdYear[i] = Integer.parseInt(st.nextToken());
    }

    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();
    GregorianCalendar today = new GregorianCalendar();
    int todayDate = today.get(Calendar.DAY_OF_MONTH);
    int todayMonth = today.get(Calendar.MONTH);
    int todayYear = today.get(Calendar.YEAR);
    int last_matched_id = 0;

    for (int i = 0; i < duration; i++) 
    {
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      year[i] = currDate.get(Calendar.YEAR);

      int matched_id = -1;

      for (int j = last_matched_id; j < newStartDate.length; j++)
      {
        if (day[i] == pdDay[j] && month[i]+1 == pdMonth[j] && year[i] == pdYear[j]) 
        {
          matched_id = j;
          last_matched_id = j;
          break;
        }
      }

      if (matched_id != -1) 
      {
        status[i] = newStatus[matched_id];
      } else 
      {
        status[i] = 1;
      }
      
      if ( !(year[i] > todayYear ||  
          (year[i] == todayYear && month[i] > todayMonth) ||
          (year[i] == todayYear && month[i] == todayMonth && day[i] >= todayDate))
         ) 
      {
        status[i] = 0;
      }
      
      currDate.add(Calendar.DATE, 1);
    }
    */
  }

  /** Constructor takes a string of dd-mm-yyyy and an int array for status 
   *  Zero status will be skipped. 
   *  add by charlie, HRP & School day before today will remain unchanged. for dispaly blue background color.
   */
  public void createCalendarControl (String[] newStartDate, int[] newStatus, boolean viewOriginal) 
  {
    StringTokenizer st = new StringTokenizer (newStartDate[0], "-");
    String start_day = st.nextToken();
    String start_month = st.nextToken();
    String start_year = st.nextToken();
    
    startDate = new GregorianCalendar (Integer.parseInt(start_year), Integer.parseInt(start_month)-1, Integer.parseInt(start_day));
    // 08-09-2005 changed by Wisley Lui for extend calender from 12 months to 16 months
    //duration = 365;
    //duration =489;
    duration = 1219; // change from 16months to 40 months
    // end changed by Wisley Lui

    startDate.setFirstDayOfWeek(Calendar.SUNDAY);

    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    int[] pdDay = new int[newStartDate.length];
    int[] pdMonth = new int[newStartDate.length];
    int[] pdYear = new int[newStartDate.length];

    for (int i = 0; i < newStartDate.length; i++)
    {
      st = new StringTokenizer (newStartDate[i], "-");
      pdDay[i] = Integer.parseInt(st.nextToken());
      pdMonth[i] = Integer.parseInt(st.nextToken());
      pdYear[i] = Integer.parseInt(st.nextToken());
    }

    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();
    GregorianCalendar today = new GregorianCalendar();
    int todayDate = today.get(Calendar.DAY_OF_MONTH);
    int todayMonth = today.get(Calendar.MONTH);
    int todayYear = today.get(Calendar.YEAR);
    int last_matched_id = 0;

    for (int i = 0; i < duration; i++) 
    {
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      year[i] = currDate.get(Calendar.YEAR);

      int matched_id = -1;

      for (int j = last_matched_id; j < newStartDate.length; j++)
      {
        if (day[i] == pdDay[j] && month[i]+1 == pdMonth[j] && year[i] == pdYear[j]) 
        {
          matched_id = j;
          last_matched_id = j;
          break;
        }
      }

      if (matched_id != -1) 
      {
        status[i] = newStatus[matched_id];
      } else 
      {
        status[i] = 1;
      }

      if (!viewOriginal) {      
          if ( !(year[i] > todayYear ||  
              (year[i] == todayYear && month[i] > todayMonth) ||
              (year[i] == todayYear && month[i] == todayMonth && day[i] >= todayDate))){

              // add by charlie 23-02-2004, set  HRP day before today -3
              //                            and  set school day before today -4

               switch (status[i]) {
                 case 3 : // do nothing , keep the value for HRP day before today
                          break;
                 case 2 : // do nothing,  keepsthe value f school day before today
                          break;
                 default : status[i] = 0;
                           break;
               }
//                   status[i] = 0;
          }
      }
      
      currDate.add(Calendar.DATE, 1);
    }
    
  }


  /** Constructor takes a string of dd-mm-yyyy and an int array for status 
   *  Zero status will be skipped. 
   */
  public CalendarControl (String newStartDate, String[] newDateString, int[] newStatus) 
  {
    createCalendarControl (newStartDate, newDateString, newStatus, false) ;
  }


  /** Constructor takes a string of dd-mm-yyyy and an int array for status 
   *  Zero status will be skipped. 
   */
  public CalendarControl (String newStartDate, String[] newDateString, int[] newStatus, boolean viewOriginal) 
  {
    createCalendarControl (newStartDate, newDateString, newStatus, viewOriginal) ;
  }


  /** Constructor takes a string of dd-mm-yyyy and an int array for status 
   *  Zero status will be skipped. 
   */
  public void createCalendarControl (String newStartDate, String[] newDateString, int[] newStatus, boolean viewOriginal) 
  {

    String start_day = newStartDate.substring(6,8);
    String start_month = newStartDate.substring(4,6);
    String start_year = newStartDate.substring(0,4);
    
    startDate = new GregorianCalendar (Integer.parseInt(start_year), Integer.parseInt(start_month)-1, Integer.parseInt(start_day));
    // 08-09-2005 changed by Wisley Lui for extend calender from 12 months to 16 months
    //duration = 365;
    //duration =489;
    duration = 1219; //change from 16 months to 40 months
    // end changed by Wisley Lui
    
    startDate.setFirstDayOfWeek(Calendar.SUNDAY);

    status = new int[duration];
    day = new int[duration];
    month = new int[duration];
    year = new int[duration];
    weekday = new int[duration];

    int[] pdDay = new int[newDateString.length];
    int[] pdMonth = new int[newDateString.length];
    int[] pdYear = new int[newDateString.length];
    StringTokenizer st;

    for (int i = 0; i < newDateString.length; i++)
    {
      st = new StringTokenizer (newDateString[i], "-");
      pdDay[i] = Integer.parseInt(st.nextToken());
      pdMonth[i] = Integer.parseInt(st.nextToken());
      pdYear[i] = Integer.parseInt(st.nextToken());
    }

    GregorianCalendar currDate = (GregorianCalendar) startDate.clone();
  GregorianCalendar today = new GregorianCalendar();
    int todayDate = today.get(Calendar.DAY_OF_MONTH);
    int todayMonth = today.get(Calendar.MONTH);
    int todayYear = today.get(Calendar.YEAR);
    int last_matched_id = 0;

    for (int i = 0; i < duration; i++) 
    {
      day[i] = currDate.get(Calendar.DAY_OF_MONTH);
      month[i] = currDate.get(Calendar.MONTH);
      weekday[i] = getWeekDay(currDate.get(Calendar.DAY_OF_WEEK));
      year[i] = currDate.get(Calendar.YEAR);

      int matched_id = -1;

      for (int j = last_matched_id; j < newDateString.length; j++)
      {
        if (day[i] == pdDay[j] && month[i]+1 == pdMonth[j] && year[i] == pdYear[j]) 
        {
          matched_id = j;
          last_matched_id = j;
          break;
        }
      }

      if (matched_id != -1) 
      {
        status[i] = newStatus[matched_id];
      } else 
      {
        status[i] = 1;
      }

      if (!viewOriginal) {
          if ( !(year[i] > todayYear ||  
              (year[i] == todayYear && month[i] > todayMonth) ||
              (year[i] == todayYear && month[i] == todayMonth && day[i] >= todayDate))
             ) 
          {
            status[i] = 0;
          }
      }
      
      currDate.add(Calendar.DATE, 1);
    }
    
  }


  /** This sets the status of the calendar control using a new string.
   *  The new status string must be the same length as the duration.
   */
  public void setStatus (String newStatus) 
  {
    if (newStatus.length() == duration) 
    {
      for (int i = 0; i < duration; i++)
      {
        status[i] = Character.getNumericValue(newStatus.charAt(i));
      }
    }
  }

  /** Change the read/write status of the control
   */
   public void setReadWrite (boolean newReadWrite)
   {
     readWrite = newReadWrite;
   }
   
  /** Returns a status array */
   public int[] getStatus ()
   {
     return status;
   }

   public String getStatusString ()
   {
     String output = "";
     for (int i = 0; i < duration; i++) 
     {
       output += status[i];
     }
     return output;
   }



  public String getStartDate () 
  {
    int loc = 0;
// comment by charlie 21-02-2004, use the first deliverydate in table schoolscheduledate as the start date for each scheduleId
//    for (int i = 0; i < duration; i++)
//    {
//      if (status [i] > 1) 
//      {
//        loc = i;
//        break;
//      }
//    }

    String oDay = Integer.toString(day[loc]);
    String oMonth = Integer.toString(month[loc] + 1);
    String oYear = Integer.toString(year[loc]);
    if (Integer.parseInt(oDay) < 10) 
    {
      oDay = "0" + oDay;
    }
    if (Integer.parseInt(oMonth) < 10)
    {
      oMonth = "0" + oMonth;
    }
    return oYear + oMonth + oDay;
  }


  public String getEndDate ()
  {
    int loc = duration - 1;
    for (int i = 0; i < duration; i++) 
    {
      if (status[i] > 1) 
      {
        loc = i;
      }
    }
    String oDay = Integer.toString(day[loc]);
    String oMonth = Integer.toString(month[loc] + 1);
    String oYear = Integer.toString(year[loc]);
    if (Integer.parseInt(oDay) < 10) 
    {
      oDay = "0" + oDay;
    }
    if (Integer.parseInt(oMonth) < 10)
    {
      oMonth = "0" + oMonth;
    }
    return oYear + oMonth + oDay;
  }

  public static int getWeekDay (int orgWeekday)
  {
    switch (orgWeekday) 
    {
      case (Calendar.SUNDAY):
        return 7;
      case (Calendar.MONDAY):
        return 1;
      case (Calendar.TUESDAY):
        return 2;
      case (Calendar.WEDNESDAY):
        return 3;
      case (Calendar.THURSDAY):
        return 4;
      case (Calendar.FRIDAY):
        return 5;
      case (Calendar.SATURDAY):
        return 6;
    }
    return 8;
  }
  
  /** Returns a date array in the format of dd-mm-yyyy 
   *  where dd = [1..31], mm = [1..12]
   * */
   public String[] getDateString ()
   {
     String[] output = new String[duration];
     for (int i = 0; i < duration; i++) 
     {
       output[i] = day[i] + "-" + month[i]+1 + "-" + year[i];
     }
     return output;
   }

  /** Returns a date array in the format of dd-mm-yyyy 
   *  where dd = [01..31], mm = [1..12]
   * */
   public String[] getScheduleDateString ()
   {
     String[] output2 = new String[duration];
     for (int i = 0; i < duration; i++) 
     {
       int dayTemp = day[i];
       int monthTemp = month[i] + 1;
       if (dayTemp < 10) 
        output2[i] = "0" + day[i] + "-" + monthTemp + "-" + year[i];
       else
        output2[i] = day[i] + "-" + monthTemp + "-" + year[i];
     }
     return output2;
   }
   
   public int getPublishedDaysCount ()
   {
     int count = 0;
     for (int i = 0; i < duration; i++) 
     {
       if (status[i] != 0) 
       {
         count++;
       }
     }
     return count;
   }

   public String[] getPublishedDateString ()
   {
     String[] output = new String[this.getPublishedDaysCount()];
     int j = 0;
     for (int i = 0; i < duration; i++) 
     {
       if (status[i] != 0) 
       {
        output[j] = day[i] + "-" + (month[i]+1) + "-" + year[i];
        j++;
       }
     }
     return output;
   }

   public int[] getPublishedStatus ()
   {
     int[] output = new int[this.getPublishedDaysCount()];
     int j = 0;
     for (int i = 0; i < duration; i++)
     {
       if (status[i] != 0)
       {
         output[j] = status[i];
         j++;
       }
     }
     return output;
   }

   public int getPublishedDaysCountNoOne ()
   {
     int count = 0;
     for (int i = 0; i < duration; i++) 
     {
       if (status[i] != 0 && status[i] != 1) 
       {
         count++;
       }
     }
     return count;
   }

   public String[] getPublishedDateStringNoOne ()
   {
     String[] output = new String[this.getPublishedDaysCountNoOne()];
     int j = 0;
     for (int i = 0; i < duration; i++) 
     {
       if (status[i] != 0 && status[i] != 1) 
       {
        output[j] = day[i] + "-" + (month[i]+1) + "-" + year[i];
        j++;
       }
     }
     return output;
   }

   public int[] getPublishedStatusNoOne ()
   {
     int[] output = new int[this.getPublishedDaysCountNoOne()];
     int j = 0;
     for (int i = 0; i < duration; i++)
     {
       if (status[i] != 0 && status[i] != 1)
       {
         output[j] = status[i];
         j++;
       }
     }
     return output;
   }
  
   public long getMilliseconds (int index)
   {
     GregorianCalendar gc = new GregorianCalendar();
     gc.set(year[index], month[index], day[index]);
     return gc.getTime().getTime();
   }

  /** This generates a random status string that is the same length as the duration.
   *  It can be used with setStatus to randomize the status.
   */
  public String randomStatus ()
  {
    char randomStatus[] = new char[duration];
    Random r = new Random();
    for (int i = 0; i < duration; i++) 
    {
      randomStatus [i] = (char) r.nextInt(4);
    }
    return new String(randomStatus);
  }

  /** Used by toHtml.
   *  Returns the font color of the table cell based on it's status.
   */

  public String getFontColor (int item)
  {
    String output = " class=";
    switch (item)
    {
      case 0:  // Non-published (Grey)
        output += "''";
        break;
      case 1:  // Clear (White)
        output += "''";
        break;
      case 2: // School Days (Yellow)
        output += "'calendarDayFont'";
        break;
      case 3: // HRP (Green)
        output += "'calendarDayFont'";
        break;
      case -1: // Special for start of month
        output += "''";
        break;
      case -2 :// Special for start of month and start of week
        output += "''";
        break;
      case -3 :// Special for HRP date which before today
        output += "'hrpbefore_font'";
        break;
      case -4 :// Special for school date which before today
        output += "'schoolbefore_font'";
        break;        
      case -5 :// Special for non schedule date
        output += "''";
        break;                
    }
    return output;
  }




  /**
   *  Returns the background color of the table cell based on it's status.
   */
  public String getBgColor (int item)
  {
//    String output = " bgcolor=";
    String output = " class=";    
    switch (item)
    {
      case 0:  // Non-published (Grey)
//        output += "'#999999'";
        output += "'nonpub'";        
        break;
      case 1:  // Clear (White)
//        output += "'#ffffff'";
          output += "'clear'";
        break;
      case 2: // School Days (Yellow)
//        output += "'#ffff66'";
          output += "'school'";
        break;
      case 3: // HRP (Green)
//        output += "'#99ff66'";
          output += "'hrp'";
        break;
      case -1: // Special for start of month
//        output += "'#808080'";
          output += "'start'";
        break;
      case -2 :// Special for start of month and start of week
//        output += "'#808080'";
          output += "'start'";
        break;
      case -3 :// Special for HRP date which before today
//        output += "'red'";
          output += "'hrpbefore'";
        break;
      case -4 :// Special for school date which before today
//        output += "'red'";
          output += "'schoolbefore'";
        break;        
      case -5 :// Special for non schedule date
//        output += "'red'";
          output += "'nonschedule'";
        break;                
    }
    return output;
  }

  /** Used by toHtmlPrint.
   *  Returns the image of the table cell based on it's status and date.
   */
  public String getBgImage (int item, int day)
  {
    String output = "";  
    String location = "../img/";

    switch (item)
    {
      case 0:  // Non-published (Grey, with date)
        output = "<img src='" + location + "grey" + day + ".gif'>";
        break;
      case 1:  // no order Clear (Grey , with date)
        output = "<img src='" + location + "grey" + day + ".gif'>";
        break;
      case 2: // School Days (white, with date)
        output = "<img src='" + location + "white" + day + ".gif'>";
        break;
      case 3: // HRP (circle, with date)
        output = "<img src='" + location + "circle" + day + ".gif'>";
        break;
      case -1: // Special for start of month ()
        if (day >0 && day < 32) {
        output = "<img src='" + location + "drakgrey" + day + ".gif'>";
        } else {
        output = "<img src='" + location + "drakgrey.gif'>";
        }
        break;
      case -2 :// Special for start of month and start of week
       if (day >0 && day < 32) {
          output = "<img src='" + location + "drakgrey" + day + ".gif'>";
       }else {
        output = "<img src='" + location + "drakgrey.gif'>";
       }   
        break;
      case -4: // School Days before today(blue, with date)
        output = "<img src='" + location + "red_font" + day + ".gif'>";
        break;
      case -3: // HRP before today(blue, with date)
        output = "<img src='" + location + "red_font" + day + ".gif'>";
        break;        
    }

    return output;
  }



/**
 * 
 * convert -4 to 2 & -3 to 3 for front end javascript 
 * 
 */
  public String genJavaScript_schoolschedule ()
  {
    if (!readWrite)
    {
      return "";
    }
    int tmp = 0;
    String output = "var status = new Array (" + duration + ");\n";
    for (int i = 0; i < duration; i++) 
    {
      if (month[i] == month[0] && year[i] != year[0]) 
      {
        output += "status[" + i + "] = 0;\n";
      } else 
      {
        switch (status[i]) {
            case -4 : tmp = 2;
                      break;
            case -3 : tmp = 3;
                      break;
            default : tmp = status[i];
                      break;
        }
        output += "status[" + i + "] = " + tmp + ";\n";
      }
    }
    return output;
  }



  public String genJavaScript ()
  {
    if (!readWrite)
    {
      return "";
    }
    String output = "var status = new Array (" + duration + ");\n";
    for (int i = 0; i < duration; i++) 
    {
      // 08-09-2005 changed by Wisley for extend calendar from 12 months to 16 months
      //if (month[i] == month[140] && year[i] != year[140]) 
      //{
      //  output += "status[" + i + "] = 0;\n";
      //} else 
      //{
        output += "status[" + i + "] = " + status[i] + ";\n";
      //}
    }
    return output;
  }

  public String genLegend ()
  {
    int countSchool = 0;
    int countHRP = 0;
    String output;

    for (int i = 0; i < duration; i++) 
    {
//      if (status[i] == 2 || ) 
      if (status[i] == 2 || status[i] == -4 ) // School day before today will add to total also       
        countSchool++;
//      else if (status[i] == 3)
      else if (status[i] == 3 || status[i] == -3) // HRP day before today will add to total also     
        countHRP++;
    }
    output = "<table class=\"calendar\"><tr>";
    output += "<td width=\"10\" bgcolor=\"#99ff66\">&nbsp; </td>";
    output += "<td>HRP Days: <font color=\"#000080\"><b>" + countHRP + "</b></font>&nbsp; </td>";
    output += "<td width=\"10\" bgcolor=\"#FFFF66\">&nbsp;</td><td>School Days: <font color=\"#000080\"><b>" + countSchool + "</b></font> </td>";
    output += "<td width=\"10\" bgcolor=\"#999999\">&nbsp;</td>";
    output += "<td>Non-publishing day(s)</td></tr></table>";
    return output;
  }

  public String gen_count ()
  {
    int countSchool = 0;
    int countHRP = 0;
    String output;

    for (int i = 0; i < duration; i++) 
    {
      if (status[i] == 2) 
        countSchool++;
      else if (status[i] == 3)
        countHRP++;
    }
    output = "No. of HRP Days:" + countHRP;
    output += "No. of School Days:" + countSchool;
    return output;
  }
  
  public String toHtml()
  {
    String output ="<table cellpadding=3 cellspacing=3>\n"; // = "<table>\n";
    int row_flag = 1;
    int week_flag = 0;
    for (int i = 0; i < duration; i++) 
    {
  
      // If this is new month
      if (i == 0 || month[i] != month[i-1]) 
      { 

        // 08-09-2005 changed by Wisley Lui for extend calender from 12 months to 16 months
        // If this is the 16th month, break
        // 18-08-06 change from 16 months to 40 months by Leo Ching
        // If this is the 40th month, break
        /*if (month[i] == month[0] && i != 0)
        {
          //endOfControl = i-1;
          break;
        } */
        if (year[i] != year[0])
        { 
          int startMonth = month[0];
          int currentMonth = month[i];
          if (startMonth > 7)
          {
            if ( year[i] - year[0] > 3 && startMonth - currentMonth == 8)
            {
              break;
            }
          }
          else
          {
            if (year[i] - year[0] > 2 && currentMonth - startMonth == 4)
            {
              break;
            }
          }
        }
        
        // end of month clean up        

        // end changed by Wisley
        
        if (week_flag < 6 && i != 0) 
        {
          for (int j = 0; j < 6 - week_flag; j++) 
            {
             output += "<tr><td>&nbsp;</td></tr>\n";
            }
          week_flag = 0;
        } else 
        {
          week_flag = 0;
        }

        // starting new row if needed
        if (row_flag == 1) 
        {
          if (i != 0) 
          {
            output += "</table></td></tr><tr><td valign=top>\n";
          } 
          output += "<tr><td valign=top>\n";
        } else 
        {
          output += "</table></td><td valign=top>\n"; 
        }
        
        if (row_flag++ == months_per_row) 
        {
          row_flag = 1;
        }
        

        // new month header
        output += "<table class=calendar>\n\t<tr><th class=calendarHeader align=center colspan=7>" + monthname[month[i]] + " " + year[i] + "</th></tr>\n";
        output += "<tr>";
        for (int k = 0; k < weekname.length; k++)
        {
          output += "<td width=14%>" + weekname[k] + "</td>";
        }
        output += "</tr>\n";
        // add in empty cells at the start of a month
        int temp_day_string = 1;
        if (i == 0) 
        {
          int start_week_number = startDate.get(Calendar.WEEK_OF_MONTH);
          int start_weekday = getWeekDay(new GregorianCalendar (year[0], month[0], 1).get(Calendar.DAY_OF_WEEK));
          if (start_weekday == 7 )
          {
            start_weekday = 0;
          }
          for (int j = 1; j < start_week_number; j++) 
          {
            output += "<tr>";
            week_flag++;
            for (int k = 0; k < 7; k++)
             {
                if (j == 1 && k < start_weekday) 
                {
                  output += "<td>&nbsp;</td>";
                } else 
                {
                  output += "<td align=center" + getBgColor(-1) + ">" + temp_day_string++ + "</td>";
                }
             }
            output += "</tr>\n";
          }
        }  
        
          if (weekday[i] != 7) 
          {
             output += "\t<tr>";
             week_flag++;
             int start_weekday1 = getWeekDay(new GregorianCalendar (year[0], month[0], 1).get(Calendar.DAY_OF_WEEK));             
               for (int j = 0; j < weekday[i]; j++) 
             {
               if (i == 0 && temp_day_string < day[i]) 
               {
         // add by charlie, 09-09-2003, format the dates before today  
                 if(week_flag == 1) {
                         if(j >= start_weekday1) {               
                              output += "<td align=center" + getBgColor(-2) + ">" + temp_day_string++ + "</td>\n";
                          }else {
                              output += "<td></td>\n";
                          }
                 }else {
                     output += "<td align=center" + getBgColor(-2) + ">" + temp_day_string++ + "</td>\n";
                 } 
         // end add by charlie
//                output += "<td align=center" + getBgColor(-2) + ">" + temp_day_string++ + "</td>\n";

               } else
               {
                 output += "<td></td>\n";
               }
             }
          }
        
        
      } // end of handling a new month
      
      if (weekday[i] == 7)
      {
        output += "\t<tr>";
        week_flag++;
      }
      output += "<td width=14% align=center";
      if (readWrite) {

        switch (status[i]) {
           case 0 : break;  // do nothing 
           case -3 : output += " class='hrpbefore' "; 
                    break;
           case -4 : output += " class='schoolbefore' ";
                    break;
           default : output += " onClick='date_select(" + i + ");' style='cursor:hand'";        
                     break;
        }
       
//       output += status[i] != 0 ? " onClick='date_select(" + i + ");' style='cursor:hand'":"";
//System.out.println("inside write status => " + status[i] + "  date => " + day[i] + "-" + (month[i] + 1 ) + "-" + year[i] + "   " );              
      }
      output += getBgColor(status[i]) + " id='td" + i + "' status='" + status[i] + "'>" + day[i] + "</td>\n"; // + "</td>";
//System.out.println("line 1401 status => " + status[i] + "  date => " + day[i] + "-" + (month[i] +1) + "-" + year[i] + "   " + getBgColor(status[i]) );      
      if (weekday[i] == 6) 
      {
        output += "</tr>\n";
      }

    }

    if (week_flag < 6) 
    {
      for (int j = 0; j < 6 - week_flag; j++) 
      {
       output += "<tr><td>&nbsp;</td></tr>\n";
      }
    }
    output += "</table>\n</td></tr></table>\n";
    return output;
  }



/**
 * disable the days after scheduleenddate , set to nonschedule (grey)
 * display HRP & School days before today in red (font)
 * 
 */

  public String toHtml(String input_scheduleenddate)
  {
    Calendar scheduleenddate = Calendar.getInstance();
    Calendar calendardate = Calendar.getInstance();

    if (readWrite == true ) {    // if the calendar is readonly , input_scheduleenddate can be any value
    int input_year = Integer.parseInt(input_scheduleenddate.substring(0,4));
    int input_month = (Integer.parseInt(input_scheduleenddate.substring(4,6)) -1);
    int input_day = Integer.parseInt(input_scheduleenddate.substring(6));
    scheduleenddate.set(input_year,input_month,input_day);
    }
    
    String output ="<table cellpadding=3 cellspacing=3>\n"; // = "<table>\n";
    int row_flag = 1;
    int week_flag = 0;
    
    
    for (int i = 0; i < duration; i++) 
    {
  
      // If this is new month
      if (i == 0 || month[i] != month[i-1]) 
      { 

        // 08-09-2005 changed by Wisley Lui for extend calender from 12 months to 16 months
        // If this is the 16th month, break

        //if (month[i] == month[0] && i != 0)
        //if (month[i] == month[140] && year[i]-year[140])
        //{
          //endOfControl = i-1;
        //  break;
        //} 
        
        // end of month clean up        

        // end changed by Wisley
        
        if (week_flag < 6 && i != 0) 
        {
          for (int j = 0; j < 6 - week_flag; j++) 
            {
             output += "<tr><td>&nbsp;</td></tr>\n";
            }
          week_flag = 0;
        } else 
        {
          week_flag = 0;
        }

        // starting new row if needed
        if (row_flag == 1) 
        {
          if (i != 0) 
          {
            output += "</table></td></tr><tr><td valign=top>\n";
          } 
          output += "<tr><td valign=top>\n";
        } else 
        {
          output += "</table></td><td valign=top>\n"; 
        }
        
        if (row_flag++ == months_per_row) 
        {
          row_flag = 1;
        }
        

        // new month header
        output += "<table class=calendar>\n\t<tr><th class=calendarHeader align=center colspan=7>" + monthname[month[i]] + " " + year[i] + "</th></tr>\n";
        output += "<tr>";
        for (int k = 0; k < weekname.length; k++)
        {
          output += "<td width=14%>" + weekname[k] + "</td>";
        }
        output += "</tr>\n";
        // add in empty cells at the start of a month
        int temp_day_string = 1;
        if (i == 0) 
        {
          int start_week_number = startDate.get(Calendar.WEEK_OF_MONTH);
          int start_weekday = getWeekDay(new GregorianCalendar (year[0], month[0], 1).get(Calendar.DAY_OF_WEEK));
          if (start_weekday == 7 )
          {
            start_weekday = 0;
          }
          for (int j = 1; j < start_week_number; j++) 
          {
            output += "<tr>";
            week_flag++;
            for (int k = 0; k < 7; k++)
             {
                if (j == 1 && k < start_weekday) 
                {
                  output += "<td>&nbsp;</td>";
                } else 
                {
                  output += "<td align=center" + getBgColor(-1) + ">" + temp_day_string++ + "</td>";
                }
             }
            output += "</tr>\n";
          }
        }  
        
          if (weekday[i] != 7) 
          {
             output += "\t<tr>";
             week_flag++;
             int start_weekday1 = getWeekDay(new GregorianCalendar (year[0], month[0], 1).get(Calendar.DAY_OF_WEEK));             
               for (int j = 0; j < weekday[i]; j++) 
             {
               if (i == 0 && temp_day_string < day[i]) 
               {
         // add by charlie, 09-09-2003, format the dates before today  
                 if(week_flag == 1) {
                         if(j >= start_weekday1) {               
                              output += "<td align=center" + getBgColor(-2) + ">" + temp_day_string++ + "</td>\n";
                          }else {
                              output += "<td></td>\n";
                          }
                 }else {
                     output += "<td align=center" + getBgColor(-2) + ">" + temp_day_string++ + "</td>\n";
                 } 
         // end add by charlie
//                output += "<td align=center" + getBgColor(-2) + ">" + temp_day_string++ + "</td>\n";

               } else
               {
                 output += "<td></td>\n";
               }
             }
          }
        
        
      } // end of handling a new month
      
      if (weekday[i] == 7)
      {
        output += "\t<tr>";
        week_flag++;
      }
      output += "<td width=14% align=center";
      if (readWrite) {

        switch (status[i]) {
           case 0 : break;  // do nothing 
           case -3 : break; //output += " class='hrpbefore' "; 
           case -4 : break; //output += " class='schoolbefore' ";
           default : if (after_scheduleenddate(scheduleenddate,calendardate,i) == false ) {
                           if ( status[i] != 3) {
                             output += " onClick='date_select(" + i + ");' style='cursor:hand'" ; 
                           }
                             
                     }else {
                            status[i] = -5; // set the cell color to nonschedule if after scheduleenddate.
                     }
                     break;
        }
       
//       output += status[i] != 0 ? " onClick='date_select(" + i + ");' style='cursor:hand'":"";
//System.out.println("inside write status => " + status[i] + "  date => " + day[i] + "-" + (month[i] + 1 ) + "-" + year[i] + "   " );              
      }
      output += getBgColor(status[i]) + " id='td" + i + "' status='" + status[i] + "'> <font " + getFontColor(status[i]) + " >" + day[i] + "</font></td>\n"; // + "</td>";
//System.out.println("line 1634 status => " + status[i] + "  date => " + day[i] + "-" + (month[i] +1) + "-" + year[i] + "   " + getBgColor(status[i]) );      
      if (weekday[i] == 6) 
      {
        output += "</tr>\n";
      }

    }

    if (week_flag < 6) 
    {
      for (int j = 0; j < 6 - week_flag; j++) 
      {
       output += "<tr><td>&nbsp;</td></tr>\n";
      }
    }
    output += "</table>\n</td></tr></table>\n";
    return output;
  }

private boolean after_scheduleenddate(Calendar scheduleenddate, Calendar calendardate ,int i) {
        calendardate.set(year[i],month[i],day[i],0,0,0);
//        System.out.print("scheduleenddate =>" + scheduleenddate.get(Calendar.YEAR) + "-" + (scheduleenddate.get(Calendar.MONTH) +1) + "-" + scheduleenddate.get(Calendar.DATE)+ "  " + scheduleenddate.get(Calendar.HOUR)+  ":" + scheduleenddate.get(Calendar.MINUTE)+  ":" + scheduleenddate.get(Calendar.SECOND));
//        System.out.println("  calendar date =>" + calendardate.get(Calendar.YEAR) + "-" + (calendardate.get(Calendar.MONTH)+1) + "-" + calendardate.get(Calendar.DATE) + "    " + calendardate.get(Calendar.HOUR) + ":" + calendardate.get(Calendar.MINUTE) + ":" + calendardate.get(Calendar.SECOND) + "   caendate date after scheduleenddate is => " + calendardate.after(scheduleenddate)  );       
        return calendardate.after(scheduleenddate);
}


  public String toString() 
  {
    String output="";
    for (int i = 0; i < duration; i++) 
    {
      if (i == 0 || month[i] != month[i-1]) 
      {
        output += "\n" + monthname[month[i]].toUpperCase() + " " + year[i] + "\n";
        if (weekday[i] != 7) 
        {
            for (int j = 0; j < weekday[i]; j++) 
           {
             output += "[] ";
           }
        }
      }
      output+= "[" + day[i] + "/" + status[i] + "] ";
      if (weekday[i] == 6) 
      {
        output += "\n";
      }
    }
    return output;
  }

// add by charlie for print calendar 
  public String toHtmlPrint()
  {
    String output ="<table cellpadding=3 cellspacing=3>\n";
    int row_flag = 1;
    int week_flag = 0;
    for (int i = 0; i < duration; i++) 
    {
  
      // If this is new month
      if (i == 0 || month[i] != month[i-1]) 
      { 

        // If this is the 12th month, break

        if (month[i] == month[0] && i != 0) 
        {
          break;
        } 
        
        // end of month clean up        
        if (week_flag < 6 && i != 0) 
        {
          for (int j = 0; j < 6 - week_flag; j++) 
            {
             output += "<tr><td>&nbsp;</td></tr>\n";

            }
          week_flag = 0;
        } else 
        {
          week_flag = 0;
        }

        // starting new row if needed
        if (row_flag == 1) 
        {
          if (i != 0) 
          {
            output += "</table></td></tr><tr><td valign=top>\n";
          } 
          output += "<tr><td valign=top>\n";
        } else 
        {
          output += "</table></td><td valign=top>\n"; 
        }
        
        if (row_flag++ == months_per_row) 
        {
          row_flag = 1;
        }

        // new month header
        output += "<table class=calendar>\n\t<tr><th class=calendarHeader align=center colspan=7>" + monthname[month[i]] + " " + year[i] + this.genTotalDays(year[i],month[i]) + "</th></tr>\n";

        output += "<tr>";
        for (int k = 0; k < weekname.length; k++)
        {
          output += "<td width=14%>" + weekname[k] + "</td>";
        }
        output += "</tr>\n";
        
        // add in empty cells at the start of a month
        int temp_day_string = 1;
        if (i == 0) 
        {
          int start_week_number = startDate.get(Calendar.WEEK_OF_MONTH);

          int start_weekday = getWeekDay(new GregorianCalendar (year[0], month[0], 1).get(Calendar.DAY_OF_WEEK));
          if (start_weekday == 7 )
          {
            start_weekday = 0;
          }
          for (int j = 1; j < start_week_number; j++) 
          {
            output += "<tr>";
            week_flag++;
            for (int k = 0; k < 7; k++)
             {
                if (j == 1 && k < start_weekday) 
                {
                  output += "<td>&nbsp;</td>";
                } else 
                {
                  output += "<td align=center>" + this.getBgImage(-1,temp_day_string++) + "</td>";                  
                }
             }
            output += "</tr>\n";
          }
        }  
        
          if (weekday[i] != 7) 
          {
             output += "\t<tr>";
             week_flag++;
              for (int j = 0; j < weekday[i]; j++) 
             {
               if (i == 0 && temp_day_string < day[i]) 
               {
               output += "<td align=center>" + this.getBgImage(-2,temp_day_string++) + "</td>\n";                 
               } else
               {
                 output += "<td></td>\n";
               }
             }
          }
        
        
      } // end of handling a new month
      
      if (weekday[i] == 7)
      {
        output += "\t<tr>";
        week_flag++;
      }
      output += "<td width=14% align=center";
      if (readWrite) {
        output += status[i] != 0 ? " onClick='date_select(" + i + ");' style='cursor:hand'":"";
      }

      output += ">" + this.getBgImage(status[i],day[i] ) + "</td>\n";
      if (weekday[i] == 6) 
      {
        output += "</tr>\n";
      }

    }

    if (week_flag < 6) 
    {
      for (int j = 0; j < 6 - week_flag; j++) 
      {
       output += "<tr><td>&nbsp;</td></tr>\n";
      }
    }
    output += "</table>\n</td></tr></table>\n";

    return output;
  }
 
// end add by charlie



//  public 

  public static void main(String[] args)
  {

  }
}

