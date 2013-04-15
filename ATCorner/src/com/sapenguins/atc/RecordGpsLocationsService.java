package com.sapenguins.atc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import dataSources.GpsLocationDataSource;
import dataSources.GpsLocationObj;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


public class RecordGpsLocationsService extends Service implements LocationListener{
	 	private static final int DEFAULT_UPDATE_TIME_INTERVAL = 60*1000; //updates is made every minutes
	    private static final int DEFAULT_UPDATE_DISTANCE = 15; //in meter

	    private LocationManager locationManager;
	    GpsLocationDataSource gpsLocationDataSource;
	    Context context;
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
	    	context = this;
	    	gpsLocationDataSource = new GpsLocationDataSource(this);
	    	gpsLocationDataSource.open();
	        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        criteria = new Criteria();
	        updateInterval = getUpdateTimeInterval();
	        updateDistance = getUpdateDistance();
	        locationManager.requestLocationUpdates(updateInterval, updateDistance, criteria, this, null);
	    }

	    @Override
	    public void onDestroy() {
	        gpsLocationDataSource.close();
	        super.onDestroy();
	    }
	     
	    /**
	     * Get the update time-interval as indicated in the option menu
	     * @return the general time interval for each update of coordinate
	     */
	    private int getUpdateTimeInterval() {
	        //TODO get the update interval from the user setting
	        return DEFAULT_UPDATE_TIME_INTERVAL;
	    }

	    /**
	     * Get the update distance interval as indicated in the option menu
	     * @return the general minimum distance for each update of coordinate
	     */
	    private int getUpdateDistance() {
	        //TODO get the update distance from the user setting
	        return DEFAULT_UPDATE_DISTANCE;
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
	    	GpsLocationObj loc = new GpsLocationObj(location.getLongitude(),
	    			location.getLatitude(), getTimeStamp());
	    	gpsLocationDataSource.addGpsLocation(loc);
	    }

	    @Override
	    public void onProviderDisabled(String provider) {
	    	gpsLocationDataSource.close();
	        locationManager.removeUpdates(this);
	    }

	    @Override
	    public void onProviderEnabled(String provider) {
	    	gpsLocationDataSource.open();
	        locationManager.requestLocationUpdates(updateInterval, updateDistance, criteria, this, null);
	    }

	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	        // TODO Auto-generated method stub	
	    }	

}
