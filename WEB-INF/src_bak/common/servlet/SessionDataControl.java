package common.servlet;

import java.lang.Integer;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Hashtable;

public class SessionDataControl 
{
  /** Keeps the reference object */
  Hashtable ht = new Hashtable () ;

  /** Keeps the last access time of the object */
  Hashtable htLastAccess = new Hashtable () ;

  /** Total reference of the object created */
  int maxNumCreated = 0 ;

  /** Auto expiry time: will used in whenever a reference created */
  long autoExpiryInLong = -1 ;
  
  public SessionDataControl()
  {}

  public String toString() { 
    String s = "" ;
    java.util.Enumeration enumKeys = htLastAccess.keys() ;
    while (enumKeys.hasMoreElements()) 
    {
      Integer iIdx = (Integer)enumKeys.nextElement() ;
      Timestamp lastAcc = new Timestamp ( ((Calendar)htLastAccess.get((Integer)iIdx)).getTime().getTime() ) ;
      s += ("ref:<" + iIdx.intValue() + ">  Last Access:<" + lastAcc.toString() + ">  " +ht.get((Integer)iIdx)) + "\n" ;
    }
    return s ;
  }

  public SessionDataControl(long autoExp) { autoExpiryInLong = autoExp; }

  public int getListSize() { return ht.size() ; }

  public boolean hasElement() { return ht.size() != 0 ; }

  public void setAutoExpiredTimeInLong(long autoExp) { autoExpiryInLong = autoExp; }
  
  public Object getObjectByIndex (int idx) {
    htLastAccess.put (new Integer(idx), (Calendar)Calendar.getInstance()) ;
    return ht.get(new Integer(idx)) ;
  }

  public Object getObjectByIndex (String idx) 
  {
    try {
      int iIdx = Integer.parseInt(idx) ;
      return getObjectByIndex (iIdx) ;
//      htLastAccess.put (new Integer(iIdx), (Calendar)Calendar.getInstance()) ;
//      return ht.get(new Integer(iIdx)) ;
    }
    catch (Exception e) {return null ; }
  }

  public synchronized int newObject (Object obj) {
    if (autoExpiryInLong > 0) {
      houseKeepExpired (autoExpiryInLong) ;
    }
    int iNumObj = (ht.size() == 0)? 0 : maxNumCreated;
    ht.put (new Integer(iNumObj), obj) ;
    htLastAccess.put (new Integer(iNumObj), (Calendar)Calendar.getInstance()) ;
    maxNumCreated ++ ;
    return iNumObj;
  }

  public synchronized void setObjectToExistingIndex (Object obj, String idx) {
    try {
      int iIdx = Integer.parseInt(idx) ;
      setObjectToExistingIndex (obj, iIdx) ;
    }
    catch (Exception e) {}
  }

  public synchronized void setObjectToExistingIndex (Object obj, int iIdx) {
    try {
      int iTotalObj = ht.size() ;
      if (0 <= iIdx && iIdx < maxNumCreated && ht.get(new Integer(iIdx)) != null)  {
        ht.put (new Integer(iIdx), obj) ;
        htLastAccess.put (new Integer(iIdx), (Calendar)Calendar.getInstance()) ;
      }
    }
    catch (Exception e) {}
  }

  public synchronized void removeObjectFromExistingIndex (String idx) {
    try {
      removeObjectFromExistingIndex (Integer.parseInt(idx)) ;
    } catch (Exception e) {}
  }
  
  public synchronized void removeObjectFromExistingIndex (int idx) {
    try {
      Integer iIdx = new Integer(idx) ;
      ht.remove((Integer) iIdx) ;
      htLastAccess.remove((Integer) iIdx) ;
      if (ht.size()==0) maxNumCreated=0 ; // reset index if the entire object has been cleared.
    } catch (Exception e) {}
  }


  public synchronized int houseKeepExpired (long expireTimeInLong) {
    long currentTime = Calendar.getInstance().getTime().getTime() ;
    int numErased = 0 ;
    java.util.Enumeration enumKeys = htLastAccess.keys() ;
    while (enumKeys.hasMoreElements()) 
    {
      Integer iIdx = (Integer)enumKeys.nextElement() ;
      long lastAccessTimeInLong = ((Calendar)htLastAccess.get((Integer)iIdx)).getTime().getTime() ;
      // remove object if expired
      if (currentTime - lastAccessTimeInLong > expireTimeInLong) {
        ht.remove((Integer)iIdx) ;
        htLastAccess.remove((Integer)iIdx) ;
        numErased ++;
      }
    }
    if (ht.size()==0) maxNumCreated=0 ; // reset index if the entire object has been cleared.
    return numErased ;
  }
  

  public static void main (String arg[]) {
    SessionDataControl sdc = new SessionDataControl() ;
    sdc.newObject(new String ("123")) ;
    try { Thread.sleep(100); } catch (Exception e){}
    sdc.newObject(new String ("ABC")) ;
    try { Thread.sleep(200); } catch (Exception e){}
    sdc.newObject(new String ("xyz")) ; 
    sdc.removeObjectFromExistingIndex("4");
    sdc.setObjectToExistingIndex((String)"QQQ", 1);
    sdc.removeObjectFromExistingIndex(0);
    sdc.newObject(new String ("123")) ;
    sdc.removeObjectFromExistingIndex(1);
    sdc.removeObjectFromExistingIndex(2);
    sdc.removeObjectFromExistingIndex(3);
    sdc.newObject(new String ("123")) ;
    System.out.println(sdc.toString()) ;
  }
}