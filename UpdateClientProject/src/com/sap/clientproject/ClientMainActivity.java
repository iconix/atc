package com.sap.clientproject;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import static android.content.Context.LOCATION_SERVICE;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import staticVariables.*;
import supports.*;

public class ClientMainActivity extends Activity{
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_client_main);
        checkGpsService();
        checkDeviceLoggedIn();
        startCoordinateService();
        goToLastSavedActivity();
    }

     /**
     * Detect whether the device have access to the GPS service. If not then redirect the 
     * user to turn it on
     */
    private void checkGpsService() {
        LocationManager service = (LocationManager)getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabledGPS) {
            Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
            Intent turnOnGPSSignalIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(turnOnGPSSignalIntent);
        }
    }
    
    /**
     * Check if the device is logged in. If not go to login page
     */
    private void checkDeviceLoggedIn() {
        if (getAccountID().equals("")) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            finish();
            startActivity(i);
        }
    }
    
    /**
     * Start the coordinate service. Depend on the user's choice, the coordinate
     * will either be stored in their local device or through our server
     */
    private void startCoordinateService() {
        if (getCoordinateSaveType().equals(PreferenceValue.COORDINATE_SERVICE_DEVICE)) 
            startInputCoordinateService();
        else {
            startSendingCoordinateService();
        }
    }
    
    /**
     * Go to the last saved activity. If none is found, then go to the main menu
     */
    private void goToLastSavedActivity() {
        String activity = getLastSavedActivity();
        Intent activityIntent = null;
        if (activity.equals("") || activity.equals(PreferenceValue.VIEW_MAIN_MENU)) 
            activityIntent = new Intent(this, MainMenu.class);
        //TODO add more view in
        else activityIntent = new Intent(this, MainMenu.class);
        finish();
        startActivity(activityIntent);
    }
	
    /**
     * Detect whether the service that sending the coordinate is to the server is running.
     * If it has not started, then starts it. 
     */
    private void startSendingCoordinateService() {
         ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
         for (RunningServiceInfo srv : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (Services.SEND_COORDINATE_SERVICE.equals(srv.service.getClassName())) return;
         }
         startService(new Intent(ClientMainActivity.this, SendCoordinateService.class));
    }
    
    /**
     * @TODO
     * Detect whether the service that input the coordinate to the device is running.
     * If it has not started, then starts it
     */
    private void startInputCoordinateService() {
        //TODO implement the service
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo srv : manager.getRunningServices(Integer.MAX_VALUE)) {
           if (Services.INPUT_COORDINATE_SERVICE.equals(srv.service.getClassName())) return;
        }
        //startService(new Intent(ClientMainActivity.this, InputCoordinateService.class));
    }

    /**
     * Get the account ID, which suppose to be unique for each person using the application.
     * @return the account ID
     */
    private String getAccountID() {
        return getPreferenceValue(SharedPreference.PREFERENCE, SharedPreference.ACCOUNT_ID);
    }
    
    /**
     * Get the last saved view so we can immediately go back there when start program
     * @return last saved view
     */
    private String getLastSavedActivity() {
        return getPreferenceValue(SharedPreference.PREFERENCE, SharedPreference.LAST_SAVED_VIEW);
    }
    
    /**
     * get the coordinate saved type. User can choose to save the information on
     * our DB or directly on their phone.
     */
    private String getCoordinateSaveType() {
        return getPreferenceValue(SharedPreference.PREFERENCE, SharedPreference.COORDINATE_SERVICE);
    }
    
    /**
     * Get the preference value of a given key stored in a given preference list
     * @param preference
     * @param key
     * @return the string value of the key
     */
    private String getPreferenceValue(String preference, String key) {
        SharedPreferences settings = getSharedPreferences(preference, 0);
        return settings.getString(key, "");
    }
}
