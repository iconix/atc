package com.sap.clientproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.gms.maps.*;
import android.location.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements LocationListener{
    
    private GoogleMap googleMap;
    
    SupportMapFragment mMapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        initMap();
        addSomeLocationToMap();
    }
    
    /**
     * add some fixed location to the map. For this function, just add
     * the coordinate of 
     */
    private void addSomeLocationToMap() {
        LatLng coordinate  = new LatLng(37.77699, -122.42697);
        
        googleMap.addMarker(new MarkerOptions() 
            .title("twitter")
            .snippet("Twitter HQ")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            .position(coordinate)
        );
    }
    
    /**
     * Initiate the map. Obtain the map fragment
     */
    private void initMap() {
        SupportMapFragment mf = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.singlemap);
        googleMap = mf.getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void onLocationChanged(Location location) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
      
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS is disable");
        builder.setCancelable(false);
        
        //take user to turn on the GPS setting
        builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
               Intent startGps = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
               startActivity(startGps);
           } 
        });
        
        //close
        builder.setNegativeButton("Leave GPS off", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        
        //display the alert
        AlertDialog alert = builder.create();
        alert.show();
    }

}
