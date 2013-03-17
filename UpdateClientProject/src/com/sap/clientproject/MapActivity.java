package com.sap.clientproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.gms.maps.*;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import classesAndManagers.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import staticVariables.*;

public class MapActivity extends FragmentActivity implements LocationListener{ 
    Context context;
    
    private GoogleMap googleMap;
    private String accountID;
    private PinLocationManager pinLocationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        initMap();
        
        //init the global variables
        context = this;
        accountID = getAccountID();
        pinLocationManager = new PinLocationManager();
        
        //Set up the map and provider
        LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        String provider = lm.getBestProvider(new Criteria(), true);
        if (provider == null) {
            onProviderDisabled(provider);
        }
        
        addSomeLocationToMap();
        pinLocation();
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
     * @param the title for the pin
     * @param the description of the pin
     * @param the longitude and latitude of the location of the pin
     * @param boolean whether the pin is draggable
     */
    private void addPin(String title, String description, LatLng latlng, boolean draggable) {
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .title(title)
                .snippet(description)
                .draggable(draggable)
                .position(latlng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))   
                );
        //TODO remove marker mechanism
    }
    
    /**
     * Get the current time when the new coordinate was taken.
     * The current time is recorded as UTC
     * @return the current time represent in the format yyyyMMdd_HHmmss
     */
    private final String getTimeStamp() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
            return sdf.format(new Date());
    }
    
    /**
     * Add a pin the the database
     * @param the title for the pin
     * @param the description of the pin
     * @param the longitude and latitude of the location of the pin
     */
    private void addPinToDB(String title, String description, LatLng latlng) {
        final String myAccountID = accountID;
        final double latitude = latlng.latitude;
        final double longitude = latlng.longitude;
        final String myTitle = title;
        final String myDescription = description;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
            try {
                PinLocation pinLocation = new PinLocation(myAccountID, Long.valueOf(getTimeStamp()), longitude, latitude, myTitle, myDescription);
                    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                    postParameters.add(new BasicNameValuePair("pinLocation", pinLocationManager.getPinLocationInStringFormat(pinLocation)));

                    AppHttpClient.executeHttpPost(ServerVariables.URL, postParameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return null;
            }
        }.execute();
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
