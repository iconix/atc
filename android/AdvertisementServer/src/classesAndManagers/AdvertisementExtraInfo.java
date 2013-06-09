package classesAndManagers;

import staticVariables.SpecialCharacters;

public class AdvertisementExtraInfo {
	String startDate;  //not empty
	String endDate;  //not empty
	String longDescription;
	String tags;
	String address;
	
	/**
	 * Constructor
	 * @param startDate
	 * @param endDate
	 * @param longDescription
	 * @param tags
	 * @param address
	 */
	public AdvertisementExtraInfo(String startDate, String endDate,
			String longDescription, String tags, String address) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.longDescription = longDescription;
		this.tags = tags;
		this.address = address;
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

	/**
	 * To string override
	 */
	@Override
	public String toString() {
		return "AdvertisementExtraInfo [startDate=" + startDate + ", endDate="
				+ endDate + ", longDescription=" + longDescription + ", tags="
				+ tags + ", address=" + address + "]";
	}
	
	/**
	 * Get a parsable string of all the fields in the AdvertisementExtraInfo object
	 */
	public String getAdsInfoInString() {
		return startDate + SpecialCharacters.delimiter 
				+ endDate + SpecialCharacters.delimiter
				+ longDescription + SpecialCharacters.delimiter 
				+ tags + SpecialCharacters.delimiter
				+ address;
	}
}
