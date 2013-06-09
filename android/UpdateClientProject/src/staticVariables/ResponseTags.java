/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package staticVariables;

/**
 * Tag to tell whether the request is success. Tag usually send from the server device
 * @author minhthaonguyen
 */
public class ResponseTags {
    //Error tag or success tag to tell if the request is a valid query in DB
    public static final String LOGIN_ERROR_TAG = "Error";
    public static final String LOGIN_SUCCESS_TAG = "Logged in";
    
    public static final String REGISTER_ERROR_TAG = "Error";
    public static final String REGISTER_SUCCESS_TAG = "Registered";
}
