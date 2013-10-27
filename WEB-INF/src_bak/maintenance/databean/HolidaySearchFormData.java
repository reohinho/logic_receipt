package maintenance.databean;

import common.jsp.databean.GenericSearchFormData;


public class HolidaySearchFormData extends GenericSearchFormData
{
	public static int maxFields = 2;
	public static final int holidayDate = 1;
	public static final int holidayDescription = 2;
	
	public HolidaySearchFormData()
	{
		super(maxFields);
		this.setShortcut("HM");
	}
	
	protected void initLabels()
	{
		setLabel(this.holidayDate, "holidayDate","Date(DD/MM/YY)","");
		setLabel(this.holidayDescription, "holidayDescription","Description","");
	}
}