package classesAndManagers;

import java.sql.Blob;

import staticVariables.SpecialCharacters;

public class Business {
	protected String name;
	protected String email;
	protected String password_digest;
	protected String remember_token;
	protected String websiteURL;
	protected int imageOption;
	protected String imageURL;
	protected Blob imageUpload;
	protected String shortDescription;
	protected String longDescription;
	protected long sundayOpenTime;
	protected long sundayCloseTime;
	protected long mondayOpenTime;
	protected long mondayCloseTime;
	protected long tuesdayOpenTime;
	protected long tuesdayCloseTime;
	protected long wednesdayOpenTime;
	protected long wednesdayCloseTime;
	protected long thursdayOpenTime;
	protected long thursdayCloseTime;
	protected long fridayOpenTime;
	protected long fridayCloseTime;
	protected long saturdayOpenTime;
	protected long saturdayCloseTime;
	protected double latitude;
	protected double longitude;
	protected String address;
	protected int phoneNumber;
	protected long createdAt;
	protected long updatedAt;
	
	/**
	 * Class Constructor
	 */
	public Business(String name, String email, String password_digest, String remember_token, String websiteURL, int imageOption, String imageURL, Blob imageUpload, String shortDescription,
			String longDescription, long sundayOpenTime, long sundayCloseTime, long mondayOpenTime, long mondayCloseTime, long tuesdayOpenTime, long tuesdayCloseTime,
			long wednesdayOpenTime, long wednesdayCloseTime, long thursdayOpenTime, long thursdayCloseTime, long fridayOpenTime, long fridayCloseTime, long saturdayOpenTime, long saturdayCloseTime,
			double latitude, double longitude, String address, int phoneNumber, long createdAt, long updatedAt) {
		this.name = name;
		this.email = email;
		this.password_digest = password_digest;
		this.remember_token = remember_token;
		this.websiteURL = websiteURL;
		this.imageOption = imageOption;
		this.imageURL = imageURL;
		this.imageUpload = imageUpload;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.sundayOpenTime = sundayOpenTime;
		this.sundayCloseTime = sundayCloseTime;
		this.mondayOpenTime = mondayOpenTime;
		this.mondayCloseTime = mondayCloseTime;
		this.tuesdayOpenTime = tuesdayOpenTime;
		this.tuesdayCloseTime = tuesdayCloseTime;
		this.wednesdayOpenTime = wednesdayOpenTime;
		this.wednesdayCloseTime = wednesdayCloseTime;
		this.thursdayOpenTime = thursdayOpenTime;
		this.thursdayCloseTime = thursdayCloseTime;
		this.fridayOpenTime = fridayOpenTime;
		this.fridayCloseTime = fridayCloseTime;
		this.saturdayOpenTime = saturdayOpenTime;
		this.saturdayCloseTime = saturdayCloseTime;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	/**
	 * Class Constructor
	 */
	public Business(String business, Blob imageUpload) {
		String[] array = business.split(SpecialCharacters.delimiter);
		this.name = array[0];
		this.email = array[1];
		this.password_digest = array[2];
		this.remember_token = array[3];
		this.websiteURL = array[4];
		this.imageOption = Integer.valueOf(array[5]);
		this.imageURL = array[6];
		this.shortDescription = array[7];
		this.longDescription = array[8];
		this.sundayOpenTime = Long.valueOf(array[9]);
		this.sundayCloseTime = Long.valueOf(array[10]);
		this.mondayOpenTime = Long.valueOf(array[11]);
		this.mondayCloseTime = Long.valueOf(array[12]);
		this.tuesdayOpenTime = Long.valueOf(array[13]);
		this.tuesdayCloseTime = Long.valueOf(array[14]);
		this.wednesdayOpenTime = Long.valueOf(array[15]);
		this.wednesdayCloseTime = Long.valueOf(array[16]);
		this.thursdayOpenTime = Long.valueOf(array[17]);
		this.thursdayCloseTime = Long.valueOf(array[18]);
		this.fridayOpenTime = Long.valueOf(array[19]);
		this.fridayCloseTime = Long.valueOf(array[20]);
		this.saturdayOpenTime = Long.valueOf(array[21]);
		this.saturdayCloseTime = Long.valueOf(array[22]);
		this.latitude = Double.valueOf(array[23]);
		this.longitude = Double.valueOf(array[24]);
		this.address = array[25];
		this.phoneNumber = Integer.valueOf(array[26]);
		this.createdAt = Long.valueOf(array[27]);
		this.updatedAt = Long.valueOf(array[28]);
		
		this.imageUpload = imageUpload;
	}
	
	/**
	 * Public Getter - gets business name
	 * @return business name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Public Setter - sets business name
	 * @param business name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Public Getter - gets email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Public Setter - sets email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Public Getter - get password digest
	 * @return password digest
	 */
	public String getPasswordDigest() {
		return password_digest;
	}
	
	/**
	 * Public Setter - set the password digest
	 * @param password digest
	 */
	public void setPasswordDigest(String password_digest) {
		this.password_digest = password_digest;
	}
	
	/**
	 * Public Getter - gets the remember token
	 * @return remember token
	 */
	public String getRememberToken() {
		return remember_token;
	}
	
	/**
	 * Public Setter - sets the remember token
	 * @param remember token
	 */
	public void setRememberToken(String remember_token) {
		this.remember_token = remember_token;
	}
	
	/**
	 * Public Getter - gets the website URL
	 * @return website URL
	 */
	public String getWebsiteURL() {
		return websiteURL;
	}
	
	/**
	 * Public Setter - sets the website URL
	 * @param website URL
	 */
	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}
	
	/**
	 * Public Getter - gets the image option
	 * @return image option
	 */
	public int getImageOption() {
		return imageOption;
	}
	
	/**
	 * Public Setter - sets the image option
	 * @param image option 
	 */
	public void setImageOption(int imageOption) {
		this.imageOption = imageOption;
	}
	
	/**
	 * Public Getter - gets the image URL
	 * @return image URL
	 */
	public String getImageURL() {
		return imageURL;
	}
	
	/**
	 * Public Setter - sets the image URL
	 * @param image URL
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	/**
	 * Public Getter - gets the image upload
	 * @return image upload
	 */
	public Blob getImageUpload() {
		return imageUpload;
	}
	
	/**
	 * Public Setter - sets the image upload
	 * @param image upload
	 */
	public void setImageUpload(Blob imageUpload) {
		this.imageUpload = imageUpload;
	}
	
	/**
	 * Public Getter - gets the short description
	 * @return short description
	 */
	public String getShortDescription() {
		return shortDescription;
	}
	
	/**
	 * Public Setter - sets the short description
	 * @param short description
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	/**
	 * Public Getter - gets the long description
	 * @return long description
	 */
	public String getLongDescription() {
		return longDescription;
	}
	
	/**
	 * Public Setter - sets the long description
	 * @param long description
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	
	/**
	 * Public Getter - gets the monday open time
	 * @return monday open time
	 */
	public long getMondayOpenTime() {
		return mondayOpenTime;
	}
	
	/**
	 * Public Setter - sets the monday open time
	 * @param monday open time
	 */
	public void setMondayOpenTime(long mondayOpenTime) {
		this.mondayOpenTime = mondayOpenTime;
	}
	
	/**
	 * Public Getter - gets the monday close time
	 * @return monday close time
	 */
	public long getMondayCloseTime() {
		return mondayCloseTime;
	}
	
	/**
	 * Public Setter - sets the monday close time
	 * @param monday close time
	 */
	public void setMondayCloseTime(long mondayCloseTime) {
		this.mondayCloseTime = mondayCloseTime;
	}
	
	/**
	 * Public Getter - gets the tuesday open time
	 * @return tuesday open time
	 */
	public long getTuesdayOpenTime() {
		return tuesdayOpenTime;
	}
	
	/**
	 * Public Setter - sets the tuesday open time
	 * @param tuesday open time
	 */
	public void setTuesdayOpenTime(long tuesdayOpenTime) {
		this.tuesdayOpenTime = tuesdayOpenTime;
	}
	
	/**
	 * Public Getter - gets the tuesday close time
	 * @return tuesday close time
	 */
	public long getTuesdayCloseTime() {
		return tuesdayCloseTime;
	}
	
	/**
	 * Public Setter - sets the tuesday close time
	 * @param tuesday close time
	 */
	public void setTuesdayCloseTime(long tuesdayCloseTime) {
		this.tuesdayCloseTime = tuesdayCloseTime;
	}	

	/**
	 * Public Getter - gets the wednesday open time
	 * @return wednesday open time
	 */
	public long getWednesdayOpenTime() {
		return wednesdayOpenTime;
	}
	
	/**
	 * Public Setter - sets the wednesday open time
	 * @param wednesday open time
	 */
	public void setWednesdayOpenTime(long wednesdayOpenTime) {
		this.wednesdayOpenTime = wednesdayOpenTime;
	}
	
	/**
	 * Public Getter - gets the wednesday close time
	 * @return wednesday close time
	 */
	public long getWednesdayCloseTime() {
		return wednesdayCloseTime;
	}
	
	/**
	 * Public Setter - sets the wednesday close time
	 * @param wednesday close time
	 */
	public void setWednesdayCloseTime(long wednesdayCloseTime) {
		this.wednesdayCloseTime = wednesdayCloseTime;
	}	
	
	/**
	 * Public Getter - gets the thursday open time
	 * @return thursday open time
	 */
	public long getThursdayOpenTime() {
		return thursdayOpenTime;
	}
	
	/**
	 * Public Setter - sets the thursday open time
	 * @param thursday open time
	 */
	public void setThursdayOpenTime(long thursdayOpenTime) {
		this.thursdayOpenTime = thursdayOpenTime;
	}
	
	/**
	 * Public Getter - gets the thursday close time
	 * @return thursday close time
	 */
	public long getThursdayCloseTime() {
		return thursdayCloseTime;
	}
	
	/**
	 * Public Setter - sets the thursday close time
	 * @param thursday close time
	 */
	public void setThursdayCloseTime(long thursdayCloseTime) {
		this.thursdayCloseTime = thursdayCloseTime;
	}		
	
	/**
	 * Public Getter - gets the friday open time
	 * @return friday open time
	 */
	public long getFridayOpenTime() {
		return fridayOpenTime;
	}
	
	/**
	 * Public Setter - sets the friday open time
	 * @param friday open time
	 */
	public void setFridayOpenTime(long fridayOpenTime) {
		this.fridayOpenTime = fridayOpenTime;
	}
	
	/**
	 * Public Getter - gets the friday close time
	 * @return friday close time
	 */
	public long getFridayCloseTime() {
		return fridayCloseTime;
	}
	
	/**
	 * Public Setter - sets the friday close time
	 * @param friday close time
	 */
	public void setFridayCloseTime(long fridayCloseTime) {
		this.fridayCloseTime = fridayCloseTime;
	}
	
	/**
	 * Public Getter - gets the saturday open time
	 * @return saturday open time
	 */
	public long getSaturdayOpenTime() {
		return saturdayOpenTime;
	}
	
	/**
	 * Public Setter - sets the saturday open time
	 * @param saturday open time
	 */
	public void setSaturdayOpenTime(long saturdayOpenTime) {
		this.saturdayOpenTime = saturdayOpenTime;
	}
	
	/**
	 * Public Getter - gets the saturday close time
	 * @return saturday close time
	 */
	public long getSaturdayCloseTime() {
		return saturdayCloseTime;
	}
	
	/**
	 * Public Setter - sets the saturday close time
	 * @param saturday close time
	 */
	public void setSaturdayCloseTime(long saturdayCloseTime) {
		this.saturdayCloseTime = saturdayCloseTime;
	}
	
	/**
	 * Public Getter - gets the sunday open time
	 * @return sunday open time
	 */
	public long getSundayOpenTime() {
		return sundayOpenTime;
	}
	
	/**
	 * Public Setter - sets the sunday open time
	 * @param sunday open time
	 */
	public void setSundayOpenTime(long sundayOpenTime) {
		this.sundayOpenTime = sundayOpenTime;
	}
	
	/**
	 * Public Getter - gets the sunday close time
	 * @return sunday close time
	 */
	public long getSundayCloseTime() {
		return sundayCloseTime;
	}
	
	/**
	 * Public Setter - sets the sunday close time
	 * @param sunday close time
	 */
	public void setSundayCloseTime(long sundayCloseTime) {
		this.sundayCloseTime = sundayCloseTime;
	}
	
	/**
	 * Public Getter - gets the latitude
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Public Setter - sets the latitude
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Public Getter - gets the longitude
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Public Setter - sets the longitude
	 * @pparam longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Public Getter - gets the address
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Public Setter - sets the address
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Public Getter - gets the phone number
	 * @return phone number
	 */
	public int getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Public Setter - sets the phone number
	 * @param phone number
	 */
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Public Getter - gets the creation time
	 * @return creation time
	 */
	public long getCreatedAt() {
		return createdAt;
	}
	
	/**
	 * Public Setter - sets the creation time
	 * @param creation time
	 */
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * Public Getter - gets the update time
	 * @return update time
	 */
	public long getUpdatedAt() {
		return updatedAt;
	}
	
	/**
	 * Public Setter - sets the update time
	 * @param update time
	 */
	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}
		
}
