package objects;

public class PinMarkerObj {
	
	int pinID;
	String title;
	String description;
	double latitude;
	double longitude;
	long time;
	String pinType;
	String imageUrl;
	int nearbyLoc;
	
	/**
	 * Empty constructor for PinMarkerObj
	 */
	public PinMarkerObj() {	}
	
	/**
	 * Constructor of new PinMarkerObj
	 * @param pinID
	 * @param time
	 * @param title
	 * @param description
	 * @param latitude
	 * @param longitude
	 * @param pinType
	 * @param imageUrl
	 * @param nearbyLoc //this is actually the business ID number
	 */
	public PinMarkerObj(int pinID, String title, String description, double longitude, double latitude, long time, String pinType, String imageUrl, int nearbyLoc){
		this.pinID = pinID;
		this.title = title;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
		this.pinType = pinType;
		this.imageUrl = imageUrl;
		this.nearbyLoc = nearbyLoc;
	}
	
	/**
	 * Constructor of new PinMarkerObj
	 * @param time
	 * @param title
	 * @param description
	 * @param latitude
	 * @param longitude
	 * @param pinType
	 */
	public PinMarkerObj(int pinID, String title, String description, double longitude, double latitude, String time, String pinType, String imageUrl, int nearbyLoc){
		this.pinID = pinID;
		this.title = title;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = Long.valueOf(time);
		this.pinType = pinType;
		this.imageUrl = imageUrl;
		this.nearbyLoc = nearbyLoc;
	}
	
	
	
	/**
	 * @return the pinID
	 */
	public int getPinID() {
		return pinID;
	}

	/**
	 * @param pinID the pinID to set
	 */
	public void setPinID(int pinID) {
		this.pinID = pinID;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = Long.valueOf(time);
	}

	/**
	 * @return the pinType
	 */
	public String getPinType() {
		return pinType;
	}

	/**
	 * @param pinType the pinType to set
	 */
	public void setPinType(String pinType) {
		this.pinType = pinType;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the nearbyLoc
	 */
	public int getNearbyLoc() {
		return nearbyLoc;
	}

	/**
	 * @param nearbyLoc the nearbyLoc to set
	 */
	public void setNearbyLoc(int nearbyLoc) {
		this.nearbyLoc = nearbyLoc;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PinMarkerObj [pinID=" + pinID + ", title=" + title
				+ ", description=" + description + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", time=" + time + ", pinType="
				+ pinType + ", imageUrl=" + imageUrl + ", nearbyLoc="
				+ nearbyLoc + "]";
	}
	
}