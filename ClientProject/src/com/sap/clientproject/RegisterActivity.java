package com.sap.clientproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.*;
 
public class RegisterActivity extends Activity {
    
	EditText registerUsername;
	EditText registerEmail;
	EditText registerPassword;
	Button registerButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        registerUsername = (EditText)findViewById(R.id.reg_name);
        registerUsername = (EditText)findViewById(R.id.reg_email);
        registerPassword = (EditText)findViewById(R.id.reg_password);
        registerButton = (Button)findViewById(R.id.btnRegister);
        
        //putting the account ID into the preferences setting
  		SharedPreferences settings = getSharedPreferences(ClientMainActivity.UNIQUE_ID, 0);
  		SharedPreferences.Editor editor = settings.edit();
  		//TODO give the device the new ID or give it existing ID
  		editor.putString(ClientMainActivity.ACCOUNT_ID, "");
  		editor.commit();
    }
}