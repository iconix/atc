package classesAndManagers;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;
import db.DBSetup;

public class AccountsManager {
	private DBConnection connection;
	private static final String delimiter = "DELIMITER";
	private static final String endLn = System.getProperty("line.separator");
	
	/**
	 * Class contructor. Create a new DB connection
	 */
	public AccountsManager() {
		connection = new DBConnection();
	}
	
	/**
	 * Add a new account to the DB table with the information from the Account object
	 * @param Account object
	 */
	public void addNewAccount(Account account) {
		String query = "INSERT INTO " + DBSetup.getClientAccountDBTable() + 
				" VALUES(\"" + account.accountID + "\", \"" +
				account.deviceID + "\", \"" +
				account.password + "\", \"" +
				account.email + "\")";
		try {
			connection.runUpdate(query);
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
		return account.accountID + delimiter + account.deviceID + delimiter +
				account.password + delimiter + account.email + endLn;
	}
	
	/**
	 * Make a query to the database to check if any client account with the accountID
	 * is already existed. If there is, then return true, else return false
	 * @param accountID
	 * @return whether the account with the accountID existed in the DB
	 */
	public boolean isAccountWithGivenUsernameExisted(String accountID) {
		String query = "SELECT * FROM " + DBSetup.getClientAccountDBTable() +
				" WHERE accountID=\"" + accountID + "\"";
		try {
			ResultSet rs = connection.runQuery(query);
			if (rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Make a query to the database to check if any client account with the accountID
	 * and password is already existed. If there is, then return true, else return false
	 * @param accountID
	 * @param password
	 * @return whether the account with the accountID and password existed in the DB
	 */
	public boolean isAccountExisted(String accountID, String password) {
		String query = "SELECT * FROM " + DBSetup.getClientAccountDBTable() +
				" WHERE accountID=\"" + accountID + "\" AND password=\"" + password + "\"";
		try {
			ResultSet rs = connection.runQuery(query);
			if (rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Make a query to the database to check if this device has already registeredt. 
	 * If so then return true, else return false
	 * @param accountID
	 * @param password
	 * @param deviceID
	 * @return whether the account with the accountID and password existed in the DB
	 */
	public boolean isThisDeviceHasAccount(String accountID, String password, String deviceID) {
		String query = "SELECT * FROM " + DBSetup.getClientAccountDBTable() +
				" WHERE accountID=\"" + accountID + "\" AND password=\"" + password +
				"\" AND deviceID=\"" + deviceID + "\"";
		try {
			ResultSet rs = connection.runQuery(query);
			if (rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Make a query to the database to get the email of an account given its username and password
	 * @param accountID
	 * @param password
	 * @return the email associate with the account
	 */
	public String getEmailAssociateWithAccount(String accountID, String password) {
		String query = "SELECT * FROM " + DBSetup.getClientAccountDBTable() +
				" WHERE accountID=\"" + accountID + "\" AND password=\"" + password + "\"";
		try {
			ResultSet rs = connection.runQuery(query);
			if (rs.next()) return (String) rs.getObject("email");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
}
