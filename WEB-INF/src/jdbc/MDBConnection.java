package jdbc;

import java.sql.*;
import java.io.*;


public class MDBConnection {

  final static String dbURL = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=c:\\logic_receipt\\db.mdb";
  final static String dbDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
  
  public MDBConnection()
  {

  }

  public static Connection getConnection() throws ClassNotFoundException,SQLException
  {

	  Connection dbCon = null;
	  try
	  {
		  Class.forName(dbDriver).newInstance();
		  dbCon = DriverManager.getConnection(dbURL);

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
