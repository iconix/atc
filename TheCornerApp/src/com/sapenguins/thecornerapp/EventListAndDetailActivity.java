package com.sapenguins.thecornerapp;

import java.lang.reflect.Field;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.sapenguins.thecornerapp.EventDetailFragment.OnClickPass;
import com.sapenguins.thecornerapp.EventListFragment.OnDetailPass;
import com.sapenguins.thecornerapp.constants.MenuSpinnerItems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;

public class EventListAndDetailActivity extends SherlockFragmentActivity implements OnDetailPass, OnClickPass, ActionBar.OnNavigationListener{

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.event_list_and_detail);
		context = this;
		initActionBar();
		detailFragment = (EventDetailFragment) getSupportFragmentManager().findFragmentById(R.id.event_detail_fragment);
		listFragment = (EventListFragment) getSupportFragmentManager().findFragmentById(R.id.list_event_fragment);
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
		detailFragment.dislayEventDetail(eventId, title, imageUrl);
		eventId = id;
		eventTitle = title;
		eventImg = imageUrl;
		eventDesc = desc;
		eventLongitude = longitude;
		eventLatitude = latitude;
		eventDistance = distance;	
	}
	
	public void onClickPass() {
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

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (itemPosition == MenuSpinnerItems.HALF_MILES_ITEM) 
			listFragment.getBasicEventsFromDB(0.5);
		if (itemPosition == MenuSpinnerItems.ONE_MILES_ITEM) 
			listFragment.getBasicEventsFromDB(1);
		if (itemPosition == MenuSpinnerItems.TWO_MILES_ITEM) 
			listFragment.getBasicEventsFromDB(2);
		if (itemPosition == MenuSpinnerItems.FIVE_MILES_ITEM) 
			listFragment.getBasicEventsFromDB(5);
		if (itemPosition == MenuSpinnerItems.TEN_MILES_ITEM) 
			listFragment.getBasicEventsFromDB(10);
		return false;
	}
	
	

}
