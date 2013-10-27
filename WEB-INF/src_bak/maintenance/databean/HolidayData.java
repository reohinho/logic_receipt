package maintenance.databean;

import common.jsp.databean.GenericWebFormData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.JdbcConnection;


public class HolidayData extends GenericWebFormData
{
	public static int maxFields = 2;
	public static final int holidayDate = 1;
	public static final int holidayDescription = 2;
	
	public HolidayData()
	{
		super(maxFields);
		this.setShortcut("HM");
	}
	
	public HolidayData(ResultSet rs)
	{
		super(maxFields);
		this.setShortcut("HM");
	
		try
		{
			this.setData(HolidayData.holidayDate, rs.getString("DATE"));
			this.setData(HolidayData.holidayDescription, rs.getString("DESCRIPTION"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	protected void initLabels()
	{
		setLabel(this.holidayDate, "holidayDate","Date(DD/MM/YYYY)","REQUIRED");
		setLabel(this.holidayDescription, "holidayDescription","Holiday Description","");
	}
	
	public boolean validate()
	{ 
	    boolean result = super.validate();
		
	    return result ;
	}

	public boolean validateDB() {
		boolean result = true;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = JdbcConnection.getConnection();
			ps = conn.prepareStatement("select * from holiday where date = ?");
			ps.setTimestamp(1, this.getTimestampByField(HolidayData.holidayDate));
			rs = ps.executeQuery();
			if(rs.next())
			{
				result = false;
				this.setErrFlag(this.holidayDate);
				this.addError(this.holidayDate, "10001", "Duplicate entry");
			}
		}
		catch(Exception e)
	    	{
				result = false;
				this.setErrFlag(this.holidayDate);
				this.addError(this.holidayDate, "10099", "Unknown Exception");
	    		e.printStackTrace();
	    	}
	    	finally{
	    		if(rs!=null)try{rs.close();}catch(Exception e){e.printStackTrace();}
	    		if(ps!=null)try{ps.close();}catch(Exception e){e.printStackTrace();}
	    		if(conn!=null)try{conn.close();}catch(Exception e){e.printStackTrace();}
	    	}
		return result;
	}

}