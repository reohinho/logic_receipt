package transaction.databean;

import common.jsp.databean.GenericWebFormData;
import java.util.*;

import java.sql.ResultSet;

public class TransactionLineData extends GenericWebFormData
{
	public static int maxFields = 5;
	public static final int lineNo = 1;
	public static final int itemNo = 2;
	public static final int unitPrice = 3;
	public static final int qty = 4;
	public static final int lineAmount = 5;
	
	
	public TransactionLineData()
	{
		super(maxFields);
	}
	
	public TransactionLineData(ResultSet rs)
	{
		super(maxFields);
		try
		{
			this.setData(TransactionLineData.lineNo, rs.getString("lineno"));
			this.setData(TransactionLineData.itemNo, rs.getString("itemNo"));
			this.setData(TransactionLineData.unitPrice, rs.getString("unitPrice"));
			this.setData(TransactionLineData.qty, rs.getString("qty"));
			this.setData(TransactionLineData.lineAmount, rs.getString("total"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	protected void initLabels()
	{
		setLabel(this.lineNo, "lineNo","Line No.","");
		setLabel(this.itemNo, "itemNo","Item No.","REQUIRED");
		setLabel(this.unitPrice,"unitPrice","Item Price","NUMBER");
		setLabel(this.qty,"qty","Quantity","NUMBER");
		setLabel(this.lineAmount,"lineAmount","Line Total","");
	}
}