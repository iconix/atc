package com.sapenguins.thecornerapp;

import com.google.analytics.tracking.android.EasyTracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

public class DealListActivity extends FragmentActivity{

	//private static final String[] CONTENT = new String[] { "All", "Entertainment", "Shopping", "Food"};
	
	DealListFragment listFragment;
	Context context;
	
	View categoryAll;
	View categoryEntertainment;
	View categoryShopping;
	View categoryFood;
	
	View viewMap;
	View viewEvents;
	View viewDeals;
	View viewFavorites;
	
	View menuContainer;
	View menuCollapse;
	
	ImageView categoryIcon;
	ImageView settingIcon;
	
	View mainView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deal_list_activity);
		context = this;
		listFragment = (DealListFragment) getSupportFragmentManager().findFragmentById(R.id.list_deal_fragment);
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
		menuContainer = findViewById(R.id.deal_list_menu_selection_container);
		menuCollapse = findViewById(R.id.deal_list_menu_collapse_bar);
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
		categoryIcon = (ImageView)findViewById(R.id.deal_list_menu_selection);
		settingIcon = (ImageView)findViewById(R.id.deal_list_setting);
		setCategoryIconOnClickListener();
		setSettingIconOnClickListener();
	}
	
	/**
	 * Handle the event when the category icon is clicked
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
	 * Handle the event when the setting icon is clicked
	 */
	private void setSettingIconOnClickListener() {
		settingIcon.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}		
		});
	}
	
	/**
	 * Init view components and their listeners
	 */
	private void initViews() {
		viewMap = findViewById(R.id.deal_list_menu_map_view);
		viewEvents = findViewById(R.id.deal_list_menu_event_view);
		viewDeals = findViewById(R.id.deal_list_menu_deal_view);
		viewFavorites = findViewById(R.id.deal_list_menu_favorite_view);
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
	 * Handle click event when the deals item is pressed
	 */
	private void setViewDealsOnClickListener() {
		viewDeals.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				menuContainer.setVisibility(View.GONE);
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
				Intent intent = new Intent(context, FavoriteListActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * Init the category components and their listeners
	 */
	private void initCategories() {
		categoryAll = findViewById(R.id.deal_list_menu_category_all);
		categoryEntertainment = findViewById(R.id.deal_list_menu_category_entertainment);
		categoryShopping = findViewById(R.id.deal_list_menu_category_shopping);
		categoryFood = findViewById(R.id.deal_list_menu_category_food);
		setCategoryAllOnClickListener();
		setCategoryEntertainmentOnClickListener();
		setCategoryFoodOnClickListener();
		setCategoryShoppingOnClickListener();
	}
	
	/**
	 * Reset all the category background to transparent
	 */
	private void resetCategoryBackground() {
		categoryAll.setBackgroundColor(0x00ffffff);
		categoryEntertainment.setBackgroundColor(0x00ffffff);
		categoryFood.setBackgroundColor(0x00ffffff);
		categoryShopping.setBackgroundColor(0x00ffffff);
	}
	
	/**
	 * Handle the event when the category all is clicked
	 */
	private void setCategoryAllOnClickListener() {
		categoryAll.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resetCategoryBackground();
				categoryAll.setBackgroundColor(0x22ffffff);
				menuContainer.setVisibility(View.GONE);
				listFragment.setSearchCategory("All");
			}
		});
	}
	
	/**
	 * Handle the event when the category entertainment is clicked
	 */
	private void setCategoryEntertainmentOnClickListener() {
		categoryEntertainment.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resetCategoryBackground();
				categoryEntertainment.setBackgroundColor(0x22ffffff);
				menuContainer.setVisibility(View.GONE);
				listFragment.setSearchCategory("Entertainment");
			}
		});
	}
	
	/**
	 * Handle the event when the category education is clicked
	 */
	private void setCategoryShoppingOnClickListener() {
		categoryShopping.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resetCategoryBackground();
				categoryShopping.setBackgroundColor(0x22ffffff);
				menuContainer.setVisibility(View.GONE);
				listFragment.setSearchCategory("Shopping");
			}
		});
	}
	
	/**
	 * Handle the event when the category service is clicked
	 */
	private void setCategoryFoodOnClickListener() {
		categoryFood.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resetCategoryBackground();
				categoryFood.setBackgroundColor(0x22ffffff);
				menuContainer.setVisibility(View.GONE);
				listFragment.setSearchCategory("Food");
			}
		});
	}
}
