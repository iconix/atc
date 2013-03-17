package db;
import java.sql.*;

public class DBSetup {
	protected static final String coordinateDB = "GPSCoordinate";
	protected static final String clientAccountDB = "ClientAccounts";
	private DBConnection connection;
	
	/**
	 * Main function, create a new table to store the coordinate. 
	 * Print statement to indicate if the DB is successfully setup
	 */
	public static void main(String[] arg0) {
		new DBSetup();
		System.out.println("Successfully setting up the DBs");
	}
	
	/**
	 * Class contructor. Create a new DB tables to store information
	 */
	public DBSetup() {
		connection = new DBConnection();
		createNewClientAccountsTable();
		createNewGPSCoordinateTable();
	}
	
	/**
	 * Create a new table in the database with the name from clientAccountDB.
	 * This database will contains the accountID, the hashed password, 
	 * the device unique ID, and the email address
	 * 		accountID: CHAR(16);
	 * 		deviceID: CHAR(64);
	 * 		password: CHAR(64);
	 * 		email: CHAR(32);
	 */
	private void createNewClientAccountsTable() {
		removeClientAccountsTableIfExisted();
		String query = "CREATE TABLE " + clientAccountDB + 
				"(accountID CHAR(16), deviceID CHAR(64), password CHAR(64), email CHAR(32))";
		try {
			connection.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove the table with the name from clientAccountDB
	 */
	private void removeClientAccountsTableIfExisted() {
		removeTableIfExisted(clientAccountDB);
	}
	
	/**
	 * Create a new table in the database with the name from coordinateDB.
	 * This database will contains the date/time in which the coordinate was taken, 
	 * the longitude, and the latitude of the coordinate
	 * 		accountID: CHAR(16);
	 * 		deviceID: CHAR(64);
	 * 		time: BIGINT; format should be like this yyyyMMddHHmmss
	 * 		longitude: DOUBLE;
	 * 		latitude: DOUBLE;
	 */
	private void createNewGPSCoordinateTable() {
		removeGPSCoordinateTableIfExisted();
		String query = "CREATE TABLE " + coordinateDB + 
				"(accountID CHAR(16), deviceID CHAR(64), time BIGINT, longitude DOUBLE, latitude DOUBLE)";
		try {
			connection.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove the table with the name from coordinateDB
	 */
	private void removeGPSCoordinateTableIfExisted() {
		removeTableIfExisted(coordinateDB);
	}
	
	/**
	 * Remove a table from the database
	 * @param the name of the table to be remove
	 */
	private void removeTableIfExisted(String tableName) {
		String query = "show tables like \"" + tableName + "\"";
		try {
			ResultSet rs = connection.runQuery(query);
			if (!rs.first()) return;
			String dropTable = "DROP TABLE " + tableName;
			connection.runUpdate(dropTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the name of the coordinateDB table
	 * @return the name of the coordinateDB table
	 */
	public static String getCoordinateDBTable() {
		return coordinateDB;
	}
	
	/**
	 * Get the name of the clientAccountDB table
	 * @return the name of the clientAccountDB table
	 */
	public static String getClientAccountDBTable() {
		return clientAccountDB;
	}
	
}
