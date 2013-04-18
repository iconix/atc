package com.sapenguins.atc;

import java.util.ArrayList;

import staticVariables.PreferenceValue;
import staticVariables.SharedPreference;
import templates.CustomMenu;
import templates.CustomMenu.OnMenuItemSelectedListener;
import templates.CustomMenuItem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

public class MapAndHistoryActivity extends FragmentActivity implements OnMenuItemSelectedListener {
	MapFragment mapFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_and_map);
		setCurrentView(PreferenceValue.VIEW_SINGLE_MAP);
		mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_history_fragment);
		initMenubar();
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
    	AlertDialog.Builder builder = new AlertDialog.Builder(MapAndHistoryActivity.this);
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
				dialog.cancel();
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
    	mapFragment.updateMapType(mapViewType);
		/*if (mapViewType.equals(PreferenceValue.MAP_VIEW_HYBRID)) {
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		} else if (mapViewType.equals(PreferenceValue.MAP_VIEW_SATELLITE)) {
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		} else if (mapViewType.equals(PreferenceValue.MAP_VIEW_TERRAIN)){
			googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		} else {
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}*/
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


