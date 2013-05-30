package com.sapenguins.thecornerapp;

import java.lang.reflect.Field;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.analytics.tracking.android.EasyTracker;
import com.sapenguins.thecornerapp.DealListFragment.Swipe;
import com.sapenguins.thecornerapp.constants.MenuSpinnerItems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DealListActivity extends SherlockFragmentActivity implements Swipe, ActionBar.OnNavigationListener{

	private static final String[] CONTENT = new String[] { "All", "Entertainment", "Shopping", "Food"};
	public static final int DEVICE_VERSION = android.os.Build.VERSION.SDK_INT;
	public static final int HONEYCOMB_VERSION = android.os.Build.VERSION_CODES.HONEYCOMB;
	
	DealListFragment listFragment;
	ActionBar actionbar;
	Context context;
	String[] maximumDistances;
	
	MenuItem homeItem;
	
	int dealId;
	String dealTitle;
	String dealImg;
	String dealDesc;
	String dealDistance;
	double dealLongitude;
	double dealLatitude;
	
	//views for the tab bar
	View itemOne;
	TextView itemOneText;
	TextView itemTwoText;
	View itemThree;
	TextView itemThreeText;
	
	View dealDetailContainer;
	
	String category;
	double distance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.deal_list_activity);
		context = this;
		distance = 0.5;
		initActionBar();
		listFragment = (DealListFragment) getSupportFragmentManager().findFragmentById(R.id.list_deal_fragment);
		initViewPager();
	}
	
	@Override
	public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance().activityStart(this); // Add this method.
	}
	
	@Override
	protected void onDestroy() {
		EasyTracker.getInstance().activityStop(this);
		super.onDestroy();
	}
	
	/**
	 * Initiate the view pager and its adapters
	 */
	private void initViewPager() {
		itemOne = findViewById(R.id.deal_tab_bar_item_one);
		itemThree = findViewById(R.id.deal_tab_bar_item_three);
		itemOneText = (TextView)findViewById(R.id.deal_tab_bar_item_one_text);
		itemTwoText = (TextView)findViewById(R.id.deal_tab_bar_item_two_text);
		itemThreeText = (TextView)findViewById(R.id.deal_tab_bar_item_three_text);

		category = CONTENT[0];
		setupTab(category);
		setupItemOneClickListener();
		setupItemThreeClickListener();
	}
	
	/**
	 * Set up the listener for when the item one is clicked
	 */
	private void setupItemOneClickListener() {
		itemOne.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				swipeLeftToRight();
			}
		});
	}
	
	/**
	 * Set up the listener for when the item one is clicked
	 */
	private void setupItemThreeClickListener() {
		itemThree.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				swipeRightToLeft();
			}
		});
	}
	
	/**
	 * Handle when the detail view is swipe from right to left. Change the category
	 */
	private void swipeRightToLeft() {
		category = itemThreeText.getText().toString();
		setupTab(category);
		listFragment.setSearchCategory(category);
	}
	
	/**
	 * Handle when the detail view is swipe from left to right. Change the category
	 */
	private void swipeLeftToRight() {
		category = itemOneText.getText().toString();
		setupTab(category);
		listFragment.setSearchCategory(category);
	}
	
	/**
	 * Set the content for each of the tab bar given its middle tab content
	 */
	private void setupTab(String middleTab) {
		itemTwoText.setText(middleTab);
		if (middleTab.equals(CONTENT[0])) {
			itemOneText.setText(CONTENT[CONTENT.length - 1]);
			itemThreeText.setText(CONTENT[1]);
		} else if (middleTab.equals(CONTENT[CONTENT.length - 1])) {
			itemOneText.setText(CONTENT[CONTENT.length - 2]);
			itemThreeText.setText(CONTENT[0]);
		} else {
			for (int i = 1; i < CONTENT.length - 1; i++) {
				if (middleTab.equals(CONTENT[i])) {
					itemOneText.setText(CONTENT[i - 1]);
					itemThreeText.setText(CONTENT[i+1]);
					break;
				}
			}
		}
	}

	/**
	 * Create action bar on top of the activity and implement click listener for each of its items
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onCreateOptionsMenu(com.actionbarsherlock.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate( R.menu.menu_bar, menu );
		homeItem = menu.findItem(R.id.menu_home);

		homeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				startActivity(new Intent(context, HomeActivity.class));
				return true;
			}
		});
		return true; 
	}
	
	/**
	 * Init the actionbar
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
		actionbar = getSupportActionBar();
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg_black));
        maximumDistances = getResources().getStringArray(R.array.distances);
        Context actionbarContext = actionbar.getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(actionbarContext, R.array.distances, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionbar.setListNavigationCallbacks(list, this);
        actionbar.hide();
	}
	
	/**
	 * Snarf the menu key.
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (DEVICE_VERSION < HONEYCOMB_VERSION) 
				openOptionsMenu();
			if (actionbar.isShowing()) actionbar.hide();
			else actionbar.show();
			return true; //always eat it!
		}
		return super.onKeyDown(keyCode, event); 
	} 

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (itemPosition == MenuSpinnerItems.HALF_MILES_ITEM) {
			distance = 0.5;
			listFragment.setSearchDistance(distance);
		}
		if (itemPosition == MenuSpinnerItems.ONE_MILES_ITEM) {
			distance = 1;
			listFragment.setSearchDistance(distance);
		}
		if (itemPosition == MenuSpinnerItems.TWO_MILES_ITEM) {
			distance = 2;
			listFragment.setSearchDistance(distance);
		}
		if (itemPosition == MenuSpinnerItems.FIVE_MILES_ITEM) {
			distance = 5;
			listFragment.setSearchDistance(distance);
		}
		if (itemPosition == MenuSpinnerItems.TEN_MILES_ITEM) {
			distance = 10;
			listFragment.setSearchDistance(distance);
		}
		return false;
	}

	@Override
	public void triggerSwipe(boolean fromLeftToRight) {
		if (fromLeftToRight) {
			swipeLeftToRight();
		} else swipeRightToLeft();
	}

}
