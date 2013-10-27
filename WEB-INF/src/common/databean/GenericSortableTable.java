package common.databean;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


public class GenericSortableTable 
{
  // ***** data objects definition begin *****
  
  /** 
   *  List of column Heading in ordered Vector list ( for ordering ) 
   */
  protected Vector columnHead = new Vector () ;

  /** 
   *  List of key in ordered Vector list
   */
  protected Vector key = new Vector () ;

  /** 
   *  Hashtable of mapping
   *  key -> (String) id
   *  value -> (Vector) info 
   */
  protected Hashtable recordSet = new Hashtable () ;
  
  /** Title of this table */
  protected String tableTitle = "" ;

  // ***** data objects definition end *****

  // ***** Object functions begin *****
  /** 
   * Obtain the column head names
   */
  public Vector getColumnHead () { return columnHead ; } 

  /** 
   * Set the column head names
   */
  public void setColumnHead (Vector newColumnHead) { columnHead = newColumnHead ; }
  public void setColumnHead (String newColumnHead[]) { 
    Vector v = new Vector () ;
    if (newColumnHead != null && newColumnHead.length > 0)
      for (int i = 0 ; i < newColumnHead.length ; i++)
        v.addElement((String)newColumnHead[i]);
    setColumnHead (v) ;
  }

  /**
   * Obtain the title of this table
   */
  public String getTableTitle () { return tableTitle ;}

  /**
   * Set the title of this table
   * @param s (String) caller supplied table title
   */
  public void setTableTitle (String s) { if (s!=null) tableTitle = s ; }

  /** 
   * Obtain the keys which already set in specific order
   */
  public Vector getKey () { return key ; }

  /** 
   * Set the key Vector
   */
  public void setKey (Vector newKey) { key = newKey ; }

  /** 
   * Obtain the data mapping
   */
  public Hashtable getTableData () { return recordSet ; }

  /** 
   * Obtain the data mapping
   */
  public Vector getRowByKeyId (String key) { return key==null ? (Vector)null : (Vector)recordSet.get ((String)key) ; }


  /** set table info 
   * @param info (Hashtable) Detail, 0 or more String each, with respect to the id by Hashtable
   * @param sortkey (boolean) true if required to sort the key list according to the key name
   * key -> (String) table key
   * value -> (Vector) record set 
   */
  public boolean setTableData (Hashtable info, boolean sortkey) 
  {
    boolean canSet = true ;
    
    if (info==null || info.size()==0) return !canSet ;

    // sort id in acending order by default
    Vector v ;
    if (sortkey)
      v = sortKey(info.keys()) ;
    else 
    {
      v = new Vector () ;
      Enumeration enume = info.keys() ;
      while (enume.hasMoreElements())
        v.addElement(new String((String)enume.nextElement()));
    }

    if (canSet) {
      recordSet = info ;
      setKey(v) ;
    }

    return canSet ;
  }

  /** Sort Enumeration parameter in acending order
   * @param enum (Enumeration) list to be sorted
   * @return (Vector) acending ordered list
   */
  protected Vector sortKey(Enumeration enume) 
  {
    Vector v = new Vector () ;
    while (enume.hasMoreElements()) 
    {
      String id = (String)enume.nextElement() ;
      boolean ins = false ;
      for (int i = 0 ; i < v.size() ; i++) {
        if (id.compareTo((String)v.elementAt(i)) <0) {
          v.insertElementAt (new String(id), i) ;
          ins=true ; 
          break ;
        }
      }
      if (!ins) v.addElement (new String(id)) ;
    }
    return v;
  }

  public String[] getKeyInArray () {
    String s[] = new String [key.size()] ;
    for (int i = 0 ; i < key.size() ; i++) 
       s[i] = (String) key.elementAt(i) ;
    return s ;
  }

  /** Return string array with respect to the data column n (start with 1,2,3...)
   * @param colnum (int) column number starts with 1,2,3...
   * @return (String[]) column array
   */
  public String[] getColumnInArray (int column) {
    String s[] = new String [key.size()] ;
    for (int i = 0 ; i < key.size() ; i++) {
       String k = (String) key.elementAt(i) ;
       s[i]  = (String) ((Vector)recordSet.get((String)k)).elementAt(column-1) ;
    }
    return s ;
  }


  /** Sort the column in decending order with respect to the data column n (start with 1,2,3...)
   * @param colnum (int) column number starts with 1,2,3...
   * @return (boolean) false if not sorted
   */
  public boolean doSortColumnDesc (int colnum) { return doSortColumn (colnum, false) ; } 

  /** Sort the column in acending order with respect to the data column n (start with 1,2,3...)
   * @param colnum (int) column number starts with 1,2,3...
   * @return (boolean) false if not sorted
   */
  public boolean doSortColumn (int colnum) { return doSortColumn (colnum, true) ; } 

  /** Obtain the key list with respect to the data column n (start with 1,2,3...)
   * @param colnum (int) column number starts with 1,2,3...
   * @return (boolean) false if not sorted
   */
  public boolean doSortColumn (int colnum, boolean isAcending) 
  {
    Vector v = new Vector() ;
    Vector refSortList = new Vector () ;
    boolean sorted = true ;

    Enumeration enume = recordSet.keys();
    while (enume.hasMoreElements()) 
    {
      String id = (String)enume.nextElement() ;
      Vector vdetail = (Vector)recordSet.get((String)id) ;
      if (vdetail==null || vdetail.size() < colnum) return false ;
      String cmpelem = (String)vdetail.elementAt(colnum-1) ;
      boolean ins = false ;
      for (int i = 0 ; i < refSortList.size() ; i++) {
        if (isAcending) {
          if (cmpelem.compareTo((String)refSortList.elementAt(i)) <0) {
            refSortList.insertElementAt (new String(cmpelem), i) ;
            v.insertElementAt (new String(id), i) ;
            ins=true ; 
            break ;
          }
        }
        else {
          if (cmpelem.compareTo((String)refSortList.elementAt(i)) > 0) {
            refSortList.insertElementAt (new String(cmpelem), i) ;
            v.insertElementAt (new String(id), i) ;
            ins=true ; 
            break ;
          }
        }
      }
      if (!ins) {
        refSortList.addElement (new String(cmpelem)) ;
        v.addElement (new String(id)) ;
      }
    }
    
    if (sorted) setKey(v) ;
    return sorted ;
  }

  public String toString() 
  {
    String s="" ;
    s += columnHead + "\n" + key + "\n" + recordSet ;
    return s ;
  }

  public GenericSortableTable() {}

  public GenericSortableTable(Hashtable info, boolean sortkey) {
    setTableData(info, sortkey) ;
  }

  public static void main (String arg[]) 
  {
    GenericSortableTable tmp = new GenericSortableTable() ;
    Hashtable ht = new Hashtable () ;

    Vector dh = new Vector (), db = new Vector (), db1 = new Vector (), db2 = new Vector () ;
    dh.addElement((String)"aa3");    dh.addElement((String)"bb3");
    ht.put (new String("2"), (Vector)dh) ;
    
    db1.addElement((String)"aa2");    db1.addElement((String)"bb1");
    ht.put (new String("1"), (Vector)db1) ;

    db2.addElement((String)"aa1");    db2.addElement((String)"bb2");
    ht.put (new String("3"), (Vector)db2) ;

    tmp.setTableData(ht, true);
    
    System.out.println(tmp.toString()) ;

    tmp.doSortColumn(1);
    System.out.println(tmp.toString()) ;

    tmp.doSortColumn(2);
    System.out.println(tmp.toString()) ;

/* Expected output:
[]
[1, 2, 3]
{3=[aa1, bb2], 2=[aa3, bb3], 1=[aa2, bb1]}
[]
[3, 1, 2]
{3=[aa1, bb2], 2=[aa3, bb3], 1=[aa2, bb1]}
[]
[1, 3, 2]
{3=[aa1, bb2], 2=[aa3, bb3], 1=[aa2, bb1]}
 */
 
  }

  
}