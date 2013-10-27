package transaction.databean;

import common.jsp.databean.GenericSearchFormData;


public class TransactionSearchFormData extends GenericSearchFormData
{
	public static int maxFields = 6;
	public static final int txNo = 1;
	public static final int name = 2;
	public static final int officerId = 3;
	public static final int status = 4;
	public static final int refNo = 5;
	public static final int txDate = 6;

	
	public TransactionSearchFormData()
	{
		super(maxFields);
		this.setShortcut("IS");
	}
	
	protected void initLabels()
	{
		setLabel(this.txNo, "txNo","Receipt No.","");
		setLabel(this.name, "name","Name","");
		setLabel(this.officerId,"officerId","Officer ID","");
		setLabel(this.status,"status","Status","");
		setLabel(this.refNo,"refNo","Ref. No.","");
		setLabel(this.txDate,"txDate","Transaction Date","");

	}
}