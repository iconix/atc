/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classesAndManagers;

import java.sql.*;
import java.util.ArrayList;

import staticVariables.*;
/**
 *
 * @author minhthaonguyen
 */
public class PinLocationManager {
    
    
    public PinLocationManager() {
        //do nothing
    }
    
    /**
     * Add a new pin location to the DB table with the information from the PinLocation object
     * @param connection to the DB
     * @param PinLoncation object
     */
    public void addNewPin(Connection connection, PinLocation pinLocation) {

        String query = "INSERT INTO " + TableName.pinLocationDB + 
                            " VALUES(\"" + pinLocation.accountID + "\", " +
                            pinLocation.time + ", " +
                            pinLocation.longitude + ", " +
                            pinLocation.latitude + ", \"" +
                            pinLocation.title + "\", \"" +
                            pinLocation.description+ "\")";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Remove a pin location from the DB table
     * @param Connection to the DB
     * @param PinLocation object
     */
     public void removePin(Connection connection, PinLocation pinLocation) {

        String query = "delete from " + TableName.pinLocationDB + 
                            " where accountID = \"" + pinLocation.accountID + 
                            "\" and time = " + pinLocation.time + 
                            " and longitude = " + pinLocation.longitude +
                            " and latitude = " + pinLocation.latitude +
                            " and title = \"" + pinLocation.title + 
                            "\" and description = \"" + pinLocation.description+ "\"";
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
     public ArrayList<PinLocation> queryPins(Connection connection, PinConfig pinConfig) {
    	 ArrayList<PinLocation> pinLocations = new ArrayList<PinLocation>();
    	 String query = "select * from " + TableName.pinLocationDB +
    			 " where accountID = \"" + pinConfig.getAccountID() +
    			 "\" and time > " + pinConfig.getLowerTime() + 
    			 " and time < " + pinConfig.getHigherTime() +
    			 " and longitude > " + pinConfig.getLowerLongitude() +
    			 " and longitude < " + pinConfig.getHigherLongitude() +
    			 " and latitude > " + pinConfig.getLowerLatitude() +
    			 " and longitude < " + pinConfig.getHigherLatitude();
    	 System.out.println(query);
    	 try {
    		 Statement stmt = connection.createStatement();
    		 ResultSet rs = stmt.executeQuery(query);
    		 rs.beforeFirst();
    		 while (rs.next()) {
    			 PinLocation pinLocation = new PinLocation(rs.getString("accountID"), rs.getLong("time"),
    					 rs.getDouble("longitude"), rs.getDouble("latitude"), 
    					 rs.getString("title"), rs.getString("description"));
    			 pinLocations.add(pinLocation);
    		 }
    	 } catch (SQLException e) {
             e.printStackTrace();
         }
    	 return pinLocations;
     }
     
    /**
    * Edit a pin location from the DB table
    * @param Connection to the DB
    * @param old PinLocation object
    * @param new PinLocation object
    */
    public void editPin(Connection connection, PinLocation oldPinLocation, PinLocation newPinLocation) {
         removePin(connection, oldPinLocation);
         addNewPin(connection, newPinLocation);
    }
}
