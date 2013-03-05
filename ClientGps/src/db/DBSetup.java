package db;
import java.sql.*;

public class DBSetup {
	protected static final String coordinateDB = "GPSCoordinate";
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
		createNewGPSCoordinateTable();
	}
	
	/**
	 * Create a new table in the database with the name from coordinateDB.
	 * This database will contains the date/time in which the coordinate was taken, 
	 * the longitude, and the latitude of the coordinate
	 * 		userID: CHAR(16);
	 * 		time: CHAR(16);
	 * 		longitude: CHAR(32);
	 * 		latitude: CHAR(32);
	 */
	private void createNewGPSCoordinateTable() {
		removeGPSCoordinateTableIfExisted();
		String query = "CREATE TABLE " + coordinateDB + 
				"(userID CHAR(16), time CHAR(16), longitude CHAR(32), latitude CHAR(32))";
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
}
