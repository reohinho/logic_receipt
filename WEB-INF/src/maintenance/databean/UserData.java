package maintenance.databean;

import java.sql.*;
import jdbc.JdbcConnection;

import common.jsp.databean.GenericWebFormData;


public class UserData extends GenericWebFormData
{
	public static int maxFields = 12;
	public static final int userId = 1;
	public static final int userName = 2;
	public static final int transaction = 3;
	public static final int item = 4;
	public static final int bankCode = 5;
	public static final int country = 6;
	public static final int holiday = 7;
	public static final int message = 8;
	public static final int report = 9;
	public static final int password = 10;
	public static final int backup = 11;
	public static final int user = maxFields;
	

	
	public UserData()
	{
		super(maxFields);
		this.setShortcut("UM");
	}
	
	public UserData(ResultSet rs)
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
	
	public boolean validate()
	{ 
	   boolean result = super.validate();
	   //result = validateDB();
	   
	   return result;
	}
	
	private boolean validateDB()
	{
		boolean result = true;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = JdbcConnection.getConnection();
			ps = conn.prepareStatement("SELECT 1 FROM USER WHERE USERID = ?");
			ps.setString(1, getData(this.userId));
			rs = ps.executeQuery();
			if(rs.next())
			{
				result = false;
				this.setErrFlag(this.userId);
				this.addError(this.userId, "10000", "User ID already exists");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
    	}
		
		return result;
	}
	
	protected void initLabels()
	{
		setLabel(this.userId, "userId","User ID","");
		setLabel(this.userName, "userName","Login Name","");
		setLabel(this.transaction,"transaction","Transaction","");
		setLabel(this.item,"item","Item","");
		setLabel(this.country,"country","Country","");
		setLabel(this.bankCode,"bankCode","Bank Code","");
		setLabel(this.holiday,"holiday","Holiday","");
		setLabel(this.message,"message","Message","");
		setLabel(this.report,"report","Report","");
		setLabel(this.password,"password","Password","");
		setLabel(this.backup,"backup","Backup","");
		setLabel(this.user,"user","User","");
	}
}