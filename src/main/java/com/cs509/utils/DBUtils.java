package com.cs509.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
	// These are to be configured and NEVER stored in the code.
		// once you retrieve this code, you can update
		public final static String rdsMySqlDatabaseUrl = "cardmakerpro.c6jxersfdptv.ca-central-1.rds.amazonaws.com";
		public final static String dbUsername = "root";
		public final static String dbPassword = "P15s&w4rd";
			
		public final static String jdbcTag = "jdbc:mysql://";
		public final static String rdsMySqlDatabasePort = "3306";
		public final static String multiQueries = "?allowMultiQueries=true";
		   
		public final static String dbName = "cs509db";    // default created from MySQL WorkBench

		// pooled across all usages.
		static Connection conn;
	 
		/**
		 * Singleton access to DB connection to share resources effectively across multiple accesses.
		 */
		public static Connection connect() throws Exception {
			if (conn != null) { return conn; }
			
			try {
				conn = DriverManager.getConnection(
						jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + dbName + multiQueries,
						dbUsername,
						dbPassword);
				return conn;
			} catch (Exception ex) {
				throw new Exception("Failed in database connection");
			}
		}
}
