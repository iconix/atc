package db;

import java.sql.*;
/**
 * ssh -i smartsense.pem ubuntu@ec2-54-241-229-70.us-west-1.compute.amazonaws.com
 * Amazon DB: mysql ebdb -h aa1bdj7bwieyv5s.cxstmaj8fdow.us-west-1.rds.amazonaws.com -P 3306 -u sapenguins -p 
 * Amazon DB password: sapenguins
 * mysql c_cs108_minhthao -h mysql-user.stanford.edu -u ccs108minhthao -p
 * Handing all the accesses to the DB
 */

public class DBConnection {
	
	private static final String MYSQL_USERNAME = "sapenguins";
	private static final String MYSQL_PASSWORD = "sapenguins";
	private static final String MYSQL_DATABASE_SERVER = "aa1bdj7bwieyv5s.cxstmaj8fdow.us-west-1.rds.amazonaws.com:3306";
	private static final String MYSQL_DATABASE_NAME = "ebdb";
	
	
	/*
	private static final String MYSQL_USERNAME = "ccs108minhthao";
	private static final String MYSQL_PASSWORD = "moulohth";
	private static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	private static final String MYSQL_DATABASE_NAME = "c_cs108_minhthao";
	*/
	
	private static Connection con;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + MYSQL_DATABASE_SERVER + "/" + MYSQL_DATABASE_NAME + "?autoReconnect=true";
			con = DriverManager.getConnection(url, MYSQL_USERNAME, MYSQL_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Update the MySQL constants to correct values!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Add the MySQL jar file to your build path!");
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
}
