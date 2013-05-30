package com.sapenguins.thecornerapp.objects;

public class AdRowItem {
	String title;
	String description;
	String distance;
	String imageUrl;
	String beginTime;
	String endTime;
	
	/**
	 * Empty constructor
	 */
	public AdRowItem() {
		super();
	}

	/**
	 * Constructor from fields
	 * @param title
	 * @param description
	 * @param distance
	 * @param imageUrl
	 */
	public AdRowItem(String title, String description,
			String distance, String imageUrl, String beginTime, String endTime) {
		super();
		this.title = title;
		this.description = description;
		this.distance = distance;
		this.imageUrl = imageUrl;
		this.beginTime = beginTime;
		this.endTime = endTime;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
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
	 * @return the beginTime
	 */
	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdRowItem [title=" + title + ", description=" + description
				+ ", distance=" + distance + ", imageUrl=" + imageUrl
				+ ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}
}
