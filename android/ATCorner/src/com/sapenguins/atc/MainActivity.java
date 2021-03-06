package com.sapenguins.atc;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import dataSources.SQLTablesHelper;

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

public class MainActivity extends FragmentActivity {
	
	long toTime;
	long timeGap;
	long fromTime;
	
	public static final String RECORD_GPS_LOCATION_SERVICE = "com.sapenguins.atc.RecordGpsLocationsService";
    public static final String RECORD_PHOTO_SERVICE = "com.sapenguins.atc.PhotoRecordingService";
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (isGooglePlay()) {
        	setContentView(R.layout.activity_main);
        	checkGpsService();
        	recordingPhotoService();
        	recordingGpsLocationService();
        	goToLastSavedActivity();
        }
        
        //SQLTablesHelper tableHelper = new SQLTablesHelper(this);  //need for when table change
        //tableHelper.onCreate(tableHelper.getReadableDatabase());
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
     * Check if the photo recording service is turned on. 
     */
    private void recordingPhotoService() {
    	 ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
         for (RunningServiceInfo srv : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (RECORD_PHOTO_SERVICE.equals(srv.service.getClassName())) return;
         }
         startService(new Intent(MainActivity.this, PhotoRecordingService.class));
    }
    
    /**
     * Go to the last saved activity. If none is found, then go to the main menu
     */
    private void goToLastSavedActivity() {
    	//set the time period option back to 1 day
    	//and force reset the begin time
    	setTimePeriodOption(PreferenceValue.SPINNER_ONE_DAY);
    	setBeginTime(0);
    	
        String activity = getLastSavedActivity();
        Intent activityIntent = null;
        if (activity.equals(PreferenceValue.VIEW_SINGLE_MAP))
        	activityIntent = new Intent(this, SingleMapViewActivity.class);
        else if (activity.equals(PreferenceValue.VIEW_MAP_AND_HISTORY))
        	activityIntent = new Intent(this, MapAndHistoryActivity.class);
        else if (activity.equals(PreferenceValue.VIEW_DETAIL_AND_HISTORY))
        	activityIntent = new Intent(this, DetailAndHistoryActivity.class);
        else if (activity.equals(PreferenceValue.VIEW_DETAIL_AND_PROMOTION))
        	activityIntent = new Intent(this, PromotionListAndDetailActivity.class);
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
     * Set the current time period option
     * @param time period option
     */
    private void setTimePeriodOption(int periodOption) {
    	updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.TIME_PERIOD_PREFERENCE, periodOption);
    }
    
    /**
     * Set the begin time
     * @param begin time
     */
    private void setBeginTime(long beginTime) {
    	updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.TIME_BEGIN_PREFERENCE, beginTime);
    }
    
    /**
     * Modify the information stored in the shared preferences
     * @param the ID associate with the preference
     * @param the key string of a field in the preference
     * @param the value of above key
     */
    private void updateSystemPreferences(String preferenceID, String key, int value) {
        SharedPreferences settings = getSharedPreferences(preferenceID, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
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
