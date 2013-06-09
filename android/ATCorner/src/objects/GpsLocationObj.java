package objects;

public class GpsLocationObj {
	double longitude;
	double latitude;
	long timeStamp;

	/**
	 * Empty constructor of new GpsLocationObj
	 */
	public GpsLocationObj() {

	}

	/**
	 * Constructor of new GpsLocationObj
	 * @param longitude
	 * @param latitude
	 * @param timeStamp
	 */
	public GpsLocationObj(double longitude, double latitude, long timeStamp) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.timeStamp = timeStamp;
	}

	/**
	 * Constructor of new GpsLocationObj
	 * @param longitude
	 * @param latitude
	 * @param timeStamp
	 */
	public GpsLocationObj(double longitude, double latitude, String timeStamp) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.timeStamp = Long.valueOf(timeStamp);
	}

	/**
	 * Constructor of new GpsLocationObj
	 * @param longitude
	 * @param latitude
	 * @param timeStamp
	 */
	public GpsLocationObj(String longitude, String latitude, String timeStamp) {
		this.longitude = Double.valueOf(longitude);
		this.latitude = Double.valueOf(latitude);
		this.timeStamp = Long.valueOf(timeStamp);
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = Double.valueOf(longitude);
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = Double.valueOf(latitude);
	}

	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = Long.valueOf(timeStamp);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GpsLocationObj [longitude=" + longitude + ", latitude="
				+ latitude + ", timeStamp=" + timeStamp + "]";
	}



}
