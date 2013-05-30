package com.sapenguins.thecornerapp;

import com.google.analytics.tracking.android.EasyTracker;
import com.sapenguins.thecornerapp.EventListFragment.Swipe;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public class EventListActivity extends FragmentActivity implements Swipe{

	private static final String[] CONTENT = new String[] { "All", "Entertainment", "Education", "Service", "Organization"};
	public static final int DEVICE_VERSION = android.os.Build.VERSION.SDK_INT;
	public static final int HONEYCOMB_VERSION = android.os.Build.VERSION_CODES.HONEYCOMB;
	
	EventListFragment listFragment;
	Context context;
	
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
		setContentView(R.layout.event_list_activity);
		context = this;
		distance = 0.5;
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

	@Override
	public void triggerSwipe(boolean fromLeftToRight) {
		if (fromLeftToRight) {
			swipeLeftToRight();
		} else swipeRightToLeft();
	}

}
