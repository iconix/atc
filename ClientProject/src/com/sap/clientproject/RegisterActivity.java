package com.sap.clientproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        //putting the account ID into the preferences setting
  		SharedPreferences settings = getSharedPreferences(ClientMainActivity.UNIQUE_ID, 0);
  		SharedPreferences.Editor editor = settings.edit();
  		//TODO give the device the new ID or give it existing ID
  		editor.putString(ClientMainActivity.ACCOUNT_ID, "");
  		editor.commit();
    }
}