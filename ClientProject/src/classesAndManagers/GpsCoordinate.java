package classesAndManagers;

public class GpsCoordinate {
	protected String accountID;
	protected String deviceID;
	protected String time;
	protected String longitude;
	protected String latitude;
	
	private static final String delimiter = "DELIMITER";
	private static final int numParams = 5;
	
	/**
	 * Class constructor
	 * Create a new GpsCoordinate instance, with input of time, longitude and latitude
	 * @param the accountID
	 * @param the deviceID
	 * @param time where the coordinate was taken
	 * @param the longitude of the instance
	 * @param the latitude of the instance
	 */
	public GpsCoordinate(String accountID, String deviceID, String time, String longitude, String latitude) {
		this.accountID = accountID;
		this.deviceID = deviceID;
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Class constructor
	 * Create a new GpsCoordinate instance with the input is one single string where
	 * all the information is concatenated and separated by delimiter
	 * @param the string of the coordinate
	 */
	public GpsCoordinate(String coordinate) {
		String line = coordinate;
		String[] fields = new String[numParams];
		for (int j = 0; j < numParams; j++) {
			int index = line.indexOf(delimiter);
			fields[j] = line.substring(0, index);
			line = line.substring(index + delimiter.length());
		}
		this.accountID = fields[0];
		this.deviceID = fields[1];
		this.time = fields[2];
		this.latitude = fields[3];
		this.longitude = fields[4];
	}
	
	/**
	 * Public setter. Set the accountID of the GpsCoordinate instance
	 * @param accountID where the coordinate was taken
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	
	/**
	 * Public getter. Get the accountID of the GpsCoordinate
	 * @return the accountID where the coordinate was taken
	 */
	public String getAccountID() {
		return accountID;
	}
	
	/**
	 * Public setter. Set the accountID of the GpsCoordinate instance
	 * @param accountID where the coordinate was taken
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
	/**
	 * Public getter. Get the accountID of the GpsCoordinate
	 * @return the accountID where the coordinate was taken
	 */
	public String getDeviceID() {
		return deviceID;
	}
	
	/**
	 * Public setter. Set the time of the GpsCoordinate instance
	 * @param time where the coordinate was taken
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	/**
	 * Public getter. Get the time of the GpsCoordinate
	 * @return the time where the coordinate was taken
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * Public setter. Set the longitude of the GpsCoordinate instance
	 * @param the longitude of the instance
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Public getter. Get the longitude of the GpsCoordinate instance
	 * @return the longtitude of the instance
	 */
	public String getLongitude() {
		return longitude;
	}
	
	/**
	 * Public setter. Set the latitude of the GpsCoordinate instance
	 * @param the latitude of the instance
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Public getter. Get the latitude of the GpsCoordinate instance
	 * @return the latitude of the instance
	 */
	public String getLatitude() {
		return latitude;
	}
	
	/**
	 * Return the string representation of the coordinate
	 * @return String of the coordinate
	 */
	public String toString() {
		return "longitude: " + longitude + "\t" +
				"latitude: " + latitude + "\t" +
				"was taken at: " + time + "\t" +
				"by: " + accountID + "\t" +
				"using: " + deviceID;
	}

}
