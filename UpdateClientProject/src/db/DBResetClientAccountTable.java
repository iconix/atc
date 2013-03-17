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
public class DBResetClientAccountTable {
    
    public DBResetClientAccountTable() {
        //do nothing
    }
    
    /**
     * main function calling the table reset
     * @param arg0 
     */
    public static void main(String[] arg0) {
        ResetClientAccountTable();
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
    public static void ResetClientAccountTable() {
        try {
            Connection connection = DBConnection.getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("drop table if exists " + TableName.clientAccountDB);
            String query = "CREATE TABLE " + TableName.clientAccountDB + 
				"(accountID CHAR(16), deviceID CHAR(64), password CHAR(64), email CHAR(32))";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
