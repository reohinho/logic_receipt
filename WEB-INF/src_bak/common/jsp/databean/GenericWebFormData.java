/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/databean/GenericWebFormData.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:15 $
  $Revision: 1.1.1.1 $
*/

/*
 * Available CHECKING:
 *  - DATE, YMD, TIMESTAMP
 *  - NUMBER, INTEGER, POSITIVE, NONNEGATIVE
 *  - REQUIRED, UPPERCASE, BLANKONLY
 *  - CHECKBOX
 */
package common.jsp.databean;

import java.sql.Timestamp;
import java.util.Vector ;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.scmp.circ.utility.CircUtilities;
import com.scmp.circ.utility.CircMessage;
import common.databean.DataValidator;
import common.databean.DataFormatter;

public abstract class GenericWebFormData extends GenericStringData
{
  final public String chkRequired = "REQUIRED" ;
  final public String chkBlankOnly = "BLANKONLY" ;
  final public String chkUppercase = "UPPERCASE" ;
  final public String chkDate = "DATE" ;
  final public String chkTimestamp = "TIMESTAMP" ;
  final public String chkCheckBox = "CHECKBOX" ;
  final public String chkNumber = "NUMBER" ;
  final public String chkInteger = "INTEGER" ;
  final public String chkPositive = "POSITIVE" ;
  final public String chkZeroOrAbove = "NONNEGATIVE" ;
  
  private String SYSTEM_ObjectId ;
  private String SYSTEM_UserId ;
  private String PageID;
  private String Message;
  private String shouldRefresh;
  private String shortcut;
  private boolean failedToLoad = false ;

  public static final int ALL_PAGES = -1 ;
  
  private boolean errorFlag ;
  private String fieldnames[] ;
  private String labels[] ;
  private String checkings[] ;
  private int pagenos[] ;
  private int maxlengths[];
  private Vector vError = new Vector() ;

  private String sessionRef ;
    private String webFormName;

  public boolean isLoadFailed()
  {
    return failedToLoad ;
  }

  public void setFailedToLoad()
  {
    failedToLoad = true ;
    this.errFlag[0] = true ;    
  }

  public void clearFailedToLoad()
  {
    failedToLoad = false ;
  }
  
  public String getSystemObjectId()
  {
    return SYSTEM_ObjectId ;  
  }
  
  public void setSystemObjectId(String pObjectId)
  {
    SYSTEM_ObjectId = pObjectId ;  
  }

  public String getPageID() 
  {
      return PageID;
  }

  public void setPageID(String pPageID) 
  {
      PageID = pPageID;
  }

  public String getShouldRefresh() 
  {
    return shouldRefresh;
  }

  public void setShouldRefresh(String pShouldRefresh) 
  {
      shouldRefresh = pShouldRefresh;
  }
  
  public String getShortcut()
  {
	  return shortcut;
  }
  
  public void setShortcut(String newValue)
  {
	  shortcut = newValue;
  }
  public String getMessage() 
  {
      return Message;
  }

  public void setMessage(String pMessage) 
  {
      Message = pMessage;
  }
  /**
   * Parent method override. 
   * When storing a timestamp value as string:
   * (a) empty string will be stored if value = null
   * (b) if field checking is set to DATE or YMD, YYYYMMDD will be stored
   * (c) otherwise, assume field used for output, so set it to DD-MMM-YY.
   */
  public void setData(int fieldno, Timestamp value)
  {
    if (value == null)
    {
      setData(fieldno, "") ;
    }
    else if (isTimestamp(fieldno))
    {
      setData(fieldno, DataFormatter.Timestamp2YYYYMMDDWithTimeFormat(value));
    }
    else if (isDate(fieldno) || isYMD(fieldno))
    {
      setData(fieldno, DataFormatter.Timestamp2YYYYMMDDFormat(value));      
    }
    else
    {
      setData(fieldno, DataFormatter.Timestamp2ScreenFormat(value));
    }
    return ;
  }
  
  /**
   * Return true if web form data is valid.
   * If this method is not overriden, it will return value by GenericStringData.hasErr().
   */
  public boolean isValid() 
  {
    return (this.hasErr() == false ) ;
  }

  /**
   * Return the error vector
   */
  public Vector getErrorVector()
  {
    return vError ;
  }

  /**
   * Return all error messages in a vector. Each vector element contains one error message in String format.
   * Assume pageno = 1.
   */
  public Vector getErrorMsg()
  {
    return getErrorMsg(1) ;
  }

  /**
   * Return all error messages in a vector for specified page. 
   * @PARAM int page
   * @RETURN Vector Each vector element contains one error message in String format.
   */
  public Vector getErrorMsg(int page)
  {
    Vector v = new Vector() ;
    for (int i =0; i < vError.size(); i++)
    {
      ErrorItem item = (ErrorItem)vError.elementAt(i) ;
      if ((page == ALL_PAGES) || (pagenos[item.fieldno-1] == page))
      {
        v.addElement(composeErrorMsg(item.errorCode, item.errorMsg));
      }
    }
    return v ;
  }


  /**
   * Return error mark in HTML if specified "pFieldNo" is in error condition.
   * @PARAM int fieldno
   */
  public String getErrorMark(int pFieldno)
  {
    if (this.getErrFlag(pFieldno) == true)
      return "*" ;
    else
      return "" ;
  }

  /**
   * Return a composed error message by first translating errorCode into generic error message, 
   * then append extraMsg
   * @PARAM String errorCode
   * @PARAM String extraMsg
   * @RETURN String
   */
  private String composeErrorMsg(String errorCode, String extraMsg)
  {
    StringBuffer sb = new StringBuffer() ;

    if (errorCode != null && errorCode.length() > 0)
    {
      //sb.append(CircMessage.getMessage(Integer.parseInt(errorCode))) ;
    }
    if (sb.length() > 0) { sb.append(" ") ; }

    sb.append(extraMsg) ;

    return sb.toString() ;
  }
  
  /**
   * Return error message associated with specified "pFieldno"
   */
  public Vector getErrorMsgByFieldNo(int pFieldno)
  {
    Vector v = new Vector () ;
    for (int i =0; i < vError.size(); i++)
    {
      ErrorItem item = (ErrorItem)vError.elementAt(i) ;
      if (item.fieldno == pFieldno)
      {
        v.addElement(composeErrorMsg(item.errorCode, item.errorMsg));
      }
    }
    return v ;
  }
  
  public boolean dataIsEmpty(int field)
  {
    String s = getDataOrg(field-1) ;
    if (s == null || s.equals("")) 
    {
      return true ;
    }
    else
    {
      return false ;
    }
  }
  
  public void resetFormData() 
  {
  }

  protected abstract void initLabels() ;
 
  public GenericWebFormData(int maxFields) 
  {
    super(maxFields);
    labels = new String[maxFields] ;
    fieldnames = new String[maxFields] ;
    checkings = new String[maxFields] ;
    pagenos = new int[maxFields] ; 
    maxlengths = new int[maxFields] ; 
    this.initLabels();
  }

  /**
   * Set input field name, screen label and basic checking by "fieldno"
   */
  public void setLabel(int fieldno, String fieldname, String label, String checking)
  { // if no page number is given, assume field is on page 1
    setLabel(fieldno, fieldname, 0, label, checking, 1) ;
  }

  public void setLabel(int fieldno, String fieldname, int maxlength, String label, String checking)
  { // if no page number is given, assume field is on page 1
    setLabel(fieldno, fieldname, maxlength, label, checking, 1) ;
  }


  /**
   * Set Screen Variable by field
   */
  public void setLabel(int fieldno, String fieldname, String label, String checking, int pageno)
  {
    setLabel(fieldno, fieldname, 0, label, checking, pageno) ;
  }

  public void setLabel(int fieldno, String fieldname, int maxlength, String label, String checking, int pageno)
  {
    int pos = fieldno - 1;
    
    labels[pos] = label ;
    fieldnames[pos] = fieldname ;
    checkings[pos] = checking ;
    pagenos[pos] = pageno ;
    maxlengths[pos] = maxlength ;
  }

  public void setChecking(int fieldno, String newChecking)
  {
    int pos = fieldno - 1;
    
    checkings[pos] = newChecking ;
  }


  public int getMaxLength(int fieldno)
  {
    int pos = fieldno - 1;
    return maxlengths[pos];
  }
  
  public void setMaxLength(int fieldno, int maxlength)
  {
    int pos = fieldno - 1;
    maxlengths[pos] = maxlength ;
  }
  
  public class ErrorItem
  {
    public int fieldno ;
    public String errorCode ;
    public String errorMsg ;
    
    public ErrorItem(int pFieldNo, String pErrorCode, String pErrorMsg)
    {
      fieldno = pFieldNo ;
      errorCode = pErrorCode ;
      errorMsg = pErrorMsg ;
    }
  }

  /**
   * Parent method override.
   * If data type checkign is "DATE" or "YMD", assume data is in YYYYMMDD format.
   * Otherwise, assume date is in DD-MM-YY format (Dataformatter.frontendDateFormatinput).
   */
  public Timestamp getTimestampByField(int fieldno)
  {
    String inputDate = getDataOrg(fieldno);
    if (isDate(fieldno) || isYMD(fieldno))
    {
      return CircUtilities.getTimestampByString(inputDate, DataFormatter.frontendDateFormatYYYYMMDD.toPattern()) ;            
    }
    else if (isTimestamp(fieldno))
    {
      return CircUtilities.getTimestampByString (inputDate, DataFormatter.frontendDateFormatYYYYMMDDWithTime.toPattern()) ;      
    }
    else
    {
      return CircUtilities.getTimestampByString (inputDate, DataFormatter.frontendDateFormatInput.toPattern()) ;      
    }
  }
  
  /**
   * Parent Method overriden to include field number and store error to internal vector this.vError.
   */
  public void addError (int fieldno, String errCode, String msg)
  {
    int i ;
    String placeholder = "$label$" ;
    String errMsg ;
    
    this.setErrFlag(fieldno);
    
    i = msg.indexOf(placeholder) ;
    if (i == -1)
    { 
      errMsg = msg ;
    }
    else
    {
      StringBuffer sb = new StringBuffer(msg) ;
      sb.replace(i, i+placeholder.length(), this.getLabel(fieldno)) ;
      errMsg = sb.toString();
    }

    this.setErrFlag(fieldno);
    this.vError.add( new ErrorItem(fieldno, errCode, errMsg) ) ;
  }

  public boolean validate()
  { // if no page number is given, do validation on page 1
    return validate(1) ;
  }

  public void resetErrorByPage(int pageNo)
  {
    // clear error flag for individual field
    boolean newErrorFlag = false ;
    
    for (int i=1; i <= this.dataSize ; i++)
    {
      if (isFieldOnPage(i, pageNo) == true)
      {
        this.errFlag[i] = false ; 
      }
      // recalculate overall error flag
      newErrorFlag = (newErrorFlag || this.errFlag[i]) ;
    }
    
    // set overall error flag
    this.errFlag[0] = newErrorFlag ;

    // generate new error vector
    Vector v = new Vector() ;
    for (int j=0; j < this.vError.size(); j++)
    {
      ErrorItem ei = (ErrorItem)vError.elementAt(j) ;
      if (isFieldOnPage(ei.fieldno, pageNo) == false)
      {
        v.addElement(ei);
      }
    }
    vError = v ;
  }

  private boolean isFieldOnPage(int fieldno, int pageNo)
  {
    return ((getPageno(fieldno) == pageNo) || (pageNo == ALL_PAGES)) ;
  }
  
  /*
   * Validate data item according to checking conditions and set errFlag accordingly.
   * Return true if no error was found.
   * 
   *  REQUIRED :- String must have length > 0
   *  NUMBER :- Field must contain a valid number, empty string is valid
   *  DATE :- Field must contain a valid date value, empty string is valid
   */
  public boolean validate(int pageNo)
  {
    this.resetErrorByPage(pageNo);    
    
    for (int i=1; i <= this.dataSize ; i++)
    {
      if (isFieldOnPage(i, pageNo) == true)
      {      
        String chk = this.getChecking(i) ;
        if ((chk == null) || chk.equals(""))  
        { // if not checking require, do nothing
        }
        else
        {
          String d = this.getData(i).trim() ;
          boolean emptyValue = (d == null || d.length() == 0) ;
          if (isBlankOnly(i))
          {
            if (emptyValue == false)
            {
              this.addError(i, "", "Field $label$ must be blank");
            }
          }
          if (isRequired(i))
          {
            if (emptyValue == true)
            {
              this.addError(i, "", "Field $label$ cannot be blank");
            }
          }
          if (isNumber(i))
          {
            if (emptyValue)
            { // if input is empty, assume OK
            }
            else
            {
              if (DataValidator.isDouble(d) == false)
              {
                this.addError(i, "", "Field $label$ should be a number.");
              }
              else
              {
                Double dbl = this.getDoubleByField(i) ;
                if (isInteger(i))
                {
                 
                  /*Long tempLong = new Long(dbl.longValue()) ;
                  if (dbl.compareTo(new Double(tempLong.doubleValue())) != 0)
                  {
                      this.addError(i, "", "Field $label$ should be an integer.");                    
                  }
                  */
                  /** remarked codes above are replaced by johnnie on 2004-05-12 **/
                  try
                  {
                    long l = Long.parseLong(this.getData(i));
                  }catch(NumberFormatException ne )
                  {
                      this.addError(i, "", "Field $label$ should be an integer.");                    
                  }                  
                }
                if (isPositive(i))
                {
                    if (dbl.compareTo(new Double(0)) <= 0)
                    {
                      this.addError(i, "", "Field $label$ must be greater than zero.");
                    }
                }
                if (isNonNegative(i)) 
                {
                    if (dbl.compareTo(new Double(0)) < 0) 
                    {
                        this.addError(i, "", "Field $label$ must be greater than or equal to zero.");
                    }
                }
              }
            }
          }
          if (isYMD(i) || isDate(i))
          {
            if (emptyValue)
            { // if input is empty, assume OK
            }
            else
            {
              if (DataValidator.isInputDate(d) == false)
              {
                this.addError(i, "", "Field $label$ should be a date in " + DataFormatter.frontendDateFormatInput.toPattern());
              }
              else
              { // if successful, reformat data value and store in databean as YYYYMMDD
                String tempStr ; 
                try {
                  Date tempDate = DataFormatter.frontendDateFormatInput.parse(d) ;
                  tempStr = DataFormatter.frontendDateFormatYYYYMMDD.format(tempDate) ;
                }
                catch (Exception e) 
                {
                  tempStr = d ;
                }
                this.setData(i, tempStr);            
              }
            }          
          }
        }
      }
    }
    return (this.hasErr() == false) ;
  }
  
  public String getLabel(int fieldno)
  {
    return labels[fieldno-1] ;
  }

  public String getFieldname(int fieldno)
  {
    return fieldnames[fieldno-1] ;
  }

  public String getChecking(int fieldno)
  {
    if (checkings[fieldno-1] == null)
    {
     return "" ; 
    }
    else
    {
      return checkings[fieldno-1] ;
    }
  }

  public int getPageno(int fieldno)
  {
    return pagenos[fieldno - 1] ;
  }

  public void setWithHttpRequest (HttpServletRequest request)
  {
    this.setWithHttpRequest(request, 1);
  }

  /*
   *  CHECKBOX :- if not found in request, 'N' will be returned. otherwise, value is'Y'.
   *  UPPERCASE :- Content will be converted to uppercase by String.toUpperCase()
   */
  public void setWithHttpRequest (HttpServletRequest request, int pageNo) 
  { 
    for (int i=1; i <= this.dataSize ; i++)
    {
      if (isFieldOnPage(i, pageNo) == true)
      {
        String fn = this.getFieldname(i) ;
        // skip entries with null fieldname
        if (fn != null)  
        {
          String s = request.getParameter(fn) ;
          String chk = this.getChecking(i) ;
          if (isUppercase(i))
          {
            if (s != null)
            { 
              s = s.toUpperCase() ;
            }          
          }
          if (isCheckbox(i))
          { // for checkbox data
            this.setData(i, ((s == null) ? "N" : "Y")); 
          }
          else
          { // non-checkbox data
            if (s != null) 
            {
              this.setData(i, s); 
            }
          }
        }
      }
    }
  }

  public boolean isUppercase(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return (chk.indexOf(this.chkUppercase) != -1) ;    
  }
  
  public boolean isDate(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return (chk.indexOf(this.chkDate) != -1) ;    
  }
  
  public boolean isPositive(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return (chk.indexOf(this.chkPositive) != -1) ;    
  }
  
  public boolean isYMD(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return (chk.indexOf("YMD") != -1) ;    
  }
  
  public boolean isTimestamp(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return (chk.indexOf(this.chkTimestamp) != -1) ;    
  }
  
  public boolean isNumber(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return ((chk.indexOf(this.chkNumber) != -1) || (chk.indexOf(this.chkInteger) != -1)) ;    
  }
  
  public boolean isInteger(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return (chk.indexOf(this.chkInteger) != -1) ;    
  }

  public boolean isNonNegative(int fieldno) 
  {
      String chk = this.getChecking(fieldno);
      return (chk.indexOf(this.chkZeroOrAbove) != -1);
  }
  
  public boolean isCheckbox(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return (chk.indexOf(this.chkCheckBox) != -1) ;    
  }
  
  public boolean isRequired(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return (chk.indexOf(this.chkRequired) != -1) ;
  }

  public boolean isBlankOnly(int fieldno)
  {
    String chk = this.getChecking(fieldno) ;
    return (chk.indexOf(this.chkBlankOnly) != -1) ;
  }
  
  
  public String getSystemUserId()
  {
    return SYSTEM_UserId;
  }

  public void setSystemUserId(String newUserId)
  {
    SYSTEM_UserId = newUserId;
  }

  public void setCheckingRequired(int fieldno)
  {
    clearCheckingBlankOnly(fieldno) ;
    setCheckingString(fieldno,  this.chkRequired) ;
  }

  public void clearCheckingRequired(int fieldno)
  {
    clearCheckingString(fieldno, this.chkRequired) ;
  }

  public void setCheckingBlankOnly(int fieldno)
  {
    clearCheckingRequired(fieldno) ;
    setCheckingString(fieldno,  this.chkBlankOnly) ;
  }

  public void clearCheckingBlankOnly(int fieldno)
  {
    clearCheckingString(fieldno, this.chkBlankOnly) ;
  }

  private void setCheckingString(int fieldno, String s)
  {
    String chk =  this.getChecking(fieldno).trim() ;
    if (chk.indexOf(s) == -1)
    {
      if (chk.length() > 0) { chk = chk + "," + s ; } else { chk = s ; }
      this.setChecking(fieldno, chk);
    }
  }
  
  private void clearCheckingString(int fieldno, String s)
  {
    String chk =  this.getChecking(fieldno).trim() ;
    int i = chk.indexOf(s);
    if (i != -1)
    {
      chk = CircUtilities.replace(chk, s, "") ;
      this.setChecking(fieldno, chk);
    }
  }

    public String getWebFormName()
    {
        return webFormName;
    }

    public void setWebFormName(String newWebFormName)
    {
        webFormName = newWebFormName;
    }
  
}