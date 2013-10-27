package common.jsp.databean;

import common.databean.GenericTable;

import java.util.Vector;


/** 
 * Front-end table object representation, including table data inherit from GenericTable and additional button feature in front-end.
 **/

public class GenericSummayTableData extends GenericTable
{
  protected String[] buttonBesidesTitle ;
  protected String[] buttonLinkBesidesTitle ;


  /** Default constructor does nothing*/
  public GenericSummayTableData() {}
  
  /** Constructor for table creation
   * @param title (String) Table title
   * @param buttonNames (Vector) Name of buttons associate this table
   * @param buttonLinks (Vector) Links associated to the button names
   * @param colHead (Vector) Column headers
   * @param rowAttr (Vector) Rows
   */
  public GenericSummayTableData(String title, Vector buttonNames, Vector buttonLinks, Vector colHead, Vector rowAttr) 
  {
    super.setTableData(colHead, rowAttr) ;
    setTableTitle(title);
    setButtonNameLinks(buttonNames, buttonLinks);
  }


  public static void main (String arg[]) 
  {
    GenericSummayTableData tmp = new GenericSummayTableData() ;

    Vector bn = new Vector (), bl = new Vector () ;
    bn.addElement((String)"aa");
    bn.addElement((String)"bb");
    bl.addElement((String)"aa.html");
    bl.addElement((String)"bb.html");
    tmp.setButtonNameLinks(bn, bl);

    tmp.setTableTitle("TESTING");

    Vector dh = new Vector (), db = new Vector (), db1 = new Vector (), db2 = new Vector () ;
    dh.addElement((String)"aa");
    dh.addElement((String)"bb");
    
    db1.addElement((String)"aa1");
    db1.addElement((String)"bb1");

    db2.addElement((String)"aa2");
    db2.addElement((String)"bb2");

    db.addElement((Vector)db1);
    db.addElement((Vector)db2);

    tmp.setTableData(dh, db);

    
    System.out.println(tmp.toString()) ;

  }

  
  /** String representation of this object */
  public String toString() 
  {
    String str = "" ;
    str += "Table title: " + (tableTitle.equals("")?"none":tableTitle) + "\n" ;
    str += "Button accociated: " ;
    if (buttonBesidesTitle==null || buttonLinkBesidesTitle==null) str += "none \n" ;
    else 
    {
      str += "\n" ;
      for (int i = 0 ; i < buttonBesidesTitle.length ; i++) 
        str += "  " + buttonBesidesTitle[i] + " links " + buttonLinkBesidesTitle[i] + "\n" ;
    }
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
   * Obtain button list associates on this object
   */
  public String[] getButtonNames () { return buttonBesidesTitle ;}

  /**
   * Obtain button links associates on this object
   */
  public String[] getButtonLinks () { return buttonLinkBesidesTitle ;}


  // ***** Section for setting this object *****


  /**
   * Set the link buttons (only if name and link are equal size Vector and not null)
   * @param name (Vector) button names beside table title
   * @param link (Vector) links associates with names
   */
  public void setButtonNameLinks (Vector name, Vector link) 
  {
    if (name==null || link==null || name.size()!=link.size()) return  ;
    buttonBesidesTitle = null ; buttonLinkBesidesTitle = null ;
    int size= name.size() ;
    buttonBesidesTitle = new String[size] ;
    buttonLinkBesidesTitle = new String[size] ;
    for (int i = 0 ; i < size ; i++) 
    {
      String aName = (String)name.elementAt(i) ;
      if (aName==null) aName = "" ;
      String aLink = (String)link.elementAt(i) ;
      if (aLink==null) aLink = "" ;
      buttonBesidesTitle[i] = new String (aName) ;
      buttonLinkBesidesTitle[i] = new String (aLink) ;
    }
       
    
  }
  

}