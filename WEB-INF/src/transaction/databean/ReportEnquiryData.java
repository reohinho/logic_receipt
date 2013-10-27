package transaction.databean;

import common.jsp.databean.GenericSearchFormData;
import java.sql.ResultSet;

public class ReportEnquiryData extends GenericSearchFormData
{
	public static int maxFields = 4;
	public static final int periodStart = 1;
	public static final int periodEnd = 2;
	public static final int officerId = 3;
	public static final int receiptType = maxFields;

	public void setData(int fieldno, String data)
	{
		if(fieldno == this.periodStart || fieldno == this.periodEnd)
		{
			StringBuffer sb = new StringBuffer(data);
			int length = 14; //ddmmyyyyhhmmss
		
			int paddingSize = length - data.length();
			for(int i=0;i<paddingSize;i++) {sb.append("0");}
			super.setData(fieldno, sb.toString());
		}
		else
			super.setData(fieldno, data);
	}
	
	public ReportEnquiryData()
	{
		super(maxFields);
	}
	
	protected void initLabels()
	{
		setLabel(this.periodStart, "periodStart","Period Start","");
		setLabel(this.periodEnd, "periodEnd","Period End","");
		setLabel(this.officerId,"officerId","Officer ID","");
		setLabel(this.receiptType,"receiptType","Receipt Type","");
	}
}