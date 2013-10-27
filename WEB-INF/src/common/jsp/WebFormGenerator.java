package common.jsp;

import common.databean.DataFormatter;
import common.databean.DataValidator;
import common.jsp.databean.GenericWebFormData;

import java.util.Date;
import java.util.Vector;

public class WebFormGenerator 
{
  GenericWebFormData obj ;
  
  public WebFormGenerator(GenericWebFormData formData)
  {
    this.obj = formData ;
  }

  /* 
   * Return Formatted Screen Label as <TD class='fieldname' $htmlOptions$>LABEL</td>
   */
  public String TDforLabel(int fieldno, String htmlOptions)
  {
    StringBuffer sb = new StringBuffer() ;

    sb.append("<td ") ;
    sb.append(htmlOptions) ;
    if (obj.getErrFlag(fieldno) == true)
    {
      sb.append(" class='fieldnameError'>") ;    
    }
    else
    {
      if (obj.isRequired(fieldno))
      {
        sb.append(" class='fieldnameCompulsory'>") ;              
      }
      else
      {
        sb.append(" class='fieldname'>") ;      
      }
    }
    sb.append(obj.getLabel(fieldno)) ;
    sb.append(obj.getErrorMark(fieldno)) ;
    sb.append("</td>") ;

    return sb.toString();
  }

  /* 
   * Return Formatted Screen Label as <TD class='fieldname' $htmlOptions$>LABEL, ADDED CONTENTs</td>
   */
  public String TDforLabel(int fieldno, String htmlOptions, String addedContent)
  {
    StringBuffer sb = new StringBuffer() ;

    sb.append("<td ") ;
    sb.append(htmlOptions) ;
    if (obj.getErrFlag(fieldno) == true)
    {
      sb.append(" class='fieldnameError'>") ;    
    }
    else
    {
      if (obj.isRequired(fieldno))
      {
        sb.append(" class='fieldnameCompulsory'>") ;              
      }
      else
      {
        sb.append(" class='fieldname'>") ;      
      }
    }
    sb.append(obj.getLabel(fieldno)) ;
    sb.append(obj.getErrorMark(fieldno)) ;
    sb.append(addedContent) ;
    sb.append("</td>") ;

    return sb.toString();
  }
  public String TDforFieldInput(int fieldno, int size, String htmlOptions)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<TD>") ;
    sb.append(htmlFieldInput(fieldno, size, htmlOptions)) ;
    sb.append("</TD>") ;
    
    return sb.toString();    
  }

  public String TDforFieldInput(int fieldno)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<TD>") ;
    sb.append(htmlFieldInput(fieldno)) ;
    sb.append("</TD>") ;
    
    return sb.toString();    
  }

  /*
   * Return Error Vector for specified page in HTML format
   * Output: $msg<br>$msg<br>.....<br>
   */
  public String htmlErrorMsg(int pageno)
  {
    StringBuffer sb = new StringBuffer("") ;

    Vector v = obj.getErrorMsg(pageno) ;
    if (v != null)
    {
      sb.append("<p class='inputError'>") ;
      for (int i=0; i< v.size() ;i++)
      { 
        if (i > 0) { sb.append("<BR>") ; }
        sb.append((String) v.elementAt(i)) ;
      }
      sb.append("</p>") ;
    }
    
    return sb.toString();        
  }

  public String htmlErrorMsg()
  {
    return htmlErrorMsg(1) ;  
  }

  /**
   * Format data for html output purpose. Date field is formatted as DD-MMM-YY (DataFormatter.ScreenFormat)
   * @PARAM int fieldno field no in web form data
   * @RETURN String formatted string for html ouptut
   */
  public String htmlFieldOutput(int fieldno)
  {
    if (obj.isDate(fieldno) || obj.isYMD(fieldno))
    { // data type is DATE and stored value is in YYYYMMDD, return as DD-MMM-YY.
      try
      {
        Date tempDate = DataFormatter.frontendDateFormatYYYYMMDD.parse(obj.getData(fieldno)) ;
        return DataFormatter.frontendDateFormat.format(tempDate) ;
      }
      catch (Exception e)
      { // if parse failed, return original string
        return obj.getDataForHtml(fieldno)  ;      
      }
    }
    else
    {
      return obj.getDataForHtml(fieldno)  ;
    }
  }
  
  public String htmlFieldInput(int fieldno)
  {
    return this.htmlFieldInput(fieldno, "") ;
  }
  public String htmlFieldInput(int fieldno,boolean view)
  {
    return this.htmlFieldInput(fieldno, "",view) ;
  }
  public String htmlFieldInput(int fieldno, int size, String htmlOptions)
  {
    return this.htmlFieldInput(fieldno, " size="+size+" " +htmlOptions) ;
  }  
  	public String htmlFieldInput(int fieldno, String htmlOptions, boolean view) {
		StringBuffer sb = new StringBuffer();
		if (view) {
			String value = obj.getData(fieldno);
			if (obj.isDate(fieldno) || obj.isYMD(fieldno)) {
				if (DataValidator.isYMDDate(value)) {
					try {
						Date d = DataFormatter.frontendDateFormatYYYYMMDD.parse(value);
						sb.append(DataFormatter.frontendDateFormatInput.format(d));
						sb.append(this.htmlFieldHidden(fieldno));
					} catch (Exception e) {
						sb.append("BAD-DATE");
					}
				} else { // if stored value is not a valid date, treat it as normal text
					sb.append(obj.getDataForHtml(fieldno));
				}
			//}else if (obj.isTimestamp(fieldno)){
			//	try {
			//		Date d = DataFormatter.frontendDateFormatYYYYMMDDWithTime.parse(value);
			//		sb.append(DataFormatter.frontendDateFormatWithTime.format(d));
			//		sb.append(this.htmlFieldHidden(fieldno));
			//	} catch (Exception e) {
			//		sb.append("BAD-DATE");
			//	}
			} else {
				sb.append(obj.getDataForHtml(fieldno));
				sb.append(this.htmlFieldHidden(fieldno));
			}
			
		} else {
			return htmlFieldInput(fieldno, htmlOptions);
		}
		return sb.toString();
	}
  public String htmlFieldInput(int fieldno, String htmlOptions)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<input type='text' name='") ;
    sb.append(obj.getFieldname(fieldno)) ;
    sb.append("' ") ;
    sb.append(" id=\"") ;
    sb.append(obj.getFieldname(fieldno)) ;
    sb.append("\" ") ;
    String opt =  (htmlOptions == null ?  ""  :  htmlOptions) ;
    sb.append(opt) ;
    
    if (obj.getMaxLength(fieldno) > 0) 
    {
      sb.append("MAXLENGTH=") ;
      sb.append(obj.getMaxLength(fieldno)) ;
      if (opt.toUpperCase().indexOf("SIZE=") < 0)  // append size attribute only if not defined in HTMLOptions
      {
          sb.append(" size=") ;
          sb.append(obj.getMaxLength(fieldno)) ;
      }
    }
    
    sb.append(" value=\"") ;
    if (obj.isDate(fieldno) || obj.isYMD(fieldno))
    {
      String value = obj.getData(fieldno) ;
      if (DataValidator.isYMDDate(value))
      {
        try 
        {
          Date d = DataFormatter.frontendDateFormatYYYYMMDD.parse(value) ;
          sb.append(DataFormatter.frontendDateFormatInput.format(d)) ;
        }
        catch (Exception e)
        {
          sb.append("BAD-DATE") ; 
        }
      }
      else
      { // if stored value is not a valid date, treat it as normal text
        sb.append(obj.getDataForHtml(fieldno)) ;
      }
    }
    else
    {
      sb.append(obj.getDataForHtml(fieldno)) ;
    }
    sb.append("\">") ;
    return sb.toString() ;
  }
  
  /* 
   * Return <INPUT TYPE='TEXT' NAME='$fieldname' SIZE='$size' VALUE='$value'>
   */
  public String htmlFieldInput(int fieldno, int size)
  {
    return this.htmlFieldInput(fieldno, size, "") ;
  }

  /* 
   * Return <input type="checkbox" name="$fieldname" value="checkbox" checked>
   */
  public String htmlCheckbox(int fieldno, String value)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<input type='checkbox' value='checkbox' name='") ;
    sb.append(obj.getFieldname(fieldno)) ;
    sb.append("'") ;
    if (value.equals("Y"))
    {
      sb.append(" checked") ;
    }
    sb.append(">") ;
    return sb.toString();
  }

  public String htmlCheckbox2(int fieldno, String value, String htmlOption)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<input type='checkbox' value='" + value + "' name='") ;
    sb.append(obj.getFieldname(fieldno)) ;
    sb.append("'") ;
    if (value.equals("Y"))
    {
      sb.append(" checked") ;
    }
    if (!htmlOption.equals(""))
    {
        sb.append(" " + htmlOption);
    }
    sb.append(">") ;
    return sb.toString();
  }

  /* 
   * Return <input type="checkbox" name="$fieldname" value="checkbox" checked>
   */
  public String htmlCheckbox(int fieldno)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<input type='checkbox' value='checkbox' name='") ;
    sb.append(obj.getFieldname(fieldno)) ;
    sb.append("'") ;
    if (obj.getData(fieldno).equals("Y"))
    {
      sb.append(" checked") ;
    }
    sb.append(">") ;
    return sb.toString();
  }

  /* 
   * If the input field value is checked 
   * Return <input type="checkbox" name="$fieldname" value="checkbox" checked onclick="$fieldname.checked=true">
   * else
   * Return <input type="checkbox" name="$fieldname" value="checkbox" checked onclick="$fieldname.checked=false">
   */
  public String htmlReadOnlyCheckbox(int fieldno)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<input type='checkbox' value='checkbox' name='") ;
    sb.append(obj.getFieldname(fieldno)) ;
    sb.append("'") ;
    if (obj.getData(fieldno).equals("Y"))
    {
      sb.append(" checked") ;
      sb.append(" onclick=\""+obj.getFieldname(fieldno)+".checked=true\"");
    } else {
      sb.append(" onclick=\""+obj.getFieldname(fieldno)+".checked=false\"");
    }
    sb.append(">") ;
    return sb.toString();
  }
  
  /* 
   * Return <input type="checkbox" name="$fieldname" value="checkbox" checked $htmlOptions>
   */
  public String htmlCheckboxWithHtmlOptions(int fieldno, String htmlOptions)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<input type='checkbox' value='checkbox' name='") ;
    sb.append(obj.getFieldname(fieldno)) ;
    sb.append("'") ;
    if (obj.getData(fieldno).equals("Y"))
    {
      sb.append(" checked") ;
    }
    if (htmlOptions != null && !htmlOptions.equals("")) sb.append(" "+htmlOptions);
    sb.append(">") ;
    return sb.toString();
  }

  /* 
   * Return <INPUT TYPE='HIDDEN' NAME='$fieldname' VALUE='$value'>
   */
  	public String htmlFieldHidden(int fieldno) {
		StringBuffer sb = new StringBuffer();
		sb.append("<input type='hidden' name='");
		sb.append(obj.getFieldname(fieldno));
		sb.append("'");
		sb.append(" id='");
		sb.append(obj.getFieldname(fieldno));
		sb.append("' ");
		String data = (obj.getData(fieldno) == null ? "" : obj.getData(fieldno));
		if (data.trim().length() > 0) {
			sb.append(" value='");
			String value = obj.getData(fieldno);
			if (obj.isDate(fieldno) || obj.isYMD(fieldno)) {
				
				if (DataValidator.isYMDDate(value)) {
					try {
						Date d = DataFormatter.frontendDateFormatYYYYMMDD.parse(value);
						sb.append(DataFormatter.frontendDateFormatInput.format(d));
					} catch (Exception e) {
						sb.append("BAD-DATE");
					}
				} else { // if stored value is not a valid date, treat it as normal text
					sb.append(obj.getDataForHtml(fieldno));
				}
			//}else if (obj.isTimestamp(fieldno)){
			//	try {
					//Date d = DataFormatter.frontendDateFormatYYYYMMDDWithTime.parse(value);
			//		sb.append(value);
		//		} catch (Exception e) {
			//		sb.append("BAD-DATE");
			//	}
			} else {
				sb.append(obj.getData(fieldno));
			}
			sb.append("'");
		}
		sb.append(">");
		return sb.toString();
	}
  /*
	 * Return <TEXTAREA NAME='$fieldname' COLS=$cols ROWS=$rows>$value</TEXTAREA>
	 */
  public String htmlForInputTextArea(int fieldno, int cols, int rows)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<textarea name='") ;
    sb.append(obj.getFieldname(fieldno)) ;
    sb.append("' cols='") ;
    sb.append(cols) ;
    sb.append("' rows='") ;
    sb.append(rows) ;
    sb.append("'>") ;
    sb.append(obj.getDataForHtml(fieldno)) ;
    sb.append("</textarea>") ;

    return sb.toString();
  }

  /* 
   * Return <TEXTAREA NAME='$fieldname' COLS=$cols ROWS=$rows $htmlOptions>$value</TEXTAREA>
   */
  public String htmlForInputTextArea(int fieldno, int cols, int rows, String htmlOptions)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<textarea name='") ;
    sb.append(obj.getFieldname(fieldno)) ;
    sb.append("' cols='") ;
    sb.append(cols) ;
    sb.append("' rows='") ;
    sb.append(rows) ;
    sb.append("'") ;
    if (htmlOptions != null && !htmlOptions.equals("")) sb.append(" "+htmlOptions);
    sb.append(">");
    sb.append(obj.getDataForHtml(fieldno)) ;
    sb.append("</textarea>") ;

    return sb.toString();
  }

  /* 
   * Return <TD><TEXTAREA NAME='$fieldname' COLS=$cols ROWS=$rows>$value</TEXTAREA></TD>
   */
  public String TDforInputTextArea(int fieldno, int cols, int rows)
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("<td>") ;
    sb.append(htmlForInputTextArea(fieldno, cols, rows)) ;
    sb.append("</td>") ;

    return sb.toString();
  }

  /* 
   * Return Formatted Screen Label as <TD class='fieldname'>LABEL</td>
   */
  public String TDforLabel(int fieldno)
  {
    return TDforLabel(fieldno, "") ;
  }  
}