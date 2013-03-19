/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import staticVariables.TableName;

/**
 *
 * @author minhthaonguyen
 */
public class DBResetGpsCoordinateTable {
    
    public DBResetGpsCoordinateTable() {
        //do nothing
    }
    
    /**
     * main function calling the table reset
     * @param arg0 
     */
    public static void main(String[] arg0) {
        ResetGpsCoordinateTable();
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
    public static void ResetGpsCoordinateTable() {
        try {
            Connection connection = DBConnection.getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("drop table if exists " + TableName.coordinateDB);
            String query = "CREATE TABLE " + TableName.coordinateDB + 
				"(accountID CHAR(16), deviceID CHAR(64), time BIGINT, longitude DOUBLE, latitude DOUBLE)";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
