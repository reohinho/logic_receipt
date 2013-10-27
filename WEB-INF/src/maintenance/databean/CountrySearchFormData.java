package maintenance.databean;

import common.jsp.databean.GenericSearchFormData;


public class CountrySearchFormData extends GenericSearchFormData
{
	public static int maxFields = 3;
	public static final int countryCode = 1;
	public static final int countryDescription = 2;
	public static final int shortCut = 3;
	
	public CountrySearchFormData()
	{
		super(maxFields);
		this.setShortcut("CM");
	}
	
	protected void initLabels()
	{
		setLabel(this.countryCode, "countryCode","Country Code","");
		setLabel(this.countryDescription, "countryDescription","Country","");
		setLabel(this.shortCut,"shortcut","","");
	}
}