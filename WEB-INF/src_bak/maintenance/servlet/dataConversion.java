package maintenance.servlet;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;

import jdbc.MDBConnection;
import jdbc.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Vector;

import common.jsp.databean.GeneralDataInHtml;

public class dataConversion extends HttpServlet 
{
	public void doGet (HttpServletRequest req,
            HttpServletResponse res)
	throws ServletException, IOException
	{
		String contextPath= (String)req.getSession().getAttribute("contextPath");
		
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		//String sql = "select recno, item, amount, max_quantity, type, payment_type from ttypeoffee";
		//String sql = "select recno, name, nationality, passport_no, cash_total, sex, dob, bankcode, lstuser from treceipt";
		//String sql = "SELECT recno, name, nationality, passport_no, cash_total, sex, dob, lstuser, lsttran FROM TRECEIPT WHERE RECNO >= 66198 AND RECNO <= 67982";
		String sql = "SELECT recno, item3, item_desc3,quantity3, amount3, type3 from treceipt WHERE RECNO >= 66198 AND RECNO <= 67982 and item3 is not null";
		//String sql = "select * from tuser";
		String uri = "";
		try
		{
			conn = MDBConnection.getConnection();
			ps = conn.prepareStatement(sql);
			System.out.println("sql="+sql);
			rs = ps.executeQuery();
			Vector v = new Vector();
			while(rs.next())
			{
				String[] data = new String[6];

				int i=0;
				/*data[i++] = rs.getString("recno");
				data[i++] = rs.getString("name");
				data[i++] = rs.getString("nationality");
				data[i++] = rs.getString("passport_no");
				data[i++] = rs.getString("cash_total");
				data[i++] = rs.getString("sex");
				data[i++] = rs.getString("dob");
				data[i++] = rs.getString("lstuser");
				data[i++] = rs.getString("LSTTRAN");*/
				
				data[i++] = rs.getString("recno");
				data[i++] = rs.getString("item3");
				data[i++] = rs.getString("item_desc3");
				data[i++] = rs.getString("quantity3");
				data[i++] = rs.getString("amount3");
				data[i++] = rs.getString("type3");

				v.add((String[])data);
			}
			
System.out.println("v.size="+v.size());	
			conn2 = JdbcConnection.getConnection();
			for(int k=0; k<v.size();k++)
			{	
				String[] data = (String[])v.get(k);
				int j=0;
				//ps2 = conn2.prepareStatement("INSERT INTO ITEM (ITEMNO, ITEMDESCRIPTION, AMOUNT, MAXQTY, ITEMTYPE, PAYMENTTYPE, CREATEDON, MODIFIEDON) "+
				//						" VALUES ('"+data[j++]+"', '"+data[j++]+"', "+data[j++]+", "+data[j++]+", '"+data[j++]+"', '"+data[j++]+"', sysdate(), sysdate())");
				//String insertSQL = "INSERT INTO RECEIPT_TEMP (RECEIPTNO, NAME, NATIONALITY, REFNO,AMOUNT, gender, DOB, OFFICERID, createdon) VALUES (?,?,?,?,?,?,?,?,?) ";
				String insertSQL = "INSERT INTO RECEIPT_ITEM_TEMP (lineno, receiptno, itemno, itemdescription, qty, total, unitprice, itemtype, paymenttype) values (3,?,?,?,?,?,0,?,'Cash')";	
				//String insertSQL = "INSERT INTO USER (USERID, USERNAME, CREATEDON, STATUS) VALUES (?,?,sysdate(),'READY')";	
				//String updateSQL = "UPDATE RECEIPT SET CREATEDON = ? WHERE RECEIPTNO = ? ";
				System.out.println("updateSQL = "+insertSQL);					
				ps2 = conn2.prepareStatement(insertSQL);
				ps2.setString(1, data[0]);
				ps2.setString(2, data[1]);
				ps2.setString(3, data[2]);
				ps2.setString(4, data[3]);
				ps2.setString(5, data[4]);
				ps2.setString(6, data[5]);
				//ps2.setString(7, data[6]);
				//ps2.setString(8, data[7]);
				//ps2.setString(9, data[8]);
			
				
				ps2.executeUpdate();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		res.sendRedirect(res.encodeRedirectURL(contextPath + "/shortcutServlet"));
	  
	}
	
	public void doPost (HttpServletRequest req,
            HttpServletResponse res)
	throws ServletException, IOException
	{
		doGet(req, res);
	}
}
