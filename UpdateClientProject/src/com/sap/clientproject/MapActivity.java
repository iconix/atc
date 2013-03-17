package com.sap.clientproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.gms.maps.*;
import android.location.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements LocationListener{
    
    Context context;
    private GoogleMap googleMap;
    
    SupportMapFragment mMapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        initMap();
        context = this;
        addSomeLocationToMap();
        pinLocation();
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
     * allow the user to pin location on the map and add a simple description to it
     */
    private void pinLocation() {
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            public void onMapLongClick(final LatLng latlng) {
                LayoutInflater li = LayoutInflater.from(context);
                final View v = li.inflate(R.layout.inputpin, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(v);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        EditText title = (EditText)v.findViewById(R.id.title);
                        EditText description = (EditText)v.findViewById(R.id.description);
                        addPin(title.getText().toString(), description.getText().toString(), latlng, false);
                    }
                });
                
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                      
            }
        });
    }
    
    /**
     * pin a location on the map. Allow the user to input title and the description
     * on the location pinned
     * @param: the longitude and the latitude of the location
     */
    private void addPin(String title, String description, LatLng latlng, boolean draggable) {
        googleMap.addMarker(new MarkerOptions()
                .title(title)
                .snippet(description)
                .draggable(draggable)
                .position(latlng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))   
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
