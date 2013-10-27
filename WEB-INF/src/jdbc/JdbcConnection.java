package jdbc;

import java.sql.*;
import java.io.*;


public class JdbcConnection {

  final static String dbURL = "jdbc:mysql://localhost:3306/logic_receipt";
  final static String dbDriver = "com.mysql.jdbc.Driver";
  final static String userId = "logic";
  final static String password = "logic";

  public JdbcConnection()
  {

  }

  public static Connection getConnection() throws ClassNotFoundException,SQLException
  {

	  Connection dbCon = null;
	  try
	  {
		  Class.forName(dbDriver).newInstance();
		  dbCon = DriverManager.getConnection(dbURL,userId,password);
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  return dbCon;
  }

  public void closeConnection(Connection conn) throws SQLException
  {
      if(conn!=null)
    	  try
      	  { conn.close();	  }
      	  catch(Exception e)
      	  {	e.printStackTrace();	}
  }


}
