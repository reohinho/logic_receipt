/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/databean/DataValidator.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:14 $
  $Revision: 1.1.1.1 $
*/
package common.databean;

import java.lang.Double;

import java.text.DecimalFormat;

import java.util.Date;


public class DataValidator 
{
  public DataValidator()
  {
  }

  /**
   * This method will check whether input string contains valid 
   * numeric (double) value. Empty string is considered as "Invalid".
   * 
   * @param sDouble is the string to be examined
   * @return boolean True means string contains valid double value
   */
  public static boolean isDouble(String sDouble)
  {
    try 
    {
      new Double(sDouble) ;
      return true ;
    }
    catch (Exception e)
    {
      return false ;      
    }
  }

  /**
   * This method will check whether input string contains valid 
   * numeric (double) value and in specified DecimalFormat. 
   * Empty string is considered as "Invalid".
   * 
   * @param sDouble is the string to be examined
   * @param df is the format used for checking
   * @return boolean True means string contains valid double value
   */
  public static boolean isDouble(String sDouble, DecimalFormat df)
  {
    if (isDouble(sDouble))
    { 
      Double d1 = new Double(sDouble) ;
      Double d2 = new Double(df.format(d1)) ;
      return (d1.compareTo(d2) == 0) ;
    }
    else
    {
      return false; 
    }
  }

  /**
   * This method will check whether input string contains valid 
   * numeric (double) value and in specified pattern. 
   * Empty string is considered as "Invalid".
   * 
   * @param sDouble is the string to be examined
   * @param pattern is the pattern used for checking, e.g. "0.00"
   * @return boolean True means string contains valid double value
   */
  public static boolean isDouble(String sDouble, String pattern)
  {
    DecimalFormat df = makeDecimalFormatFromString(pattern) ;
    return isDouble(sDouble, df) ;
  }


  private static DecimalFormat makeDecimalFormatFromString(String pattern)
  {
    DecimalFormat df = new DecimalFormat(pattern) ;
    
    return df ;
  }
  
  /**
   * This method will check whether input string contains valid 
   * numeric (integer) value. Empty string is considered as "Invalid".
   * 
   * @param strInteger is the string to be examined
   * @return boolean True means string contains valid integer value
   */
  public static boolean isInteger (String strInteger) 
  {
    try 
    {
      Integer.parseInt(strInteger);
      return true;
    } catch (Exception e)
    {
      return false;      
    }
  }

  /**
   * This method will check whether input string contains valid 
   * date value as defined in DataFormatter.frontendDateFormatInput.
   * Empty string is considered as "Invalid".
   * 
   * @param strDate is the string to be examined
   * @return boolean True means string contains valid date value
   */
  public static boolean isInputDate (String strDate)
  {
    String newDateStr ;
    
    try
    {
      Date d = DataFormatter.frontendDateFormatInput.parse(strDate) ;
      newDateStr = DataFormatter.frontendDateFormatInput.format(d) ;
    }
    catch (Exception e)
    { // if parse error occurs, returns as error
      return (false) ;
    }
    return (strDate.equals(newDateStr)) ;
  }
  
  /**
   * This method will check whether input string contains valid 
   * date value as YYYYMMDD.
   * Empty string is considered as "Invalid".
   * 
   * @param strDate is the string to be examined
   * @return boolean True means string contains valid date value
   */
  public static boolean isYMDDate (String strDate)
  {
    String newDateStr ;
    try
    {
      Date d = DataFormatter.frontendDateFormatYYYYMMDD.parse(strDate) ;
      newDateStr = DataFormatter.frontendDateFormatYYYYMMDD.format(d) ;
    }
    catch (Exception e)
    { // if parse error occurs, returns as error
      return (false) ;
    }
    return (strDate.equals(newDateStr)) ;
  }


    /**
     * This method will check whether the input string contains maximum 2 digit after the decimal point
     * @param str 
     * @return boolean 
     */
    public static boolean is2decimalformat(String str)
    {
        if (str == null || str.equals(""))
          return true;
        if (str.indexOf(".") == -1)
          return true;

        String tmpStr = str.substring(str.indexOf(".")+1);

        if (tmpStr.length() > 2)
          return false;

        return true;  
    }
    
  /**
   * This method will check whether input string contains only Letter, Digit or some special characters 
   * Special characters : # ' / - _ & ( ) . , + <space>
   * Empty string is considered as "valid".
   * 
   * @param inStr is the string to be examined
   * @return boolean True means string contains valid characters
   */
  public static boolean isLetterDigitOrSpecialChar (String inStr)
  {
        boolean isValid = true;    
        char tmpChar[] = inStr.toCharArray();
        for (int i=0; i < tmpChar.length; i++) {
            if (!Character.isLetterOrDigit(tmpChar[i])) {
                String tmpStr =  (new Character(tmpChar[i])).toString();
                if (!(tmpStr.equals("#") || tmpStr.equals("'")  || tmpStr.equals("/")  || tmpStr.equals("-")  || tmpStr.equals("_")  || tmpStr.equals("&")  || tmpStr.equals("(") 
                     || tmpStr.equals(")") || tmpStr.equals(".") || tmpStr.equals(",") || tmpStr.equals("+") || tmpStr.equals(" "))) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
  }
  
  /**
   * This method will check whether input string contains only Letter or Digit 
   * Empty string is considered as "valid".
   * 
   * @param inStr is the string to be examined
   * @return boolean True means string contains valid characters
   */
  public static boolean isLetterOrDigit (String inStr)
  {
        boolean isValid = true;    
        char tmpChar[] = inStr.toCharArray();
        for (int i=0; i < tmpChar.length; i++) {
            if (!Character.isLetterOrDigit(tmpChar[i])) {
                isValid = false;
                break;
            }
        }
        return isValid;
  }
  

  public static void main(String[] args)
  {
    DataValidator dataValidator = new DataValidator();
/*
    float a = (float)5.29;
    float b = (float)3001.23 ;
    float c = a * b ;
    float d = (float)((float)5.29 * (float)3001.23) ;

    double a1 = (double)5.22 ;
    double b1 = 3.22 * 5.22 * 3.6;
    double c1 = (double)((double)a1 * (double)b1)  ;
    double d1 = (double)((double)5.22 * (double)3.22)* (double)3.6 ;

    System.out.println((double) 60.51024) ;
    System.out.println("Reason for check double this way: Precision problem on float/double") ;
    System.out.println("float a=" + a) ;
    System.out.println("float b=" + b) ;
    System.out.println("float c=" + c) ;
    System.out.println("float d=" + d) ;
    System.out.println("float c=d? " + (c==d)) ;
    System.out.println("double a1=" + a1) ;
    System.out.println("double b1=" + b1) ;
    System.out.println("double c1=" + c1) ;
    System.out.println("double d1=" + d1) ;
    System.out.println("double c1=d1? " + (c1==d1)) ;
    System.out.println("==========================================") ;
    
    System.out.println("Self-test result : DataValidator") ;
    System.out.println("false is " + dataValidator.isDouble("", DataFormatter.frontendExchangeRateFormat)) ;  
    System.out.println("true is " + dataValidator.isDouble("3", DataFormatter.frontendExchangeRateFormat)) ;  
    System.out.println("true is " + dataValidator.isDouble("3.0", DataFormatter.frontendExchangeRateFormat)) ;  
    System.out.println("true is " + dataValidator.isDouble("3.5", DataFormatter.frontendExchangeRateFormat)) ;  
    System.out.println("true is " + dataValidator.isDouble("3.56", DataFormatter.frontendExchangeRateFormat)) ;  
    System.out.println("true is " + dataValidator.isDouble("3.567", DataFormatter.frontendExchangeRateFormat)) ;  

    System.out.println("true is " + dataValidator.isDouble("3.5678", DataFormatter.frontendExchangeRateFormat)) ;  
    System.out.println("false is " + dataValidator.isDouble("3.567891", DataFormatter.frontendExchangeRateFormat)) ;  
    System.out.println("false is " + dataValidator.isDouble("3.5678912", DataFormatter.frontendExchangeRateFormat)) ;  
*/
    String testDate = "14x-04--4" ;
    System.out.println(testDate + " valid ? " + dataValidator.isInputDate(testDate)) ;
    testDate = "14-04-14" ;
    System.out.println(testDate + " valid ? " + dataValidator.isInputDate(testDate)) ;
    testDate = "14-14-14" ;
    System.out.println(testDate + " valid ? " + dataValidator.isInputDate(testDate)) ;
  }
}