package classesAndManagers;

import db.*;
import java.sql.*;

public class GpsCoordinateManager {
	private DBConnection connection;
	private static final String delimiter = "DELIMITER";
	private static final String endLn = System.getProperty("line.separator");
	
	/**
	 * Class contructor. Create a new DB connection
	 */
	public GpsCoordinateManager() {
		connection = new DBConnection();
	}
	
	/**
	 * Add a new coordinate to the DB table with the information from the GpsCoordinate instance
	 * @param coordinate instance of the GpsCoordinate
	 */
	public void addNewGpsCoordiate(GpsCoordinate coordinate) {
		String query = "INSERT INTO " + DBSetup.getCoordinateDBTable() + 
				" VALUES(\"" + coordinate.userID + "\", \"" +
				coordinate.time + "\", \"" +
				coordinate.longitude + "\", \"" +
				coordinate.latitude + "\")";
		try {
			connection.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the coordinate in the GpsCoordinate in the string format separate by the given delimiter
	 * @param instance of GpsCoordinate
	 * @return string format of the coordinate
	 */
	public String getGpsCoordinateInStringFormat(GpsCoordinate coordinate) {
		return coordinate.userID + delimiter + coordinate.time + delimiter +
				coordinate.longitude + delimiter + coordinate.latitude + delimiter + endLn;
	}
	
	/**
	 * Write the coordinate given in the string format to the GpsCoordinate instance
	 * @param string format of the coordinate
	 * @return instance of GpsCoordinate
	 */
	public GpsCoordinate getGpsCoordinateFromString(String coordinate) {
		return new GpsCoordinate(coordinate);
	}
}
