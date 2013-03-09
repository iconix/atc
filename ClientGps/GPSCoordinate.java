public class GpsCoordinate {
	protected String userID;
	protected String time;
	protected String longitude;
	protected String latitude;

	private static final String delimiter = "DELIMITER";
	private static final int numParams = 4;

	/**
	 * Class constructor
	 * Create a new GpsCoordinate instance, with input of time, longitude and latitude
	 * @param the userID
	 * @param time where the coordinate was taken
	 * @param the longitude of the instance
	 * @param the latitude of the instance
	 */
	public GpsCoordinate(String userID, String time, String longitude, String latitude) {
		this.userID = userID;
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
		this.userID = fields[0];
		this.time = fields[1];
		this.latitude = fields[2];
		this.longitude = fields[3];
	}

	/**
	 * Public setter. Set the userID of the GpsCoordinate instance
	 * @param userID where the coordinate was taken
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * Public getter. Get the userID of the GpsCoordinate
	 * @return the userID where the coordinate was taken
	 */
	public String getUserID() {
		return userID;
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
				"by: " + userID;
	}

}
