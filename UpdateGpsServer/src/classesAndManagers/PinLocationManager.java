/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classesAndManagers;

import java.sql.*;
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
    * Edit a pin location from the DB table
    * @param Connection to the DB
    * @param old PinLocation object
    * @param new PinLocation object
    */
    public void editPin(Connection connection, PinLocation oldPinLocation, PinLocation newPinLocation) {
         removePin(connection, oldPinLocation);
         addNewPin(connection, newPinLocation);
    }
    

    /**
     * Write the coordinate in the GpsCoordinate in the string format separate by the given delimiter
     * @param instance of GpsCoordinate
     * @return string format of the coordinate
     */
    public String getPinLocationInStringFormat(PinLocation pinLocation) {
        return pinLocation.accountID + SpecialCharacters.delimiter + pinLocation.time + SpecialCharacters.delimiter +
                    pinLocation.longitude + SpecialCharacters.delimiter + pinLocation.latitude + SpecialCharacters.delimiter + 
                    pinLocation.title + SpecialCharacters.delimiter + pinLocation.description + SpecialCharacters.endLn;
    }
}
