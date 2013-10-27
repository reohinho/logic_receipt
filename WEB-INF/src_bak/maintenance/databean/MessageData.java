package maintenance.databean;

import java.sql.ResultSet;

import common.jsp.databean.GenericWebFormData;


public class MessageData extends GenericWebFormData
{
	public static int maxFields = 2;
	public static final int code = 1;
	public static final int definedMessage = 2;

	
	public MessageData()
	{
		super(maxFields);
		this.setShortcut("MM");
	}
	
	public MessageData(ResultSet rs)
	{
		super(maxFields);
		this.setShortcut("MM");
		try
		{
			this.setData(MessageData.code, rs.getString("code"));
			this.setData(MessageData.definedMessage, rs.getString("definedMessage"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void initLabels()
	{
		setLabel(this.code, "code","Code","");
		setLabel(this.definedMessage, "definedMessage","Message","");

	}
}