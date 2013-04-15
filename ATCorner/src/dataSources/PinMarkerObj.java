package dataSources;

public class PinMarkerObj {
	private String title;
	private String description;
	private double latitude;
	private double longitude;
	private long time;
	
	/**
	 * Empty constructor for PinMarkerObj
	 */
	public PinMarkerObj() {	}
	
	/**
	 * Constructor of new PinMarkerObj
	 * @param time
	 * @param title
	 * @param description
	 * @param latitude
	 * @param longitude
	 */
	public PinMarkerObj(String title, String description, double longitude, double latitude, long time){
		this.title = title;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
	}
	
	/**
	 * Constructor of new PinMarkerObj
	 * @param time
	 * @param title
	 * @param description
	 * @param latitude
	 * @param longitude
	 */
	public PinMarkerObj(String title, String description, double longitude, double latitude, String time){
		this.title = title;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = Long.valueOf(time);
	}
	
	/**
	 * Constructor of new PinMarkerObj
	 * @param time
	 * @param title
	 * @param description
	 * @param latitude
	 * @param longitude
	 */
	public PinMarkerObj(String title, String description, String longitude, String latitude, String time){
		this.title = title;
		this.description = description;
		this.latitude = Double.valueOf(latitude);
		this.longitude = Double.valueOf(longitude);
		this.time = Long.valueOf(time);
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
}