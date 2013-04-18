package com.sapenguins.atc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import dataSources.GpsLocationDataSource;
import dataSources.GpsLocationObj;
import dataSources.PinMarkerDataSource;
import dataSources.PinMarkerObj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import staticVariables.*;

public class MapFragment extends Fragment implements LocationListener{

	GoogleMap googleMap;
	Context context;
	PinMarkerDataSource pinMarkerDataSource;
	GpsLocationDataSource gpsLocationDataSource;
	ArrayList<PinMarkerObj> pinMarkerObjects;
	LocationManager locationManager;
	String provider;
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View view = inflater.inflate(R.layout.map, container, false);
		
		pinMarkerDataSource = new PinMarkerDataSource(context);
		gpsLocationDataSource = new GpsLocationDataSource(context);
		
		pinMarkerDataSource.open();
		gpsLocationDataSource.open();
		
		initProvider();
		initMap();
		addPinsFromDB();
		addVisitedPathFromDB();
		addPinLocationOption();
		
		return view;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		pinMarkerDataSource.close();
		gpsLocationDataSource.close();
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	public void onPause() {
		pinMarkerDataSource.open();
		gpsLocationDataSource.open();
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	public void onResume() {
		pinMarkerDataSource.open();
		gpsLocationDataSource.open();
		super.onResume();
	}
	
	/**
	 * Update the map view type
	 * @param type of map view
	 */
	public void updateMapType(String mapViewType) {
		if (mapViewType.equals(PreferenceValue.MAP_VIEW_HYBRID)) {
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		} else if (mapViewType.equals(PreferenceValue.MAP_VIEW_SATELLITE)) {
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		} else if (mapViewType.equals(PreferenceValue.MAP_VIEW_TERRAIN)){
			googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		} else {
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}
	}
	
	/**
	 * Check the provider that provide the map
	 */
	private void initProvider() {
		locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), true);
        if (provider == null) onProviderDisabled(provider); 
	}
	
	/**
     * Initiate the map. Obtain the map fragment
     */
    private void initMap() {
        SupportMapFragment mf = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.single_map_fragment);
        googleMap = mf.getMap();
        googleMap.setMyLocationEnabled(true);
        if (provider != null) {
	        Location lastKnown = locationManager.getLastKnownLocation(provider);
	        LatLng latLng = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
	        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
	        googleMap.moveCamera(cameraUpdate);
        }
    }
	
    /**
     * Display the visited locations directly from the DB
     */
    private void addVisitedPathFromDB() {
    	ArrayList<GpsLocationObj> visitedLocations = gpsLocationDataSource.getGpsLocations();
    	PolylineOptions lineOptions = new PolylineOptions();
    	lineOptions.color(Color.RED);
    	lineOptions.width(3);
    	for (GpsLocationObj location : visitedLocations) {
    		lineOptions.add(new LatLng(location.getLatitude(), location.getLongitude()));
    	}
    	Polyline polyline = googleMap.addPolyline(lineOptions);
    }
    
    /**
     * allow the user to pin location on the map and add a simple description to it
     */
    private void addPinLocationOption() {
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

        	@Override
            public void onMapLongClick(final LatLng latlng) {
                LayoutInflater li = LayoutInflater.from(context);
                final View v = li.inflate(R.layout.inputpin, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(v);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        EditText title = (EditText)v.findViewById(R.id.pin_title);
                        EditText description = (EditText)v.findViewById(R.id.pin_description);
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
     * Add a pin to the database
     * @param the title for the pin
     * @param the description of the pin
     * @param the longitude and latitude of the location of the pin
     */
    private void addPinToDB(String title, String description, LatLng latlng) {
        PinMarkerObj pin = new PinMarkerObj(title, description, latlng.longitude, latlng.latitude, getTimeStamp());
    	pinMarkerDataSource.addPin(pin);
    }
    
    /**
     * Add the pins stored in DB to the map
     */
    private void addPinsFromDB() {
    	pinMarkerObjects = pinMarkerDataSource.getPins();
    	for (PinMarkerObj pin : pinMarkerObjects) {
    		addPin(pin.getTitle(), pin.getDescription(), new LatLng(pin.getLatitude(), pin.getLongitude()), false);
    	}	
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
	
	
	//------------------INHERITED CLASSES FROM LOCATION LISTENER ----------
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
        googleMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
      
    }

    public void onProviderEnabled(String provider) {
    	pinMarkerDataSource.open();
		gpsLocationDataSource.open();
    }

    public void onProviderDisabled(String provider) {
    	pinMarkerDataSource.close();
		gpsLocationDataSource.close();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
