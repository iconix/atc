/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.*;
import staticVariables.*;

/**
 *
 * @author minhthaonguyen
 */
public class DBResetPinLocationTable {
    
    public DBResetPinLocationTable() {
        //do nothing
    }
    
    /**
     * main function calling the table reset
     * @param arg0 
     */
    public static void main(String[] arg0) {
        ResetPinLocationTable();
    }
    
    /**
    * Create a new table in the database with the name from clientAccountDB.
    * This database will contains the accountID, the hashed password, 
    * the device unique ID, and the email address
    * 		accountID: CHAR(16);
    * 		time: BIGINT; format should be like this yyyyMMddHHmmss
    * 		longitude: DOUBLE;
    * 		latitude: DOUBLE;
    * 		title: CHAR(255);
    * 		description: CHAR(255)
    */
    public static void ResetPinLocationTable() {
        try {
            Connection connection = DBConnection.getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("drop table if exists " + TableName.pinLocationDB);
            String query = "CREATE TABLE " + TableName.pinLocationDB + 
				"(accountID CHAR(16), time BIGINT, longitude DOUBLE, latitude DOUBLE, title CHAR(255), description CHAR(255))";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
