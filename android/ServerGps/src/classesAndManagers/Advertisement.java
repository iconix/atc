package classesAndManagers;

import staticVariables.SpecialCharacters;

public class Advertisement {
	protected int businessId;
	protected double longitude;
	protected double latitude;
	protected long startDate;
	protected long endDate;
	protected String title;
	protected String tags;
	protected long creationTime;
	protected long updateTime;
	
	/**
	 * Class constructor
	 * Create a new Advertisement
	 * @param business Id
	 * @param longitude
	 * @param latitude
	 * @param the start date of the deal 
	 * @param the end date of the deal
	 * @param the name of the deal
	 * @param the description for the deal
	 * @param the time the deal was created
	 * @param the time the deal was last updated
	 */
	public Advertisement(int businessId, double longitude, double latitude, long startDate, long endDate, String title, String tags, long creationTime, long updateTime) {
		this.businessId = businessId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.tags = tags;
		this.creationTime = creationTime;
		this.updateTime = updateTime;
	}
	
	/**
	 * Class constructor
	 * Create a new Advertisement
	 * @param business Id
	 * @param longitude
	 * @param latitude
	 * @param the start date of the deal (given as String)
	 * @param the end date of the deal (given as String)
	 * @param the name of the deal
	 * @param the description for the deal
	 * @param the time the deal was created (given as String)
	 * @param the time the deal was last updated (given as String)
	 */
	public Advertisement(int businessId, double longitude, double latitude, String startDate, String endDate, String title, String tags, String creationTime, String updateTime) {
		this.businessId = businessId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.startDate = Long.valueOf(startDate);
		this.endDate = Long.valueOf(endDate);
		this.title = title;
		this.tags = tags;
		this.creationTime = Long.valueOf(creationTime);
		this.updateTime = Long.valueOf(updateTime);
	}
	
	/**
	 * Class constructor
	 * Create a new Advertisement
	 * @param business Id
	 * @param longitude (given as String)
	 * @param latitude (given as String)
	 * @param the start date of the deal 
	 * @param the end date of the deal 
	 * @param the name of the deal
	 * @param the description for the deal
	 * @param the time the deal was created 
	 * @param the time the deal was last updated 
	 */
	public Advertisement(int businessId, String longitude, String latitude, long startDate, long endDate, String title, String tags, long creationTime, long updateTime) {
		this.businessId = businessId;
		this.longitude = Double.valueOf(longitude);
		this.latitude = Double.valueOf(latitude);
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.tags = tags;
		this.creationTime = creationTime;
		this.updateTime = updateTime;
	}

	/**
	 * Class constructor
	 * Create a new Advertisement
	 * @param business Id
	 * @param longitude (given as String)
	 * @param latitude (given as String)
	 * @param the start date of the deal (given as String)
	 * @param the end date of the deal (given as String)
	 * @param the name of the deal
	 * @param the description for the deal
	 * @param the time the deal was created (given as String)
	 * @param the time the deal was last updated (given as String)
	 */
	public Advertisement(int businessId, String longitude, String latitude, String startDate, String endDate, String title, String tags, String creationTime, String updateTime) {
		this.businessId = businessId;
		this.longitude = Double.valueOf(longitude);
		this.latitude = Double.valueOf(latitude);
		this.startDate = Long.valueOf(startDate);
		this.endDate = Long.valueOf(endDate);
		this.title = title;
		this.tags = tags;
		this.creationTime = Long.valueOf(creationTime);
		this.updateTime = Long.valueOf(updateTime);
	}
	
	/**
	 * Class constructor
	 * Create a new Advertisement where the input is a single string
	 * @param advertisment string
	 */
	public Advertisement(String ad) {
		String[] array = ad.split(SpecialCharacters.delimiter);
		this.businessId = Integer.valueOf(array[0]);
		this.longitude = Double.valueOf(array[1]);
		this.latitude = Double.valueOf(array[2]);
		this.startDate = Long.valueOf(array[3]);
		this.endDate = Long.valueOf(array[4]);
		this.title = array[5];
		this.tags = array[6];
		this.creationTime = Long.valueOf(creationTime);
		this.updateTime = Long.valueOf(updateTime);
	}
	
	/**
	 * Return string representation of Advertisement
	 * @param instance of Advertisement
	 * @return string form of Advertisement
	 */
	public String getAdvertismentInStringFormat() {
		return businessId + SpecialCharacters.delimiter + longitude + SpecialCharacters.delimiter + latitude + SpecialCharacters.delimiter +
			    startDate + SpecialCharacters.delimiter + endDate + SpecialCharacters.delimiter + title + SpecialCharacters.delimiter +
				     tags + SpecialCharacters.delimiter + creationTime + SpecialCharacters.delimiter + updateTime;
		
	}
	
	/**
	 * Public Setter - sets the businessId
	 * @param the business Id
	 */
	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}
	
	/**
	 * Public Getter - gets the businessId
	 * @return the business Id
	 */
	public int getBusinessId() {
		return businessId;
	}
	
	/** 
	 * Public Setter - sets the longitude (double)
	 * @param the longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/** 
	 * Public Setter - sets the longitude (String)
	 * @param the longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = Double.valueOf(longitude);
	}
	
	/**
	 * Public Getter - gets the longitude
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/** 
	 * Public Setter - sets the latitude (double)
	 * @param the latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/** 
	 * Public Setter - sets the latitude (String)
	 * @param the latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = Double.valueOf(latitude);
	}
	
	/**
	 * Public Getter - gets the latitude
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}	
	
	/**
	 * Public Setter - sets the start date (long)
	 * @param the state date
	 */
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Public Setter - sets the start date (String)
	 * @param the start date
	 */
	public void setStartDate(String startDate) {
		this.startDate = Long.valueOf(startDate);
	}
	
	/**
	 * Public Getter - gets the start date
	 * @return the start date
	 */
	public long getStartDate() {
		return startDate;
	}
	
	/**
	 * Public Setter - sets the end date (long)
	 * @param the end date
	 */
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Public Setter - sets the end date (String)
	 * @param the end date
	 */
	public void setEndDate(String endDate) {
		this.endDate = Long.valueOf(endDate);
	}
	
	/** 
	 * Public Getter - gets the end date
	 * @return the end date
	 */
	public long getEndDate() {
		return endDate;
	}
	
	/** 
	 * Public Setter - sets the title
	 * @param the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 
	 * Public Getter - gets the title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Public Setter - sets the tags
	 * @param the tags
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	/**
	 * Public Getter - gets the tags
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	
	/**
	 * Public Setter - sets the creation time (long)
	 * @param the creation time
	 */
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	
	/**
	 * Public Setter - sets the creation time (String)
	 * @param the creation time
	 */
	public void setCreationTime(String creationTime) {
		this.creationTime = Long.valueOf(creationTime);
	}
	
	/**
	 * Public Getter - gets the creation time
	 * @return the creation time
	 */
	public long getCreationTime() {
		return creationTime;
	}
	
	/** 
	 * Public Setter - sets the update time (long)
	 * @param the update time
	 */
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * Public Setter - sets the update time (String)
	 * @param the update time
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = Long.valueOf(updateTime);
	}
	
	/**
	 * Public Getter - gets the update time
	 * @return the update time
	 */
	public long getUpdateTime() {
		return updateTime;
	}
}
	
	
	