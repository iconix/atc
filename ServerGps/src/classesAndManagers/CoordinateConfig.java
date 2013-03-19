/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classesAndManagers;

/**
 *
 * @author minhthaonguyen
 */
public class CoordinateConfig {
    String accountID;
    String deviceID;
    double lowerLongitude;
    double higherLongitude;
    double lowerLatitude;
    double higherLatitude;
    long lowerTime;
    long higherTime;
    
    /**
     * Construct a new instant of CoordinateConfig. This config is passed to the server
     * to execute an accurate query
     * @param accountID
     * @param deviceID
     * @param lowerLongitude
     * @param higherLongitude
     * @param lowerLatitude
     * @param higherLatitude
     * @param lowerTime
     * @param higherTime 
     */
    public CoordinateConfig(String accountID, String deviceID, double lowerLongitude, double higherLongitude, 
            double lowerLatitude, double higherLatitude, long lowerTime, long higherTime) {
        this.accountID = accountID;
        this.deviceID = deviceID;
        this.lowerLatitude = lowerLatitude;
        this.higherLatitude = higherLatitude;
        this.lowerLongitude = lowerLongitude;
        this.higherLongitude = higherLongitude;
        this.lowerTime = lowerTime;
        this.higherTime = higherTime;
    }
    
    /**
     * Alternate constructor applied when all the inputs are strings
     * @param accountID
     * @param deviceID
     * @param lowerLongitude
     * @param higherLongitude
     * @param lowerLatitude
     * @param higherLatitude
     * @param lowerTime
     * @param higherTime 
     */
    public CoordinateConfig(String accountID, String deviceID, String lowerLongitude, String higherLongitude, 
            String lowerLatitude, String higherLatitude, String lowerTime, String higherTime) {
        this.accountID = accountID;
        this.deviceID = deviceID;
        this.lowerLatitude = Double.valueOf(lowerLatitude);
        this.higherLatitude = Double.valueOf(higherLatitude);
        this.lowerLongitude = Double.valueOf(lowerLongitude);
        this.higherLongitude = Double.valueOf(higherLongitude);
        this.lowerTime = Long.valueOf(lowerTime);
        this.higherTime = Long.valueOf(higherTime);
    }
    
    /**
     * Get the accountID of the coordinateConfig object
     * @return accountID
     */
    public String getAccountID() {
        return accountID;
    }
    
    /**
     * Set the accountID of the coordinateConfig Object
     * @param accountID
     */
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
    
     /**
     * Get the deviceID of the coordinateConfig object
     * @return deviceID
     */
    public String getDeviceID() {
        return deviceID;
    }
    
    /**
     * Set the deviceID of the coordinateConfig Object
     * @param deviceID
     */
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
    
    /**
     * Get the lowerLongitude of the coordinateConfig object
     * @return lowerLongitude
     */
    public double getLowerLongitude() {
        return lowerLongitude;
    }
    
    /**
     * Set the lowerLongitude of the coordinateConfig Object
     * @param lowerLongitude
     */
    public void setLowerLongitude(double lowerLongitude) {
        this.lowerLongitude = lowerLongitude;
    }
    
    /**
     * Set the lowerLongitude of the coordinateConfig Object
     * @param lowerLongitude
     */
    public void setLowerLongitude(String lowerLongitude) {
        this.lowerLongitude = Double.valueOf(lowerLongitude);
    }
    
    /**
     * Get the higherLongitude of the coordinateConfig object
     * @return higherLongitude
     */
    public double getHigherLongitude() {
        return higherLongitude;
    }
    
    /**
     * Set the higherLongitude of the coordinateConfig Object
     * @param higherLongitude
     */
    public void setHigherLongitude(double higherLongitude) {
        this.higherLongitude = higherLongitude;
    }
    
    /**
     * Set the higherLongitude of the coordinateConfig Object
     * @param higherLongitude
     */
    public void setHigherLongitude(String higherLongitude) {
        this.higherLongitude = Double.valueOf(higherLongitude);
    }
    
    
    /**
     * Get the lowerLatitude of the coordinateConfig object
     * @return lowerLatitude
     */
    public double getLowerLatitude() {
        return lowerLatitude;
    }
    
    /**
     * Set the lowerLatitude of the coordinateConfig Object
     * @param lowerLatitude
     */
    public void setLowerLatitude(double lowerLatitude) {
        this.lowerLatitude = lowerLatitude;
    }
    
    /**
     * Set the lowerLatitude of the coordinateConfig Object
     * @param lowewLatitude
     */
    public void setLowerLatitude(String lowerLatitude) {
        this.lowerLatitude = Double.valueOf(lowerLatitude);
    }
    
    /**
     * Get the higherLatitude of the coordinateConfig object
     * @return higherLatitude
     */
    public double getHigherLatitude() {
        return higherLatitude;
    }
    
    /**
     * Set the higherLatitude of the coordinateConfig Object
     * @param higherLatitude
     */
    public void setHigherLatitude(double higherLatitude) {
        this.higherLatitude = higherLatitude;
    }
    
    /**
     * Set the higherLatitude of the coordinateConfig Object
     * @param higherLatitude
     */
    public void setHigherLatitude(String higherLatitude) {
        this.higherLatitude = Double.valueOf(higherLatitude);
    }
    
    /**
     * Get the lowerTime of the coordinateConfig object
     * @return lowerTime
     */
    public long getLowerTime() {
        return lowerTime;
    }
    
    /**
     * Set the lowerTime of the coordinateConfig Object
     * @param lowerTime
     */
    public void setLowerTime(long lowerTime) {
        this.lowerTime = lowerTime;
    }
    
    /**
     * Set the lowerTime of the coordinateConfig Object
     * @param lowerTime
     */
    public void setLowerTime(String lowerTime) {
        this.lowerTime = Long.valueOf(lowerTime);
    }
    
    /**
     * Get the higherTime of the coordinateConfig object
     * @return higherTime
     */
    public long getHigherTime() {
        return higherTime;
    }
    
    /**
     * Set the HigherTime of the coordinateConfig Object
     * @param lowerTime
     */
    public void setHigherTime(long higherTime) {
        this.higherTime = higherTime;
    }
    
    /**
     * Set the higherTime of the coordinateConfig Object
     * @param higherTime
     */
    public void setHigherTime(String higherTime) {
        this.higherTime = Long.valueOf(higherTime);
    }
    
    /**
     * Write the CoordinateConfig in String format
     * @return String representation of CoordinateConfig
     */
    @Override
    public String toString() {
        return "accountID: " + accountID + 
                "\t deviceID: " + deviceID +
                "\t lowerLongitude: " + lowerLongitude +
                "\t higherLongitude: " + higherLongitude +
                "\t lowerLatitude: " + lowerLatitude +
                "\t higherLatitude: " + higherLatitude +
                "\t lowerTime: " + lowerTime +
                "\t higherTime: " + higherTime;
    }
}
