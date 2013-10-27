package maintenance.databean;

import common.jsp.databean.GenericSearchFormData;


public class BankCodeSearchFormData extends GenericSearchFormData
{
	public static int maxFields = 3;
	public static final int bankCode = 1;
	public static final int bankCodeDescription = 2;
	public static final int shortCut = 3;
	
	public BankCodeSearchFormData()
	{
		super(maxFields);
		this.setShortcut("BM");
	}
	
	protected void initLabels()
	{
		setLabel(this.bankCode, "bankCode","Bank Code","");
		setLabel(this.bankCodeDescription, "bankCodeDescription","Bank Name","");
		setLabel(this.shortCut,"shortcut","","");
	}
}