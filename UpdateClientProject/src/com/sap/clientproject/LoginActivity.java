package com.sap.clientproject;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import classesAndManagers.AppHttpClient;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import staticVariables.*;
 
public class LoginActivity extends Activity {

    public static final int MIN_USERNAME_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 8;

    //errors
    public static final String INVALID_LOGIN_ID_ERROR = "Username must has at least " + MIN_USERNAME_LENGTH + " characters"; 
    public static final String INVALID_LOGIN_PASSWORD_ERROR = "Password must has at least " + MIN_PASSWORD_LENGTH + " characters"; 
    public static final String GENERAL_LOGIN_ERROR = "Invalid input of username or password";
    public static final String UNKNOWN_ERROR = "Unknown error attemp login. Please try again later";

    EditText loginID;
    EditText loginPassword;
    Button loginButton;
    TextView registerScreen;
    TextView errorMessage;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
        loginButton = (Button)findViewById(R.id.btnLogin);
        loginID = (EditText)findViewById(R.id.loginUserID);
        loginPassword = (EditText)findViewById(R.id.loginUserPassword);
        registerScreen = (TextView)findViewById(R.id.link_to_register);
        errorMessage = (TextView)findViewById(R.id.loginErrorMessage);
 
        addRegisterScreenClickListener();
        addLoginButtonClickListener();
    }
    
    /**
     * Add a click listener for the login screen to transfer to the register page
     */
    private void addRegisterScreenClickListener() {
    	registerScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    
    /**
     * Add a click listener for the login button. When the login button is clicked, 
     * It first check if the username and password are of the right format. If not, then
     * it prompt the user to input them again. If the input it valid, it send the request
     * to server to check if the account exist on the server, and if so, if this device is 
     * new to that account. Upon getting successful login, the accountID should be shared
     * at the sharedPreferences and intent to reload the main page is fired
     */
    private void addLoginButtonClickListener(){
        //add a click listener for the login button
        loginButton.setOnClickListener (new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        if (loginID.getText() == null || loginID.getText().length() < MIN_USERNAME_LENGTH)
                            return INVALID_LOGIN_ID_ERROR;
                        if (loginPassword.getText() == null || loginPassword.getText().length() < MIN_PASSWORD_LENGTH)
                            return INVALID_LOGIN_PASSWORD_ERROR;
                        try {
                            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                            postParameters.add(new BasicNameValuePair(RequestParameters.LOGIN_ACCOUNT_ID, loginID.getText().toString()));
                            postParameters.add(new BasicNameValuePair(RequestParameters.LOGIN_ACCOUNT_PASSWORD, 
                                    Cracker.generatingHashValue(loginPassword.getText().toString())));
                            postParameters.add(new BasicNameValuePair(RequestParameters.LOGIN_DEVICE_ID, getDeviceID()));
                            return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return UNKNOWN_ERROR;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        if (result.equals(INVALID_LOGIN_ID_ERROR) || result.equals(INVALID_LOGIN_PASSWORD_ERROR)
                                || (result.equals(UNKNOWN_ERROR))) {
                            errorMessage.setText(result);
                            loginID.setText("");
                            loginPassword.setText("");
                        } else if (result.contains(ResponseTags.LOGIN_ERROR_TAG)){
                            errorMessage.setText(GENERAL_LOGIN_ERROR);
                            loginID.setText("");
                            loginPassword.setText("");
                        } else if (result.contains(ResponseTags.LOGIN_SUCCESS_TAG)){
                            //put the username to the shared preferences
                            SharedPreferences settings = getSharedPreferences(ClientMainActivity.UNIQUE_ID, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString(ClientMainActivity.ACCOUNT_ID, loginID.getText().toString());
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
