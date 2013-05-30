package com.sapenguins.thecornerapp;

import com.google.analytics.tracking.android.EasyTracker;
import com.sapenguins.thecornerapp.FavoriteListFragment.Swipe;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public class FavoriteListActivity extends FragmentActivity implements Swipe{

	FavoriteListFragment listFragment;
	Context context;
	
	int favoriteId;
	String favoriteTitle;
	String favoriteImg;
	String favoriteDesc;
	String favoriteDistance;
	double favoriteLongitude;
	double favoriteLatitude;
	
	//component of the tab bar
	View tabPast;
	TextView pastText;
	View pastUnderline;
	View tabOngoing;
	TextView ongoingText;
	View ongoingUnderline;
	boolean currentIsOngoing = true; 
	
	View favoriteDetailContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite_list_activity);
		context = this;
		listFragment = (FavoriteListFragment) getSupportFragmentManager().findFragmentById(R.id.list_favorite_fragment);
		setupTabBarComponents();
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
	 * Setup the component for the tab bar
	 */
	private void setupTabBarComponents() {
		tabPast = findViewById(R.id.favorite_tab_bar_past);
		pastText = (TextView) findViewById(R.id.favorite_tab_bar_past_text);
		pastUnderline = findViewById(R.id.favorite_tab_bar_past_text_underline);
		tabOngoing = findViewById(R.id.favorite_tab_bar_ongoing);
		ongoingText = (TextView) findViewById(R.id.favorite_tab_bar_ongoing_text);
		ongoingUnderline = findViewById(R.id.favorite_tab_bar_ongoing_text_underline);
		
		//handle swipe action
		setTabPastClickListener();
		setTabOngoingClickListener();
	}
	
	/**
	 * set the listener for then the tab past is click
	 */
	private void setTabPastClickListener() {
		tabPast.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				goToPastList();
			}
		});
	}
	
	/**
	 * set the listener for then the tab event is click
	 */
	private void setTabOngoingClickListener() {
		tabOngoing.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				goToOngoingList();
			}
		});
	}
	
	/**
	 * Go to the ongoing list view
	 */
	private void goToOngoingList() {
		if (!currentIsOngoing) {
			pastText.setTypeface(null, Typeface.NORMAL);
			ongoingText.setTypeface(null, Typeface.BOLD);
			pastUnderline.setBackgroundColor(0xff222222);
			ongoingUnderline.setBackgroundColor(0xff444444);
			listFragment.getBasicFavoritesFromDB(true);
			currentIsOngoing = true;
		}
	}
	
	/**
	 * Go to the past list view
	 */
	private void goToPastList() {
		if (currentIsOngoing) {
			pastText.setTypeface(null, Typeface.BOLD);
			ongoingText.setTypeface(null, Typeface.NORMAL);
			pastUnderline.setBackgroundColor(0xff444444);
			ongoingUnderline.setBackgroundColor(0xff222222);
			listFragment.getBasicFavoritesFromDB(false);
			currentIsOngoing = false;
		}
	}
	
	//---------------------------------------
	//--HANDLE INTERACTIONS BETWEEN FRAGMENT-
	//---------------------------------------
	
	@Override
	public void triggerSwipe() {
		if (currentIsOngoing)  goToPastList();
        else goToOngoingList();
	}
      
}
