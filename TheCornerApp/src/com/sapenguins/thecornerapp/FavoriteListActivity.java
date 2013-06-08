package com.sapenguins.thecornerapp;

import com.google.analytics.tracking.android.EasyTracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

public class FavoriteListActivity extends FragmentActivity{

	FavoriteListFragment listFragment;
	Context context;
	//listFragment.getBasicFavoritesFromDB(true);
	
	View categoryPast;
	View categoryOngoing;
	
	View viewMap;
	View viewEvents;
	View viewDeals;
	View viewFavorites;
	
	View menuContainer;
	View menuCollapse;
	
	ImageView categoryIcon;
	
	View mainView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite_list_activity);
		context = this;
		listFragment = (FavoriteListFragment) getSupportFragmentManager().findFragmentById(R.id.list_favorite_fragment);
		initMenuSelection();
		initTabBarComponents();
		initCategories();
		initViews();
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
	 * Init the menu selection, the left sidebar tab
	 */
	private void initMenuSelection() {
		menuContainer = findViewById(R.id.favorite_list_menu_selection_container);
		menuCollapse = findViewById(R.id.favorite_list_menu_collapse_bar);
		menuContainer.setVisibility(View.GONE);
		menuCollapse.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				menuContainer.setVisibility(View.GONE);
			}
		});
	}
	

	
	/**
	 * Init the tab bar component and its listeners
	 */
	private void initTabBarComponents() {
		categoryIcon = (ImageView)findViewById(R.id.favorite_list_menu_selection);
		setCategoryIconOnClickListener();
	}
	
	/**
	 * Handle the favorite when the category icon is clicked
	 */
	private void setCategoryIconOnClickListener() {
		categoryIcon.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				menuContainer.setVisibility(View.VISIBLE);
			}		
		});
	}
	
	/**
	 * Init view components and their listeners
	 */
	private void initViews() {
		viewMap = findViewById(R.id.favorite_list_menu_map_view);
		viewEvents = findViewById(R.id.favorite_list_menu_event_view);
		viewDeals = findViewById(R.id.favorite_list_menu_deal_view);
		viewFavorites = findViewById(R.id.favorite_list_menu_favorite_view);
		setViewMapOnClickListener();
		setViewEventsOnClickListener();
		setViewDealsOnClickListener();
		setViewFavoritesOnClickListener();
	}
	
	/**
	 * Handle click event when the map item is pressed
	 */
	private void setViewMapOnClickListener() {
		viewMap.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AdsMapActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * Handle click event when the events item is pressed
	 */
	private void setViewEventsOnClickListener() {
		viewEvents.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, EventListActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * Handle click event when the favorites item is pressed
	 */
	private void setViewDealsOnClickListener() {
		viewDeals.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, DealListActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * Handle click event when the favorites item is pressed
	 */
	private void setViewFavoritesOnClickListener() {
		viewFavorites.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				menuContainer.setVisibility(View.GONE);
			}
		});
	}
	
	/**
	 * Init the category components and their listeners
	 */
	private void initCategories() {
		categoryOngoing = findViewById(R.id.favorite_list_menu_category_ongoing);
		categoryPast = findViewById(R.id.favorite_list_menu_category_past);
		setCategoryAllOnClickListener();
		setCategoryEntertainmentOnClickListener();
	}
	
	/**
	 * Reset all the category background to transparent
	 */
	private void resetCategoryBackground() {
		categoryOngoing.setBackgroundColor(0x00ffffff);
		categoryPast.setBackgroundColor(0x00ffffff);
	}
	
	/**
	 * Handle the event when the category all is clicked
	 */
	private void setCategoryAllOnClickListener() {
		categoryOngoing.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resetCategoryBackground();
				categoryOngoing.setBackgroundColor(0x22ffffff);
				menuContainer.setVisibility(View.GONE);
				listFragment.getBasicFavoritesFromDB(true);
			}
		});
	}
	
	/**
	 * Handle the event when the category entertainment is clicked
	 */
	private void setCategoryEntertainmentOnClickListener() {
		categoryPast.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resetCategoryBackground();
				categoryPast.setBackgroundColor(0x22ffffff);
				menuContainer.setVisibility(View.GONE);
				listFragment.getBasicFavoritesFromDB(true);
			}
		});
	}
}
