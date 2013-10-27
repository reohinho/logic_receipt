package transaction.databean;

import common.jsp.databean.GenericWebFormData;
import java.util.*;

import java.sql.ResultSet;

public class TransactionData extends GenericWebFormData
{
	public static int maxFields = 15;
	public static final int txNo = 1;
	public static final int name = 2;
	public static final int refNo = 3;
	public static final int txType = 4;
	public static final int nationality = 5;
	public static final int paymentAmt = 6;
	public static final int paymentType = 7;
	public static final int paymentRef = 8;
	public static final int officerId = 9;
	public static final int isVoid = 10;
	public static final int voidReason = 11;
	public static final int dob = 12;
	public static final int gender = 13;
	public static final int name2 = 14;
	public static final int printerName = 15;
	
	protected Vector transactionLineVec;
	
	public TransactionData()
	{
		super(maxFields);
		this.setShortcut("TX");
	}
	
	public TransactionData(ResultSet rs)
	{
		super(maxFields);
		this.setShortcut("TX");

		try
		{
			this.setData(TransactionData.txNo, rs.getString("receiptno"));
			this.setData(TransactionData.name, rs.getString("name"));
			this.setData(TransactionData.txType, rs.getString("receipttype"));
			this.setData(TransactionData.refNo, rs.getString("refno"));
			this.setData(TransactionData.officerId, rs.getString("officerid"));
			this.setData(TransactionData.paymentAmt, rs.getString("amount"));
			this.setData(TransactionData.paymentType, rs.getString("paymenttype"));
			this.setData(TransactionData.paymentRef, rs.getString("paymentref"));
			if(rs.getString("voidReason")!=null&&!rs.getString("voidReason").equals(""))
				this.setData(TransactionData.isVoid, "Y");
			this.setData(TransactionData.voidReason, rs.getString("voidReason"));
			this.setData(TransactionData.dob, rs.getString("dob"));
			this.setData(TransactionData.gender, rs.getString("gender"));
			this.setData(TransactionData.name2, rs.getString("name2"));
			this.setData(TransactionData.nationality, rs.getString("nationality"));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setLineVec(Vector vec)
	{
		transactionLineVec = vec;
	}
	
	public Vector getLineVec()
	{
		return transactionLineVec;
	}
	
	protected void initLabels()
	{
		setLabel(this.txNo, "txNo","Receipt No.","");
		setLabel(this.name, "name","Name","REQUIRED");
		setLabel(this.refNo,"refNo","PPT/Ref No.","");
		setLabel(this.txType,"txType","Type","");
		setLabel(this.nationality,"nationality","Nationality","REQUIRED");
		setLabel(this.paymentAmt,"paymentAmt","Amount","");
		setLabel(this.paymentType,"paymentType","Payment Type","");
		setLabel(this.paymentRef,"paymentRef","Payment Ref.","");
		setLabel(this.officerId,"officerId","Officer","");
		setLabel(this.isVoid,"isVoid","isVoid","");
		setLabel(this.voidReason,"voidReason","Reason","REQUIRED",4);
		setLabel(this.dob,"dob","Date of Birth", "REQUIRED");
		setLabel(this.gender,"gender", "Gender", "");
		setLabel(this.name2,"name2","","");
		setLabel(this.printerName,"printerName","","");
	}
}