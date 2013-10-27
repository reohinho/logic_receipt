/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/complaint/servlet/ComplaintSearchServlet.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */
package maintenance.servlet;

import common.databean.DataFormatter;
import common.jsp.databean.GenericSearchFormData;
import maintenance.databean.ItemData;
import maintenance.databean.ItemSearchFormData;
import generic.servlet.AppSearchServlet;

import jdbc.JdbcConnection;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.http.HttpServletRequest;

public class ItemSearchServlet extends AppSearchServlet 
{
  public boolean getAcceptHttpGetMethod()  { return true ; }
  public String getMenuNo() { return "IM" ; } 
  public String getPageIdForResultPage()  { return ("MA_ITEM,1") ;  }
  public String getPageIdForSearchForm()  { return ("MA_ITEM,0") ; }

  public GenericSearchFormData createSearchForm(HttpServletRequest request)
  {
    return new ItemSearchFormData() ;
  }

  public Vector actionSearch(GenericSearchFormData o)
  {
	ItemSearchFormData iSearchData = (ItemSearchFormData)o;  
	  
    Vector v = new Vector() ;
    Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	String sql = "SELECT * FROM ITEM WHERE STATUS = 'READY' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(ItemSearchFormData.itemNo)))
		sql += "AND ITEMNO = '"+iSearchData.getData(ItemSearchFormData.itemNo)+"' ";
	if(!CircUtilities.isEmptyString(iSearchData.getData(ItemSearchFormData.itemDescription)))
		sql += "AND ITEMDESCRIPTION like '%"+iSearchData.getData(ItemSearchFormData.itemDescription)+"%' ";
    try
	{
 System.out.println("ITEM SEARCH="+sql);
    	conn = JdbcConnection.getConnection();
    	ps = conn.prepareStatement(sql);
    	rs = ps.executeQuery();
    	while(rs.next())
    	{
    		ItemData iData = new ItemData(rs);
     		v.add((ItemData)iData);
    	}
	}
    catch(Exception e)
    { // ignore search error, just return empty vector
    }
    finally
    {
    	if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	}

    return v ;
  }
  
  /**
   * Read all screen form data from HTTP request
   */
  protected GenericSearchFormData getWebFormRequest (GenericSearchFormData form, HttpServletRequest request) 
  { 
	ItemSearchFormData iSearchData = (ItemSearchFormData)form;
	iSearchData.setData(ItemSearchFormData.itemNo, request.getParameter(iSearchData.getFieldname(ItemSearchFormData.itemNo)));
	iSearchData.setData(ItemSearchFormData.itemDescription, request.getParameter(iSearchData.getFieldname(ItemSearchFormData.itemDescription)));
	return iSearchData ;
  }

  protected Object datamapEntityToView (Object obj) throws CircException 
  { 
    return null;
  }
  
}