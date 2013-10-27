/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/databean/GeneralDataInHtml.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:14 $
  $Revision: 1.1.1.1 $
*/
package common.jsp.databean;


import common.jsp.HTMLGenerator;
import common.databean.GenericSortableTable;
import common.databean.FormatDate;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.JdbcConnection;

import java.util.Calendar;
import java.util.Vector;
import java.util.Hashtable;

public class GeneralDataInHtml 
{
  public GeneralDataInHtml() {}

  
  
    public static boolean isAllowAccess(String userId, String access, String function)
    {
    	boolean result = false;
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String sql = "";
    	try
    	{
    		conn = JdbcConnection.getConnection();
    		sql = "SELECT 1 FROM USER WHERE "+access+" like '%"+function+"%' AND USERID = '"+userId+"'";
 
 System.out.println(sql); 		
    		
    		ps = conn.prepareStatement(sql);
    		rs = ps.executeQuery();
    		if(rs.next())
    		{
    			result = true;
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally {
			  if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
		      if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
			  if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
		  }
    	return result;
    }
  /** get Item details
   * 
   * @param itemNo
   * @return Vector [itemDescrition][unitPrice]
   */
    public static String getUserName(String userId)
    {
      String userName = "";
      Connection conn = null;
  	  PreparedStatement ps = null;
  	  ResultSet rs = null;
  	  String sql = "SELECT USERNAME FROM USER WHERE USERID = ?";
  	  try
  	  {
  		  conn = JdbcConnection.getConnection();
  		  ps = conn.prepareStatement(sql);
  		  ps.setString(1, userId);
  		  rs = ps.executeQuery();
  		  if(rs.next());
  		  {
  			userName = rs.getString(1);
  		  }
  	  }
  	  catch(Exception e)
  	  {
  		  e.printStackTrace();
  	  }
  	  finally {
  		  if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
  	      if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
  		  if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
  	  }
  	  return userName;
    }
  
  
  public static String getCountryName(String userId)
    {
      String userName = "";
      Connection conn = null;
  	  PreparedStatement ps = null;
  	  ResultSet rs = null;
  	  String sql = "SELECT description FROM country WHERE code = ?";
  	  try
  	  {
  		  conn = JdbcConnection.getConnection();
  		  ps = conn.prepareStatement(sql);
  		  ps.setString(1, userId);
  		  rs = ps.executeQuery();
  		  if(rs.next());
  		  {
  			userName = rs.getString(1);
  		  }
  	  }
  	  catch(Exception e)
  	  {
  		  e.printStackTrace();
  	  }
  	  finally {
  		  if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
  	      if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
  		  if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
  	  }
  	  return userName;
    }
  
/** get Item details
 * 
 * @param itemNo
 * @return Vector [itemDescrition][unitPrice]
 */
  public static Vector getItemDescription(String itemNo)
  {
	  Vector result=new Vector();
	  Connection conn = null;
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  String sql = "SELECT ITEMDESCRIPTION, AMOUNT FROM ITEM WHERE ITEMNO = ?";
	  try
	  {
		  conn = JdbcConnection.getConnection();
		  ps = conn.prepareStatement(sql);
		  ps.setString(1, itemNo);
		  rs = ps.executeQuery();
		  if(rs.next());
		  {
			  result.add((String)rs.getString(1));
			  result.add((Double)new Double(rs.getDouble(2)));
		  }
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  finally {
		  if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
	      if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		  if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	  }
	  return result;
  }
  
  public static String getItemTypeDesc(String itemType)
  {	
	  String desc = "";
	  if(itemType!=null)
	  {
		  if(itemType.equals("A"))
		  { desc = "Visa";	}
		  else if(itemType.equals("B"))
		  { desc = "Others";	}
		  else if(itemType.equals("C"))
		  { desc = "Type C";	}
	  }
	  return desc;
  }
  
  public static String getPaymentTypeDesc(String paymentType)
  {
	  String desc = "";
	  if(paymentType!=null)
	  {
		  if(paymentType.equals("cash"))
		  { desc = "Cash";	}
		  else if(paymentType.equals("cheque"))
		  { desc = "Cheque";	}
		  else if(paymentType.equals("creditcard"))
		  { desc = "Credit Card";	}
		  else if(paymentType.equals("bankin"))
		  { desc = "Bank in";	}
	  }
	  return desc;
  }
  
  
  public static boolean isAccessible(String userId, String shortcut)
  {
	  Connection conn = null;
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  boolean isAccessible = false;
	  try
	  {
		  String sql = "";
		  if(shortcut!=null&&shortcut.toUpperCase().equals("TX"))
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(TRANSACTION) LIKE  LCASE('%X%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("TE")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(TRANSACTION) LIKE  LCASE('%E%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("TV")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(TRANSACTION) LIKE  LCASE('%V%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("TR")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(TRANSACTION) LIKE  LCASE('%R%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("IM"))
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(ITEM) LIKE  LCASE('%C%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("IS")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(ITEM) LIKE  LCASE('%S%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("IE")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(ITEM) LIKE  LCASE('%E%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("ID")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(ITEM) LIKE  LCASE('%D%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("BM"))
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(BANKCODE) LIKE  LCASE('%C%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("BS")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(BANKCODE) LIKE  LCASE('%S%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("BE")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(BANKCODE) LIKE  LCASE('%E%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("BD")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(BANKCODE) LIKE  LCASE('%D%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("CM"))
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(COUNTRY) LIKE  LCASE('%C%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("CS")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(COUNTRY) LIKE  LCASE('%S%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("CE")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(COUNTRY) LIKE  LCASE('%E%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("CD")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(COUNTRY) LIKE  LCASE('%D%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("HM"))
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(HOLIDAY) LIKE  LCASE('%C%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("HS")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(HOLIDAY) LIKE  LCASE('%S%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("HE")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(HOLIDAY) LIKE  LCASE('%E%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("HD")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(HOLIDAY) LIKE  LCASE('%D%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("UM"))
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(USER) LIKE  LCASE('%C%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("US")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(USER) LIKE  LCASE('%S%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("UE")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(USER) LIKE  LCASE('%E%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("UD")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(USER) LIKE  LCASE('%D%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("ME"))
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(MESSAGE) LIKE  LCASE('%E%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("R1")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(REPORT) LIKE  LCASE('%1%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("R2")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(REPORT) LIKE  LCASE('%2%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("R3")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(REPORT) LIKE  LCASE('%3%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("R4")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(REPORT) LIKE  LCASE('%4%')";
		  }
		  else if(shortcut!=null&&shortcut.toUpperCase().equals("BK")) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? AND LCASE(BACKUP) = 'y'";
		  }
		  else if(shortcut!=null&&(shortcut.toUpperCase().equals("PE")||shortcut.toUpperCase().equals("MM"))) 
		  {
			  sql = "SELECT 1 FROM USER WHERE USERID = ? ";
		  }
		  
		  
		  conn = JdbcConnection.getConnection();
		  ps = conn.prepareStatement(sql);
		  ps.setString(1, userId);
		  rs = ps.executeQuery();
		  if(rs.next())
		  {
			  isAccessible = true;
		  }
		  
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  finally
		{
			if(rs!=null) try{rs.close();}catch(Exception e){e.printStackTrace();}
			if(ps!=null) try{ps.close();}catch(Exception e){e.printStackTrace();}
			if(conn!=null) try{conn.close();}catch(Exception e){e.printStackTrace();}
		}
	  return isAccessible;
  }

  /** Obtain HTML pulldown on user ID and user name 
   * usage:  GeneralDataInHtml.getPulldownUserList("A", "itemNo", (String)null, "<OPTION VALUE=\"\">---- Please Select ----", "") 
   * */
  public static synchronized String getPulldownUserList(String selectName, String selectedId, String defaultOption, String additionalAction) {
    /** Get item list */
	  GenericSortableTable gt = new GenericSortableTable() ; 
	  Hashtable htData = new Hashtable () ;
	  Connection conn = null;
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try {
	    conn = JdbcConnection.getConnection();
	    
	    ps = conn.prepareStatement("SELECT * FROM USER WHERE STATUS = 'READY'");
	    rs = ps.executeQuery();
	    while(rs.next())
	    {
	        Vector vtmp = new Vector () ;
	        vtmp.addElement((String)rs.getString("userName") );
	        htData.put((String)rs.getString("userId"), (Vector) vtmp);
	    }
	  
	    gt.setTableData(htData, true);
	    gt.doSortColumn(1);
	  }
	  catch (Exception e) {}
	  finally {
		  if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
	      if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		  if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	  }
      return getPulldownList (gt, selectName, selectedId, defaultOption, additionalAction) ;
  }
  
  /** Obtain HTML pulldown on user ID and user name 
   * usage:  GeneralDataInHtml.getPulldownUserList("countryCode", (String)null, "<OPTION VALUE=\"\">---- Please Select ----", "") 
   * */
  public static synchronized String getPulldownCountryList(String selectName, String selectedId, String defaultOption, String additionalAction) {
    /** Get item list */
	  GenericSortableTable gt = new GenericSortableTable() ; 
	  Hashtable htData = new Hashtable () ;
	  Connection conn = null;
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try {
	    conn = JdbcConnection.getConnection();
	    
	    ps = conn.prepareStatement("SELECT * FROM COUNTRY WHERE STATUS = 'READY'");
	    rs = ps.executeQuery();
	    while(rs.next())
	    {
	        Vector vtmp = new Vector () ;
	        vtmp.addElement((String)rs.getString("description") );
	        htData.put((String)rs.getString("code"), (Vector) vtmp);
	    }
	  
	    gt.setTableData(htData, true);
	    gt.doSortColumn(1);
	  }
	  catch (Exception e) {}
	  finally {
		  if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
	      if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		  if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	  }
      return getPulldownList (gt, selectName, selectedId, defaultOption, additionalAction) ;
  }
  

  public static String getDefaultPriceOfItems() {

	String result = "";
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
		conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement("SELECT ITEMNO, AMOUNT FROM ITEM WHERE STATUS = 'READY'");
		rs = ps.executeQuery();

		while(rs.next()) {
			result += "<input type=\"hidden\" name=\""+rs.getString(1)+"\" value = \""+rs.getDouble(2)+"\">";
		}
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
	  	if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}

	return result; 


  }

  public static synchronized double getDefaultPriceOfItem(String itemNo) {

	double defaultPrice = 0;
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
		conn = JdbcConnection.getConnection();
		ps = conn.prepareStatement("SELECT AMOUNT FROM ITEM WHERE ITEMNO = ?");
		ps.setString(1, itemNo);
		rs = ps.executeQuery();

		if(rs.next()) {
			defaultPrice = rs.getDouble(1);
		}
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
	  	if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}

	return defaultPrice ;
  }

  /** Obtain HTML pulldown on item code and name 
   * usage:  GeneralDataInHtml.getPulldownItemList("A", "itemNo", (String)null, "<OPTION VALUE=\"\">---- Please Select ----", "") 
   * */
  public static synchronized String getPulldownItemList(String itemType, String selectName, String selectedId, String defaultOption, String additionalAction) {
    /** Get item list */
	  GenericSortableTable gt = new GenericSortableTable() ; 
	  Hashtable htData = new Hashtable () ;
	  Connection conn = null;
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try {
	    conn = JdbcConnection.getConnection();
	    
	    ps = conn.prepareStatement("SELECT * FROM ITEM WHERE STATUS = 'READY' AND ITEMTYPE = ?");
	    ps.setString(1, itemType);
	    rs = ps.executeQuery();
	    while(rs.next())
	    {
	        Vector vtmp = new Vector () ;
	        vtmp.addElement((String)rs.getString("itemDescription") );
	        htData.put((String)rs.getString("itemNo"), (Vector) vtmp);
	    }
	  
	    gt.setTableData(htData, true);
	    gt.doSortColumn(1);
	  }
	  catch (Exception e) {}
	  finally {
		  if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
	      if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		  if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	  }
      return getPulldownList (gt, selectName, selectedId, defaultOption, additionalAction) ;
  }


  /** Obtain HTML pulldown from generic table with description in the first column */
  public static synchronized String getPulldownList(GenericSortableTable gt, String selectName, String selectedId, String defaultSelection, String additionalAction) {
    if (gt != null) {
      String selCode[] = gt.getKeyInArray();
      String selDesc[] = gt.getColumnInArray(1);
      return HTMLGenerator.generatePulldownList(selCode, selDesc, selectName, selectedId, additionalAction, defaultSelection)   ;
    }
    else 
      return "" ;
  }

  /** Obtain HTML pulldown from generic table with description in the first column */
  public static synchronized String getPulldownListInOneLine(GenericSortableTable gt, String selectName, String selectedId, String defaultSelection, String additionalAction) {
    if (gt != null) {
      String selCode[] = gt.getKeyInArray();
      String selDesc[] = gt.getColumnInArray(1);
      return HTMLGenerator.generatePulldownListInOneLine(selCode, selDesc, selectName, selectedId, additionalAction, defaultSelection)   ;
    }
    else 
      return "" ;
  }

      /** Obtain the Year pulldown 
     * @param int startYear - the number of year before this year, eg, if the pulldown start from last year, the StartYear = -1
     * @param int totalYear - the number of the options
     * @param String selectName
     * @param String selectedId
     * @param String defaultOption
     * @param additionalAction
     **/
    public static synchronized String getPulldownYear(int startYear, int totalYear, String selectName, String selectedId, String defaultOption, String additionalAction) 
    {
        String strPulldown = "";
        String arrYear[] = null;
        if (totalYear > 0) {
            arrYear = new String[totalYear];
            Calendar cal = Calendar.getInstance();
            int curYear = cal.get(Calendar.YEAR);
            for (int i=0; i<totalYear; i++) {
                arrYear[i] = String.valueOf(curYear + (i+startYear));
            }
            strPulldown = HTMLGenerator.generatePulldownList(arrYear, arrYear, selectName, selectedId, additionalAction, defaultOption);
        } 
        return strPulldown;
    }

    public static synchronized String getPullDownMonth(boolean isShortMonth, String selectName, String selectedId, String defaultOption, String additionalAction) 
    {
        String arrMonthNum[] = new String [12];
        for (int i=0; i< 12; i++) 
        {
            if (i > 8) 
                arrMonthNum[i] = String.valueOf(i+1);
            else
                arrMonthNum[i] = "0" + String.valueOf(i+1);
        }
        if (isShortMonth) 
            return HTMLGenerator.generatePulldownList(arrMonthNum, FormatDate.getShortMonthArray(), selectName, selectedId, additionalAction, defaultOption );
        else 
            return HTMLGenerator.generatePulldownList(arrMonthNum, FormatDate.getMonthArray(), selectName, selectedId, additionalAction, defaultOption );
            
    }

public static void main(String atgs[]){
//    System.out.println("customer name => " + GeneralDataInHtml.getCustomerNameByAddressNo("000000000030"));
 
    
}


}