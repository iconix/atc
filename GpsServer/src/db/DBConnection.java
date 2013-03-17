package db;

import java.sql.*;
/**
 * mysql c_cs108_minhthao -h mysql-user.stanford.edu -u ccs108minhthao -p
 * Handing all the accesses to the DB
 */

public class DBConnection {
	
	private static final String MYSQL_USERNAME = "ccs108minhthao";
	private static final String MYSQL_PASSWORD = "moulohth";
	private static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	private static final String MYSQL_DATABASE_NAME = "c_cs108_minhthao";
	
	private static Connection con;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + MYSQL_DATABASE_SERVER + "/" + MYSQL_DATABASE_NAME;
			con = DriverManager.getConnection(url, MYSQL_USERNAME, MYSQL_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("CS108 student: Update the MySQL constants to correct values!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("CS108 student: Add the MySQL jar file to your build path!");
		}
	}
	
	public static Connection getConnection() {
		return con;
	}
	
	public static void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * CS108 student: Do not add/remove any methods to this file since this file will be replaced
	 * when we test your code!
	 */
}
