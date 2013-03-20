package classesAndManagers;

import staticVariables.*;
public class GpsCoordinate {
	protected String accountID;
	protected String deviceID;
	protected long time;
	protected double longitude;
	protected double latitude;
	
	
	/**
	 * Class constructor
	 * Create a new GpsCoordinate instance, with input of time, longitude and latitude
	 * @param the accountID
	 * @param the deviceID
	 * @param time where the coordinate was taken
	 * @param the longitude of the instance
	 * @param the latitude of the instance
	 */
	public GpsCoordinate(String accountID, String deviceID, long time, double longitude, double latitude) {
		this.accountID = accountID;
		this.deviceID = deviceID;
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
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
		this.time = Long.valueOf(time);
		this.latitude = Double.valueOf(latitude);
		this.longitude = Double.valueOf(longitude);
	}
	
	/**
	 * Class constructor
	 * Create a new GpsCoordinate instance with the input is one single string where
	 * all the information is concatenated and separated by delimiter
	 * @param the string of the coordinate
	 */
	public GpsCoordinate(String coordinate) {
		String[] fields = coordinate.split(SpecialCharacters.delimiter);
		this.accountID = fields[0];
		this.deviceID = fields[1];
		this.time = Long.valueOf(fields[2]);
		this.longitude = Double.valueOf(fields[3]);
		this.latitude = Double.valueOf(fields[4]);
	}
	
    /**
     * Return the parsable string representation of the GpsCoordinate
     * @param instance of GpsCoordinate
     * @return parsable string format of the coordinate
     */
    public String getGpsCoordinateInStringFormat() {
        return accountID + SpecialCharacters.delimiter + deviceID + SpecialCharacters.delimiter +
                    time + SpecialCharacters.delimiter + longitude + SpecialCharacters.delimiter + latitude;
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
	 * Public setter. Set the deviceID of the GpsCoordinate instance
	 * @param deviceID where the coordinate was taken
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
	/**
	 * Public getter. Get the deviceID of the GpsCoordinate
	 * @return the deviceID where the coordinate was taken
	 */
	public String getDeviceID() {
		return deviceID;
	}
	
	/**
	 * Public setter. Set the time of the GpsCoordinate instance
	 * @param time where the coordinate was taken
	 */
	public void setTime(String time) {
		this.time = Long.valueOf(time);
	}
	
	/**
	 * Public setter. Set the time of the GpsCoordinate instance
	 * @param time where the coordinate was taken
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
	/**
	 * Public getter. Get the time of the GpsCoordinate
	 * @return the time where the coordinate was taken
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * Public setter. Set the longitude of the GpsCoordinate instance
	 * @param the longitude of the instance
	 */
	public void setLongitude(String longitude) {
		this.longitude = Double.valueOf(longitude);
	}
	
	/**
	 * Public setter. Set the longitude of the GpsCoordinate instance
	 * @param the longitude of the instance
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Public getter. Get the longitude of the GpsCoordinate instance
	 * @return the longtitude of the instance
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Public setter. Set the latitude of the GpsCoordinate instance
	 * @param the latitude of the instance
	 */
	public void setLatitude(String latitude) {
		this.latitude = Double.valueOf(latitude);
	}
	
	/**
	 * Public setter. Set the latitude of the GpsCoordinate instance
	 * @param the latitude of the instance
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Public getter. Get the latitude of the GpsCoordinate instance
	 * @return the latitude of the instance
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Return the string representation of the coordinate
	 * @return String of the coordinate
	 */
        @Override
	public String toString() {
		return "longitude: " + longitude + "\t" +
				"latitude: " + latitude + "\t" +
				"was taken at: " + time + "\t" +
				"by: " + accountID + "\t" +
				"using: " + deviceID;
	}

}
