package com.sapenguins.thecornerapp;

import com.google.analytics.tracking.android.EasyTracker;
import com.sapenguins.thecornerapp.FavoriteDetailFragment.OnClickPass;
import com.sapenguins.thecornerapp.FavoriteListFragment.OnDetailPass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public class FavoriteListAndDetailActivity extends FragmentActivity implements OnDetailPass, OnClickPass {

	FavoriteDetailFragment detailFragment;
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
	boolean currentIsOngoing; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite_list_and_detail);
		context = this;
		detailFragment = (FavoriteDetailFragment) getSupportFragmentManager().findFragmentById(R.id.favorite_detail_fragment);
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
				if (!currentIsOngoing) {
					pastText.setTypeface(null, Typeface.BOLD);
					ongoingText.setTypeface(null, Typeface.NORMAL);
					pastUnderline.setBackgroundColor(0xffa2a2ff);
					ongoingUnderline.setBackgroundColor(0xffcfcfcf);
					listFragment.getBasicFavoritesFromDB(false);
					currentIsOngoing = true;
				}
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
				if (currentIsOngoing) {
					pastText.setTypeface(null, Typeface.NORMAL);
					ongoingText.setTypeface(null, Typeface.BOLD);
					pastUnderline.setBackgroundColor(0xffcfcfcf);
					ongoingUnderline.setBackgroundColor(0xffa2a2ff);
					listFragment.getBasicFavoritesFromDB(true);
					currentIsOngoing = false;
				}
			}
		});
	}
	
	//---------------------------------------
	//--HANDLE INTERACTIONS BETWEEN FRAGMENT-
	//---------------------------------------
	//@Override
	public void onDetailPass(int id, String title, String imageUrl, String desc, double longitude, double latitude, String distance) {
		detailFragment.dislayFavoriteDetail(id, title, imageUrl);
		favoriteId = id;
		favoriteTitle = title;
		favoriteImg = imageUrl;
		favoriteDesc = desc;
		favoriteLongitude = longitude;
		favoriteLatitude = latitude;
		favoriteDistance = distance;	
	}
	
	public void onClickPass() {
		Intent intent = new Intent(context, FavoriteFullDetailActivity.class);	
		intent.putExtra("favoriteId", favoriteId);
		intent.putExtra("favoriteTitle", favoriteTitle);
		intent.putExtra("favoriteImg", favoriteImg);
		intent.putExtra("favoriteDesc", favoriteDesc);
		intent.putExtra("favoriteLongitude", favoriteLongitude);
		intent.putExtra("favoriteLatitude", favoriteLatitude);
		intent.putExtra("favoriteDistance", favoriteDistance);
		startActivity(intent);
	}
}
