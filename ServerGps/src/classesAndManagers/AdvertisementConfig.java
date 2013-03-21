package classesAndManagers;

public class AdvertisementConfig {
	double lowerLongitude;
	double higherLongitude;
	double lowerLatitude;
	double higherLatitude;
	long startTime;
	long endTime;
	
	/**
	 * Class Constructor
	 * Create an Advertisement configuration to be passed to the server
	 * @param lowerLongitude
	 * @param higherLongitude
	 * @param lowerLatitude
	 * @param higherLatitude
	 * @param startTime
	 * @param endTime
	 */
	public AdvertisementConfig (double lowerLongitude, double higherLongitude, double lowerLatitude, double higherLatitude,
				long startTime, long endTime) {
		this.lowerLongitude = lowerLongitude;
		this.higherLongitude = higherLongitude;
		this.lowerLatitude = lowerLatitude;
		this.higherLatitude = higherLatitude;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * Class Constructor with string inputs
	 * Create an Advertisement configuration to be passed to the server
	 * @param lowerLongitude
	 * @param higherLongitude
	 * @param lowerLatitude
	 * @param higherLatitude
	 * @param startTime
	 * @param endTime
	 */
	public AdvertisementConfig (String lowerLongitude, String higherLongitude, String lowerLatitude, String higherLatitude,
			String startTime, String endTime) {
		this.lowerLongitude = Double.valueOf(lowerLongitude);
		this.higherLongitude = Double.valueOf(higherLongitude);
		this.lowerLatitude = Double.valueOf(lowerLatitude);
		this.higherLatitude = Double.valueOf(higherLatitude);
		this.startTime = Long.valueOf(startTime);
		this.endTime = Long.valueOf(endTime);
	}
	
	/**
	 * Public Setter - sets lower longitude (double)
	 * @param lower longitude (double)
	 */
	public void setLowerLongitude(double lowerLongitude) {
		this.lowerLongitude = lowerLongitude;
	}
	
	/**
	 * Public Setter - sets lower longitude (String)
	 * @param lower longitude (String)
	 */
	public void setLowerLongitude(String lowerLongitude) {
		this.lowerLongitude = Double.valueOf(lowerLongitude);
	}
	
	/**
	 * Public Getter - gets lower longitude
	 * @return lower longitude
	 */
	public double getLowerLongitude() {
		return lowerLongitude;
	}
	
	/**
	 * Public Setter - sets higher longitude (double)
	 * @param higher longitude (double)
	 */
	public void setHigherLongitude(double higherLongitude) {
		this.higherLongitude = higherLongitude;
	}
	
	/**
	 * Public Setter - sets higher longitude (String)
	 * @param higher longitude (String)
	 */
	public void setHigherLongitude(String higherLongitude) {
		this.higherLongitude = Double.valueOf(higherLongitude);
	}
	
	/**
	 * Public Getter - gets higher longitude
	 * @return higher longitude
	 */
	public double getHigherLongitude() {
		return higherLongitude;
	}
	
	/**
	 * Public Setter - sets lower latitude (double)
	 * @param lower latitude (double)
	 */
	public void setLowerLatitude(double lowerLatitude) {
		this.lowerLatitude = lowerLatitude;
	}
	
	/**
	 * Public Setter - sets lower latitude (String)
	 * @param lower latitude (String)
	 */
	public void setLowerLatitude(String lowerLatitude) {
		this.lowerLatitude = Double.valueOf(lowerLatitude);
	}
	
	/**
	 * Public Getter - gets lower latitude
	 * @return lower latitude
	 */
	public double getLowerLatitude() {
		return lowerLatitude;
	}
	
	/**
	 * Public Setter - sets higher latitude (double)
	 * @param higher latitude (double)
	 */
	public void setHigherLatitude(double higherLatitude) {
		this.higherLatitude = higherLatitude;
	}
	
	/**
	 * Public Setter - sets higher latitude (String)
	 * @param higher latitude (String)
	 */
	public void setHigherLatitude(String higherLatitude) {
		this.higherLatitude = Double.valueOf(higherLatitude);
	}
	
	/**
	 * Public Getter - gets higher latitude
	 * @return higher latitude
	 */
	public double getHigherLatitude() {
		return higherLatitude;
	}
	
	/**
	 * Public Setter - sets start time (long)
	 * @param start time (long)
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Public Setter - sets start time (String)
	 * @param start time (String)
	 */
	public void setStartTime(String startTime) {
		this.startTime = Long.valueOf(startTime);
	}
	
	/**
	 * Public Getter - gets start time 
	 * @return start time
	 */
	public long getStartTime() {
		return startTime;
	}
	
	/**
	 * Public Setter - sets end time (long)
	 * @param end time (long)
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * Public Setter - sets end time (String)
	 * @param end time (String)
	 */
	public void setEndTime(String endTime) {
		this.endTime = Long.valueOf(endTime);
	}
	
	/**
	 * Public Getter - gets end time
	 * @param end time
	 */
	public long getEndTime() {
		return endTime;
	}
}