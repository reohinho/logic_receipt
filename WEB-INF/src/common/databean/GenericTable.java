/*
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/databean/GenericTable.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:14 $
 * $Revision: 1.1.1.1 $
 */
package common.databean;

import java.sql.Timestamp;

import java.util.Vector;


/** Table data's object representation */

public class GenericTable
{
  protected String tableTitle = "" ;
  protected String[] columnHeader ;
  protected String[][] rowAttribute ; 


  /** Default constructor does nothing*/
  public GenericTable() {}
  
  /** Constructor for table creation
   * @param title (String) Table title
   * @param colHead (Vector) Column headers
   * @param rowAttr (Vector) Rows
   */
  public GenericTable(String title, Vector colHead, Vector rowAttr) 
  {
    setTableTitle(title);
    setTableData (colHead, rowAttr) ;
  }

  /** Constructor for table creation
   * @param title (String) Table title
   * @param colHead (String[]) Column headers
   * @param rowAttr (Vector) Rows
   */
  public GenericTable(String title, String[] colHead, Vector rowAttr) 
  {
    setTableTitle(title);
    setTableData (colHead, rowAttr) ;
    
  }

  /** Constructor for table creation
   * @param title (String) Table title
   * @param colHead (String[]) Column headers
   * @param rowAttr (String[][]) Rows
   * 
   */
  public GenericTable(String title, String[] colHead, String[][] rowAttr)
  {
    setTableTitle(title);
    columnHeader = colHead;
    rowAttribute = rowAttr;
  }

  /** String representation of this object */
  public String toString() 
  {
    String str = "" ;
    str += "Table title: " + (tableTitle.equals("")?"none":tableTitle) + "\n" ;
    str += "Table: " ;
    if (columnHeader == null || rowAttribute == null) str += "none \n" ;
    else
    {
      str += "\n" ;
      for (int j = 0 ; j < columnHeader.length ; j++)
        str += "  [" + columnHeader[j] + "]" ;
      str += "\n" ;
      for (int k1 = 0 ; k1 < rowAttribute.length ; k1++) {
        for (int k2 = 0 ; k2 < columnHeader.length ; k2++) 
          str += "  <" + rowAttribute[k1][k2] + ">" ;
        str += "\n" ;
      }
    }
    return str ;
  }

  // ***** Section for retrieving this object information *****

  /**
   * Obtain the title of this table
   */
  public String getTableTitle () { return tableTitle ;}


  /**
   * Obtain column headers of this table
   */
  public String[] getTableColumnHeader () { return columnHeader ;}

  /**
   * Obtain body data of this table
   */
  public String[][] getTableBody () { return rowAttribute ;}

  /**
   * Obtain the column data of the attribute table
   */
   public String[] getTableBodyColumn(int colIndex) 
   {
      if (rowAttribute != null && rowAttribute[0].length > colIndex) {
        int numRow = rowAttribute.length;
        String tmp[] = new String[numRow];
        for (int i=0; i<numRow; i++) 
        {
          tmp[i] = rowAttribute[i][colIndex];
        }
        return tmp;
      } 
      else {
        return null;
      }
   }

   /** get the number of rows in the table
    * @return noOfRow (int) the number of rows in the table
    */  
    public int getNumOfRow () 
    {
        if (rowAttribute != null) 
            return rowAttribute.length;
        else 
            return 0;
    }

   /** get the Display String by the id 
    * @param strId (String) the value of the id
    * @param idxId (int) the index of the id - column index
    * @param idxDisplay (int) the index of the display value, can get any column the user want
    */
  public String getDisplayById (String strId, int idxId, int idxDisplay) 
  {
      String strDisplay = "";
      if (rowAttribute != null && rowAttribute[0].length > idxId && rowAttribute[0].length > idxDisplay) {
        int numRow = rowAttribute.length;
        for (int i=0; i<numRow; i++) 
        {
          if (rowAttribute[i][idxId].equals(strId)) 
          {
            strDisplay = rowAttribute[i][idxDisplay];
            break;
          }
        }
        return strDisplay;
      } else {
        return strDisplay;
      }
  }
  // ***** Section for setting this object *****

  /**
   * Set the title of this table
   * @param s (String) caller supplied table title
   */
  public void setTableTitle (String s) { if (s!=null) tableTitle = s ; }


  /**
   * Set the table column and body (only if column header and # of rows are matched and both not null)
   * @param tblHead (String[]) Column header of this table in string array
   * @param tblBody (Vector) Body of this table in 2-D Vector
   */
  public void setTableData (String[] tblHead, Vector tblBody) 
  {
    if (tblHead==null || tblBody==null) return ;
    int numCol = tblHead.length ;
    int numRow = tblBody.size() ;
    String thisColHead[] = tblHead ;
    String thisRows[][] = new String [numRow][numCol] ;
    boolean hasError = false ;
    for (int i = 0 ; i < numRow ; i ++) {
       Vector vtmprow = (Vector) tblBody.elementAt(i) ;
       if (vtmprow==null || vtmprow.size() != numCol) 
       {
         hasError = true ;
         break ;
       }
//       thisRows[i] = new String [numCol] ;
       for (int j = 0 ; j < numCol ; j++) 
       {
         thisRows[i][j] = new String ((String)vtmprow.elementAt(j)) ;
       }       
    }

    if (!hasError) {
      columnHeader = thisColHead ;
      rowAttribute = thisRows ;
    }
  }

  /**
   * Set the table column and body (only if column header and # of rows are matched and both not null)
   * @param tblHead (Vector) Column header of this table
   * @param tblBody (Vector) Body of this table in 2-D Vector
   */
  public void setTableData (Vector tblHead, Vector tblBody) 
  {
    if (tblHead==null || tblBody==null) return ;
    int numCol = tblHead.size() ;
    int numRow = tblBody.size() ;
    String thisColHead[] = new String [numCol] ;
    String thisRows[][] = new String [numRow][numCol] ;
    boolean hasError = false ;
    for (int iCol = 0 ; iCol < numCol ; iCol++){
       thisColHead[iCol] = new String ((String)tblHead.elementAt(iCol)) ;
    }
    for (int i = 0 ; i < numRow ; i ++) {
       Vector vtmprow = (Vector) tblBody.elementAt(i) ;
       if (vtmprow==null || vtmprow.size() != numCol) 
       {
         hasError = true ;
         break ;
       }
       thisRows[i] = new String [numCol] ;
       for (int j = 0 ; j < numCol ; j++) 
       {
         thisRows[i][j] = new String ((String)vtmprow.elementAt(j)) ;
       }       
    }

    if (!hasError) {
      columnHeader = thisColHead ;
      rowAttribute = thisRows ;
    }
  }

  public void setTableData (String[] tblHead, String[][]tblBody)
  {
    columnHeader = tblHead;
    rowAttribute = tblBody;
  }

  public String toHtml (String attribute) 
  {
    if (columnHeader==null || rowAttribute==null) return "" ;
    int numOfColumns = columnHeader.length;
    int numOfRows = rowAttribute.length;
    String tableCell = "";
    StringBuffer sb = new StringBuffer();
    
   sb.append("<table ") ;
   sb.append(attribute) ;
   sb.append(">\n") ;
    sb.append("<tr class=columnhead>");
    for (int j = 0; j < numOfColumns; j++) 
    {
      sb.append( "<td valign=top>" + columnHeader[j] + "</td>");
    }
    sb.append("</tr>\n");
    for (int i = 0; i < numOfRows; i++) 
    {
       sb.append( "<tr>");
      for (int j = 0; j < numOfColumns; j++) 
      {
        
        if (rowAttribute[i][j] == null) {
          tableCell = "";
        } else 
        {
          tableCell = rowAttribute[i][j];
        }
        sb.append(  "<td valign=top>" + tableCell + "</td>");
      }
      sb.append( "</tr>\n");
    }

    sb.append( "</table>\n") ;
    
    return sb.toString();
  }
  

  public String toHtml_slow (String attribute) 
  {
    if (columnHeader==null || rowAttribute==null) return "" ;
    int numOfColumns = columnHeader.length;
    int numOfRows = rowAttribute.length;
    String tableCell = "";
    String output="<table " + attribute + ">\n";
    output += "<tr class=columnhead>";
    for (int j = 0; j < numOfColumns; j++) 
    {
      output += "<td valign=top>" + columnHeader[j] + "</td>";
    }
    output += "</tr>\n";
    for (int i = 0; i < numOfRows; i++) 
    {
      output += "<tr>";
      for (int j = 0; j < numOfColumns; j++) 
      {
        
        if (rowAttribute[i][j] == null) {
          tableCell = "";
        } else 
        {
          tableCell = rowAttribute[i][j];
        }
        output += "<td valign=top>" + tableCell + "</td>";
      }
      output += "</tr>\n";
    }
    
    return output + "</table>\n";
  }
    
  public static void main (String arg[]) 
  {
    GenericTable tmp = new GenericTable() ;


    tmp.setTableTitle("TESTING");

    Vector dh = new Vector (), db = new Vector (), db1 = new Vector (), db2 = new Vector () ;
    dh.addElement((String)"aa");
    dh.addElement((String)"bb");
    dh.addElement((String)"aa2");
    dh.addElement((String)"bb3");
    
    db1.addElement((String)"aa1");
    db1.addElement((String)"bb1");
    db1.addElement((String)"aa1");
    db1.addElement((String)"bb1");

    db2.addElement((String)"aa2 asdf asdf");
    db2.addElement((String)"bb2");
    db2.addElement((String)"aa1 asdf a");
    db2.addElement((String)"bb asdf  asdf asf1");

    for (int i = 0; i < 2000 ; i++) {
        db.addElement((Vector)db1);
        db.addElement((Vector)db2);
    }

System.out.println(new Timestamp(System.currentTimeMillis())) ;
    tmp.setTableData(dh, db);
    
    String s = tmp.toHtml("") ;
System.out.println(new Timestamp(System.currentTimeMillis())) ;

//    System.out.println(tmp.toString() + "\n") ;
//    System.out.println(s + "\n");



  }

  


}