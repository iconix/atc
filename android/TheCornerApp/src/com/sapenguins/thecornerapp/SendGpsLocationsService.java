package com.sapenguins.thecornerapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.supports.AppHttpClient;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings.Secure;


public class SendGpsLocationsService extends Service implements LocationListener{
	private static final int DEFAULT_UPDATE_TIME_INTERVAL = 15*60*1000; //updates is made 15 minutes
	private static final int DEFAULT_UPDATE_DISTANCE = 0; //so that update is made by the time

	private LocationManager locationManager;
	Context context;
	Criteria criteria;
	int updateInterval;
	int updateDistance;
	String deviceId;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		context = this;
		deviceId = getDeviceID();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		criteria = new Criteria();
		locationManager.requestLocationUpdates(DEFAULT_UPDATE_TIME_INTERVAL, DEFAULT_UPDATE_DISTANCE, criteria, this, null);
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
	 * When the location is updated/changed, the new coordinate is send to the server
	 */
	@Override
	public void onLocationChanged(Location location) {
		final double latitude = location.getLatitude();
		final double longitude = location.getLongitude();
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
					postParameters.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));
					postParameters.add(new BasicNameValuePair("deviceID", deviceId));
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
		locationManager.requestLocationUpdates(DEFAULT_UPDATE_TIME_INTERVAL, DEFAULT_UPDATE_DISTANCE, criteria, this, null);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub	
	}	

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
