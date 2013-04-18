package com.sapenguins.atc;

import staticVariables.PreferenceValue;
import staticVariables.SharedPreference;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;


public class MainMenu extends Activity{
	ImageView mapView;
    ImageView dealsView;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main_menu);
        setCurrentView(PreferenceValue.VIEW_MAIN_MENU);
        
        mapView = (ImageView)findViewById(R.id.mapIcon);
        mapView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SingleMapViewActivity.class);
                startActivity(i);
            }
        });

        
        dealsView = (ImageView)findViewById(R.id.ads);
        dealsView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), MapAndHistoryActivity.class);
                startActivity(i);
            }
        }); 
        /*   
        dealsView = (ImageView)findViewById(R.id.ads);
        dealsView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), AdsSingleViewActivity.class);
                startActivity(i);
            }
        }); */
        
    }
    
    /**
     * Set the current view
     * @param currentView
     */
    private void setCurrentView(String currentView) {
    	updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.LAST_SAVED_VIEW, currentView);
    }
    
    /**
     * Modify the information stored in the shared preferences
     * @param the ID associate with the preference
     * @param the key string of a field in the preference
     * @param the value of above key
     */
    private void updateSystemPreferences(String preferenceID, String key, String value) {
        SharedPreferences settings = getSharedPreferences(preferenceID, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
