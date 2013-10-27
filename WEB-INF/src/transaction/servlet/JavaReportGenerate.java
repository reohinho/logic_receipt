package transaction.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.JdbcConnection;
import common.databean.DataFormatter;
import java.util.*;

import com.scmp.circ.utility.*;

import transaction.databean.ReportEnquiryData;

public class JavaReportGenerate
{

	public JavaReportGenerate()
	{
	}
	
	public ArrayList getDailyTransactionReportData(ReportEnquiryData reData)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT ifnull(T1.NAME, ''), T3.ITEMDESCRIPTION, IF(T1.PAYMENTTYPE='cash', T2.TOTAL, 0),"+
					 "IF(T1.PAYMENTTYPE='cheque',T2.TOTAL,0),T1.RECEIPTNO,DATE_FORMAT(T1.CREATEDON, '%d-%m-%Y %r'),"+
					 "OFFICERID,IF(T1.STATUS='READY','','Y') "+
					 "FROM RECEIPT T1, RECEIPT_ITEM T2, ITEM T3 "+
					 "WHERE T1.RECEIPTNO = T2.RECEIPTNO "+
					 "AND T2.ITEMNO = T3.ITEMNO ";
					 //"AND TRUNC(CREATEDON) = TO_DATE(?, 'DD-MM-YY')";
		
		String where = "";
		if(reData.getData(ReportEnquiryData.periodStart)!=null&&!reData.getData(ReportEnquiryData.periodStart).equals(""))
			where += "AND T1.CREATEDON >= '"+reData.getData(ReportEnquiryData.periodStart)+"' ";
		if(reData.getData(ReportEnquiryData.periodEnd)!=null&&!reData.getData(ReportEnquiryData.periodEnd).equals(""))
			where += "AND T1.CREATEDON <= '"+reData.getData(ReportEnquiryData.periodEnd)+"' ";
		if(reData.getData(ReportEnquiryData.officerId)!=null&&!reData.getData(ReportEnquiryData.officerId).equals(""))
			where += "AND OFFICERID = '"+reData.getData(ReportEnquiryData.officerId)+"' ";
System.out.println("DailyTransactionReport:"+where);	

		String order = "ORDER BY T1.RECEIPTNO "; 
		
		ArrayList list = new ArrayList();
		try
		{
			conn = JdbcConnection.getConnection();
			ps = conn.prepareStatement(sql+where+order);
			//ps.setString(1, date);
			rs = ps.executeQuery();
			while(rs.next())
			{
//System.out.println("receiptno="+rs.getString("receiptno"));
				int recordSize = 8;
				String[] data = new String[recordSize];
				for(int i=0;i<recordSize;i++)
				{
					data[i] = rs.getString(i+1);
					//System.out.println("data["+i+"]="+data[i]);
				}
				list.add((String[])data);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
System.out.println("result set size="+list.size());
		return list;
	}
	
	public ArrayList getDailyTransactionOfficerReportData(ReportEnquiryData reData)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		
		String officerSQL = "SELECT DISTINCT OFFICERID FROM RECEIPT ";
		
		String sql = "SELECT T1.NAME, T3.ITEMDESCRIPTION, IF(T1.PAYMENTTYPE='cash', T2.TOTAL, 0),"+
					 "IF(T1.PAYMENTTYPE='cheque',T2.TOTAL,0),T1.RECEIPTNO,DATE_FORMAT(T1.CREATEDON,'%d-%m-%Y %r'),"+
					 "OFFICERID,IF(T1.STATUS='READY','','Y') "+
					 "FROM RECEIPT T1, RECEIPT_ITEM T2, ITEM T3 "+
					 "WHERE T1.RECEIPTNO = T2.RECEIPTNO "+
					 "AND T2.ITEMNO = T3.ITEMNO ";
					
		String where = "";
		if(reData.getData(ReportEnquiryData.periodStart)!=null&&!reData.getData(ReportEnquiryData.periodStart).equals(""))
			where += "AND T1.CREATEDON >= '"+reData.getData(ReportEnquiryData.periodStart)+"' ";
		if(reData.getData(ReportEnquiryData.periodEnd)!=null&&!reData.getData(ReportEnquiryData.periodEnd).equals(""))
			where += "AND T1.CREATEDON <= '"+reData.getData(ReportEnquiryData.periodEnd)+"' ";
		if(reData.getData(ReportEnquiryData.officerId)!=null&&!reData.getData(ReportEnquiryData.officerId).equals(""))
			where += "AND OFFICERID = '"+reData.getData(ReportEnquiryData.officerId)+"' ";
System.out.println("DailyTransactionOfficerReport:"+where);	

		String order = "ORDER BY OFFICERID "; 
		
		ArrayList list = new ArrayList();
		try
		{
			conn = JdbcConnection.getConnection();
			ps = conn.prepareStatement(sql+where+order);
			//ps.setString(1, date);
			rs = ps.executeQuery();
			while(rs.next())
			{
				int recordSize = 8;
				String[] data = new String[recordSize];
				for(int i=0;i<recordSize;i++)
				{
					data[i] = rs.getString(i+1);
				}
				list.add((String[])data);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
		return list;
	}
	
	public ArrayList getTransactionSummaryReportData(ReportEnquiryData reData)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT T3.ITEMDESCRIPTION, sum(T2.TOTAL), count(1) "+
					 "FROM RECEIPT T1, RECEIPT_ITEM T2, ITEM T3 "+
					 "WHERE T1.RECEIPTNO = T2.RECEIPTNO "+
					 "AND T2.ITEMNO = T3.ITEMNO "+
					 "AND T1.STATUS = 'READY' ";				
		String where = "";
		if(reData.getData(ReportEnquiryData.periodStart)!=null&&!reData.getData(ReportEnquiryData.periodStart).equals(""))
			where += "AND T1.CREATEDON >= '"+reData.getData(ReportEnquiryData.periodStart)+"' ";
		if(reData.getData(ReportEnquiryData.periodEnd)!=null&&!reData.getData(ReportEnquiryData.periodEnd).equals(""))
			where += "AND T1.CREATEDON <= '"+reData.getData(ReportEnquiryData.periodEnd)+"' ";
		if(reData.getData(ReportEnquiryData.officerId)!=null&&!reData.getData(ReportEnquiryData.officerId).equals(""))
			where += "AND OFFICERID = '"+reData.getData(ReportEnquiryData.officerId)+"' ";
System.out.println("receiptType="+reData.getData(reData.receiptType));

		if(reData.getData(ReportEnquiryData.receiptType)!=null&&!reData.getData(ReportEnquiryData.receiptType).equals(""))
			where += "AND RECEIPTTYPE = '"+reData.getData(ReportEnquiryData.receiptType)+"' ";
System.out.println("TransactionSummaryReport:"+where);	

		String group = "GROUP BY T3.ITEMDESCRIPTION ";

		ArrayList list = new ArrayList();
		try
		{
			conn = JdbcConnection.getConnection();
			ps = conn.prepareStatement(sql+where+group);
			//ps.setString(1, date);
			rs = ps.executeQuery();
			while(rs.next())
			{
				int recordSize = 3;
				String[] data = new String[recordSize];
				for(int i=0;i<recordSize;i++)
				{
					data[i] = rs.getString(i+1);
				}
				list.add((String[])data);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
		return list;
	}
	
	public ArrayList getVisaSummaryReportData(ReportEnquiryData reData)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT T1.RECEIPTNO, T1.NAME, ifnull(T1.name2,''), T1.refno, T1.nationality,"+
					 "ifnull(date_format(T1.dob, '%d-%b-%Y'),''), T1.gender, ifnull(date_format(T1.CREATEDON, '%d-%b-%Y %r'),''), T1.officerid,IF(T1.STATUS='READY','','Y') "+
					 "FROM RECEIPT T1, RECEIPT_ITEM T2, ITEM T3 "+
					 "WHERE T1.RECEIPTNO = T2.RECEIPTNO "+
					 "AND T1.RECEIPTTYPE = 'A'"+
					 "AND T1.STATUS = 'READY' "+
					 "AND T2.ITEMNO = T3.ITEMNO ";					
		String where = "";
		if(reData.getData(ReportEnquiryData.periodStart)!=null&&!reData.getData(ReportEnquiryData.periodStart).equals(""))
			where += "AND T1.CREATEDON >= '"+reData.getData(ReportEnquiryData.periodStart)+"' ";
		if(reData.getData(ReportEnquiryData.periodEnd)!=null&&!reData.getData(ReportEnquiryData.periodEnd).equals(""))
			where += "AND T1.CREATEDON <= '"+reData.getData(ReportEnquiryData.periodEnd)+"' ";
		if(reData.getData(ReportEnquiryData.officerId)!=null&&!reData.getData(ReportEnquiryData.officerId).equals(""))
			where += "AND OFFICERID = '"+reData.getData(ReportEnquiryData.officerId)+"' ";
System.out.println("VisaSummaryReport:"+where);	

		//String group = "GROUP BY T3.ITEMDESCRIPTION ";

		ArrayList list = new ArrayList();
		try
		{
			conn = JdbcConnection.getConnection();
			ps = conn.prepareStatement(sql+where);
			//ps.setString(1, date);
			rs = ps.executeQuery();
			//int k = 0;
			while(rs.next())
			{
				//System.out.println(++k);
				int recordSize = 10;
				String[] data = new String[recordSize];
				for(int i=0;i<recordSize;i++)
				{
					data[i] = rs.getString(i+1);
				}
				list.add((String[])data);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
		return list;
	}
	
	public ArrayList getReceiptPrintData(String txNo)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT T1.name, T1.refno, T1.NATIONALITY, T3.itemdescription, "+
					 "T2.qty, FORMAT(T2.unitprice,2), FORMAT(T2.total,2), T1.paymenttype, T1.paymentref, "+
					 "T1.amount, T1.receiptno, T4.definedmessage, T1.receipttype, ifnull(T1.name2,''), "+
					 "T1.gender, ifnull(date_format(T1.dob, '%d-%b-%Y'),''), T1.OFFICERID, ifnull(date_format(T1.CREATEDON, '%d-%b-%Y (%a) %r'),''), date_format(T1.CREATEDON,'%Y%m%d') "+
					 "FROM RECEIPT T1, RECEIPT_ITEM T2, ITEM T3, MESSAGE T4 "+
					 "WHERE T1.RECEIPTNO = T2.RECEIPTNO "+
					 "AND T2.ITEMNO = T3.ITEMNO "+
					 "AND T4.CODE = 'SM' "+
					 "AND T1.RECEIPTNO = ?";
					
System.out.println("Receipt Print:"+sql);	
System.out.println("Receipt Print:"+txNo);	


		ArrayList list = new ArrayList();
		try
		{
			conn = JdbcConnection.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, txNo);
			rs = ps.executeQuery();
			while(rs.next())
			{
				int recordSize = 19;
				String[] data = new String[recordSize];
				for(int i=0;i<recordSize;i++)
				{
					data[i] = rs.getString(i+1);
				}
				list.add((String[])data);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
		return list;
	}
	

}