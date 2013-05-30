package com.sapenguins.thecornerapp.objects;

import com.sapenguins.thecornerapp.constants.SpecialCharacters;

public class AdInfo {
	int id;
	int businessID;
	String title;
	double latitude;
	double longitude;
	String imageUrl;
	String shortDescription;
	String startDate;  //not empty
	String endDate;  //not empty
	String longDescription;
	String tags;
	String address;
	
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
	 * @param longDescription
	 * @param tags
	 * @param address
	 */
	public AdInfo(int id, int businessID, String title, double latitude,
			double longitude, String imageUrl, String shortDescription,
			String startDate, String endDate, String longDescription,
			String tags, String address) {
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


	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdInfo [id=" + id + ", businessID=" + businessID + ", title="
				+ title + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", imageUrl=" + imageUrl + ", shortDescription="
				+ shortDescription + ", startDate=" + startDate + ", endDate="
				+ endDate + ", longDescription=" + longDescription + ", tags="
				+ tags + ", address=" + address + "]";
	}



	/**
	 * Get a parsable string of all the fields in the AdvertisementExtraInfo object
	 */
	public String getAdInfoInString() {
		return id + SpecialCharacters.delimiter 
				+ businessID + SpecialCharacters.delimiter 
				+ title + SpecialCharacters.delimiter 
				+ latitude + SpecialCharacters.delimiter
				+ longitude + SpecialCharacters.delimiter 
				+ imageUrl + SpecialCharacters.delimiter 
				+ shortDescription + SpecialCharacters.delimiter 
				+ startDate + SpecialCharacters.delimiter 
				+ endDate + SpecialCharacters.delimiter
				+ longDescription + SpecialCharacters.delimiter 
				+ tags + SpecialCharacters.delimiter
				+ address;
	}
}
