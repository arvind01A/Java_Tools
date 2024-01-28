package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Append_To_PatternLayout {
	private static Logger logger =LoggerFactory.getLogger(Append_To_PatternLayout.class);
	
	public static void main(String[] args) {
		logger.debug("Start main(-) method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//Load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			logger.debug("JDBC driver class is loaded");
			//establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","MYDB9AM","arvind");
			logger.debug("Connection established with Db s/w");
			//create JDBC Statement obj
			st = con.createStatement();
			logger.debug("JDBC Statement object is created");
			//Send and execute the SELECT SQL Query to DB s/w
			rs = st.executeQuery("SELECT * FROM STUDENT");
			logger.info("SQL Query is executed and the ResultSet object is generated");
			//process the ResultSet obj
			while(rs.next()!=false) {
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getInt(3));
			}
			logger.debug("ResultSet is Processed");
		}
		catch(ClassNotFoundException cnf) {
			logger.error("Problem in Loading jdbc driver class");
			cnf.printStackTrace();
		}
		catch(SQLException se) {
			logger.error("Some DB Problem " + se.getMessage() + "  " + se.getErrorCode());
			se.printStackTrace();
		}
		catch(Exception e) {
			logger.error("Unknown DB Problem " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			//close jdbc objs
			logger.debug("Closing jdbc objs");
			try {
				if(rs!=null)
					rs.close();
				logger.debug("ResultSet object is closed");
			}
			catch(SQLException se) {
				logger.error("Problem in closing ResultSet");
				se.printStackTrace();
			}
			try {
				if(st!=null)
					st.close();
				logger.debug("Statement object is closed");
			}
			catch(SQLException se) {
				logger.error("Problem in closing Statement");
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
				logger.debug("Connection object is closed");
			}
			catch(SQLException se) {
				logger.error("Problem in closing Connection");
				se.printStackTrace();
			}
		}
	}
}
