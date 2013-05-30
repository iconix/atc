package com.sapenguins.thecornerapp.objects;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;

public class BasicAd {
	int id;
	int businessID;
	String title;
	double latitude;
	double longitude;
	String imageUrl;
	String shortDescription;
	String startDate;  //not empty
	String endDate;  //not empty
	
	/**
	 * Create an empty Basic Event object
	 */
	public BasicAd() {
	}

	
	
	/**
	 * @param id
	 * @param businessID
	 * @param title
	 * @param latitude
	 * @param longitude
	 * @param imageUrl
	 * @param shortDescription
	 * @param startDate
	 * @param endDate
	 */
	public BasicAd(int id, int businessID, String title, double latitude,
			double longitude, String imageUrl, String shortDescription,
			String startDate, String endDate) {
		super();
		this.id = id;
		this.businessID = businessID;
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
		this.imageUrl = imageUrl;
		this.shortDescription = shortDescription;
		this.startDate = startDate;
		this.endDate = endDate;
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
	 * @return the businessID
	 */
	public int getBusinessID() {
		return businessID;
	}



	/**
	 * @param businessID the businessID to set
	 */
	public void setBusinessID(int businessID) {
		this.businessID = businessID;
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



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasicAd [id=" + id + ", businessID=" + businessID + ", title="
				+ title + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", imageUrl=" + imageUrl + ", shortDescription="
				+ shortDescription + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}



	/**
	 * Get basic concatenated string of Basic Event
	 * @return parsable Basic Event  String
	 */
	public String getBasicAdString() {
		return id + SpecialCharacters.delimiter + 
				businessID + SpecialCharacters.delimiter + 
				title + SpecialCharacters.delimiter + 
				latitude + SpecialCharacters.delimiter + 
				longitude + SpecialCharacters.delimiter + 
				imageUrl + SpecialCharacters.delimiter + 
				shortDescription + SpecialCharacters.delimiter + 
				startDate + SpecialCharacters.delimiter + 
				endDate;
	}
}
