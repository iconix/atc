package com.sap.clientproject;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ClientMainActivity extends Activity{

	//Service name
	public static final String SEND_COORDINATE_SERVICE = "com.sap.clientproject.SendCoordinateService";
	
	
	//unique ID in which all the information is stored in the DB
	public static final String UNIQUE_ID = "MyUniqueID";
	public static final String ACCOUNT_ID = "accountID";
	
	private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 1500;
    
    private static ImageView splash;
    
    private String accountID;
    
    private Intent sendCoordinateService;
    
    Button mapView;
    Button singleAdsView;
	    
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
	    if (accountID.equals("")) loginToDevice();
	    else {
	    	mapView = (Button)findViewById(R.id.map_view);
	    	
	    	mapView.setOnClickListener(new Button.OnClickListener() {
	    		@Override
				public void onClick(View arg0) {
			    	startSendingCoordinateService();
			    	Intent i = new Intent(getApplicationContext(), AdListActivity.class);
			        startActivity(i);
	    		}
	    	});
	    	
	    	/*singleAdsView = (Button)findViewById(R.id.single_ad_view);
	    	singleAdsView.setOnClickListener(new Button.OnClickListener() {
	    		@Override
				public void onClick(View arg0) {
			    	startSendingCoordinateService();
			    	Intent i = new Intent(getApplicationContext(), SingleAdActivity.class);
			        startActivity(i);
	    		}
	    	});*/
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
	 * @return the account ID
	 */
	private String getAccountID() {
		SharedPreferences settings = getSharedPreferences(UNIQUE_ID, 0);
		return settings.getString(ACCOUNT_ID, "");
	}
	
	/**
	 * Log in to your account. Open a new window with the login layout
	 */
	private void loginToDevice() {
		Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_main, menu);
		return true;
	}

}
