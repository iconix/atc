package com.sapenguins.thecornerapp.objects;

public class EventRowItem {
	String title;
	String description;
	String distance;
	String imageUrl;
	
	/**
	 * Empty constructor
	 */
	public EventRowItem() {
		super();
	}

	/**
	 * Constructor from fields
	 * @param title
	 * @param description
	 * @param distance
	 * @param imageUrl
	 */
	public EventRowItem(String title, String description,
			String distance, String imageUrl) {
		super();
		this.title = title;
		this.description = description;
		this.distance = distance;
		this.imageUrl = imageUrl;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EventRowItem [title=" + title + ", description="
				+ description + ", distance=" + distance + ", imageUrl="
				+ imageUrl + "]";
	}
	
	
}
