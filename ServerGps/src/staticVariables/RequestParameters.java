/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package staticVariables;

/**
 *
 * @author minhthaonguyen
 */
public class RequestParameters {
    
    //input request for the login 
    public static final String LOGIN_ACCOUNT_ID = "loginAccountID";
    public static final String LOGIN_ACCOUNT_PASSWORD = "loginAccountPassword";
    public static final String LOGIN_DEVICE_ID = "loginDeviceID";
    
    
    //input request for register
    public static final String REGISTER_ACCOUNT_ID = "registerAccountID";
    public static final String REGISTER_ACCOUNT_PASSWORD = "registerAccountPassword";
    public static final String REGISTER_ACCOUNT_EMAIL = "registerAccountEmail";
    public static final String REGISTER_DEVICE_ID = "registerDeviceID";
    
    //input request for the coordinate
    public static final String COORDINATE_INPUT_ACCOUNT_ID = "coordinateInputAccountID";
    public static final String COORDINATE_INPUT_DEVICE_ID = "coordinateInputDeviceID";
    public static final String COORDINATE_INPUT_TIME = "coordinateInputTime";
    public static final String COORDINATE_INPUT_LONGITUDE = "coordinateInputLongitude";
    public static final String COORDINATE_INPUT_LATITUDE = "coordinateInputLatitude";
    
    //input request for the pin
    public static final String PIN_INPUT_ACCOUNT_ID = "pinInputAccountID";
    public static final String PIN_INPUT_TIME = "pinInputTime";
    public static final String PIN_INPUT_LONGITUDE = "pinInputLongitude";
    public static final String PIN_INPUT_LATITUDE = "pinInputLatitude";
    public static final String PIN_INPUT_TITLE = "pinInputTitle";
    public static final String PIN_INPUT_DESCRIPTION = "pinInputDescription";
    
    
    //Request of the coordinate from DB
    public static final String COORDINATE_REQUEST_ACCOUNT_ID = "coordinateRequestAccountID";
    public static final String COORDINATE_REQUEST_DEVICE_ID = "coordinateRequestAccountID";
    public static final String COORDINATE_REQUEST_LOWER_LONGITUDE = "coordinateRequestLowerLongitude";
    public static final String COORDINATE_REQUEST_HIGHER_LONGITUDE = "coordinateRequestHigherLongitude";
    public static final String COORDINATE_REQUEST_LOWER_LATITUDE = "coordinateRequestLowerLatitude";
    public static final String COORDINATE_REQUEST_HIGHER_LATITUDE = "coordinateRequestHigherLatitude";
    public static final String COORDINATE_REQUEST_LOWER_TIME = "coordinateRequestLowerTime";
    public static final String COORDINATE_REQUEST_HIGHER_TIME = "coordinateRequestHigherTime";
    
    
    //Request of the pin from DB
    public static final String PIN_REQUEST_ACCOUNT_ID = "pinRequestAccountID";
    public static final String PIN_REQUEST_LOWER_LONGITUDE = "pinRequestLowerLongitude";
    public static final String PIN_REQUEST_HIGHER_LONGITUDE = "pinRequestHigherLongitude";
    public static final String PIN_REQUEST_LOWER_LATITUDE = "pinRequestLowerLatitude";
    public static final String PIN_REQUEST_HIGHER_LATITUDE = "pinRequestHigherLatitude";
    public static final String PIN_REQUEST_LOWER_TIME = "pinRequestLowerTime";
    public static final String PIN_REQUEST_HIGHER_TIME = "pinRequestHigherTime";
    
}
