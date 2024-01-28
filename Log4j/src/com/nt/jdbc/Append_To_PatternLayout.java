package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import oracle.net.aso.l;

public class Append_To_PatternLayout {
	//enabling the Logging on the current class
	private static Logger logger = Logger.getLogger(Append_To_PatternLayout.class);
	
	static {
		try {
			PropertyConfigurator.configure("src/com/nt/commons/log4j_RollingFileAppender.properties");
			logger.info("Log4j setup is ready");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.fatal("Problem while setting up Log4j");
		}
	}
	
	public static void main(String[] args) {
		logger.debug("Start of main(-) method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			logger.debug("JDBC driver class is loaded");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","MYDB9AM","arvind");
			logger.debug("Connection establish with DB s/w");
			if(con != null) {
				st = con.createStatement();
				logger.debug("JDBC statement object is created");
			}
			if(st != null) {
				rs = st.executeQuery("SELECT * FROM STUDENT");
				logger.debug("SQL query is send to DB s/w for execution and RS obj is generated");
			}
			if(rs != null) {
				while(rs.next()) {
					System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getInt(3));
				}
				logger.debug("ResultSet obj is processed");
			}
		}
		catch(SQLException se) {
			se.printStackTrace();
			logger.error("Some DB Problem:: " + se.getMessage() + " SQL error code " + se.getErrorCode());
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
			logger.error("Problem is Loading jdbc driver class");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.fatal("Unkown DB Problem " + e.getMessage());
		}
		finally {
			logger.debug("Closing JDBC object");
			try {
				if(rs != null)
					rs.close();
				logger.debug("ResultSet object is closed");
			}
			catch(SQLException se) {
				se.printStackTrace();
				logger.error("Problem is closing ResultSet object " + se.getMessage());
			}
			try {
				if(st != null)
					rs.close();
				logger.debug("Statement object is closed");
			}
			catch(SQLException se) {
				se.printStackTrace();
				logger.error("Problem is closing Statement object " + se.getMessage());
			}
			try {
				if(con != null)
					con.close();
				logger.debug("Connection object is closed");
			}
			catch(SQLException se) {
				se.printStackTrace();
				logger.error("Problem is closing Connection object " + se.getMessage());
			}
		}
		logger.debug("end of main(-) method");
	}
}
