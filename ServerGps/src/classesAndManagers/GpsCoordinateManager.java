package classesAndManagers;

import java.sql.*;
import java.util.ArrayList;

import staticVariables.*;

public class GpsCoordinateManager {


    public GpsCoordinateManager() {
        //do nothing
    }

    /**
     * Add a new coordinate to the DB table with the information from the GpsCoordinate instance
     * @param connection to the DB
     * @param coordinate instance of the GpsCoordinate
     */
    public void addNewGpsCoordinate(Connection connection, GpsCoordinate coordinate) {

        String query = "INSERT INTO " + TableName.coordinateDB + 
                            " VALUES(\"" + coordinate.accountID + "\", \"" +
                            coordinate.deviceID + "\", " +
                            coordinate.time + ", " +
                            coordinate.longitude + ", " +
                            coordinate.latitude + ")";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Query for pins with the given PinConfig
     * @param PinConfig instance
     */
     public ArrayList<GpsCoordinate> queryCoordinates(Connection connection, CoordinateConfig coordinateConfig) {
    	 ArrayList<GpsCoordinate> gpsCoordinates = new ArrayList<GpsCoordinate>();
    	 String query = "select * from " + TableName.coordinateDB +
    			 " where accountID = \"" + coordinateConfig.getAccountID() +
    			 "\" and deviceID = \"" + coordinateConfig.getDeviceID() +
    			 "\" and time > " + coordinateConfig.getLowerTime() + 
    			 " and time < " + coordinateConfig.getHigherTime() +
    			 " and longitude > " + coordinateConfig.getLowerLongitude() +
    			 " and longitude < " + coordinateConfig.getHigherLongitude() +
    			 " and latitude > " + coordinateConfig.getLowerLatitude() +
    			 " and longitude < " + coordinateConfig.getHigherLatitude() + 
    			 " order by time";
   
    	 try {
    		 Statement stmt = connection.createStatement();
    		 ResultSet rs = stmt.executeQuery(query);
    		 rs.beforeFirst();
    		 while (rs.next()) {
    			 GpsCoordinate gpsCoordinate = new GpsCoordinate(rs.getString("accountID"), 
    					 rs.getString("deviceID"), rs.getLong("time"),
    					 rs.getDouble("longitude"), rs.getDouble("latitude"));
    			 gpsCoordinates.add(gpsCoordinate);
    		 }
    	 } catch (SQLException e) {
             e.printStackTrace();
         }
    	 return gpsCoordinates;
     }
}