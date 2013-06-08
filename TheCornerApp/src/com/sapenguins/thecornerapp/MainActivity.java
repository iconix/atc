package com.sapenguins.thecornerapp;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends Activity {
	
	public static final String SEND_GPS_LOCATION_SERVICE = "com.sapenguins.thecornerapp.SendGpsLocationsService";
	Context context;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		context = this;
		//SQLTablesHelper tableHelper = new SQLTablesHelper(this);  //need for when table change
		//tableHelper.onCreate(tableHelper.getReadableDatabase());
		sendGpsLocationService();
		Intent intent = new Intent(context, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onStart() {
		EasyTracker.getInstance().activityStart(this);
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		EasyTracker.getInstance().activityStop(this);
		super.onDestroy();
	}

	/**
     * Detect whether the service that keep record of visited location is running.
     * If it has not started, then starts it. 
     */
    private void sendGpsLocationService() {
         ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
         for (RunningServiceInfo srv : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SEND_GPS_LOCATION_SERVICE.equals(srv.service.getClassName())) return;
         }
         startService(new Intent(MainActivity.this, SendGpsLocationsService.class));
    }
	
}
