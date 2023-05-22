package com.innroad.inncenter.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.innroad.inncenter.endpoints.DBCon;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.DBConnection;

public class DBTest extends TestCore{
	
	
	@Test
	public void dbCon() throws FileNotFoundException, IOException, SQLException, ParseException {
		/*System.out.println(DBenv);
		
		 Properties prop = new Properties();
		 prop.load(new FileInputStream(new File(picFilePath + File.separator + "DB.properties")));
		 String instanceName= prop.getProperty("DBInstanceName");
	     String userName= prop.getProperty("DBUserName");
	     String password= prop.getProperty("DBPassword");
		
		Connection con=DBConnection.getConnecttion(DBenv, instanceName, userName, password);
		System.out.println(con);*/
		
		/*DBConnection dbConn= new  DBConnection();
		Connection con=dbConn.getConnecttion("qa", "sql-inncenter.qainnroad.com","innSyncDb", "sysinnroaduat", "pre$a@E9");
		int advertisement_id= dbConn.getHomeawayAdvertiser(con,"3111");
		app_logs.info(advertisement_id);
		int reservation_id= dbConn.getHomeawaySynchronizationEvent(con,String.valueOf(advertisement_id));
		app_logs.info(reservation_id);*/
	/*	
		DBCon dbCon= new DBCon();
		Connection con=dbCon.getCon();
		int advertisement_id= dbCon.getAdvertiserIdFromDb(con,"3111");
		app_logs.info("advertise Id--"+advertisement_id);
		int reservation_id= dbCon.getLastRecordFromDb(con,advertisement_id);
		app_logs.info("First Id--"+reservation_id);*/
		
		Vrbo vrbo= new Vrbo();
	String response=vrbo.getConnectionKibana(vrboProperties.getProperty("KibanaUrl"),Integer.parseInt(vrboProperties.getProperty("Port")),
			vrboProperties.getProperty("Host"),"11715");
		
	String getXml=vrbo.getParseJason(response);
	System.out.println(getXml);
	}	
	
}
