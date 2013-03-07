package com.sap.gps;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.widget.*;
import android.location.*;
import android.content.*;

public class GPSActivity extends Activity {

	Button gps;
	TextView longitude;
	TextView latitude;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        gps = (Button)findViewById(R.id.gps);
        longitude =(TextView)findViewById(R.id.longitude);
        latitude =(TextView)findViewById(R.id.latitude);
        setupGPSOnClickListener();
    }
    
    private void setupGPSOnClickListener() {
	    gps.setOnClickListener(new Button.OnClickListener() {  
	    	@Override
	        public void onClick(View arg0) {  
	            LocationManager mlocManager=null;  
	            LocationListener mlocListener;  
	            mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
	            mlocListener = new MyLocationListener();  
	            mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);  
	     
	            if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
	            	if(MyLocationListener.latitude>0)  
	                {  
	                    latitude.setText("Latitude: " + MyLocationListener.latitude);  
	                    longitude.setText("Longitude: " + MyLocationListener.longitude);  
	                }  
	            } else {  
	            	//TODO notice the user that the gps has not been turned on
	            }  
	    	}  
	    });  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_gps, menu);
        return true;
    }
}
