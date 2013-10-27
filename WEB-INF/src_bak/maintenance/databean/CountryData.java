package maintenance.databean;

import common.jsp.databean.GenericWebFormData;
import java.sql.ResultSet;

public class CountryData extends GenericWebFormData
{
	public static int maxFields = 4;
	public static final int countryCode = 1;
	public static final int countryDescription = 2;
	public static final int shortCut = 3;
	public static final int commonError = maxFields;
	
	public CountryData()
	{
		super(maxFields);
		this.setShortcut("CM");
	}
	
	public CountryData(ResultSet rs)
	{
		super(maxFields);
		this.setShortcut("CM");
	
		try
		{
			this.setData(CountryData.countryCode, rs.getString("CODE"));
			this.setData(CountryData.countryDescription, rs.getString("DESCRIPTION"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void initLabels()
	{
		setLabel(this.countryCode, "countryCode","Country Code","");
		setLabel(this.countryDescription, "countryDescription","Country","");
		setLabel(this.commonError, "commonError","Common Error","");
	}
}