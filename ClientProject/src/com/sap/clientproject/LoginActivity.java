package com.sap.clientproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class LoginActivity extends Activity {
	
	EditText loginID;
	EditText loginPassword;
	Button loginButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
        loginButton = (Button)findViewById(R.id.btnLogin);
        loginID = (EditText)findViewById(R.id.loginUserID);
        loginPassword = (EditText)findViewById(R.id.loginUserPassword);
 
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
 
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
        
        loginButton.setOnClickListener (new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO send ID and password to confirm
				
			}
        });
    }
}
