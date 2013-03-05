package db;

import java.sql.*;
/**
 * mysql c_cs108_minhthao -h mysql-user.stanford.edu -u ccs108minhthao -p
 * Handing all the accesses to the DB
 */
public class DBConnection {
	static String account = "ccs108minhthao"; 
	static String password = "moulohth"; 
	static String server = "mysql-user.stanford.edu";
	static String database = "c_cs108_minhthao";
	public Statement stmt;
	
	/**
	 * Class constructor that establish a connection with the database
	 * and the statement that can be executed from the database
	 */
	public DBConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection
				( "jdbc:mysql://" + server, account ,password);
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
		} catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the connection statement
	 * @return statement
	 */
	public Statement getStatement(){
		return stmt;
	}	
	
	/**
	 * Run the query and return the result set
	 * @param queryStr
	 * @return resultSet of the query
	 * @throws SQLException
	 */
	public ResultSet runQuery(String queryStr) throws SQLException{
		return stmt.executeQuery(queryStr);	
	}
	
	/**
	 * Run the update query
	 * @param updateStr
	 * @throws SQLException
	 */
	public void runUpdate(String updateStr) throws SQLException{
		stmt.executeUpdate(updateStr);
	}
}