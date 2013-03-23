package com.sap.clientproject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import classesAndManagers.*;
import staticVariables.*;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;

public class SendCoordinateService extends Service implements LocationListener{

    private static final int DEFAULT_UPDATE_TIME_INTERVAL = 60*1000; //updates is made every minutes
    private static final int DEFAULT_UPDATE_DISTANCE = 50; //in meter

    private String accountID;
    private String deviceID;
    private LocationManager locationManager;
    Criteria criteria;
    int updateInterval;
    int updateDistance;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        deviceID = getDeviceID();
        accountID = getAccountID();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        updateInterval = getUpdateTimeInterval();
        updateDistance = getUpdateDistance();
        locationManager.requestLocationUpdates(updateInterval, updateDistance, criteria, this, null);
    }

    /**
     * Get the account ID, if the service is running, then the account ID should not be empty
     * @return the account ID
     */
    private String getAccountID() {
        SharedPreferences settings = getSharedPreferences(ClientMainActivity.UNIQUE_ID, 0);
        return settings.getString(ClientMainActivity.ACCOUNT_ID, "");
    }

    /**
     * Get the device unique ID
     * @return the device ID
     */
    private String getDeviceID() {
        return Secure.getString(getBaseContext().getContentResolver(),
            Secure.ANDROID_ID); 
    }

    /**
     * Get the update time-interval as indicated in the option menu
     * @return the general time interval for each update of coordinate
     */
    private int getUpdateTimeInterval() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int updateTimeInterval = Integer.valueOf(prefs.getInt(UserSettings.PREF_TIME_INTERVAL, DEFAULT_UPDATE_TIME_INTERVAL));
        
        return updateTimeInterval;
        // return DEFAULT_UPDATE_TIME_INTERVAL;
    }

    /**
     * Get the update distance interval as indicated in the option menu
     * @return the general minimum distance for each update of coordinate
     */
    private int getUpdateDistance() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int updateDistance = Integer.valueOf(prefs.getInt(UserSettings.PREF_DISTANCE_INTERVAL, DEFAULT_UPDATE_DISTANCE));
        
        return updateDistance;
    	//return DEFAULT_UPDATE_DISTANCE;
    }

    @Override
    public void onDestroy() {
    // TODO Auto-generated method stub
    super.onDestroy();
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
     * When the location is updated/changed, the new coordinate is send to the server
     */
    @Override
    public void onLocationChanged(Location location) {
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();
        final String myAccountID = accountID;
        final String myDeviceID = deviceID;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_INPUT_ACCOUNT_ID,
                            myAccountID));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_INPUT_DEVICE_ID,
                            myDeviceID));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_INPUT_TIME,
                            getTimeStamp()));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_INPUT_LONGITUDE,
                            String.valueOf(longitude)));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_INPUT_LATITUDE,
                            String.valueOf(latitude)));

                    AppHttpClient.executeHttpPost(ServerVariables.URL, postParameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void onProviderDisabled(String provider) {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onProviderEnabled(String provider) {
        locationManager.requestLocationUpdates(updateInterval, updateDistance, criteria, this, null);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub	
    }
	
	

}
