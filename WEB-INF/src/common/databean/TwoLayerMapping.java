package common.databean;
import java.util.Hashtable;
import java.util.Vector;

public class TwoLayerMapping 
{

  public final static int PARENT_REF = 1 ;
  public final static int CHILD_REF = 2 ;

  GenericSortableTable parent, child ;
  
  /** Default constructor does nothing
   * (Assumption here is children ids are unique)
   * */
  public TwoLayerMapping() {  
    parent = new GenericSortableTable() ;
    child = new GenericSortableTable() ;
  }

  /** Constructor takes 3 parameteres to complete this object setup <BR>
   * @param parent (Hashtable) parent list <BR>
   *  key -> (String) parent id <BR>
   *  value -> (Vector) parent info <BR>
   * @param child (Hashtable) child list <BR>
   *  key -> (String) child id <BR>
   *  value -> (Vector) child info <BR>
   * @param map (Hashtable) the mapping <BR>
   *  key -> (String) parent id <BR>
   *  value -> (Vector) child ids <BR>
   **/
  public TwoLayerMapping(Hashtable parent, Hashtable child, Hashtable map)
  {
    setGenericIdInfo(PARENT_REF, parent, true) ;
    setGenericIdInfo(CHILD_REF, child, true) ;
    setMapping(map);
  }

  // ***** data objects definition begin *****
  /** 
   *  The storage structure is: <br>
   *  key -> (String) parent id
   *  value -> (Vector) child ids 
   */
  protected Hashtable parentMapChild = new Hashtable () ;
  // ***** data objects definition end *****


  // ***** set functions begin *****
  /** set the mapping from parent id to child ids
   * @param pmapc (Hashtable) mapping defines in hashtable
   * key -> (String) parent id
   * value -> (Vector) child ids
   */
   protected void setMapping (Hashtable pmapc) { if (pmapc!=null) parentMapChild = pmapc ; }

  
  /** set parent / child info 
   * @param info (Hashtable) Detail, 0 or more String each, with respect to the id by Hashtable
   * @param parentOrChild (int) PARENT_REF or CHILD_REF
   * @param sortkey (boolean) true if required to sort the key list according to the key name
   * key -> (String) id
   * value -> (Vector) details
   */
  protected boolean setGenericIdInfo (int parentOrChild, Hashtable info, boolean sortkey) 
  {
    boolean canSet = true ;
    
    if (info==null || info.size()==0) return !canSet ;

    if (parentOrChild==PARENT_REF) { // set parent if type = 1
      parent.setTableData(info, sortkey);
    }
    else { // otherwise set child
      child.setTableData(info, sortkey);
    }

    return canSet ;
  }

  /** Obtain the key list with respect to the data column n (start with 1,2,3...)
   * @param colnum (int) column number starts with 1,2,3...
   * @param parentOrChild (int) PARENT_REF or CHILD_REF
   * @return (boolean) false if not sorted
   */
  protected boolean doSortColumn (int parentOrChild, int colnum) 
  {
    if (parentOrChild==PARENT_REF) { // set parent if type = 1
      return parent.doSortColumn(colnum) ;
    }
    else { // otherwise set child
      return child.doSortColumn(colnum) ;
    }
  }


 
  // ***** set functions end *****


  // ***** get functions begin *****
  public Vector getIds(int parentOrChild) { 
    return parentOrChild==PARENT_REF? parent.getKey() : child.getKey(); 
  }
  public Hashtable getInfo(int parentOrChild) { 
    return parentOrChild==PARENT_REF? parent.getTableData() : child.getTableData(); 
  }
  public Hashtable getParentChildMapping() { return parentMapChild ; }

  public Vector getChildIdsByParentId (String pId) 
  {
    return (Vector)getParentChildMapping().get((String)pId) ;
  }

  public String getChildNthFieldByChildId (String cId, int nth) 
  {
    Vector v = (Vector)getInfo(CHILD_REF).get((String)cId) ;
    if (v==null || v.size() < nth || nth==0) return null ;
    return (String) v.elementAt(nth-1) ;
  }

  
  public boolean sortColumn (int parentOrChild, int column) {
    Vector vSorted = null ;
    if (parentOrChild == PARENT_REF)
      return parent.doSortColumn(column) ;
    else 
      return child.doSortColumn(column) ;
  }


  // ***** get functions end *****


  // string representation
  /** 
   * Overwrite default toString()
   */
  public String toString() 
  {  
    return parent + "\n" + child + "\n" + parentMapChild ;
  }



  public static void main(String[] args)
  {
    TwoLayerMapping twoLayerMapping = new TwoLayerMapping();
    Vector v1 = new Vector () ; v1.addElement("a1aa") ; v1.addElement("b3bb"); v1.addElement("c1cc") ;
    Vector v2 = new Vector () ; v2.addElement("a3aa") ; v2.addElement("b2bb"); v2.addElement("c2cc") ;
    Vector v3 = new Vector () ; v3.addElement("a2aa") ; v3.addElement("b1bb"); v3.addElement("c3cc") ;
    Hashtable ht = new Hashtable () ;
    ht.put((String)"1", (Vector)v1) ;
    ht.put((String)"2", (Vector)v2) ;
    ht.put((String)"3", (Vector)v3) ;
    
    twoLayerMapping.setGenericIdInfo(twoLayerMapping.PARENT_REF, ht, false) ;
    System.out.println(twoLayerMapping.getIds(twoLayerMapping.PARENT_REF)) ;

    twoLayerMapping.sortColumn(twoLayerMapping.PARENT_REF, 1) ;
    System.out.println(twoLayerMapping.getIds(twoLayerMapping.PARENT_REF)) ;
    
  }
}