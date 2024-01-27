package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class SelectTest {
	
	//enabling the Logging on the current class
	private static Logger logger = Logger.getLogger(SelectTest.class);
	
	static {
			try {
				//Layout object
				SimpleLayout layout = new SimpleLayout();
				//Appender object
				ConsoleAppender appender = new ConsoleAppender(layout);
				//add appender object to Logger object
				logger.addAppender(appender);
				
				logger.setLevel(Level.ALL);
				logger.info("Log4j setup ready");
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
					//Load jdbc driver class
					Class.forName("oracle.jdbc.driver.OracleDriver");
					logger.debug("JDBC driver class is loaded");
					//establish the connection
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","MYDB9AM", "arvind");
					logger.info("Connection is establish with DB s/w");
					//create jdbc statement object
					if(con != null) {
							st = con.createStatement();
							logger.debug("JDBC statement object is created");
					}
					//send and execute SQL SELECT Query in DB s/w and get JDBC ResultSet object
					if(st != null) {
							rs = st.executeQuery("SELECT * FROM EMP");
							logger.debug("SQL query is send to Db s/w for execution and ResultSet obj is generated");
					}
					//process the ResultSet object
					if(rs != null) {
							while(rs.next()) {
									System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getInt(4) + "  " + rs.getDate(5) + "  " + rs.getInt(6) + "  " + rs.getInt(7) + "  " + rs.getInt(8));
									logger.warn("com.jdbc.SelectTest:: ResultSet obj is processed");
							}
					}
			}
			catch(ClassNotFoundException cnf) {
				cnf.printStackTrace();
				logger.error("Problem in Loading jdc driver clas");
			}
			catch(SQLException se) {
				se.printStackTrace();
				logger.error("Some DB Problem :: " + se.getMessage() + "  SQL error code " + se.getErrorCode() );
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
						rs.close();
					logger.debug("Connection object is closed");
				}
				catch(SQLException se) {
					se.printStackTrace();
					logger.error("Problem is closing Connection object " + se.getMessage());
				}
				
			}
			logger.debug("End of main(-) method");
	}

}
