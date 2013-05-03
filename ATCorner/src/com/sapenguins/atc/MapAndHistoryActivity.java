package com.sapenguins.atc;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import objects.PinMarkerObj;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.model.LatLng;
import com.sapenguins.atc.HistoryListFragment.OnCoordinatePass;
import com.sapenguins.atc.HistoryListFragment.OnDetailPass;

import staticVariables.PreferenceValue;
import staticVariables.SharedPreference;
import supports.TimeFrame;
import templates.CustomMenu;
import templates.DropDownNavigationMenuAdapter;
import templates.CustomMenu.OnMenuItemSelectedListener;
import templates.CustomMenuItem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class MapAndHistoryActivity extends SherlockFragmentActivity implements OnMenuItemSelectedListener, OnCoordinatePass, OnDetailPass, ActionBar.OnNavigationListener, OnDateSetListener, OnTimeSetListener {
	public static final int DEVICE_VERSION = android.os.Build.VERSION.SDK_INT;
	public static final int HONEYCOMB_VERSION = android.os.Build.VERSION_CODES.HONEYCOMB;
	MenuItem homeButton;
	MenuItem pinButton;
	MenuItem preferenceButton;
	MapFragment mapFragment;

	ActionBar actionBar;
	Context context;
	int[] dropdownIconResources = {R.drawable.action_bar_map_icon_48, R.drawable.action_bar_promotion_icon_48};
	String[] dropdownText = {"History", "Promotion"};

	Spinner period; 
	TextView fromDate;
	TextView fromTime;
	ImageView leftTimeArrow;
	ImageView rightTimeArrow;

	int fromYear;
	int fromMonth;
	int fromDay;
	int fromHour;
	int fromMinute;

	long beginPeriod;
	long endPeriod;
	int timePeriodOption;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_and_map);
		setCurrentView(PreferenceValue.VIEW_MAP_AND_HISTORY);
		mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_history_fragment);
		
		initMenubar();

		initActionBar();
		actionBar.hide();

		initTimeNavigationBar();
	}

	/** 
	 * Initiate the values in time navigation bar when the
	 * activity is created
	 */
	private void initTimeNavigationBar() {		
		initTimeNavigationPeriodSpinner();
		initTimeNavigationDateAndTime();
		initTimeNavigationArrowButtons();
		displayMapInCurrentTimePeriod();
	}

	/**
	 * Set up the time period spinner. If the user is in the session
	 * then keep that session information.
	 */
	private void initTimeNavigationPeriodSpinner() {
		//get the saved period setting from the session.
		timePeriodOption = getTimePeriodOption();
		beginPeriod = getBeginTime();
		endPeriod = computeEndTime(beginPeriod, timePeriodOption);

		period = (Spinner) findViewById(R.id.listmap_time_navigation_time_gap_selection);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.time_navigation_dropdown, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		period.setAdapter(adapter);
		period.setSelection(timePeriodOption, true);
		setTimeNavigationPeriodSpinnerListener();
	}

	/**
	 * Add the listener for the time navigation period spinner
	 */
	private void setTimeNavigationPeriodSpinnerListener() {
		period.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int periodOption, long arg3) {
				timePeriodOption = periodOption;
				endPeriod = computeEndTime(beginPeriod, timePeriodOption);
				setTimePeriodOption(timePeriodOption);
				displayMapInCurrentTimePeriod();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * Set up the date and time text field
	 */
	private void initTimeNavigationDateAndTime() {
		fromDate = (TextView) findViewById(R.id.listmap_time_navigation_from_day_selection);
		fromTime = (TextView) findViewById(R.id.listmap_time_navigation_from_time_selection);

		String fromDateString = TimeFrame.getDateInString(beginPeriod);
		String fromTimeString = TimeFrame.getTimeInString(beginPeriod);

		fromDate.setText(fromDateString);
		fromTime.setText(fromTimeString);

		//save the begin to the preferences
		setBeginTime(beginPeriod);

		setFromDateClickListener();
		setFromTimeClickListener();
	}

	/**
	 * Set the click listener for the from date text field
	 */
	private void setFromDateClickListener() {
		fromDate.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				fromYear = TimeFrame.getYear(beginPeriod);
				fromMonth = TimeFrame.getMonth(beginPeriod);
				fromDay = TimeFrame.getDay(beginPeriod);
				DialogFragment dateFragment = new DatePickerFragment();
				dateFragment.show(getSupportFragmentManager(), "Date Picker");
			}
		});
	}

	/**
	 * Set the click listener for the from time text field
	 */
	private void setFromTimeClickListener() {
		fromTime.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View v) {
				fromHour = TimeFrame.getHour(beginPeriod);
				fromMinute = TimeFrame.getMinute(beginPeriod);
				DialogFragment timeFragment = new TimePickerFragment();
				timeFragment.show(getSupportFragmentManager(), "Time Picker");
			}		
		});
	}

	/**
	 * Create a new date picker fragment. 
	 * Display this fragment whenever the date text field is selected
	 */
	public class DatePickerFragment extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Create a new instance of DatePickerDialog and return it
			// Month start with 0. Weird, but nothing we can do
			return new DatePickerDialog(getActivity(), (SingleMapViewActivity)getActivity(), fromYear, fromMonth -1, fromDay);
		}
	}

	/**
	 * When the date is set, the map should change and the date in 
	 * the time navigation bar should also change accordingly
	 */
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		String yearStr = String.valueOf(year);
		String monthStr = String.valueOf(monthOfYear + 1);
		if (monthOfYear < 9) monthStr = "0" + monthStr;
		String dayStr = String.valueOf(dayOfMonth);
		if (dayOfMonth < 10) dayStr = "0" + dayStr;
		String timeStr = String.valueOf(beginPeriod).substring(8);
		String newBeginPeriod = yearStr + monthStr + dayStr + timeStr;
		beginPeriod = Long.valueOf(newBeginPeriod);
		endPeriod = computeEndTime(beginPeriod, timePeriodOption);
		displayMapInCurrentTimePeriod();

		//update the from day text view
		fromDate.setText(TimeFrame.getDateInString(beginPeriod));	
		//set the begin period in the preference
		setBeginTime(beginPeriod);
	}

	/**
	 * Create a new time picker fragment. 
	 * Display this fragment whenever the date text field is selected
	 */
	public class TimePickerFragment extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Create a new instance of DatePickerDialog and return it
			// Month start with 0. Weird, but nothing we can do
			return new TimePickerDialog(getActivity(), (SingleMapViewActivity)getActivity(), fromHour, fromMinute, false);
		}
	}

	/**
	 * When the time is set, the map should change and the time in 
	 * the time navigation bar should also change accordingly
	 */
	public void onTimeSet(TimePicker view, int hour, int minute) {
		String hourStr = String.valueOf(hour);
		if(hour < 10) hourStr = "0"+ hourStr;
		String minuteStr = String.valueOf(minute);
		if (minute < 10) minuteStr = "0" + minuteStr;

		String dayStr = String.valueOf(beginPeriod).substring(0, 8);
		String secondStr = String.valueOf(beginPeriod).substring(12);
		String newBeginPeriod = dayStr + hourStr + minuteStr + secondStr;
		beginPeriod = Long.valueOf(newBeginPeriod);
		endPeriod = computeEndTime(beginPeriod, timePeriodOption);
		displayMapInCurrentTimePeriod();
		
		//update the from day text view
		fromTime.setText(TimeFrame.getTimeInString(beginPeriod));		
		//set the begin period in the preference
		setBeginTime(beginPeriod);
	}

	/**
	 * Set up the listener for the arrow button in the time navigation bar
	 */
	private void initTimeNavigationArrowButtons() {
		leftTimeArrow = (ImageView) findViewById(R.id.listmap_time_navigation_left_arrow_icon);
		rightTimeArrow = (ImageView) findViewById(R.id.listmap_time_navigation_right_arrow_icon);
		setLeftTimeArrowClickListener();
		setRightTimeArrowClickListener();
	}

	/**
	 * Set the listener for the left time arrow
	 */
	private void setLeftTimeArrowClickListener() {
		leftTimeArrow.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				endPeriod = beginPeriod;
				beginPeriod = computePriorTime(endPeriod, timePeriodOption);

				String fromDateString = TimeFrame.getDateInString(beginPeriod);
				String fromTimeString = TimeFrame.getTimeInString(beginPeriod);

				fromDate.setText(fromDateString);
				fromTime.setText(fromTimeString);

				//save the begin to the preferences
				setBeginTime(beginPeriod);
				//display new information
				displayMapInCurrentTimePeriod();
			}	
		});
	}

	/**
	 * Set the listener for the right time arrow
	 * The only distinction is that for the right time arrow.
	 * If the begin time of period is already greater than the current time
	 * Then the action is ignored, and make the background of image lighter to say this
	 */
	private void setRightTimeArrowClickListener() {
		rightTimeArrow.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				long currentTime = Long.valueOf(getCurrentTime());
				if (currentTime <= endPeriod) return;
				else {
					beginPeriod = endPeriod;
					endPeriod = computeEndTime(beginPeriod, timePeriodOption);

					String fromDateString = TimeFrame.getDateInString(beginPeriod);
					String fromTimeString = TimeFrame.getTimeInString(beginPeriod);

					fromDate.setText(fromDateString);
					fromTime.setText(fromTimeString);

					//save the begin to the preferences
					setBeginTime(beginPeriod);
					//display new information
					displayMapInCurrentTimePeriod();
				}
			}

		});
	}

	/**
	 * Display map in the current time period
	 */
	private void displayMapInCurrentTimePeriod() {
		mapFragment.clearMap();
		mapFragment.displayVisitedLocation(beginPeriod, endPeriod);
		mapFragment.displayPinnedLocation();
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
			startActivity(new Intent(getApplicationContext(), PromotionListActivity.class));
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
				mapFragment.addPinToCurrentLocation();
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
		cmi.setCurrent(true);
		menuItems.add(cmi);

		cmi = new CustomMenuItem();
		cmi.setCaption("List & Detail");
		cmi.setImageResourceId(R.drawable.history);
		cmi.setId(DETAIL_ITEM);
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
			mMenu.show(findViewById(R.id.single_map_fragment));
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
		case DETAIL_ITEM:
			startActivity(new Intent(getApplicationContext(), DetailAndHistoryActivity.class));
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
	 * Get the current time period option
	 * @return time period option
	 */
	private int getTimePeriodOption() {
		return getPreferenceIntValue(SharedPreference.PREFERENCE, SharedPreference.TIME_PERIOD_PREFERENCE);
	}

	/**
	 * Set the current time period option
	 * @param time period option
	 */
	private void setTimePeriodOption(int periodOption) {
		updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.TIME_PERIOD_PREFERENCE, periodOption);
	}

	/**
	 * Get the begin time
	 * @return begin time
	 */
	private long getBeginTime() {
		long beginTime = getPreferenceLongValue(SharedPreference.PREFERENCE, SharedPreference.TIME_BEGIN_PREFERENCE);
		if (beginTime == 0) { // no preference for the begin time is set yet
			long currentTime = Long.valueOf(getCurrentTime());
			int timePeriodOption = getTimePeriodOption();
			long timePeriod = getTimePeriod(timePeriodOption);
			beginTime = TimeFrame.computePriorTime(currentTime, timePeriod);
			setBeginTime(beginTime);
		}
		return beginTime;
	}

	/**
	 * Set the begin time
	 * @param begin time
	 */
	private void setBeginTime(long beginTime) {
		updateSystemPreferences(SharedPreference.PREFERENCE, SharedPreference.TIME_BEGIN_PREFERENCE, beginTime);
	}

	/**
	 * Extend compute prior time in TimeFrame to accept the option as
	 * argument for time gap
	 */
	private long computePriorTime(long currentTime, int periodOption) {
		return TimeFrame.computePriorTime(currentTime, getTimePeriod(periodOption));
	}

	/**
	 * Extend compute next time in TimeFrame to accept the option as
	 * argument for time gap
	 */
	private long computeEndTime(long currentTime, int periodOption) {
		return TimeFrame.computeEndTime(currentTime, getTimePeriod(periodOption));
	}

	/**
	 * Get the time period from the time period option
	 * @param time period option
	 * @return time period
	 */
	private long getTimePeriod(int periodOption) {
		switch(periodOption) {
		case PreferenceValue.SPINNER_ONE_YEAR:
			return TimeFrame.ONE_YEAR;
		case PreferenceValue.SPINNER_THREE_MONTHS:
			return TimeFrame.THREE_MONTHS;
		case PreferenceValue.SPINNER_ONE_MONTH:
			return TimeFrame.ONE_MONTH;
		case PreferenceValue.SPINNER_ONE_WEEK:
			return TimeFrame.ONE_WEEK;
		case PreferenceValue.SPINNER_ONE_DAY:
			return TimeFrame.ONE_DAY;
		case PreferenceValue.SPINNER_SIX_HOURS:
			return TimeFrame.SIX_HOURS;
		case PreferenceValue.SPINNER_THREE_HOURS:
			return TimeFrame.THREE_HOURS;
		case PreferenceValue.SPINNER_ONE_HOUR:
			return TimeFrame.ONE_HOUR;
		case PreferenceValue.SPINNER_FIFTEEN_MINUTES:
			return TimeFrame.FIFTEEN_MINUTES;
		case PreferenceValue.SPINNER_FIVE_MINUTES:
			return TimeFrame.FIVE_MINUTES;
		}
		return TimeFrame.ONE_DAY;
	}

	/**
	 * Get the preference value of a given key stored in a given preference list
	 * @param preference
	 * @param key
	 * @return the long value of the key
	 */
	private long getPreferenceLongValue(String preference, String key) {
		SharedPreferences settings = getSharedPreferences(preference, 0);
		return settings.getLong(key, 0);
	}

	/**
	 * Get the preference value of a given key stored in a given preference list
	 * @param preference
	 * @param key
	 * @return the int value of the key
	 */
	private int getPreferenceIntValue(String preference, String key) {
		SharedPreferences settings = getSharedPreferences(preference, 0);
		return settings.getInt(key, 0);
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

	/**
	 * Modify the information stored in the shared preferences
	 * @param the ID associate with the preference
	 * @param the key string of a field in the preference
	 * @param the value of above key
	 */
	private void updateSystemPreferences(String preferenceID, String key, long value) {
		SharedPreferences settings = getSharedPreferences(preferenceID, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	/**
	 * Modify the information stored in the shared preferences
	 * @param the ID associate with the preference
	 * @param the key string of a field in the preference
	 * @param the value of above key
	 */
	private void updateSystemPreferences(String preferenceID, String key, int value) {
		SharedPreferences settings = getSharedPreferences(preferenceID, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * Get the current time when the new coordinate was taken.
	 * The current time is recorded as UTC
	 * @return the current time represent in the format yyyyMMdd_HHmmss
	 */
	private String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		return sdf.format(new Date());
	}
	
	//---------------------------------------
	//--HANDLE INTERACTIONS BETWEEN FRAGMENT-
	//---------------------------------------
	@Override
	public void onCoordinatePass(LatLng coordinate) {
		mapFragment.moveCamera(coordinate);
	}
	
	@Override
	public void onDetailPass(PinMarkerObj pinObj) {
		//do nothing
	}
}


