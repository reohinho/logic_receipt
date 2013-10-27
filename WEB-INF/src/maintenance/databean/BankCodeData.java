package maintenance.databean;

import java.sql.ResultSet;

import common.jsp.databean.GenericWebFormData;


public class BankCodeData extends GenericWebFormData
{
	public static int maxFields = 4;
	public static final int bankCode = 1;
	public static final int bankCodeDescription = 2;
	public static final int shortCut = 3;
	public static final int commonError = maxFields;
	
	public BankCodeData()
	{
		super(maxFields);
		this.setShortcut("BM");
	}
	
	public BankCodeData(ResultSet rs)
	{
		super(maxFields);
		this.setShortcut("BM");
		try
		{
			this.setData(BankCodeData.bankCode, rs.getString("code"));
			this.setData(BankCodeData.bankCodeDescription, rs.getString("description"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void initLabels()
	{
		setLabel(this.bankCode, "bankCode","Bank Code","");
		setLabel(this.bankCodeDescription, "bankCodeDescription","Bank Name","REQUIRED");
		setLabel(this.shortCut,"shortcut","","");
	}
}