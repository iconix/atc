package classesAndManagers;

import staticVariables.SpecialCharacters;

public class Advertisement {
	int id;  //not null
	int business_id;  //not null
	int web_business_id; //not null
	double latitude; //not null
	double longitude; //not null
	String startDate;  //not empty
	String endDate;  //not empty
	String title;
	String imageUrl;
	String shortDescription;
	String longDescription;
	String tags;
	String address;
	
	/**
	 * @param id
	 * @param business_id
	 * @param web_business_id
	 * @param latitude
	 * @param longitude
	 * @param startDate
	 * @param endDate
	 * @param title
	 * @param imageUrl
	 * @param shortDescription
	 * @param longDescription
	 * @param tags
	 * @param dateOfWeek
	 * @param address
	 */
	public Advertisement(int id, int business_id, int web_business_id,
			double latitude, double longitude, String startDate,
			String endDate, String title, String imageUrl,
			String shortDescription, String longDescription, String tags,
			String address) {
		super();
		this.id = id;
		this.business_id = business_id;
		this.web_business_id = web_business_id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.imageUrl = imageUrl;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.tags = tags;
		this.address = address;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the business_id
	 */
	public int getBusiness_id() {
		return business_id;
	}
	/**
	 * @param business_id the business_id to set
	 */
	public void setBusiness_id(int business_id) {
		this.business_id = business_id;
	}
	/**
	 * @return the web_business_id
	 */
	public int getWeb_business_id() {
		return web_business_id;
	}
	/**
	 * @param web_business_id the web_business_id to set
	 */
	public void setWeb_business_id(int web_business_id) {
		this.web_business_id = web_business_id;
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
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	 * @return the shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}
	/**
	 * @param shortDescription the shortDescription to set
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	/**
	 * @return the longDescription
	 */
	public String getLongDescription() {
		return longDescription;
	}
	/**
	 * @param longDescription the longDescription to set
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	/**
	 * Method toString
	 */
	@Override
	public String toString() {
		return "Advertisement [id=" + id + ", business_id=" + business_id
				+ ", web_business_id=" + web_business_id + ", latitude="
				+ latitude + ", longitude=" + longitude + ", startDate="
				+ startDate + ", endDate=" + endDate + ", title=" + title
				+ ", imageUrl=" + imageUrl + ", shortDescription="
				+ shortDescription + ", longDescription=" + longDescription
				+ ", tags=" + tags + ", address=" + address + "]";
	}
	
	
	
	/**
	 * Get the complete information of a promotion in a parsable string format
	 */
	public String advertisementToString() {
		return id + SpecialCharacters.delimiter 
				+ business_id + SpecialCharacters.delimiter 
				+ web_business_id + SpecialCharacters.delimiter 
				+ latitude + SpecialCharacters.delimiter 
				+ longitude + SpecialCharacters.delimiter 
				+ startDate + SpecialCharacters.delimiter 
				+ endDate + SpecialCharacters.delimiter 
				+ title + SpecialCharacters.delimiter 
				+ imageUrl + SpecialCharacters.delimiter 
				+ shortDescription + SpecialCharacters.delimiter 
				+ longDescription + SpecialCharacters.delimiter
				+ address;
	}
	
	
	
}
