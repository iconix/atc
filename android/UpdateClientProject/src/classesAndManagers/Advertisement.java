package classesAndManagers;

import staticVariables.SpecialCharacters;

public class Advertisement {
	protected int businessId;
	protected String businessName;
	protected double longitude;
	protected double latitude;
	protected long startDate;
	protected long endDate;
	protected String title;
	protected String tags;
	protected long creationTime;
	protected long updateTime;
	protected String imageUrl;
	
	/**
	 * Class constructor
	 * Create a new Advertisement
	 * @param business Id
	 * @param business name
	 * @param longitude
	 * @param latitude
	 * @param the start date of the deal 
	 * @param the end date of the deal
	 * @param the name of the deal
	 * @param the description for the deal
	 * @param the time the deal was created
	 * @param the time the deal was last updated
	 * @param url to image
	 */
	public Advertisement(int businessId, String businessName, double longitude, double latitude, long startDate, long endDate, String title, String tags, long creationTime, long updateTime, String imageUrl) {
		this.businessId = businessId;
		this.businessName = businessName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.tags = tags;
		this.creationTime = creationTime;
		this.updateTime = updateTime;
		this.imageUrl = imageUrl;
	}
	
	/**
	 * Class constructor
	 * Create a new Advertisement
	 * @param business Id
	 * @param business name
	 * @param longitude
	 * @param latitude
	 * @param the start date of the deal (given as String)
	 * @param the end date of the deal (given as String)
	 * @param the name of the deal
	 * @param the description for the deal
	 * @param the time the deal was created (given as String)
	 * @param the time the deal was last updated (given as String)
	 * @param url to image
	 */
	public Advertisement(int businessId, String businessName, double longitude, double latitude, String startDate, String endDate, String title, String tags, String creationTime, String updateTime, String imageUrl) {
		this.businessId = businessId;
		this.businessName = businessName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.startDate = Long.valueOf(startDate);
		this.endDate = Long.valueOf(endDate);
		this.title = title;
		this.tags = tags;
		this.creationTime = Long.valueOf(creationTime);
		this.updateTime = Long.valueOf(updateTime);
		this.imageUrl = imageUrl;
	}
	
	/**
	 * Class constructor
	 * Create a new Advertisement
	 * @param business Id
	 * @param businss name
	 * @param longitude (given as String)
	 * @param latitude (given as String)
	 * @param the start date of the deal 
	 * @param the end date of the deal 
	 * @param the name of the deal
	 * @param the description for the deal
	 * @param the time the deal was created 
	 * @param the time the deal was last updated
	 * @param url to image
	 */
	public Advertisement(int businessId, String businessName, String longitude, String latitude, long startDate, long endDate, String title, String tags, long creationTime, long updateTime, String imageUrl) {
		this.businessId = businessId;
		this.businessName = businessName;
		this.longitude = Double.valueOf(longitude);
		this.latitude = Double.valueOf(latitude);
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.tags = tags;
		this.creationTime = creationTime;
		this.updateTime = updateTime;
		this.imageUrl = imageUrl;
	}

	/**
	 * Class constructor
	 * Create a new Advertisement
	 * @param business Id
	 * @param business name
	 * @param longitude (given as String)
	 * @param latitude (given as String)
	 * @param the start date of the deal (given as String)
	 * @param the end date of the deal (given as String)
	 * @param the name of the deal
	 * @param the description for the deal
	 * @param the time the deal was created (given as String)
	 * @param the time the deal was last updated (given as String)
	 * @param url to image
	 */
	public Advertisement(int businessId, String businessName, String longitude, String latitude, String startDate, String endDate, String title, String tags, String creationTime, String updateTime, String imageUrl) {
		this.businessId = businessId;
		this.longitude = Double.valueOf(longitude);
		this.latitude = Double.valueOf(latitude);
		this.startDate = Long.valueOf(startDate);
		this.endDate = Long.valueOf(endDate);
		this.title = title;
		this.tags = tags;
		this.creationTime = Long.valueOf(creationTime);
		this.updateTime = Long.valueOf(updateTime);
		this.imageUrl = imageUrl;
	}
	
	/**
	 * Class constructor
	 * Create a new Advertisement where the input is a single string
	 * @param advertisment string
	 */
	public Advertisement(String ad) {
		String[] array = ad.split(SpecialCharacters.delimiter);
		this.businessId = Integer.valueOf(array[0]);
		this.businessName = array[1];
		this.longitude = Double.valueOf(array[2]);
		this.latitude = Double.valueOf(array[3]);
		this.startDate = Long.valueOf(array[4]);
		this.endDate = Long.valueOf(array[5]);
		this.title = array[6];
		this.tags = array[7];
		this.creationTime = Long.valueOf(array[8]);
		this.updateTime = Long.valueOf(array[9]);
		this.imageUrl = array[10];
		
	}
		
	/**
	 * Return string representation of Advertisement
	 * @param instance of Advertisement
	 * @return string form of Advertisement
	 */
	public String getAdvertismentInStringFormat() {
		return businessId + SpecialCharacters.delimiter + businessName + SpecialCharacters.delimiter + longitude + SpecialCharacters.delimiter + latitude + SpecialCharacters.delimiter +
			    startDate + SpecialCharacters.delimiter + endDate + SpecialCharacters.delimiter + title + SpecialCharacters.delimiter + tags + SpecialCharacters.delimiter + 
			    creationTime + SpecialCharacters.delimiter + updateTime + SpecialCharacters.delimiter + imageUrl;
		
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
	 * Public Setter - sets the businessName
	 * @param the business name
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	/**
	 * Public Getter - sets the businessName
	 * @preturn the business name
	 */
	public String getBusinessName() {
		return businessName;
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
	
	/**
	 * Public Setter - sets the image url
	 * @param the image url
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	/**
	 * Public Getter - gets the image url
	 * @param the image url
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	
	public double getDistance(double destLongitude, double destLatitude) {
		double r = 3959; //Radius of earth is 3,959 miles
		double conversion = 69; // 69 miles for each degree of longitude or latitude
		double delta = 0.0001;
		if (Math.abs(latitude - destLatitude) < delta) {
			return Math.abs(longitude - destLongitude)*conversion;
		} else if (Math.abs(longitude - destLongitude) < delta) {
			return Math.abs(latitude - destLatitude)*conversion;
		} else {
			double dLat = Math.toRadians(destLatitude - latitude);
			double dLong = Math.toRadians(destLongitude - longitude);
			double sindLat = Math.sin(dLat/2);
			double sindLong = Math.sin(dLong/2);
			double a = Math.pow(sindLat, 2);
			double b = Math.cos(latitude)*Math.cos(destLatitude)*Math.pow(sindLong, 2);
			double distance = 2*r*Math.asin(Math.pow(a+b,0.5));
			return distance;
		}

	}
}
	
	
	