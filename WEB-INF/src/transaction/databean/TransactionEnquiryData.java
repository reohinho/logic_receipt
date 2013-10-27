package transaction.databean;

import common.jsp.databean.GenericSearchFormData;
import java.sql.ResultSet;

public class TransactionEnquiryData extends GenericSearchFormData
{
	public static int maxFields = 8;
	public static final int name = 1;
	public static final int itemDescription = 2;
	public static final int cashAmount = 3;
	public static final int chequeAmount = 4;
	public static final int txNo = 5;
	public static final int txDate = 6;
	public static final int officerId = 7;
	public static final int status = 8;

	
	public TransactionEnquiryData()
	{
		super(maxFields);
		this.setShortcut("TE");
	}
	
	public TransactionEnquiryData(ResultSet rs)
	{
		super(maxFields);
		this.setShortcut("TE");
		try
		{
			for(int i=1;i<=maxFields;i++)
			{
				this.setData(i, rs.getString(i));
			}
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	protected void initLabels()
	{
		setLabel(this.txNo, "txNo","Receipt No.","");
		setLabel(this.name, "name","Name","");
		setLabel(this.officerId,"officerId","Officer ID","");
		setLabel(this.status,"status","Status","");
		setLabel(this.cashAmount,"cashAmount","Cash","");
		setLabel(this.chequeAmount,"chequeAmount","Cheque","");
		setLabel(this.txDate,"txDate","Receipt Date","");
		setLabel(this.status,"status","Status","");
	}
}