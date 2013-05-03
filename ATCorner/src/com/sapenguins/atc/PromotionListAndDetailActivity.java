package com.sapenguins.atc;

import java.lang.reflect.Field;
import java.util.ArrayList;

import objects.PinMarkerObj;

import staticVariables.PreferenceValue;
import staticVariables.SharedPreference;
import templates.CustomMenu;
import templates.CustomMenuItem;
import templates.DropDownNavigationMenuAdapter;
import templates.CustomMenu.OnMenuItemSelectedListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.model.LatLng;
import com.sapenguins.atc.PromotionListFragment.OnDetailPass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

public class PromotionListAndDetailActivity extends SherlockFragmentActivity implements OnMenuItemSelectedListener, OnDetailPass, ActionBar.OnNavigationListener {

	public static final int DEVICE_VERSION = android.os.Build.VERSION.SDK_INT;
	public static final int HONEYCOMB_VERSION = android.os.Build.VERSION_CODES.HONEYCOMB;
	MenuItem homeButton;
	MenuItem pinButton;
	MenuItem preferenceButton;
	
	PromotionDetailFragment detailFragment;
	
	ActionBar actionBar;
	Context context;
	int[] dropdownIconResources = {R.drawable.action_bar_promotion_icon_48, R.drawable.action_bar_map_icon_48};
	String[] dropdownText = {"Promotion", "History"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_list_and_detail);
		setCurrentView(PreferenceValue.VIEW_DETAIL_AND_PROMOTION);
		detailFragment = (PromotionDetailFragment) getSupportFragmentManager().findFragmentById(R.id.promotion_detail_fragment);
		
		initMenubar();

		initActionBar();
		actionBar.hide();
	}
	
	
	/**
	 * Create an action bar with navigating drop out list
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
		actionBar.setDisplayShowTitleEnabled(false);
		Context context = getSupportActionBar().getThemedContext();
		DropDownNavigationMenuAdapter navigationDropdown = new DropDownNavigationMenuAdapter(context, dropdownIconResources, dropdownText);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(navigationDropdown, this);


	}

	/**
	 * Handle event when the an navigating item in the action bar is selected 
	 * @param item position
	 * @param item id
	 * @return true if successfully carried out
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (itemPosition == 1) {
			startActivity(new Intent(getApplicationContext(), DetailAndHistoryActivity.class));
		}
		return true;
	}

	/**
	 * Create action bar on top of the activity and implement click listener for each of its items
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
				//TODO pin in the main activity, so it can be use across fragments
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

	/**
	 * Init the view navigation bar
	 */
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
	 * Load up our menu. This menu display the viewing option of a given functionality
	 */
	private void loadMenuItems() {
		//This is kind of a tedious way to load up the menu items.
		//Am sure there is room for improvement.
		ArrayList<CustomMenuItem> menuItems = new ArrayList<CustomMenuItem>();

		CustomMenuItem cmi = new CustomMenuItem();
		cmi.setCaption("Full Map");
		cmi.setImageResourceId(R.drawable.map_menu_icon);
		cmi.setId(MAP_ITEM);
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
		cmi.setCurrent(true);
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
			//mMenu.show(findViewById(R.id.promotion_detail_fragment));
			actionBar.show();
		}
	}

	/**
	 * handle the event when the menu item is selected
	 */
	public void MenuItemSelectedEvent(CustomMenuItem selection) {
		switch (selection.getId()) {
		case MAP_ITEM:
			startActivity(new Intent(getApplicationContext(), SingleMapViewActivity.class));
			break;
		case LIST_ITEM:
			startActivity(new Intent(getApplicationContext(), MapAndHistoryActivity.class));
			break;
		}
		if (actionBar.isShowing()) actionBar.hide();
	}



	/**
	 * Set the current view
	 * @param currentView
	 */
	private void setCurrentView(String currentView) {
		updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.LAST_SAVED_VIEW, currentView);
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
	
	//---------------------------------------
	//--HANDLE INTERACTIONS BETWEEN FRAGMENT-
	//---------------------------------------
	//@Override
	public void onDetailPass(String title, String description, String distance, String imageUrl) {
		detailFragment.dislayPromotionDetail(title, description, distance, imageUrl);
	}

}
