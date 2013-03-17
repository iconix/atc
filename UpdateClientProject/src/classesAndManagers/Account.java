package classesAndManagers;

import staticVariables.*;

public class Account {
	protected String accountID;
	protected String deviceID;
	protected String password;
	protected String email;

	/**
	 * Class constructor
	 * Create a new Account object with the following parameters
	 * @param the accountID
	 * @param the deviceID
	 * @param the account's password
	 * @param the account's email
	 */
	public Account(String accountID, String deviceID, String password, String email) {
		this.accountID = accountID;
		this.deviceID = deviceID;
		this.password = password;
		this.email = email;
	}
	
	/**
	 * Class constructor
	 * Create a new Account object with the input is one single string where
	 * all the information is concatenated and separated by delimiter
	 * @param the string of the sensitive account information
	 */
	public Account(String coordinate) {
		String[] fields = coordinate.split(SpecialCharacters.delimiter);
		this.accountID = fields[0];
		this.deviceID = fields[1];
		this.password = fields[2];
		this.email = fields[3];
	}
	
	/**
	 * Public setter. Set the accountID of the Account object
	 * @param the accountID of the account
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	
	/**
	 * Public getter. Get the accountID of the Account object
	 * @return the accountID of the account
	 */
	public String getAccountID() {
		return accountID;
	}
	
	/**
	 * Public setter. Set the deviceID of the Account object
	 * @param the deviceID of the account
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
	/**
	 * Public getter. Get the deviceID of the Account object
	 * @return the deviceID of the account
	 */
	public String getDeviceID() {
		return deviceID;
	}
	
	/**
	 * Public setter. Set the password of the Account object
	 * @param the password of the account
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Public getter. Get the password of the Account object
	 * @return the password of the account
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Public setter. Set the email of the Account object
	 * @param the email of the account
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Public getter. Get the email of the Account object
	 * @return the email of the account
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Return the string representation of the account
	 * @return String of the account
	 */
        @Override
	public String toString() {
		return "accountID: " + accountID + "\t" +
				"deviceID: " + deviceID + "\t" +
				"password: " + password + "\t" +
				"email: " + email + "\n";
	}
	
}
