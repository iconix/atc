package com.sapenguins.thecornerapp;

import com.google.analytics.tracking.android.EasyTracker;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

public class EventListActivity extends FragmentActivity{

	//private static final String[] CONTENT = new String[] { "All", "Entertainment", "Education", "Service", "Organization"};
	
	EventListFragment listFragment;
	Context context;
	
	View categoryAll;
	View categoryEntertainment;
	View categoryEducation;
	View categoryService;
	View categoryOrganization;
	
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
		setContentView(R.layout.event_list_activity);
		context = this;
		listFragment = (EventListFragment) getSupportFragmentManager().findFragmentById(R.id.list_event_fragment);
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
		menuContainer = findViewById(R.id.event_list_menu_selection_container);
		menuCollapse = findViewById(R.id.event_list_menu_collapse_bar);
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
		categoryIcon = (ImageView)findViewById(R.id.event_list_menu_selection);
		settingIcon = (ImageView)findViewById(R.id.event_list_setting);
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
		viewMap = findViewById(R.id.event_list_menu_map_view);
		viewEvents = findViewById(R.id.event_list_menu_event_view);
		viewDeals = findViewById(R.id.event_list_menu_deal_view);
		viewFavorites = findViewById(R.id.event_list_menu_favorite_view);
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
				menuContainer.setVisibility(View.GONE);
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
				Intent intent = new Intent(context, FavoriteListActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * Init the category components and their listeners
	 */
	private void initCategories() {
		categoryAll = findViewById(R.id.event_list_menu_category_all);
		categoryEntertainment = findViewById(R.id.event_list_menu_category_entertainment);
		categoryEducation = findViewById(R.id.event_list_menu_category_education);
		categoryService = findViewById(R.id.event_list_menu_category_service);
		categoryOrganization = findViewById(R.id.event_list_menu_category_organization);
		setCategoryAllOnClickListener();
		setCategoryEntertainmentOnClickListener();
		setCategoryEducationOnClickListener();
		setCategoryServiceOnClickListener();
		setCategoryOrganizationOnClickListener();
	}
	
	/**
	 * Reset all the category background to transparent
	 */
	private void resetCategoryBackground() {
		categoryAll.setBackgroundColor(0x00ffffff);
		categoryEntertainment.setBackgroundColor(0x00ffffff);
		categoryEducation.setBackgroundColor(0x00ffffff);
		categoryService.setBackgroundColor(0x00ffffff);
		categoryOrganization.setBackgroundColor(0x00ffffff);
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
	private void setCategoryEducationOnClickListener() {
		categoryEducation.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resetCategoryBackground();
				categoryEducation.setBackgroundColor(0x22ffffff);
				menuContainer.setVisibility(View.GONE);
				listFragment.setSearchCategory("Education");
			}
		});
	}
	
	/**
	 * Handle the event when the category service is clicked
	 */
	private void setCategoryServiceOnClickListener() {
		categoryService.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resetCategoryBackground();
				categoryService.setBackgroundColor(0x22ffffff);
				menuContainer.setVisibility(View.GONE);
				listFragment.setSearchCategory("Service");
			}
		});
	}
	
	/**
	 * Handle the event when the category organization is clicked
	 */
	private void setCategoryOrganizationOnClickListener() {
		categoryOrganization.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resetCategoryBackground();
				categoryOrganization.setBackgroundColor(0x22ffffff);
				menuContainer.setVisibility(View.GONE);
				listFragment.setSearchCategory("Organization");
			}
		});
	}
}
