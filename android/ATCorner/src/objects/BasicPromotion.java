package objects;
import staticVariables.*;

public class BasicPromotion {
	String id;
	String title;
	double latitude;
	double longitude;
	String imageUrl;
	String shortDescription;
	
	/**
	 * Create an empty Basic Promotion object
	 */
	public BasicPromotion() {
	}

	/**
	 * Create a new Basic Promotion object 
	 * @param id
	 * @param title
	 * @param latitude
	 * @param longitude
	 * @param imageUrl
	 * @param shortDescription
	 */
	public BasicPromotion(String id, String title, double latitude,
			double longitude, String imageUrl, String shortDescription) {
		super();
		this.id = id;
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
		this.imageUrl = imageUrl;
		this.shortDescription = shortDescription;
	}
	
	/**
	 * Create a new Basic Promotion object 
	 * @param id
	 * @param title
	 * @param latitude
	 * @param longitude
	 * @param imageUrl
	 * @param shortDescription
	 */
	public BasicPromotion(String id, String title, String latitude,
			String longitude, String imageUrl, String shortDescription) {
		super();
		this.id = id;
		this.title = title;
		this.latitude = Double.valueOf(latitude);
		this.longitude = Double.valueOf(longitude);
		this.imageUrl = imageUrl;
		this.shortDescription = shortDescription;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasicPromotion [id=" + id + ", title=" + title + ", latitude="
				+ latitude + ", longitude=" + longitude + ", imageUrl="
				+ imageUrl + ", shortDescription=" + shortDescription + "]";
	}
	
	/**
	 * Get basic concatenated string of Basic Promotion
	 * @return parsable Basic Promotion String
	 */
	public String getBasicPromotionString() {
		return id + SpecialCharacters.delimiter + 
				title + SpecialCharacters.delimiter + 
				latitude + SpecialCharacters.delimiter + 
				longitude + SpecialCharacters.delimiter + 
				imageUrl + SpecialCharacters.delimiter + 
				shortDescription;
	}
}
