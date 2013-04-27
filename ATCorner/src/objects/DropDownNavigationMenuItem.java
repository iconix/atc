package objects;

public class DropDownNavigationMenuItem {
	int imageId;
	String title;
	
	/**
	 * Empty constructor
	 */
	public DropDownNavigationMenuItem() {
	}

	/**
	 * Constructor from fields
	 * @param imageId
	 * @param title
	 */
	public DropDownNavigationMenuItem(int imageId, String title) {
		this.imageId = imageId;
		this.title = title;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DropDownNavigationMenuItem [imageId=" + imageId + ", title="
				+ title + "]";
	}
	
	
}
