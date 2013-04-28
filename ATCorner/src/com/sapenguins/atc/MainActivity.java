package com.sapenguins.atc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import staticVariables.*;
import supports.TimeFrame;

public class MainActivity extends FragmentActivity {
	
	long toTime;
	long timeGap;
	long fromTime;
	
	public static final String RECORD_GPS_LOCATION_SERVICE = "com.sapenguins.atc.RecordGpsLocationsService";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (isGooglePlay()) {
        	setContentView(R.layout.activity_main);
        	checkGpsService();
        	recordingGpsLocationService();
        	goToLastSavedActivity();
        	updateTimePreference();
        }
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
     * Check if the google play is available. The google play service is required to run map
     * @return whether the play is available
     */
    private boolean isGooglePlay() {
    	int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    	if (status == ConnectionResult.SUCCESS) return true;
    	else {
    		((Dialog) GooglePlayServicesUtil.getErrorDialog(status, this, 10)).show();
    		return false;
    	}
    }
    
    /**
     * Detect whether the service that keep record of visited location is running.
     * If it has not started, then starts it. 
     */
    private void recordingGpsLocationService() {
         ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
         for (RunningServiceInfo srv : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (RECORD_GPS_LOCATION_SERVICE.equals(srv.service.getClassName())) return;
         }
         startService(new Intent(MainActivity.this, RecordGpsLocationsService.class));
    }
    
    /**
     * Go to the last saved activity. If none is found, then go to the main menu
     */
    private void goToLastSavedActivity() {
        String activity = getLastSavedActivity();
        Intent activityIntent = null;
        if (activity.equals(PreferenceValue.VIEW_SINGLE_MAP))
        	activityIntent = new Intent(this, SingleMapViewActivity.class);
        else if (activity.equals(PreferenceValue.VIEW_MAP_AND_HISTORY))
        	activityIntent = new Intent(this, MapAndHistoryActivity.class);
        else activityIntent = new Intent(this, MainMenu.class);
        finish();
        startActivity(activityIntent);
    }
    
    /**
     * Get the last saved view so we can immediately go back there when start program
     * @return last saved view
     */
    private String getLastSavedActivity() {
        return getPreferenceValue(SharedPreference.PREFERENCE, SharedPreference.LAST_SAVED_VIEW);
    }
    
    /**
     * Update the time preference setting
     */
    private void updateTimePreference() {
    	setTimeGap();
    	setToTime();
    	setFromTime();
    }
    
    /**
     * Get the preference for the time gap
     * @return time gap
     */
    private void setTimeGap() {
    	timeGap = getPreferenceTimeValue(SharedPreference.PREFERENCE, SharedPreference.TIME_GAP_PREFERENCE, 240000); //24hr
    	updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.TIME_GAP_PREFERENCE, timeGap);
    }
    
    /**
     * Set the 'to time' to the current time 
     */
    private void setToTime() {
    	toTime = Long.valueOf(getTimeStamp());
    	updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.TIME_TO_PREFERENCE, toTime);
    }
    
    /**
     * Set the 'from time' to be the time that is 'time gap' away from the current time
     */
    private void setFromTime() {
    	fromTime = TimeFrame.computePriorTime(toTime, timeGap);
    	updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.TIME_FROM_PREFERENCE, fromTime);
    }
    
    /**
     * Get the current time when the new coordinate was taken.
     * The current time is recorded as UTC
     * @return the current time represent in the format yyyyMMdd_HHmmss
     */
    private String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        return sdf.format(new Date());
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
    
    /**
     * Get the preference value of a given key stored in a given preference list
     * @param preference
     * @param key
     * @return the time value of the key
     */
    private long getPreferenceTimeValue(String preference, String key, long defaultTime) {
        SharedPreferences settings = getSharedPreferences(preference, 0);
        return settings.getLong(key, defaultTime);
    }
    
    /**
     * Modify the information stored in the shared preferences
     * @param the ID associate with the preference
     * @param the key string of a field in the preference
     * @param the value of above key
     */
    private void updateSystemPreferences(String preferenceID, String key, long value) {
        SharedPreferences settings = getSharedPreferences(preferenceID, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }
}
