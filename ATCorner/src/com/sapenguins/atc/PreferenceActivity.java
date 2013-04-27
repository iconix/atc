package com.sapenguins.atc;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

public class PreferenceActivity extends SherlockPreferenceActivity {
	 @Override
    protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	 }
}
