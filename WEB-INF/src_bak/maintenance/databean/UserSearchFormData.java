package maintenance.databean;

import java.sql.ResultSet;

import common.jsp.databean.GenericSearchFormData;


public class UserSearchFormData extends GenericSearchFormData
{
	public static int maxFields = 10;
	public static final int userId = 1;
	public static final int userName = 2;
	public static final int transaction = 3;
	public static final int item = 4;
	public static final int bankCode = 5;
	public static final int country = 6;
	public static final int holiday = 7;
	public static final int message = 8;
	public static final int backup = 9;
	public static final int report = maxFields;
	
	public UserSearchFormData()
	{
		super(maxFields);
		this.setShortcut("UM");
	}
	
	public UserSearchFormData(ResultSet rs)
	{
		super(maxFields);
		this.setShortcut("UM");
		try
		{
			for(int i=1;i<=maxFields;i++)
			{
				this.setData(i, rs.getString(i));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void initLabels()
	{
		setLabel(this.userId, "userId","User ID","");
		setLabel(this.userName, "userName","User Name","");
		setLabel(this.transaction,"transaction","Transaction","");
		setLabel(this.item,"item","Item","");
		setLabel(this.country,"country","Country","");
		setLabel(this.bankCode,"bankcode","Bank Code","");
		setLabel(this.holiday,"holiday","Holiday","");
		setLabel(this.message,"message","Message","");
		setLabel(this.backup,"backup","Backup","");
		setLabel(this.report,"report","Report","");
	}
}