package com.sapenguins.thecornerapp;

import java.lang.reflect.Field;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.analytics.tracking.android.EasyTracker;
import com.sapenguins.thecornerapp.EventListFragment.OnDetailPass;
import com.sapenguins.thecornerapp.EventListFragment.Swipe;
import com.sapenguins.thecornerapp.constants.MenuSpinnerItems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventListAndDetailActivity extends SherlockFragmentActivity implements OnDetailPass, Swipe, ActionBar.OnNavigationListener{

	private static final String[] CONTENT = new String[] { "All", "Entertainment", "Education", "Service", "Organization"};
	public static final int DEVICE_VERSION = android.os.Build.VERSION.SDK_INT;
	public static final int HONEYCOMB_VERSION = android.os.Build.VERSION_CODES.HONEYCOMB;
	
	EventDetailFragment detailFragment;
	EventListFragment listFragment;
	ActionBar actionbar;
	Context context;
	String[] maximumDistances;
	
	MenuItem homeItem;
	
	int eventId;
	String eventTitle;
	String eventImg;
	String eventDesc;
	String eventDistance;
	double eventLongitude;
	double eventLatitude;
	
	//views for the tab bar
	View itemOne;
	TextView itemOneText;
	TextView itemTwoText;
	View itemThree;
	TextView itemThreeText;
	
	View eventDetailContainer;
	
	String category;
	double distance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.event_list_and_detail);
		context = this;
		distance = 0.5;
		initActionBar();
		detailFragment = (EventDetailFragment) getSupportFragmentManager().findFragmentById(R.id.event_detail_fragment);
		listFragment = (EventListFragment) getSupportFragmentManager().findFragmentById(R.id.list_event_fragment);
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
		itemOne = findViewById(R.id.event_tab_bar_item_one);
		itemThree = findViewById(R.id.event_tab_bar_item_three);
		itemOneText = (TextView)findViewById(R.id.event_tab_bar_item_one_text);
		itemTwoText = (TextView)findViewById(R.id.event_tab_bar_item_two_text);
		itemThreeText = (TextView)findViewById(R.id.event_tab_bar_item_three_text);
		
		//to handle the swipe action
		eventDetailContainer = findViewById(R.id.event_detail_fragment_container);
		category = CONTENT[0];
		setupTab(category);
		setupItemOneClickListener();
		setupItemThreeClickListener();
		setupEventDetailContainerSwipeListener();
		setupEventDetailContainerClickListener();
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
	 * Set up the listener for when the event detail is swiped
	 */
	private void setupEventDetailContainerSwipeListener() {
		eventDetailContainer.setOnTouchListener(new View.OnTouchListener() {
			private float downX, upX;
			private static final int MIN_DISTANCE = 150;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		            downX = event.getX();
		            return false; // allow other events like Click to be processed
		        case MotionEvent.ACTION_UP:
		            upX = event.getX();	
		            float deltaX = downX - upX;
		            // horizontal swipe detection
		            if (Math.abs(deltaX) > MIN_DISTANCE) {
		                // left or right
		            	if (deltaX < 0) {    
		                    swipeLeftToRight();
		                    return true;
		                }
		                if (deltaX > 0) {    
		                    swipeRightToLeft();
		                    return true;
		                }
		            }
		            return false;
	        }
	        return false;
			}
		});
	}
	
	/**
	 * Set up the listener for when the event detail frame clicked
	 */
	private void setupEventDetailContainerClickListener() {
		eventDetailContainer.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				if (eventId != -1) {
					Intent intent = new Intent(context, EventFullDetailActivity.class);	
					intent.putExtra("eventId", eventId);
					intent.putExtra("eventTitle", eventTitle);
					intent.putExtra("eventImg", eventImg);
					intent.putExtra("eventDesc", eventDesc);
					intent.putExtra("eventLongitude", eventLongitude);
					intent.putExtra("eventLatitude", eventLatitude);
					intent.putExtra("eventDistance", eventDistance);
					startActivity(intent);
				}
			}
		});
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
	

	//---------------------------------------
	//--HANDLE INTERACTIONS BETWEEN FRAGMENT-
	//---------------------------------------
	//@Override
	public void onDetailPass(int id, String title, String imageUrl, String desc, double longitude, double latitude, String distance) {
		detailFragment.dislayEventDetail(id, title, imageUrl);
		eventId = id;
		eventTitle = title;
		eventImg = imageUrl;
		eventDesc = desc;
		eventLongitude = longitude;
		eventLatitude = latitude;
		eventDistance = distance;	
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
