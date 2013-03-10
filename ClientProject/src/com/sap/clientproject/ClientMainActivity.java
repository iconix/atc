package com.sap.clientproject;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ClientMainActivity extends Activity{

	private LocationManager locationManager;
	//Service name
	public static final String SEND_COORDINATE_SERVICE = "com.sap.clientproject.SendCoordinateService";
	
	
	//unique ID in which all the information is stored in the DB
	public static final String UNIQUE_ID = "MyUniqueID";
	
	private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 1500;
    
    private static ImageView splash;
    
    private String accountID;
    
    private Intent sendCoordinateService;
	    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	    setContentView(R.layout.activity_client_main);
	    
	    //check if whether the GPS signal is enabled.
	    //If not, then take it the user to the GPS setting
	    LocationManager service = (LocationManager)getSystemService(LOCATION_SERVICE);
	    boolean enabledGPS = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    if (!enabledGPS) {
	    	Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
	    	Intent turnOnGPSSignalIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    	startActivity(turnOnGPSSignalIntent);
	    }
	    
	    //show the splash image
	    splash = (ImageView) findViewById(R.id.splashscreen);
	    Message msg = new Message();
	    msg.what = STOPSPLASH;
	    splashHandler.sendMessageDelayed(msg, SPLASHTIME);
	    
	    //Get the account ID, if no such parameter exist, then we need to register the device
	    accountID = getAccountID();
	    if (accountID.equals("")) registerNewDevice();
	    else {
	    	startSendingCoordinateService();
	    }
	}
	
	//handler for splash screen
	private static Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            	case STOPSPLASH:
                    //remove SplashScreen from view
                    splash.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };
	
    /**
     * Detect whether the service that sending the coordinate is to the server is running.
     * If it has not started, then starts it. 
     */
	private void startSendingCoordinateService() {
		 ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	     for (RunningServiceInfo srv : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SEND_COORDINATE_SERVICE.equals(srv.service.getClassName())) return;
	     }
	     sendCoordinateService = new Intent(ClientMainActivity.this, SendCoordinateService.class);
	     startService(sendCoordinateService);
	}
	
	/**
	 * Get the account ID, which suppose to be unique for each person using the application.
	 * The ID is assigned to each mobile device once it register. In case the user 
	 * switch to new device, he or she should has an option to continue with all his/her
	 * previous data. This function is serve to get the account ID from the sharedPreferences 
	 * setting. If the accountID is empty, take the user to the registration page, where upon 
	 * inputing the valid email address, the user will receive his/her unique ID and the email
	 * confirmation
	 * @return the account ID
	 */
	private String getAccountID() {
		SharedPreferences settings = getSharedPreferences(UNIQUE_ID, 0);
		return settings.getString("accountID", "");
	}
	
	/**
	 * Register the new device. On registering, the user has the option of using
	 * previously created account (in case he/she gets new phone) or create a new
	 * fresh account and capture fresh information. In that case, server should
	 * generate a unique ID and send it to the phone and make an email with the ID 
	 * sending to the email specified by user. 
	 */
	private void registerNewDevice() {
		Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
        /*
		//putting the account ID into the preferences setting
		SharedPreferences settings = getSharedPreferences(UNIQUE_ID, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("accountID", accountID);
		editor.commit();*/
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_main, menu);
		return true;
	}

}
