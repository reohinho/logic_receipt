package common.jsp;

import common.databean.GenericSortableTable;
import common.databean.GenericTable;
import common.jsp.databean.GenericStringData;

import java.util.Hashtable;
import java.util.Vector;


public class HTMLGenerator 
{
  public static final int iJAVASCRIPT_IN_CATEGORY_SUBCATEGORY_DEPENDENCY = 0;
  public static final int iCATEGORY_IN_CATEGORY_SUBCATEGORY_DEPENDENCY = 1;
  public static final int iSUBCATEGORY_IN_CATEGORY_SUBCATEGORY_DEPENDENCY = 2;
  public static final int iJAVASCRIPT_IN_CATEGORY_MASTER_DEPENDENCY = 3;

  public HTMLGenerator()
  {
  }
  
  public static String buildJS2DArray(String v1, String v2)
  {
    return new String("new Array('" + v1 + "', '" + v2 + "')") ;
  }
  


// =====

  public static String generatePulldownList ( String[] arrValue, 
                                              String[] arrDesc, 
                                              String listName, 
                                              String selectedValue, 
                                              String addOnText,
                                              String emptyChoiceAtBeginning) {
/*                                              
    String str = generatePolldownList (arrValue, arrDesc, listName, selectedValue, addOnText) ;
    if (str == null) return "" ;
    str = str.substring(0, str.indexOf('>') +1) + emptyChoiceAtBeginning + str.substring(str.indexOf('>') +1) ;

    return str ;
*/
    String strPollDown = "";

    if (arrValue.length != arrDesc.length) {
      return strPollDown;
    }

//    if (selectedValue == null)
//      selectedValue = "";
    strPollDown += "<select name=\"" + GenericStringData.escapeDataInHtml(listName) + "\"" + addOnText + ">\n" + emptyChoiceAtBeginning;
    for (int i=0; i<arrValue.length; i++) {
      strPollDown += "<option value=\"" + GenericStringData.escapeDataInHtml(arrValue[i]) + "\"" ;
      if (selectedValue != null && arrValue[i].equals(selectedValue)) 
      {
        strPollDown += " selected";
      }
      strPollDown += ">" + GenericStringData.escapeDataInHtml(arrDesc[i]) + "</option>\n";
    }
    strPollDown += "</select>";

    return strPollDown;

  
  }

  public static String generatePulldownListInOneLine ( String[] arrValue, 
                                              String[] arrDesc, 
                                              String listName, 
                                              String selectedValue, 
                                              String addOnText,
                                              String emptyChoiceAtBeginning) {
    String strPollDown = "";
    if (arrValue.length != arrDesc.length) {
      return strPollDown;
    }
//    if (selectedValue == null)
//      selectedValue = "";
    strPollDown += "<select name='" + GenericStringData.escapeDataInHtml(listName) + "'" + addOnText + ">" + emptyChoiceAtBeginning;
    for (int i=0; i<arrValue.length; i++) {
      strPollDown += "<option value='" + GenericStringData.escapeDataInHtml(arrValue[i]) + "'" ;
      if (selectedValue != null && arrValue[i].equals(selectedValue)) 
      {
        strPollDown += " selected";
      }
      strPollDown += ">" + GenericStringData.escapeDataInHtml(arrDesc[i]) + "</option>";
    }
    strPollDown += "</select>";

    return strPollDown;

  
  }


/*
  public static String generatePolldownList(String[] arrValue, String[] arrDesc, String listName, String selectedValue, String addOnText) 
  {
    String strPollDown = "";
    if (arrValue.length != arrDesc.length) {
      return strPollDown;
    }
//    if (selectedValue == null)
//      selectedValue = "";
    strPollDown += "<select name=\"" + listName + "\"" + addOnText + ">\n";
    for (int i=0; i<arrValue.length; i++) {
      strPollDown += "<option value=\"" + arrValue[i] + "\"";
      if (selectedValue != null && arrValue[i].equals(selectedValue)) 
      {
        strPollDown += " selected";
      }
      strPollDown += ">" + arrDesc[i] + "</option>\n";
    }
    strPollDown += "</select>";

    return strPollDown;
  }
*/

  public static String generatePulldownList ( String[] arrValue, 
                                              String[] arrDesc, 
                                              String listName, 
                                              String[] arrSelectedValue, 
                                              String addOnText,
                                              String emptyChoiceAtBeginning) {
    String strPollDown = "";
    if (arrValue.length != arrDesc.length) {
      return strPollDown;
    }
    strPollDown += "<select name=\"" + GenericStringData.escapeDataInHtml(listName) + "\"" + addOnText + ">\n" + emptyChoiceAtBeginning ;
    for (int i=0; i<arrValue.length; i++) {
      strPollDown += "<option value=\"" + GenericStringData.escapeDataInHtml(arrValue[i]) + "\"";
      for (int j=0; arrSelectedValue!= null && j<arrSelectedValue.length; j++) {
        if (arrValue[i].equals(arrSelectedValue[j])) 
        {
          strPollDown += " selected";
        }
      }
      strPollDown += ">" + GenericStringData.escapeDataInHtml(arrDesc[i]) + "</option>\n";
    }
    strPollDown += "</select>";

    return strPollDown;
  }
/*
  public static String generatePolldownList(String[] arrValue, String[] arrDesc, String listName, String[] arrSelectedValue, String addOnText) 
  {
    String strPollDown = "";
    if (arrValue.length != arrDesc.length) {
      return strPollDown;
    }
    strPollDown += "<select name=\"" + listName + "\"" + addOnText + ">\n";
    for (int i=0; i<arrValue.length; i++) {
      strPollDown += "<option value=\"" + arrValue[i] + "\"";
      for (int j=0; arrSelectedValue!= null && j<arrSelectedValue.length; j++) {
        if (arrValue[i].equals(arrSelectedValue[j])) 
        {
          strPollDown += " selected";
        }
      }
      strPollDown += ">" + arrDesc[i] + "</option>\n";
    }
    strPollDown += "</select>";

    return strPollDown;
  }
*/ 


  public static String generateTable (GenericSortableTable gst, int colSeq[], String bodyRowAddition, boolean unEscape[], int colWidth[]) {
    String[] keys = gst.getKeyInArray() ;
    String sSumWidth="" ;
    int sumWidth=0 ;
    if (colWidth != null) {
      for (int cntWid = 0 ; cntWid < colWidth.length ; cntWid ++ ) 
         sumWidth += colWidth[cntWid] ;
      sSumWidth = "" + sumWidth ;
    }
    else
      sSumWidth = "100%" ;
    
    StringBuffer sb = new StringBuffer("<table  width='"+sSumWidth+"'  border='0' class='border'>\n") ;
//    if (keys.length >0) {
      // build column header
      Vector tblHead = gst.getColumnHead() ;
      
      sb.append("<tr align='left' class='columnhead'>") ;
      for (int colCnt = 0 ; tblHead!=null && colCnt < colSeq.length  ; colCnt ++) {
        String widthStr="" ;
        if (colWidth!=null && colWidth.length > colCnt  && colWidth[colCnt] > -1)
          widthStr += " width='" + colWidth[colCnt] + "'" ;
        sb.append("<th align='left'"+widthStr+">") ;
        if ((String)tblHead.elementAt(colSeq[colCnt])!=null)
          sb.append((colSeq[colCnt] < tblHead.size()) ? (String)tblHead.elementAt(colSeq[colCnt]) : "&nbsp;<!--Invalid column:"+(String)tblHead.elementAt(colCnt)+"-->") ;
        sb.append("</th>\n" );
      }
      sb.append("</tr>") ;
    if (keys.length >0) {
      for (int rowCnt = 0 ; rowCnt < keys.length ; rowCnt ++) {
        sb.append("<tr"+bodyRowAddition+">") ;
        Vector aRow = (Vector)gst.getRowByKeyId(keys[rowCnt]) ;
        for (int rowCntCol = 0 ; rowCntCol < colSeq.length ; rowCntCol ++) {
          sb.append("<td valign='top' align='left'>") ;
          if ((String)aRow.elementAt(colSeq[rowCntCol])!=null)
            sb.append((colSeq[rowCntCol] < aRow.size()) ? ((unEscape[colSeq[rowCntCol]])? (String)aRow.elementAt(colSeq[rowCntCol]): GenericStringData.escapeDataInHtml((String)aRow.elementAt(colSeq[rowCntCol]))) : "&nbsp;") ;
          sb.append("</td>\n") ;
        }
        sb.append("</tr>\n") ;
      }
    }
    sb.append("</table>\n") ;
  
    return sb.toString();
  }

  public static String generateTableSlow (GenericSortableTable gst, int colSeq[], String bodyRowAddition, boolean unEscape[], int colWidth[]) {
    String[] keys = gst.getKeyInArray() ;
    String sSumWidth="" ;
    int sumWidth=0 ;
    if (colWidth != null) {
      for (int cntWid = 0 ; cntWid < colWidth.length ; cntWid ++ ) 
         sumWidth += colWidth[cntWid] ;
      sSumWidth = "" + sumWidth ;
    }
    else
      sSumWidth = "100%" ;
    
    String str = "<table  width=\""+sSumWidth+"\"  border=\"0\" class=\"border\">\n" ;
//    if (keys.length >0) {
      // build column header
      Vector tblHead = gst.getColumnHead() ;
      
      str += "<tr align=\"left\" class=\"columnhead\">" ;
      for (int colCnt = 0 ; tblHead!=null && colCnt < colSeq.length  ; colCnt ++) {
        String widthStr="" ;
        if (colWidth!=null && colWidth.length > colCnt  && colWidth[colCnt] > -1)
          widthStr += " width=\"" + colWidth[colCnt] + "\"" ;
        str += "<th align=\"left\""+widthStr+">" ;
        if ((String)tblHead.elementAt(colSeq[colCnt])!=null)
          str +=  (colSeq[colCnt] < tblHead.size()) ? (String)tblHead.elementAt(colSeq[colCnt]) : "&nbsp;<!--Invalid column:"+(String)tblHead.elementAt(colCnt)+"-->" ;
        str += "</th>\n" ;
      }
      str += "</tr>" ;
    if (keys.length >0) {
      for (int rowCnt = 0 ; rowCnt < keys.length ; rowCnt ++) {
        str += "<tr"+bodyRowAddition+">" ;
        Vector aRow = (Vector)gst.getRowByKeyId(keys[rowCnt]) ;
        for (int rowCntCol = 0 ; rowCntCol < colSeq.length ; rowCntCol ++) {
          str += "<td valign=\"top\" align=\"left\">" ;
          if ((String)aRow.elementAt(colSeq[rowCntCol])!=null)
            str +=  (colSeq[rowCntCol] < aRow.size()) ? ((unEscape[colSeq[rowCntCol]])? (String)aRow.elementAt(colSeq[rowCntCol]): GenericStringData.escapeDataInHtml((String)aRow.elementAt(colSeq[rowCntCol]))) : "&nbsp;" ;
          str += "</td>\n" ;
        }
        str += "</tr>\n" ;
      }
    }
    str += "</table>\n" ;
  
    return str;
  }


  public static String generateTableSlow (GenericTable gt, int colSeq[], String bodyRowAddition, boolean unEscape[], int colWidth[]) {
    String [][]tblBody = gt.getTableBody() ;
    String sSumWidth="" ;
    int sumWidth=0 ;
    if (colWidth != null) {
      for (int cntWid = 0 ; cntWid < colWidth.length ; cntWid ++ ) 
         sumWidth += colWidth[cntWid] ;
      sSumWidth = "" + sumWidth ;
    }
    else
      sSumWidth = "100%" ;
    String str = "<table width=\""+sSumWidth+"\" border=\"0\" class=\"border\" cellpadding=2 cellspacing=2>\n" ;
//    if (tblBody.length >0) {
      // build column header
      String [] tblHead = gt.getTableColumnHeader() ;
      str += "<tr align=\"left\" class=\"columnhead\">" ;
      for (int colCnt = 0 ; colCnt < colSeq.length ; colCnt ++) {
        String widthStr="" ;
        if (colWidth!=null && colWidth.length > colCnt && colWidth[colCnt] > -1)
          widthStr += " width=\"" + colWidth[colCnt] + "\"" ;
        str += "<th align=\"left\""+widthStr+">" ;
        if (tblHead[colSeq[colCnt]] != null)
          str +=  (colSeq[colCnt] < tblHead.length) ? tblHead[colSeq[colCnt]] : "&nbsp;<!--Invalid column:"+tblHead[colCnt]+"-->" ;
        str += "</th>\n" ;
      }
      str += "</tr>" ;
    if (tblBody.length >0) {
      for (int rowCnt = 0 ; rowCnt < tblBody.length ; rowCnt ++) {      
        str += "<tr"+bodyRowAddition+">" ;
        String aRow[] = tblBody[rowCnt] ;
        for (int rowCntCol = 0 ; rowCntCol < colSeq.length ; rowCntCol ++) {
          String widthStr="" ;
          if (colWidth!=null && colWidth.length > rowCntCol && colWidth[rowCntCol] > -1)
            widthStr += " width=\"" + colWidth[rowCntCol] + "\"" ;
          str += "<td valign=\"top\" align=\"left\""+widthStr+">" ;
          if (aRow[colSeq[rowCntCol]] != null)
            str +=  (colSeq[rowCntCol] < aRow.length) ? ((unEscape[colSeq[rowCntCol]])? aRow[colSeq[rowCntCol]] : GenericStringData.escapeDataInHtml(aRow[colSeq[rowCntCol]])) : "&nbsp;" ;
          str += "</td>\n" ;
        }
        str += "</tr>\n" ;
      }
    }
    else  {
        str += "<tr><td colspan="+tblHead.length+" align=left valign=top>\n No record.</td></tr>\n" ;
    }
    str += "</table>\n" ;
  
    return str;
  }

  public static String generateTable (GenericTable gt, int colSeq[], String bodyRowAddition, boolean unEscape[], int colWidth[]) {
    String [][]tblBody = gt.getTableBody() ;
    String sSumWidth="" ;
    int sumWidth=0 ;
    if (colWidth != null) {
      for (int cntWid = 0 ; cntWid < colWidth.length ; cntWid ++ ) 
         sumWidth += colWidth[cntWid] ;
      sSumWidth = "" + sumWidth ;
    }
    else
      sSumWidth = "100%" ;

    
    StringBuffer sb = new StringBuffer("<table width=\""+sSumWidth+"\" border=\"0\" class=\"border\" cellpadding=2 cellspacing=2>\n" ) ;
//    if (tblBody.length >0) {
      // build column header
      String [] tblHead = gt.getTableColumnHeader() ;
      sb.append("<tr align=\"left\" class=\"columnhead\">") ;
      for (int colCnt = 0 ; colCnt < colSeq.length ; colCnt ++) {
        String widthStr="" ;
        if (colWidth!=null && colWidth.length > colCnt && colWidth[colCnt] > -1)
          widthStr += " width=\"" + colWidth[colCnt] + "\"" ;
        sb.append("<th align=\"left\""+widthStr+">") ;
        if (tblHead[colSeq[colCnt]] != null)
          sb.append((colSeq[colCnt] < tblHead.length) ? tblHead[colSeq[colCnt]] : "&nbsp;<!--Invalid column:"+tblHead[colCnt]+"-->") ;
        sb.append("</th>\n") ;
      }
      sb.append("</tr>") ;
    if (tblBody.length >0) {
      for (int rowCnt = 0 ; rowCnt < tblBody.length ; rowCnt ++) {      
        sb.append("<tr"+bodyRowAddition+">") ;
        String aRow[] = tblBody[rowCnt] ;
        for (int rowCntCol = 0 ; rowCntCol < colSeq.length ; rowCntCol ++) {
          String widthStr="" ;
          if (colWidth!=null && colWidth.length > rowCntCol && colWidth[rowCntCol] > -1)
            widthStr += " width=\"" + colWidth[rowCntCol] + "\"" ;
          sb.append("<td valign=\"top\" align=\"left\""+widthStr+">") ;
          if (aRow[colSeq[rowCntCol]] != null)
            sb.append((colSeq[rowCntCol] < aRow.length) ? ((unEscape[colSeq[rowCntCol]])? aRow[colSeq[rowCntCol]] : GenericStringData.escapeDataInHtml(aRow[colSeq[rowCntCol]])) : "&nbsp;" );
          sb.append("</td>\n") ;
        }
        sb.append("</tr>\n") ;
      }
    }
    else  {
        sb.append( "<tr><td colspan="+tblHead.length+" align=left valign=top>\n No record.</td></tr>\n" );
    }
    sb.append("</table>\n") ;
  
    return sb.toString();
  }


  public static String generatePopupPageHeader (String strDynamicName) 
  {
    return "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
           "<tr>\n" +
           "<td><h1>"+strDynamicName+"</h1></td>\n" +
           "</tr>\n" +
           "</table>\n" ;
  }

  public static String generateTable (GenericTable newTable, String attribute)
  {
    return newTable.toHtml(attribute);
  }

  public static String generateSelectionBox (SelectionBox newSelectionBox)
  {
    return newSelectionBox.toHtml();
  }

  public static String getHtmlUnicodeCurrencySymbol (String currencyCode) {
    if ( currencyCode == null )  return null ;
    else if ( currencyCode.equals("HKD") || currencyCode.equals("USD") ) return "$" ;
    else if ( currencyCode.equals("EUR") ) return "&#8364;" ;
    else if ( currencyCode.equals("GBP") ) return "&#163;" ;
    else if ( currencyCode.equals("YEN") ) return "&#165;" ;
    else if ( currencyCode.equals("RMB") ) return "&#165;" ;
    else return "$" ;
  }

  public String emptyIfNull(String obj)
  {
    if (obj == null) return "" ;
    else return GenericStringData.escapeDataInHtml(obj) ;
  }

  /* Generate a table that hightlights table entries that contain an active matrix package */
  public static String generateTableWithHighlight (GenericTable gt, int colSeq[], String bodyRowAddition, boolean unEscape[], int colWidth[]) {
    String [][]tblBody = gt.getTableBody() ;
    String sSumWidth="" ;
    int sumWidth=0 ;
    if (colWidth != null) {
      for (int cntWid = 0 ; cntWid < colWidth.length ; cntWid ++ ) 
         sumWidth += colWidth[cntWid] ;
      sSumWidth = "" + sumWidth ;
    }
    else
      sSumWidth = "100%" ;

    
    StringBuffer sb = new StringBuffer("<table width=\""+sSumWidth+"\" border=\"0\" class=\"border\" cellpadding=2 cellspacing=2>\n" ) ;
      String [] tblHead = gt.getTableColumnHeader() ;
      sb.append("<tr align=\"left\" class=\"columnhead\">") ;
      for (int colCnt = 0 ; colCnt < colSeq.length ; colCnt ++) {
        String widthStr="" ;
        if (colWidth!=null && colWidth.length > colCnt && colWidth[colCnt] > -1)
          widthStr += " width=\"" + colWidth[colCnt] + "\"" ;
        sb.append("<th align=\"left\""+widthStr+">") ;
        if (tblHead[colSeq[colCnt]] != null)
          sb.append((colSeq[colCnt] < tblHead.length) ? tblHead[colSeq[colCnt]] : "&nbsp;<!--Invalid column:"+tblHead[colCnt]+"-->") ;
        sb.append("</th>\n") ;
      }
      sb.append("</tr>") ;
    if (tblBody.length >0) {
      for (int rowCnt = 0 ; rowCnt < tblBody.length ; rowCnt ++) {      
        String aRow[] = tblBody[rowCnt] ;
        
        //check if the matrix package is active or not, if it is then highlight the entire row.
        if (aRow[colSeq.length].equals("N"))
          sb.append("<tr "+bodyRowAddition+">");
        else if (aRow[colSeq.length].equals("Y"))
          sb.append("<tr "+bodyRowAddition+" bgColor=\"#FFFF99\">");
        else
          sb.append(aRow[colSeq.length]);
        
        for (int rowCntCol = 0 ; rowCntCol < colSeq.length ; rowCntCol ++) {
          String widthStr="" ;
          if (colWidth!=null && colWidth.length > rowCntCol && colWidth[rowCntCol] > -1)
            widthStr += " width=\"" + colWidth[rowCntCol] + "\"" ;
          sb.append("<td valign=\"top\" align=\"left\""+widthStr+">") ;
          if (aRow[colSeq[rowCntCol]] != null)
            sb.append((colSeq[rowCntCol] < aRow.length) ? ((unEscape[colSeq[rowCntCol]])? aRow[colSeq[rowCntCol]] : GenericStringData.escapeDataInHtml(aRow[colSeq[rowCntCol]])) : "&nbsp;" );
          sb.append("</td>\n") ;
        }
        sb.append("</tr>\n") ;
      }
    }
    else  {
        sb.append( "<tr><td colspan="+tblHead.length+" align=left valign=top>\n No record.</td></tr>\n" );
    }
    sb.append("</table>\n") ;
  
    return sb.toString();
  }
  
  public static void main(String[] args)
  {
    HTMLGenerator hTMLGenerator = new HTMLGenerator();
/*
    String aValue[] = {"1","2","3","4","5"};
    String aDesc[] = {"One","Two","Three","Four","Five"};
    String ListName = "Testing";
    String selValue[] = {"1","4"};
    String strAdd = " size=3 onchange=\"javascript:alert('hello');\"";
    String testing = generatePolldownList(aValue, aDesc, ListName, selValue, strAdd);
    System.out.println (testing);
  */

    Hashtable ht = new Hashtable () ;

    Vector dh = new Vector (), db = new Vector (), db1 = new Vector (), db2 = new Vector () ;
    dh.addElement((String)"aa3");    dh.addElement((String)"bb3");
    ht.put (new String("2"), (Vector)dh) ;
    
    db1.addElement((String)"aa2");    db1.addElement((String)"bb1");
    ht.put (new String("1"), (Vector)db1) ;

    db2.addElement((String)"aa1");    db2.addElement((String)"bb2");
    ht.put (new String("3"), (Vector)db2) ;

    GenericSortableTable tmp = new GenericSortableTable(ht, true);
    String s[] = {"col1", "col2"} ;
    tmp.setColumnHead(s);
    int ii[] = {1 } ;

    String htmlstr = hTMLGenerator.generateTable(tmp, ii, " onMouseOut=\"cbar(this)\" onMouseOver=\"sbar(this)\"", new boolean[2], ii);
    int i = 1 ;
    
    
    

  
  }
}