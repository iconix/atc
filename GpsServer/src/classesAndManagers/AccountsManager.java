package classesAndManagers;

import java.sql.*;
import staticVariables.*;

public class AccountsManager {
    
    public AccountsManager() {
        //do nothing
    }

    /**
     * Add a new account to the DB table with the information from the Account object
     * @param Connection to the DB
     * @param Account object
     */
    public void addNewAccount(Connection connection, Account account) {
        String query = "INSERT INTO " + TableName.clientAccountDB + 
                        " VALUES(\"" + account.accountID + "\", \"" +
                        account.deviceID + "\", \"" +
                        account.password + "\", \"" +
                        account.email + "\")";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the account information stored in the Account object in the string 
     * format separating by the given delimiter
     * @param Account object
     * @return string format of the account
     */
    public String getAccountInStringFormat(Account account) {
            return account.accountID + SpecialCharacters.delimiter + account.deviceID + SpecialCharacters.delimiter +
                            account.password + SpecialCharacters.delimiter + account.email + SpecialCharacters.endLn;
    }

    /**
     * Make a query to the database to check if any client account with the accountID
     * is already existed. If there is, then return true, else return false
     * @param Connection to the DB
     * @param accountID
     * @return whether the account with the accountID existed in the DB
     */
    public boolean isAccountWithGivenUsernameExisted(Connection connection, String accountID) {
        String query = "SELECT * FROM " + TableName.clientAccountDB +
                        " WHERE accountID=\"" + accountID + "\"";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Make a query to the database to check if any client account with the accountID
     * and password is already existed. If there is, then return true, else return false
     * @param Connection to the DB
     * @param accountID
     * @param password
     * @return whether the account with the accountID and password existed in the DB
     */
    public boolean isAccountExisted(Connection connection, String accountID, String password) {
        String query = "SELECT * FROM " + TableName.clientAccountDB +
                        " WHERE accountID=\"" + accountID + "\" AND password=\"" + password + "\"";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Make a query to the database to check if this device has already registered. 
     * If so then return true, else return false
     * @param Connection to the DB
     * @param accountID
     * @param password
     * @param deviceID
     * @return whether the account with the accountID and password existed in the DB
     */
    public boolean isThisDeviceHasAccount(Connection connection, String accountID, String password, String deviceID) {
        String query = "SELECT * FROM " + TableName.clientAccountDB +
                        " WHERE accountID=\"" + accountID + "\" AND password=\"" + password +
                        "\" AND deviceID=\"" + deviceID + "\"";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Make a query to the database to get the email of an account given its username and password
     * @param Connection to the DB
     * @param accountID
     * @param password
     * @return the email associate with the account
     */
    public String getEmailAssociateWithAccount(Connection connection, String accountID, String password) {
        String query = "SELECT * FROM " + TableName.clientAccountDB +
                        " WHERE accountID=\"" + accountID + "\" AND password=\"" + password + "\"";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) return (String) rs.getObject("email");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
