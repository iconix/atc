/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classesAndManagers;

/**
 *
 * Unlike the Account classes and GPSCoordinate class. We allow the user with the same account
 * but have multiple devices to display all the pins that they made on all of their device. 
 * We do this because pin is just a location and not a path, so there is no path-conflict.
 * @author minhthaonguyen
 */
public class PinLocation {
    String accountID;
    long time;
    double longitude;
    double latitude;
    String title;
    String description;
    
    /**
     * Default constructor. Create a new PinLocation object
     * @param accountID
     * @param time
     * @param longitude
     * @param lagitude
     * @param title
     * @param description 
     */
    public PinLocation(String accountID, long time, double longitude, double latitude, String title, String description) {
        this.accountID = accountID;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.title = title;
        this.description = description;
    }
    
    
    /**
     * Default alternate constructor. Create a new PinLocation object where all values are string
     * @param accountID
     * @param time
     * @param longitude
     * @param lagitude
     * @param title
     * @param description 
     */
    public PinLocation(String accountID, String time, String longitude, String latitude, String title, String description) {
        this.accountID = accountID;
        this.time = Long.valueOf(time);
        this.longitude = Double.valueOf(longitude);
        this.latitude = Double.valueOf(latitude);
        this.title = title;
        this.description = description;
    }
    
    /**
     * Get the accountId of the PinLocation object
     * @return accountID
     */
    public String getAccountID() {
        return accountID;
    }
    
    /**
     * Set the accountID of the PinLocation object
     * @param accountID 
     */
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    } 
    
    /**
     * Get the time of the PinLocation Object
     * @return time
     */
    public long getTime() {
        return time;
    }
    
    /**
     * Set the time of the PinLocationObject
     * @param time 
     */
    public void setTime(long time) {
        this.time = time;
    }
    
    /**
     * Set the time of the PinLocationObject
     * @param time 
     */
    public void setTime(String time) {
        this.time = Long.valueOf(time);
    }
    
    /**
     * Set the longitude of the PinLocation object
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }
    
    /**
     * Get the longitude of the PinLocation object
     * @param longitude 
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    /**
     * Get the longitude of the PinLocation object
     * @param longitude 
     */
    public void setLongitude(String longitude) {
        this.longitude = Double.valueOf(longitude);
    }
    
    /**
     * Get the latitude of the PinLocation object
     * @return 
     */
    public double getLatitude() {
        return latitude;
    }
    
    /**
     * Set the latitude of the PinLocation object
     * @param latitude 
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    /**
     * Set the latitude of the PinLocation object
     * @param latitude 
     */
    public void setLatitude(String latitude) {
        this.latitude = Double.valueOf(latitude);
    }
    
    /**
     * Get the title of the PinLocation object
     * @return title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Set the title of the PinLocation object
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get the description of the PinLocation object
     * @return description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set the description of the PinLocation object
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get the string representation of PinLocation object
     * @return string representation of PinLocation
     */
    @Override
    public String toString() {
        return "accountID: " + accountID +
                "\t time: " + time +
                "\t longitude: " + longitude + 
                "\t latitude: " + latitude +
                "\t title: " + title +
                "\t description: " + description;
    }
}
