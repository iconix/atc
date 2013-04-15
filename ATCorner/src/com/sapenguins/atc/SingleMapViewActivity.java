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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import templates.CustomMenu.OnMenuItemSelectedListener;
import templates.CustomMenuItem;
import templates.CustomMenu;
import dataSources.*;
import staticVariables.*;

public class SingleMapViewActivity extends FragmentActivity implements LocationListener, OnMenuItemSelectedListener {

	GoogleMap googleMap; 
	Context context;
	PinMarkerDataSource pinMarkerDataSource;
	GpsLocationDataSource gpsLocationDataSource;
	ArrayList<PinMarkerObj> pinMarkerObjects;
	LocationManager locationManager;
	String provider;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_map);
	    
		setCurrentView(PreferenceValue.VIEW_SINGLE_MAP);
		
		context = this;
		pinMarkerDataSource = new PinMarkerDataSource(context);
		gpsLocationDataSource = new GpsLocationDataSource(context);
		
		pinMarkerDataSource.open();
		gpsLocationDataSource.open();
		
		initProvider();
		initMenubar();
		initMap();
		addPinsFromDB();
		addVisitedPathFromDB();
		addPinLocationOption();
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		pinMarkerDataSource.close();
		gpsLocationDataSource.close();
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		pinMarkerDataSource.open();
		gpsLocationDataSource.open();
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		pinMarkerDataSource.open();
		gpsLocationDataSource.open();
		super.onResume();
	}

	/**
	 * Check the provider that provide the map
	 */
	private void initProvider() {
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), true);
        if (provider == null) onProviderDisabled(provider); 
	}
	
	/**
     * Initiate the map. Obtain the map fragment
     */
    private void initMap() {
        SupportMapFragment mf = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.single_map_fragment);
        googleMap = mf.getMap();
        googleMap.setMyLocationEnabled(true);
        reloadMapType();
        if (provider != null) {
	        Location lastKnown = locationManager.getLastKnownLocation(provider);
	        LatLng latLng = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
	        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
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
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
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
    
    
    //---------------------------------------
    //--------CREATE MENU BAR ---------------
    //---------------------------------------
    private CustomMenu mMenu;
    public static final int MAIN_MENU_ITEM = 1;
    public static final int MAP_STYLE_ITEM = 2;
    public static final int PREFERENCE_ITEM = 3;
    public static final int HISTORY_ITEM = 4;
    
    private void initMenubar() {
        mMenu = new CustomMenu(this, this, getLayoutInflater());
        mMenu.setHideOnSelect(true);
        mMenu.setItemsPerLineInPortraitOrientation(4);
        mMenu.setItemsPerLineInLandscapeOrientation(8);
        //load the menu items
        loadMenuItems();
    }
    
    /**
     * Snarf the menu key.
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            doMenu();
            return true; //always eat it!
        }
        return super.onKeyDown(keyCode, event); 
    } 

    /**
     * Load up our menu.
     */
    private void loadMenuItems() {
        //This is kind of a tedious way to load up the menu items.
        //Am sure there is room for improvement.
        ArrayList<CustomMenuItem> menuItems = new ArrayList<CustomMenuItem>();
        
        CustomMenuItem cmi = new CustomMenuItem();
        cmi.setCaption("Menu");
        cmi.setImageResourceId(R.drawable.home);
        cmi.setId(MAIN_MENU_ITEM);
        menuItems.add(cmi);
        
        cmi = new CustomMenuItem();
        cmi.setCaption("Map Style");
        cmi.setImageResourceId(R.drawable.map_menu_icon);
        cmi.setId(MAP_STYLE_ITEM);
        menuItems.add(cmi);
        
        cmi = new CustomMenuItem();
        cmi.setCaption("Preference");
        cmi.setImageResourceId(R.drawable.settings);
        cmi.setId(PREFERENCE_ITEM);
        menuItems.add(cmi);
        
        cmi = new CustomMenuItem();
        cmi.setCaption("History");
        cmi.setImageResourceId(R.drawable.history);
        cmi.setId(HISTORY_ITEM);
        menuItems.add(cmi);
        
        if (!mMenu.isShowing())
        try {
            mMenu.setMenuItems(menuItems);
        } catch (Exception e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("menu fail to load");
            alert.setMessage(e.getMessage());
            alert.show();
        }
    }
	
    /**
     * Toggle our menu on user pressing the menu key.
     */
    private void doMenu() {
        if (mMenu.isShowing()) {
            mMenu.hide();
        } else {
            //Note it doesn't matter what widget you send the menu as long as it gets view.
            mMenu.show(findViewById(R.id.single_map_fragment));
        }
    }

    /**
     * handle the event when the menu item is selected
     */
    public void MenuItemSelectedEvent(CustomMenuItem selection) {
        switch (selection.getId()) {
            case MAIN_MENU_ITEM:
				startActivity(new Intent(getApplicationContext(), MainMenu.class));
            case MAP_STYLE_ITEM:
            	onMapStyleMenuButtonPressed();
        }
    }
      
    //Map type view
    public static final int NORMAL_VIEW = 0;
    public static final int HYBRID_VIEW = 1;
    public static final int SATELLITE_VIEW = 2;
    public static final int TERRAIN_VIEW = 3;
    /**
     * build an alert dialog to display the option for map style
     */
    private void onMapStyleMenuButtonPressed() {
    	String currentMapType = getMapViewType();
    	int mapID = 0;
    	if (currentMapType.equals(PreferenceValue.MAP_VIEW_HYBRID)) mapID = 1;
    	else if (currentMapType.equals(PreferenceValue.MAP_VIEW_SATELLITE)) mapID = 2;
    	else if (currentMapType.equals(PreferenceValue.MAP_VIEW_TERRAIN)) mapID = 3;
    	
    	CharSequence[] mapOptions = {"Normal", "Hybrid", "Satellite", "Terrain"};
    	AlertDialog.Builder builder = new AlertDialog.Builder(SingleMapViewActivity.this);
    	builder.setTitle("Select Map Style")
    	.setSingleChoiceItems(mapOptions, mapID, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == TERRAIN_VIEW) setMapViewType(PreferenceValue.MAP_VIEW_TERRAIN);
				else if (which == HYBRID_VIEW) setMapViewType(PreferenceValue.MAP_VIEW_HYBRID);
				else if (which == SATELLITE_VIEW) setMapViewType(PreferenceValue.MAP_VIEW_SATELLITE);
				else setMapViewType(PreferenceValue.MAP_VIEW_NORMAL);
			}
		})
    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				reloadMapType();
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    /**
     * Set the map type to fit with the preferences
     */
    private void reloadMapType() {
    	String mapViewType = getMapViewType();
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
    
    //---------------------------------------------
    //--------------GET PREFERENCES ---------------
    //---------------------------------------------
    
    /**
     * Get the map view type. 
     * @return the type of map
     */
    private String getMapViewType() {
    	return getPreferenceValue(SharedPreference.PREFERENCE, SharedPreference.MAP_TYPE_VIEW);
    }
    
    /**
     * Set the map view type
     * @param mapViewType
     */
    private void setMapViewType(String mapViewType) {
    	updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.MAP_TYPE_VIEW, mapViewType);
    }
    
    /**
     * Set the current view
     * @param currentView
     */
    private void setCurrentView(String currentView) {
    	updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.LAST_SAVED_VIEW, currentView);
    }
    
    /**
     * Get the preference value of a given key stored in a given preference list
     * @param preference
     * @param key
     * @return the string value of the key
     */
    private String getPreferenceValue(String preference, String key) {
        SharedPreferences settings = getSharedPreferences(preference, 0);
        return settings.getString(key, "");
    }
    
    /**
     * Modify the information stored in the shared preferences
     * @param the ID associate with the preference
     * @param the key string of a field in the preference
     * @param the value of above key
     */
    private void updateSystemPreferences(String preferenceID, String key, String value) {
        SharedPreferences settings = getSharedPreferences(preferenceID, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
}


