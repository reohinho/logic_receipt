package maintenance.servlet;

import common.jsp.databean.GenericWebFormData;
import generic.servlet.AppCreateServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scmp.circ.utility.CircException;
import com.scmp.circ.utility.CircUtilities;

import maintenance.databean.ItemData;
import jdbc.JdbcConnection;


public class ItemCreateServlet extends AppCreateServlet 
{
	public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
	
	protected String getPageIdForAddMain ()  { return "MA_ITEM,2" ; }
    protected String getPageIdForAddConfirm ()  { return "MA_ITEM,3" ; }
    protected String getPageIdForAddFinal () { return "DF_FINISH,0" ; }
    protected String getMenuNo () { return "MA_ITEM"; }

    protected String getCreateSucceedMessage (GenericWebFormData wfd)
    {
        return "Item is saved sucessfully." ;
    }

    protected String getCreateFailureMessage (GenericWebFormData wfd) 
    {
        return "Item save is failed." ;
    }
        
    protected GenericWebFormData createAddForm (HttpServletRequest request)
    {
    	ItemData iData = new ItemData();
    	iData.setShortcut("IM");
    	return iData;
    }
    protected boolean actionAdd (GenericWebFormData wfd )
    {
    	boolean result = false;
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String itemNoSQL = "SELECT COUNT(ITEMNO) FROM ITEM";
    	String sql = "INSERT INTO ITEM (ITEMNO,ITEMDESCRIPTION,AMOUNT,MAXQTY,ITEMTYPE,PAYMENTTYPE,STATUS,CREATEDON) "+
    				" VALUES (LPAD(?,10,'0'),?,?,?,?,?,'READY',sysdate())";
    	try
    	{
    		ItemData iData = (ItemData)wfd;
    		conn = JdbcConnection.getConnection();
    		ps = conn.prepareStatement(itemNoSQL);
    		rs = ps.executeQuery();
    		int itemCount = 1;
    		if(rs.next())
    		{
    			itemCount = rs.getInt(1)+1;
    		}
    		
    		ps = conn.prepareStatement(sql);
    		ps.setInt(1, itemCount);
    		ps.setString(2, iData.getData(ItemData.itemDescription));
    		ps.setString(3, iData.getData(ItemData.amount));
    		ps.setString(4, iData.getData(ItemData.maxQty));
    		ps.setString(5, iData.getData(ItemData.itemType));
    		ps.setString(6, iData.getData(ItemData.paymentType));
    		ps.executeUpdate();
    		
    		result = true;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		wfd.setMessage(this.getCreateFailureMessage(wfd));
    		result = false;
    	}
    	finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
    	return result;
    }
    protected GenericWebFormData getWebFormRequest (GenericWebFormData form, HttpServletRequest request) 
    {
    	ItemData iData = (ItemData)form;
    	iData.setData(ItemData.itemNo, request.getParameter(iData.getFieldname(ItemData.itemNo)));
    	iData.setData(ItemData.itemDescription, request.getParameter(iData.getFieldname(ItemData.itemDescription)));
    	iData.setData(ItemData.amount, request.getParameter(iData.getFieldname(ItemData.amount)));
    	iData.setData(ItemData.maxQty, request.getParameter(iData.getFieldname(ItemData.maxQty)));
    	iData.setData(ItemData.itemType, request.getParameter(iData.getFieldname(ItemData.itemType)));
    	iData.setData(ItemData.paymentType, request.getParameter(iData.getFieldname(ItemData.paymentType)));
    	return iData;
    }
    
    protected Object datamapViewToEntity (Object obj) throws CircException
    {
    	return null;
    }
    
    protected boolean getAcceptHttpGetMethod()
    {
    	return true;
    }

}