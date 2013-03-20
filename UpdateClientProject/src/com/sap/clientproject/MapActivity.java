package com.sap.clientproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import com.google.android.gms.maps.*;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import classesAndManagers.*;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        initMap();
        
        //init the global variables
        context = this;
        accountID = getAccountID();
        
        //Set up the map and provider
        LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        String provider = lm.getBestProvider(new Criteria(), true);
        if (provider == null) {
            onProviderDisabled(provider);
        }
        
  
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
                        addPinToDB(title.getText().toString(), description.getText().toString(), latlng);
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
    private String getTimeStamp() {
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
             
                    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                    //postParameters.add(new BasicNameValuePair("pinLocation", pinLocationManager.getPinLocationInStringFormat(pinLocation)));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_INPUT_ACCOUNT_ID,
                            myAccountID));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_INPUT_TIME,
                            getTimeStamp()));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_INPUT_LONGITUDE,
                            String.valueOf(longitude)));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_INPUT_LATITUDE,
                            String.valueOf(latitude)));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_INPUT_TITLE,
                            myTitle));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_INPUT_DESCRIPTION,
                            myDescription));
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
        displayPinOnMap();
    }
    
    /**
     * Display pin stored in the DB of a given user to the map
     */
    private void displayPinOnMap() {
        //TODO pass the config parameter in here and add listener
        PinConfig pinConfig = new PinConfig(getAccountID(), "-180", "180", "-90", "90", "0", "20200101000000");
        
        displayPinsFromDB(pinConfig);
        
    }
    
    /**
     * Display all the visited location stored in the DB of a given user to
     * the map. Connect the locations by line according to the time in which
     * the GPS was taken
     */
    private void displayVisitedLocationOnMap() {
        //TODO grab the config parameter somewhere
        CoordinateConfig coordinateConfig = new CoordinateConfig(getAccountID(), 
                getDeviceID(), "-180", "180", "-90", "90", "0", "20200101000000");
        displayVisitedLocationFromDB(coordinateConfig);
    }
    
    /**
     * Send the request to the database to obtain the list of coordinate obtained
     * according to a given config. The response is written in string format
     * @param CoordinateConfig object
     */
    public void displayVisitedLocationFromDB(CoordinateConfig coordinateConfig) {
        final CoordinateConfig myCoordinateConfig = coordinateConfig;
         new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_REQUEST_ACCOUNT_ID,
                            myCoordinateConfig.getAccountID()));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_REQUEST_DEVICE_ID,
                            myCoordinateConfig.getDeviceID()));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_REQUEST_LOWER_LONGITUDE, 
                            String.valueOf(myCoordinateConfig.getLowerLongitude())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_REQUEST_HIGHER_LONGITUDE, 
                            String.valueOf(myCoordinateConfig.getHigherLongitude())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_REQUEST_LOWER_LATITUDE, 
                            String.valueOf(myCoordinateConfig.getLowerLatitude())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_REQUEST_HIGHER_LATITUDE, 
                            String.valueOf(myCoordinateConfig.getHigherLatitude())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_REQUEST_LOWER_TIME, 
                            String.valueOf(myCoordinateConfig.getLowerTime())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.COORDINATE_REQUEST_HIGHER_TIME, 
                            String.valueOf(myCoordinateConfig.getHigherTime())));
                    return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    String[] coordinates = result.split(SpecialCharacters.endLn);
                    ArrayList<LatLng> gpsCoordinates = new ArrayList<LatLng>();
                    for (String coordinate : coordinates) {      
                        GpsCoordinate gpsCoordinate = new GpsCoordinate(coordinate);
                        double longitude = gpsCoordinate.getLongitude();
                        double latitude = gpsCoordinate.getLatitude();
                        LatLng latlng = new LatLng(latitude, longitude);
                        gpsCoordinates.add(latlng);
                   }
                   
                   PolylineOptions rectOptions = new PolylineOptions();
                   rectOptions.color(Color.RED);
                   for (LatLng latlng : gpsCoordinates)
                     rectOptions.add(latlng);
                   Polyline polyline = googleMap.addPolyline(rectOptions);
                } 
            }
        }.execute();
    }
    
    /**
     * Send the request to the database to obtain the pin according to the given config
     * The response is written in string format which need to be parsed. 
     * Parse it and display on map
     * @param pinConfig
     */
    private void displayPinsFromDB (PinConfig pinConfig) {
        final PinConfig myPinConfig = pinConfig;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_REQUEST_ACCOUNT_ID,
                            myPinConfig.getAccountID()));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_REQUEST_LOWER_LONGITUDE, 
                            String.valueOf(myPinConfig.getLowerLongitude())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_REQUEST_HIGHER_LONGITUDE, 
                            String.valueOf(myPinConfig.getHigherLongitude())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_REQUEST_LOWER_LATITUDE, 
                            String.valueOf(myPinConfig.getLowerLatitude())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_REQUEST_HIGHER_LATITUDE, 
                            String.valueOf(myPinConfig.getHigherLatitude())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_REQUEST_LOWER_TIME, 
                            String.valueOf(myPinConfig.getLowerTime())));
                    postParameters.add(new BasicNameValuePair(RequestParameters.PIN_REQUEST_HIGHER_TIME, 
                            String.valueOf(myPinConfig.getHigherTime())));
                    return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    String[] pinLocations = result.split(SpecialCharacters.endLn);
                    for (String pinLocation : pinLocations) {      
                        PinLocation pin = new PinLocation(pinLocation);
                        String myTitle = pin.getTitle() + " @" + pin.getTime(); //title include time
                        String myDescription = pin.getDescription();
                        double longitude = pin.getLongitude();
                        double latitude = pin.getLatitude();
                        LatLng latlng = new LatLng(latitude, longitude);    
                        addPin(myTitle, myDescription, latlng, false);
                   }
                } 
                displayVisitedLocationOnMap();
            }
        }.execute();
    }
    
    /**
     * Get the device unique ID
     * @return the device ID
     */
    private String getDeviceID() {
    	return Settings.Secure.getString(getBaseContext().getContentResolver(),
            Settings.Secure.ANDROID_ID); 
    }

    
    //------------------INHERITED CLASSES FROM LOCATION LISTENER ----------
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
