package com.sap.clientproject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
 
public class UserSettingActivity extends PreferenceActivity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.usersettings);
        loadSettings();
 
    }
    
    private void loadSettings() {
    	
    }
    
    private void saveSettings(String key, boolean value) {
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	Editor edit = sp.edit();
    	edit.putBoolean(key, value);
    	edit.commit();
    }
    
    private void savePreferences(String key, String value) {
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	Editor edit = sp.edit();
    	edit.putString(key, value);
    	edit.commit();
    }
}