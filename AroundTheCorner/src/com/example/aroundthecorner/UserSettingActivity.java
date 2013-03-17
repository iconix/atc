package com.example.aroundthecorner;

import android.os.Bundle;
import android.preference.PreferenceActivity;
 
public class UserSettingActivity extends PreferenceActivity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.usersettings);
 
    }
}