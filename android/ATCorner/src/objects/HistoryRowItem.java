package objects;

public class HistoryRowItem {
	int imageId;
	String title;
	String description;
	String timeStamp;
	
	/**
	 * Create a new empty HistoryRowItem
	 */
	public HistoryRowItem() {
	}
	
	/**
	 * Construct a new HistoryRowItem
	 * @param imageId
	 * @param title
	 * @param description
	 * @param timeStamp
	 */
	public HistoryRowItem(int imageId, String title, String description,
			String timeStamp) {
		super();
		this.imageId = imageId;
		this.title = title;
		this.description = description;
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the imageId
	 */
	public int getImageId() {
		return imageId;
	}

	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(int imageId) {
		this.imageId = imageId;
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
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HistoryRowItem [imageId=" + imageId + ", title=" + title
				+ ", description=" + description + ", timeStamp=" + timeStamp
				+ "]";
	}
	
	
	
}
