package com.sapenguins.atc;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.widget.DatePicker;
import android.widget.TextView;

import templates.CustomMenu.OnMenuItemSelectedListener;
import templates.CustomMenuItem;
import templates.CustomMenu;
import templates.DropDownNavigationMenuAdapter;
import staticVariables.*;

public class SingleMapViewActivity extends SherlockFragmentActivity implements OnMenuItemSelectedListener, ActionBar.OnNavigationListener  {

	public static final int DEVICE_VERSION = android.os.Build.VERSION.SDK_INT;
	public static final int HONEYCOMB_VERSION = android.os.Build.VERSION_CODES.HONEYCOMB;
	MenuItem homeButton;
	MenuItem pinButton;
	MenuItem preferenceButton;
	
	MapFragment mapFragment;
	ActionBar actionBar;
	Context context;
	int[] dropdownIconResources = {R.drawable.action_bar_map_icon_48, R.drawable.action_bar_promotion_icon_48};
	String[] dropdownText = {"History", "Promotion"};
	
	TextView fromDate;
	TextView fromTime;
	TextView toDate;
	TextView toTime;
	
	int fromDay;
	int fromMonth;
	int fromYear;
	
	int toDay;
	int toMonth;
	int toYear;
	
	long fromTimePeriod;
	long toTimePeriod;
	
	private static final int FROM_DATE_DIALOG_ID = 0;
	private static final int FROM_TIME_DIALOG_ID = 1;
	private static final int TO_DATE_DIALOG_ID = 2;
	private static final int TO_TIME_DIALOG_ID = 3;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_map);
		context = this;
		mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_single_fragment);
		setCurrentView(PreferenceValue.VIEW_SINGLE_MAP);
		
		initMenubar();		
		reloadMapType();
		
		initActionBar();
		actionBar.hide();
	}
  
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				
			}
		    };
	    
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
			case FROM_DATE_DIALOG_ID:
				
		}
		return null;
	}
	
	/**
	 * Create the action bar whenever the menu button is clicked
	 */
	private void initActionBar() {
		setTheme(R.style.Theme_Sherlock);
		
		if (DEVICE_VERSION >= HONEYCOMB_VERSION) {
			try {
		        ViewConfiguration config = ViewConfiguration.get(this);
		        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
		        if(menuKeyField != null) {
		            menuKeyField.setAccessible(true);
		            menuKeyField.setBoolean(config, false);
		        }
		    } catch (Exception ex) {
		        // Ignore
		    }
		}

		actionBar = getSupportActionBar();
		//actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		Context context = getSupportActionBar().getThemedContext();
		DropDownNavigationMenuAdapter navigationDropdown = new DropDownNavigationMenuAdapter(context, dropdownIconResources, dropdownText);
		 
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(navigationDropdown, this);
        
        
	}
	
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
    	if (itemPosition == 1) {
    		startActivity(new Intent(getApplicationContext(), PromotionListActivity.class));
    	}
        return true;
    }
    
    /* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onCreateOptionsMenu(com.actionbarsherlock.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate( R.menu.action_bar_menu, menu );
		homeButton = menu.findItem(R.id.action_bar_home_button);
		
		homeButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				startActivity(new Intent(getApplicationContext(), MainMenu.class));
				return true;
			}
		});
		
		pinButton = menu.findItem(R.id.action_bar_pin_button);
		pinButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				mapFragment.addPinToCurrentLocation();
				return true;
			}
		});
		
		preferenceButton = menu.findItem(R.id.action_bar_preference_button);
		preferenceButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
				return true;
			}
		});
        return true; 
	}


	//---------------------------------------
    //--------CREATE MENU BAR ---------------
    //---------------------------------------
    private CustomMenu mMenu;
    public static final int MAP_ITEM = 1;
    public static final int LIST_ITEM = 2;
    public static final int DETAIL_ITEM = 3;
    
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
        	if (DEVICE_VERSION < HONEYCOMB_VERSION) 
        		openOptionsMenu();
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
        cmi.setCaption("Full Map");
        cmi.setImageResourceId(R.drawable.map_menu_icon);
        cmi.setId(MAP_ITEM);
        cmi.setCurrent(true);
        menuItems.add(cmi);
        
        cmi = new CustomMenuItem();
        cmi.setCaption("List & Map");
        cmi.setImageResourceId(R.drawable.settings);
        cmi.setId(LIST_ITEM);
        menuItems.add(cmi);
        
        cmi = new CustomMenuItem();
        cmi.setCaption("List & Detail");
        cmi.setImageResourceId(R.drawable.history);
        cmi.setId(DETAIL_ITEM);
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
        	actionBar.hide();
            mMenu.hide();
        } else {
            //Note it doesn't matter what widget you send the menu as long as it gets view.
            mMenu.show(findViewById(R.id.single_map_fragment));
            actionBar.show();
        }
    }

    /**
     * handle the event when the menu item is selected
     */
    public void MenuItemSelectedEvent(CustomMenuItem selection) {
        switch (selection.getId()) {
            case LIST_ITEM:
				startActivity(new Intent(getApplicationContext(), MapAndHistoryActivity.class));
				break;
            case DETAIL_ITEM:
            	onMapStyleMenuButtonPressed();
            	break;
        }
        if (actionBar.isShowing()) actionBar.hide();
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


