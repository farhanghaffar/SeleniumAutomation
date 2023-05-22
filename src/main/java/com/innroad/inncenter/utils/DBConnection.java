package com.innroad.inncenter.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	public static  Connection con = null;  
	public static ResultSet rs = null;  
	static Statement stmt = null;
	public static Connection getConnecttion(String env,String instanceName,String userName,String password) {
		String connectionUrl=null;
		if(env.equalsIgnoreCase("dev")) {
			connectionUrl = "sql.inncenter.pms.inrd.io:1433;"+"databaseName="+instanceName+";user="+userName+";password="+password+"";
		}else if(env.equalsIgnoreCase("qa")||env.equalsIgnoreCase("qa3")) {
			connectionUrl = "jdbc:sqlserver://sql-inncenter."+env+"innroad.com:1433;"+"databaseName="+instanceName+";user="+userName+";password="+password+"";
		}else if(env.equalsIgnoreCase("Prod")) {
			connectionUrl = "jdbc:sqlserver://sql-inncenter.innroad.com:1433;"+"databaseName="+instanceName+";user="+userName+";password="+password+"";
		}
		
		try{
			// Establish the connection.  
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			con = DriverManager.getConnection(connectionUrl);  
		}catch(Exception e1){
			e1.printStackTrace();
		}
		return con;

	}

}
