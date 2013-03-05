package com.sap.testmap;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.location.*;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;


import android.provider.Settings;

public class MainActivity extends FragmentActivity implements LocationListener {

	private GoogleMap map;
	private LocationManager locationManager;
    private String provider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabledWiFi = service
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to 
        // go to the settings
        if (!enabledGPS) {
            Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            Toast.makeText(this, "Selected Provider " + provider,
                    Toast.LENGTH_SHORT).show();
            onLocationChanged(location);
        } else {

            //do something
        }

    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        Toast.makeText(this, "Location " + lat+","+lng,
                Toast.LENGTH_LONG).show();
        LatLng coordinate = new LatLng(lat, lng);
        Toast.makeText(this, "Location " + coordinate.latitude+","+coordinate.longitude,
                Toast.LENGTH_LONG).show();
        Marker startPerc = map.addMarker(new MarkerOptions()
        .position(coordinate)
        .title("Start")
        .snippet("initialize")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
        
        LatLng loc = new LatLng(lat, lng);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 50));
    }


    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
    	
    	
    	
    	
    	
    	
    	/*
        super.onCreate(savedInstanceState);

        SupportMapFragment fragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, fragment).commit();
        
        map = fragment.getMap();
        
        
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng arg0) {
				LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		        LocationListener mlocListener = new MyLocationListener();  
		        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);  
		        
		        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
		        	if(MyLocationListener.latitude>0)  
		            {  
		        		LatLng stanford = new LatLng(MyLocationListener.latitude, MyLocationListener.longitude);
		                map.moveCamera(CameraUpdateFactory.newLatLngZoom(stanford, 15));
		            }  
		        }
			}
		});
        
        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        LocationListener mlocListener = new MyLocationListener();  
        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);  
        
        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
        	if(MyLocationListener.latitude>0)  
            {  
        		LatLng stanford = new LatLng(MyLocationListener.latitude, MyLocationListener.longitude);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(stanford, 15));
            }  
        }
        */     

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
