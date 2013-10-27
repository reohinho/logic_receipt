/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/databean/GenericStringData.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:15 $
  $Revision: 1.1.1.1 $
*/
package common.jsp.databean;
import common.databean.DataFormatter;
import com.scmp.circ.utility.*;

import java.sql.Timestamp;

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Calendar;

public class GenericStringData 
{
  public static int SetDataObjectFormat_Boolean = 1 ;
  public static int SetDataObjectFormat_Timestamp2DateInputFormat = 2 ;
  public static int SetDataObjectFormat_Date2DateInputFormat = 3 ;
  public static int SetDataObjectFormat_IntegerFromDouble = 4 ;
  public static int SetDataObjectFormat_Timestamp2DateDisplayFormat = 5;
  public static int SetDataObjectFormat_Date2DateDisplayFormat = 6;
  public static int SetDataObjectFormat_TimestampOrDate2YYYYMMDDFormat = 7;
  public static int SetDataObjectFormat_General = 9 ;

  protected String data[] ;
  protected boolean errFlag[];  // Error array - set true for index 0 if error occurs
  protected int dataSize = -1 ;
  
  protected int typeMin = 1 ; // assume type starts from 1
  protected int typeMax = 0 ; 
  protected int objectType = 0 ;


  public GenericStringData() {}

  
  public GenericStringData(int maxFields)
  {
    dataSize = maxFields ;
    data = new String[maxFields] ;
    errFlag = new boolean[maxFields+1];
  }

  public GenericStringData(int maxFields, int maxTypes)
  {
    //dataSize = maxFields ;
    //data = new String[maxFields] ;
    //errFlag = new boolean[maxFields+1];
    this (maxFields) ;
    typeMax = maxTypes ;
  }


  public void setData (int field, String value) {
    if (field<=0 || field > dataSize) return ;
    data[field-1] = value ;
  }

  public void setDataAsNull (int field) {
    if (field<=0 || field > dataSize) return ;
    data[field-1] = null ;
  }

  public void setData (int field, Boolean value) {
    if (field<=0 || field > dataSize) return ;
    
    if (value == null) { value = new Boolean(false) ; } // force NULL value as "N"
    data[field-1] = (value.booleanValue() == true ? "Y" : "N") ;
  }


  public void setData(int id, Timestamp value)
  {
    setData(id, DataFormatter.Timestamp2ScreenFormat(value));
  }
  
  public void setData(int id, Double value)
  {
    setData(id, DataFormatter.Double2ScreenFormat(value));
  }
  
  public void setData(int id, Character value)
  {
    if (value == null)
    {
      setDataAsNull(id) ;
    }
    else
    {
      setData(id, value.toString());
    }
  }
   
  public void setData(int id, Long value)
  {
    setDataFromObject (id, (Object)(value), SetDataObjectFormat_General);
  }


  public void setDataFromObject (int field, Object value, int objectFormat) {
    if (field<=0 || field > dataSize) return ;
    if (objectFormat == SetDataObjectFormat_Boolean)
      try { data[field-1] = ((Boolean)value).booleanValue()?"Y":"N" ; } catch (Exception e) {}
    else if (objectFormat == SetDataObjectFormat_General)
      try { data[field-1] = value.toString() ; } catch (Exception e) {}
    else if (objectFormat == SetDataObjectFormat_Timestamp2DateInputFormat || objectFormat == SetDataObjectFormat_Date2DateInputFormat) 
      try { data[field-1] = DataFormatter.Timestamp2ScreenFormatInput ( new Timestamp(((java.util.Date)value).getTime())) ; } catch (Exception e) { }
    else if (objectFormat == SetDataObjectFormat_Timestamp2DateDisplayFormat || objectFormat == SetDataObjectFormat_Date2DateDisplayFormat) 
      try { data[field-1] = DataFormatter.Timestamp2ScreenFormat (new Timestamp(((java.util.Date)value).getTime())) ; } catch (Exception e) {}
    else if (objectFormat == SetDataObjectFormat_TimestampOrDate2YYYYMMDDFormat) 
      try { data[field-1] = DataFormatter.Timestamp2YYYYMMDDFormat(new Timestamp(((java.util.Date)value).getTime())) ; } catch (Exception e) {}
    else if (objectFormat == SetDataObjectFormat_IntegerFromDouble) 
      try { 
        data[field-1] = value.toString() ; 
        if (data[field-1].indexOf ('.') != -1) data[field-1] = data[field-1].substring(0, data[field-1].indexOf ('.')) ;
      } catch (Exception e) {}
    
    else data[field-1] = (String)value ;
  }

/**
 * Return true if data field is  null or empty string
 */
  public boolean isDataEmpty(int field)
  {
    String s = getDataOrg(field) ;
    if (s==null) { s = "" ; } ; // set to empty string if it is null
    return (s.trim().length() == 0) ;
  }
  
  public String getDataOrg (int field) {
    if (field<=0 || field > dataSize) return null;
    return data[field-1] ;
  }

 /**
   * @param field (int) Decimal format stored in array field
   * @return Double object
   */
  public Double getDoubleByField(int field)
  {
    try { return new Double (getDataOrg(field)) ; }
    catch (Exception e) { return null ; }
  }

 /**
   * @param field (int) Decimal format stored in array field with string test
   * @return Double object
   */
  public Double getDoubleByFieldWithNDTest(int field)
  {
    try
    {
        String fieldData = getDataOrg(field);
        if (CircUtilities.isEmptyString(fieldData) == false)
        {
            if (fieldData.equalsIgnoreCase("ND") == true)
                return new Double(-1);
            else return new Double(fieldData);
        } else return new Double(0);
    }
    catch (Exception e) { return null; }
  }


 /**
   * @param field (int) Decimal format stored in array field
   * @return Integer object
   */
  public Integer getIntegerByField(int field)
  {
    try { return new Integer (getDataOrg(field)) ; }
    catch (Exception e) { return null ; }
  }

 /** 
  * @param field (int) Decimal format stored in array field
  * @return Long object
  */
 public Long getLongByField(int field)
 {
     try { return new Long (getDataOrg(field)) ;}
     catch (Exception e) {return null; }
 }

 /**
   * @param field (int) "Y"/"N" format stored in array field
   * @return Boolean object
   */
  public Boolean getBooleanByField(int field)
  {
    try { return new Boolean (getDataOrg(field).equals("Y")) ; }
    catch (Exception e) { return null ; }
  }

 /**
   * @param field (int) Single character String format stored in array field
   * @return Character object
   */
  public Character getCharacterByField(int field)
  {
    try { return new Character (getDataOrg(field).charAt(0)) ; }
    catch (Exception e) { return null ; }
  }

 /**
   * @param field (int) Input date format dd/mm/yy stored in array field
   * @return Timestamp object
   */
  public java.sql.Timestamp getTimestampByField(int field)
  {
    String inputDate = getDataOrg(field);
    return getTimestampByString (inputDate) ;
    
  }

 /**
   * @param field (int) Input date format yyyyMMdd stored in array field
   * @return Timestamp object
   */
  public java.sql.Timestamp getTimestampByFieldYYYYMMDD(int field)
  {
    String inputDate = getDataOrg(field);
    if (inputDate != null && inputDate.length()== 8) 
        return CircUtilities.getTimestampByString(inputDate, "yyyyMMdd") ;
    else 
        return null;
  }


 /**
   * @param field (int) Input date format dd-mm-yy stored in array field
   * @return String - date in YYYYMMDD format
   */
 public String getYYYYMMDDByField(int field) 
 {
     String inputDate = getDataOrg(field);
     return DataFormatter.Timestamp2YYYYMMDDFormat(CircUtilities.getTimestampByString(inputDate));
 }
  


 /**
   * @param field (int) Input date format dd-mm-yy stored in array field
   * @return java.sql.Date object
   */
  public java.sql.Date getSqlDateByField(int field)
  {
    String inputDate = getDataOrg(field);
    return new java.sql.Date (CircUtilities.getTimestampByString (inputDate).getTime()) ;
  }

/*
  public static java.sql.Timestamp getTimestampByString(String inputDate)
  {
    
    java.sql.Timestamp ts = null;
    
    if ( inputDate != null && inputDate.equals("") == false)
    {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MM-yy");
        try {
            java.util.Date dt = df.parse(inputDate);
            ts = new java.sql.Timestamp(dt.getTime());
        }
        catch (Exception e)
        { System.out.println("getTimestampByString:" + e);}
       
    }      
    return ts;
  }
*/
   /**
   * @param field (int) Input date format dd-MM-yy HH:mm (24 hours time) stored in array field
   * @return Timestamp
   */
   
  public java.sql.Timestamp getTimestampByField(int field, boolean hourFlag)
  {
    String inputDate = getDataOrg(field);
    return CircUtilities.getTimestampByString (inputDate, hourFlag) ;
  }

 /*
  * hourFlag if true, HH:mm is specified in the input Date. Otherwise, use default 24 hour
  */
/*  
  public static java.sql.Timestamp getTimestampByString(String inputDate, boolean hourFlag)
  {
    
    java.sql.Timestamp ts = null;
    String inputDateTime = "";

    if (hourFlag == true)
        inputDateTime = inputDate;
    else
        inputDateTime = inputDate + " 00:00";   // set default hour at 24:00
    
    if ( inputDate != null && inputDate.equals("") == false)
    {

        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MM-yy HH:mm");
        try {
           
            java.util.Date dt = df.parse(inputDateTime);
            ts = new java.sql.Timestamp(dt.getTime());
            
        }
        catch (Exception e)
        { System.out.println("getTimestampByString:" + e);}
       
    }      
    return ts;
  }
*/
  public String getDataForHtml (int field) {
    String s = getDataOrg (field) ;
    if (s==null) return "";
    return escapeDataInHtml (s) ;
  
  }

  public String getDataForJavascript (int field) {
    String s = getDataOrg (field) ;
    if (s==null) return "";
    return escapeDataInJavascript (s) ;
  
  }

  public static String escapeDataInJavascript (String s) {
    if (s==null) return null ;
    StringTokenizer st = new StringTokenizer (s, "\"\'\\", true) ;
    String retStr = "" ;
    while (st.hasMoreTokens()) 
    {
      String nxTkn = st.nextToken() ;
      if (nxTkn.equals("\"")) retStr += "&quot;" ;
      else if (nxTkn.equals("\'")) retStr += "\\&#039;" ;      
      else if (nxTkn.equals("\\")) retStr += "\\&#092;" ;            
      else retStr += nxTkn ;
    }
    return retStr ;
  }

  public static String escapeDataInJavascript_display (String s) {
    if (s==null) return null ;
    StringTokenizer st = new StringTokenizer (s, "\"\'\\", true) ;
    String retStr = "" ;
    while (st.hasMoreTokens()) 
    {
      String nxTkn = st.nextToken() ;
      if (nxTkn.equals("\"")) retStr += "\\\"" ;
      else if (nxTkn.equals("\'")) retStr += "\\'" ;      
      else if (nxTkn.equals("\\")) retStr += "\\\\" ;            
      else retStr += nxTkn ;
    }
    return retStr ;
  }


  public static String escapeDataInHtml (String s) {
    if (s==null) return null ;
  
    StringTokenizer st = new StringTokenizer (s, "\"><&\\", true) ;
    String retStr = "" ;
    while (st.hasMoreTokens()) 
    {
      String nxTkn = st.nextToken() ;
      if (nxTkn.equals("\"")) retStr += "&quot;" ;
      else if (nxTkn.equals(">")) retStr += "&gt;" ;
      else if (nxTkn.equals("<")) retStr += "&lt;" ;
      else if (nxTkn.equals("&")) retStr += "&amp;" ;
      else if (nxTkn.equals("\'")) retStr += "&#39;";
      else if (nxTkn.equals("\\")) retStr += "&#92;";      
      else retStr += nxTkn ;
    }
    
    return retStr ;
  }

/**
 * to unescape string for use in javascript.
 * 
 */
  public static String unescapeDataForJavascript(String s) {
    if (s==null) return null ;
  
    StringBuffer strBuf = new StringBuffer(s);
    replace(strBuf,"&quot;","\"",true);
    replace(strBuf,"&gt;",">",true);
    replace(strBuf,"&lt;","<",true);
    replace(strBuf,"&amp;","&",true);
    replace(strBuf,"&#39;","\'",true);
    replace(strBuf,"&#92;","\\",true);

    // escape string for use in javascript, change ', ", \ to \' , \" , \\
    StringTokenizer st = new StringTokenizer (strBuf.toString(), "\"\'\\", true) ;
    String retStr = "" ;
    while (st.hasMoreTokens()) 
    {
      String nxTkn = st.nextToken() ;
      if (nxTkn.equals("\"")) retStr += "\\\"" ;
      else if (nxTkn.equals("\'")) retStr += "\\\'" ;      
      else if (nxTkn.equals("\\")) retStr += "\\\\" ;            
      else retStr += nxTkn ;
    }
    return retStr;
  }

/**
 * replace sub string in string buffer
 * 
 */
  public static void replace(StringBuffer orig, String o, String n, boolean all) {
        if (orig == null || o == null || o.length() == 0 || n == null){
        } else {           
             int i = 0;
             while (i + o.length() <= orig.length()) {
               if (orig.substring(i, i + o.length()).equals(o)) {
                  orig.replace(i, i + o.length(), n);
                   if (!all)
                    break;
                   else
                    i += n.length();
               } else
                   i++;
               }
        } 
  }


  public String getData (int field) {
    String s = getDataOrg (field) ;
    if (s==null) return "";
    return s ;
  }

  public void setType (int newTypr) {
    if (newTypr<typeMin || newTypr > typeMax) return ;
    objectType = newTypr ;
  }

  public int getType () {
    return objectType ;
  }

  public String toString () 
  {
    String s="" ;
    if (typeMax != 0) 
    {
      s += "Type: " + typeMax + "\n" ;
    }

    if (dataSize != -1) 
    {
      s += "Data: \n" ;
      for (int i = 0 ; i < dataSize ; i++)
         s += data[i] + "\n" ;
    }
    return s ;
  }
  public void addError(Vector vErrorObj, String sErrorCode, String sAddOnMsg) 
  {
    String[] arrError = {sErrorCode, sAddOnMsg};
    vErrorObj.addElement(arrError);
  }

  public boolean getErrFlag(int fieldIndex) 
  {
     if (errFlag != null && fieldIndex >0 && fieldIndex < dataSize+1)
       return errFlag[fieldIndex];  
     else
       return true ;
  }
  
  public void setErrFlag(int fieldIndex) 
  {
     if (errFlag !=null && fieldIndex > 0 && fieldIndex < dataSize+1) {
      errFlag[0] = true;
      errFlag[fieldIndex] = true;
     }
  }

  public boolean hasErr () { return  errFlag[0] ; }

  public void resetErrFlag () { errFlag = null ; errFlag = new boolean [dataSize+1] ; } 

  public boolean checkStringLength (int field, int maxLength) {
    return getData(field).length() <= maxLength ;
  }

  public static java.sql.Timestamp getTimestampByString(String inputDate)
  {

    java.sql.Timestamp ts = null;

    if ( inputDate != null && inputDate.equals("") == false)
    {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date dt = df.parse(inputDate);
            ts = new java.sql.Timestamp(dt.getTime());
        }
        catch (Exception e)
        { System.out.println("getTimestampByString:" + e);}

    }
    return ts;
  }
  
  public static void main(String[] args)
  {
  }  
}