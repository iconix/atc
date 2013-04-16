package classesAndManagers;

import java.io.InputStream;
import java.io.OutputStream;

public class Promotion {
	String id;
	String business_id;
	double latitude;
	double longitude;
	String startDate;
	String endDate;
	String title;
	int imageOption;
	String imageUrl;
	InputStream imageUpload;
	String shortDescription;
	String longDescription;
	String firstTag;
	String secondTag;
	String thirdTag;
	String startTime;
	String endTime;
	boolean sunday;
	boolean monday;
	boolean tuesday;
	boolean wednesday;
	boolean thursday;
	boolean friday;
	boolean saturday;
	String address;
	boolean promotionOrEvent;
	String created_at;
	String updated_at;
	
	/**
	 * Empty constructor
	 */
	public Promotion() {
		
	}
	
	/**
	 * Constructor
	 * @param id
	 * @param business_id
	 * @param latitude
	 * @param longitude
	 * @param startDate
	 * @param endDate
	 * @param title
	 * @param imageOption
	 * @param imageUrl
	 * @param imageUpload
	 * @param shortDescription
	 * @param longDescription
	 * @param firstTag
	 * @param secondTag
	 * @param thirdTag
	 * @param startTime
	 * @param endTime
	 * @param sunday
	 * @param monday
	 * @param tuesday
	 * @param wednesday
	 * @param thursday
	 * @param friday
	 * @param saturday
	 * @param address
	 * @param promotionOrEvent
	 * @param created_at
	 * @param updated_at
	 */
	public Promotion(String id, String business_id, double latitude,
			double longitude, String startDate, String endDate, String title,
			int imageOption, String imageUrl, InputStream imageUpload,
			String shortDescription, String longDescription, String firstTag,
			String secondTag, String thirdTag, String startTime,
			String endTime, boolean sunday, boolean monday, boolean tuesday, 
			boolean wednesday, boolean thursday, boolean friday, boolean saturday, 
			String address, boolean promotionOrEvent, String created_at,
			String updated_at) {
		super();
		this.id = id;
		this.business_id = business_id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.imageOption = imageOption;
		this.imageUrl = imageUrl;
		this.imageUpload = imageUpload;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.firstTag = firstTag;
		this.secondTag = secondTag;
		this.thirdTag = thirdTag;
		this.startTime = startTime;
		this.endTime = endTime;
		this.sunday = sunday;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.address = address;
		this.promotionOrEvent = promotionOrEvent;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the business_id
	 */
	public String getBusiness_id() {
		return business_id;
	}

	/**
	 * @param business_id the business_id to set
	 */
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
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
	 * @return the imageOption
	 */
	public int getImageOption() {
		return imageOption;
	}

	/**
	 * @param imageOption the imageOption to set
	 */
	public void setImageOption(int imageOption) {
		this.imageOption = imageOption;
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
	 * @return the imageUpload
	 */
	public InputStream getImageUpload() {
		return imageUpload;
	}

	/**
	 * @param imageUpload the imageUpload to set
	 */
	public void setImageUpload(InputStream imageUpload) {
		this.imageUpload = imageUpload;
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
	 * @return the firstTag
	 */
	public String getFirstTag() {
		return firstTag;
	}

	/**
	 * @param firstTag the firstTag to set
	 */
	public void setFirstTag(String firstTag) {
		this.firstTag = firstTag;
	}

	/**
	 * @return the secondTag
	 */
	public String getSecondTag() {
		return secondTag;
	}

	/**
	 * @param secondTag the secondTag to set
	 */
	public void setSecondTag(String secondTag) {
		this.secondTag = secondTag;
	}

	/**
	 * @return the thirdTag
	 */
	public String getThirdTag() {
		return thirdTag;
	}

	/**
	 * @param thirdTag the thirdTag to set
	 */
	public void setThirdTag(String thirdTag) {
		this.thirdTag = thirdTag;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the monday
	 */
	public boolean isMonday() {
		return monday;
	}

	/**
	 * @param monday the monday to set
	 */
	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	/**
	 * @return the tuesday
	 */
	public boolean isTuesday() {
		return tuesday;
	}

	/**
	 * @param tuesday the tuesday to set
	 */
	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	/**
	 * @return the wednesday
	 */
	public boolean isWednesday() {
		return wednesday;
	}

	/**
	 * @param wednesday the wednesday to set
	 */
	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	/**
	 * @return the thursday
	 */
	public boolean isThursday() {
		return thursday;
	}

	/**
	 * @param thursday the thursday to set
	 */
	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	/**
	 * @return the friday
	 */
	public boolean isFriday() {
		return friday;
	}

	/**
	 * @param friday the friday to set
	 */
	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	/**
	 * @return the saturday
	 */
	public boolean isSaturday() {
		return saturday;
	}

	/**
	 * @param saturday the saturday to set
	 */
	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	/**
	 * @return the sunday
	 */
	public boolean isSunday() {
		return sunday;
	}

	/**
	 * @param sunday the sunday to set
	 */
	public void setSunday(boolean sunday) {
		this.sunday = sunday;
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
	 * @return the promotionOrEvent
	 */
	public boolean isPromotionOrEvent() {
		return promotionOrEvent;
	}

	/**
	 * @param promotionOrEvent the promotionOrEvent to set
	 */
	public void setPromotionOrEvent(boolean promotionOrEvent) {
		this.promotionOrEvent = promotionOrEvent;
	}

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}

	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	
	
	
}
