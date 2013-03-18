package com.sap.clientproject;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import staticVariables.ServerVariables;
import classesAndManagers.AppHttpClient;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;

import android.view.View;
import android.widget.*;
import staticVariables.Cracker;
import staticVariables.RequestParameters;
import staticVariables.ResponseTags;
 
public class RegisterActivity extends Activity {
    
    public static final int MIN_USERNAME_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MIN_EMAIL_LENGTH = 12;

    public static final String INVALID_REGISTER_ID_ERROR = "Username must has at least " + MIN_USERNAME_LENGTH + " characters"; 
    public static final String INVALID_REGISTER_PASSWORD_ERROR = "Password must has at least " + MIN_PASSWORD_LENGTH + " characters";
    public static final String INVALID_REGISTER_EMAIL_ERROR = "Email must has at least " + MIN_EMAIL_LENGTH + " characters"; 
    public static final String GENERAL_REGISTER_ERROR = "The username has already been taken";
    public static final String UNKNOWN_ERROR = "Uknown error attemp registering. Please try again later";

    EditText registerUsername;
    EditText registerEmail;
    EditText registerPassword;
    Button registerButton;
    TextView loginScreen;
    TextView errorMessage;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        registerUsername = (EditText)findViewById(R.id.reg_name);
        registerEmail = (EditText)findViewById(R.id.reg_email);
        registerPassword = (EditText)findViewById(R.id.reg_password);
        registerButton = (Button)findViewById(R.id.btnRegister);
        loginScreen = (TextView)findViewById(R.id.link_to_login);
        errorMessage = (TextView)findViewById(R.id.registerErrorMessage);
        
        addLoginScreenClickListener();
        addRegisterButtonClickListener();
    }
    
    /**
     * Add a click listener for the register screen to transfer to the login page
     */
    private void addLoginScreenClickListener() {
    	loginScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
    
    /**
     * Add a click listener for the register button. When the register button is clicked, 
     * It first check if the username, password and email are of the right format. If not, then
     * it prompt the user to input them again. If the input it valid, it send the request
     * to server to check if the account exist on the server, and if so, promt user to create
     * a new user accountID. Upon successful registered, the accountID should be shared
     * at the sharedPreferences and intent to reload the main page is fired
     */
    private void addRegisterButtonClickListener(){
        //add a click listener for the login button
        registerButton.setOnClickListener (new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    if (registerUsername.getText() == null || registerUsername.getText().length() < MIN_USERNAME_LENGTH)
                        return INVALID_REGISTER_ID_ERROR;
                    if (registerPassword.getText() == null || registerPassword.getText().length() < MIN_PASSWORD_LENGTH)
                        return INVALID_REGISTER_PASSWORD_ERROR;
                    if (registerEmail.getText() == null || registerEmail.getText().length() < MIN_EMAIL_LENGTH)
                        return INVALID_REGISTER_EMAIL_ERROR;
                    try {
                        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                        postParameters.add(new BasicNameValuePair(RequestParameters.REGISTER_USER_ID, 
                                registerUsername.getText().toString()));
                        postParameters.add(new BasicNameValuePair(RequestParameters.REGISTER_USER_PASSWORD, 
                                Cracker.generatingHashValue(registerPassword.getText().toString())));
                        postParameters.add(new BasicNameValuePair(RequestParameters.REGISTER_USER_EMAIL, 
                                registerEmail.getText().toString()));
                        postParameters.add(new BasicNameValuePair(RequestParameters.REGISTER_DEVICE_ID, 
                                getDeviceID()));
                        return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return UNKNOWN_ERROR;
                }

                @Override
                protected void onPostExecute(String result) {
                    if (result.equals(INVALID_REGISTER_ID_ERROR) || result.equals(INVALID_REGISTER_PASSWORD_ERROR) ||
                            (result.equals(INVALID_REGISTER_EMAIL_ERROR)) || (result.equals(UNKNOWN_ERROR))) {
                        errorMessage.setText(result);
                        registerUsername.setText("");
                        registerPassword.setText("");
                        registerEmail.setText("");
                    } else if (result.contains(ResponseTags.REGISTER_ERROR_TAG)) {
                        errorMessage.setText(GENERAL_REGISTER_ERROR);
                        registerUsername.setText("");
                        registerPassword.setText("");
                        registerEmail.setText("");
                    } else if (result.contains(ResponseTags.REGISTER_SUCCESS_TAG)){
                        //put the username to the shared preferences
                        SharedPreferences settings = getSharedPreferences(ClientMainActivity.UNIQUE_ID, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(ClientMainActivity.ACCOUNT_ID, registerUsername.getText().toString());
                        editor.commit();
                        //go back to the main activity
                        Intent i = new Intent(getApplicationContext(), ClientMainActivity.class);
                        startActivity(i);
                    }
                }
            }.execute();
            }
        });
    }
    
   
    
    /**
     * Get the device unique ID
     * @return the device ID
     */
    private String getDeviceID() {
    	return Secure.getString(getBaseContext().getContentResolver(),
                Secure.ANDROID_ID); 
    }
}