package com.innroad.inncenter.endpoints;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.DBConnection;

public class DBCon extends TestCore{
	Connection con =  null;
	
	public Connection openConnection() throws FileNotFoundException, IOException{
		 DBenv = "qa";
		 printString(DBenv);
		 Properties prop = new Properties();
		 prop.load(new FileInputStream(new File(picFilePath + File.separator + "DB.properties")));
		 String instanceName= prop.getProperty("DBInstanceName");
	     String userName= prop.getProperty("DBUserName");
	     String password= prop.getProperty("DBPassword");		
	     con=DBConnection.getConnecttion(DBenv, instanceName, userName, password);
	 	 printString("" + con);
	 	 return con;
	}
	
	public void closeConnection() throws SQLException {
	con.close();
	}
	
	public Connection getCon() {
		return con;
	}

	public int getAdvertiserIdFromDb(Connection con, String clientId) throws FileNotFoundException, IOException {
		
		printString(DBenv);
		int iD = 0;
		String query = "select * from homeaway.Advertiser where clientid=" + clientId;		
		try (Statement stmt = con.createStatement()) {
	      ResultSet rs = stmt.executeQuery(query);
	      while (rs.next()) {
	    	  iD = rs.getInt("Id");
	    	  printString("Data : " + iD);
	      }
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return iD;		
	}
	
	public int getLastRecordFromDb(Connection con, int id) throws FileNotFoundException, IOException {
		printString(DBenv);
		int tempId =0;		
		String query2 = "select top 1 * from homeaway.SynchronizationEvent where advertiserid="+ id +" order by id desc";		
		try (Statement stmt = con.createStatement()) {
		      ResultSet rs = stmt.executeQuery(query2);
		      while (rs.next()) {
		    	  tempId = rs.getInt("Id");
		        printString("Data : " + tempId);
		      }
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return tempId;
	}
	
	
}
