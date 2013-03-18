/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classesAndManagers;

/**
 *
 * @author minhthaonguyen
 */
public class PinConfig {
    String accountID;
    double lowerLongitude;
    double higherLongitude;
    double lowerLatitude;
    double higherLatitude;
    long lowerTime;
    long higherTime;
    
    /**
     * Construct a new instant of PinConfig. This config is passed to the server
     * to execute an accurate query
     * @param accountID
     * @param lowerLongitude
     * @param higherLongitude
     * @param lowerLatitude
     * @param higherLatitude
     * @param lowerTime
     * @param higherTime 
     */
    public PinConfig(String accountID, double lowerLongitude, double higherLongitude, 
            double lowerLatitude, double higherLatitude, long lowerTime, long higherTime) {
        this.accountID = accountID;
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
     * @param lowerLongitude
     * @param higherLongitude
     * @param lowerLatitude
     * @param higherLatitude
     * @param lowerTime
     * @param higherTime 
     */
    public PinConfig(String accountID, String lowerLongitude, String higherLongitude, 
            String lowerLatitude, String higherLatitude, String lowerTime, String higherTime) {
        this.accountID = accountID;
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
     * Set the higherLongitude of the coordinateConfig Object
     * @param higherLongitude
     */
    public void setLowerTime(String lowerTime) {
        this.lowerTime = Long.valueOf(lowerTime);
    }
}
