package com.sapenguins.thecornerapp;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.sapenguins.thecornerapp.constants.Global;
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.objects.AdInfo;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.ImageDrawable;
import com.sapenguins.thecornerapp.supports.ImageLoading;
import com.sapenguins.thecornerapp.supports.ScreenStat;
import com.sapenguins.thecornerapp.supports.ScreenToMap;
import com.sapenguins.thecornerapp.supports.TimeFrame;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdsMapActivity extends SherlockFragmentActivity implements OnDateSetListener, OnGlobalLayoutListener{
	
	public static final int DEVICE_VERSION = android.os.Build.VERSION.SDK_INT;
	public static final int HONEYCOMB_VERSION = android.os.Build.VERSION_CODES.HONEYCOMB;
	
	public static final int MAP_PADDING = 2;
	
	ActionBar actionbar;
	Context context;
	LocationManager locationManager; 
	String provider;
	
	ImageLoading imageLoading;
	ImageLoader imageLoader;
	DisplayImageOptions options;
	
	MenuItem homeItem;
	MenuItem mapTypeItem;
	
	GoogleMap googleMap;
	
	//component of the tab bar
	View tabEvent;
	TextView eventText;
	View eventUnderline;
	View tabPromotion;
	TextView promotionText;
	View promotionUnderline;
	boolean currentIsEvent; 
	int mapType;
	
	//Time navigation
	ImageView timeSelection;
	TextView dateOfAds;
	String currentDate;
	String displayDate;
	float mapCurrentZoom;
	
	boolean dialogIsOpen = false;
	
	int idsTable[][][];
	
	View mapViewContainer;;
	
	View mapCells[][];
	
	TextView mapCellsText[][];
	
	int clickedCellIds[];
	int currentIdIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.ads_map);
		context = this;
		
		imageLoading = new ImageLoading(context);
		imageLoader = imageLoading.getImageLoader();
		options = imageLoading.getDisplayImagesOption();
		
		 //get last known location to calculate the bird-eye distance
	    locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    provider = locationManager.getBestProvider(new Criteria(), true);
        if (provider == null) onProviderDisabled(provider); 
        
        initMap();
        initViewTable();

		initActionBar();
		setupTabBarComponents();

		currentIsEvent = true;
		setMapOnCameraChangeListener();
	}
	
	@Override
	public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance().activityStart(this); // Add this method.
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}
	
	@Override
	protected void onDestroy() {
		//EasyTracker.getInstance().activityStop(this);
		super.onDestroy();
	}
	
	private void initViewTable() {
		mapViewContainer = findViewById(R.id.map_view_layout_container);
		mapCells = new View[][] {{findViewById(R.id.map_view_cell_zero_zero), 
			findViewById(R.id.map_view_cell_zero_one), 
			findViewById(R.id.map_view_cell_zero_two),
			findViewById(R.id.map_view_cell_zero_three),
			findViewById(R.id.map_view_cell_zero_four), 
			findViewById(R.id.map_view_cell_zero_five)},
			
			{findViewById(R.id.map_view_cell_one_zero), 
			findViewById(R.id.map_view_cell_one_one), 
			findViewById(R.id.map_view_cell_one_two),
			findViewById(R.id.map_view_cell_one_three),
			findViewById(R.id.map_view_cell_one_four), 
			findViewById(R.id.map_view_cell_one_five)},
			
			{findViewById(R.id.map_view_cell_two_zero), 
			findViewById(R.id.map_view_cell_two_one), 
			findViewById(R.id.map_view_cell_two_two),
			findViewById(R.id.map_view_cell_two_three),
			findViewById(R.id.map_view_cell_two_four), 
			findViewById(R.id.map_view_cell_two_five)},
			
			{findViewById(R.id.map_view_cell_three_zero), 
			findViewById(R.id.map_view_cell_three_one), 
			findViewById(R.id.map_view_cell_three_two),
			findViewById(R.id.map_view_cell_three_three),
			findViewById(R.id.map_view_cell_three_four), 
			findViewById(R.id.map_view_cell_three_five)},
			
			{findViewById(R.id.map_view_cell_four_zero), 
			findViewById(R.id.map_view_cell_four_one), 
			findViewById(R.id.map_view_cell_four_two),
			findViewById(R.id.map_view_cell_four_three),
			findViewById(R.id.map_view_cell_four_four), 
			findViewById(R.id.map_view_cell_four_five)},
			
			{findViewById(R.id.map_view_cell_five_zero), 
			findViewById(R.id.map_view_cell_five_one), 
			findViewById(R.id.map_view_cell_five_two),
			findViewById(R.id.map_view_cell_five_three),
			findViewById(R.id.map_view_cell_five_four), 
			findViewById(R.id.map_view_cell_five_five)}
		};
		
		mapCellsText = new TextView[][] {{(TextView) findViewById(R.id.map_view_cell_zero_zero_text), 
			(TextView) findViewById(R.id.map_view_cell_zero_one_text), 
			(TextView) findViewById(R.id.map_view_cell_zero_two_text),
			(TextView) findViewById(R.id.map_view_cell_zero_three_text),
			(TextView) findViewById(R.id.map_view_cell_zero_four_text), 
			(TextView) findViewById(R.id.map_view_cell_zero_five_text)},
			
			{(TextView) findViewById(R.id.map_view_cell_one_zero_text), 
			(TextView) findViewById(R.id.map_view_cell_one_one_text), 
			(TextView) findViewById(R.id.map_view_cell_one_two_text),
			(TextView) findViewById(R.id.map_view_cell_one_three_text),
			(TextView) findViewById(R.id.map_view_cell_one_four_text), 
			(TextView) findViewById(R.id.map_view_cell_one_five_text)},
			
			{(TextView) findViewById(R.id.map_view_cell_two_zero_text), 
			(TextView) findViewById(R.id.map_view_cell_two_one_text), 
			(TextView) findViewById(R.id.map_view_cell_two_two_text),
			(TextView) findViewById(R.id.map_view_cell_two_three_text),
			(TextView) findViewById(R.id.map_view_cell_two_four_text), 
			(TextView) findViewById(R.id.map_view_cell_two_five_text)},
			
			{(TextView) findViewById(R.id.map_view_cell_three_zero_text), 
			(TextView) findViewById(R.id.map_view_cell_three_one_text), 
			(TextView) findViewById(R.id.map_view_cell_three_two_text),
			(TextView) findViewById(R.id.map_view_cell_three_three_text),
			(TextView) findViewById(R.id.map_view_cell_three_four_text), 
			(TextView) findViewById(R.id.map_view_cell_three_five_text)},
			
			{(TextView) findViewById(R.id.map_view_cell_four_zero_text), 
			(TextView) findViewById(R.id.map_view_cell_four_one_text), 
			(TextView) findViewById(R.id.map_view_cell_four_two_text),
			(TextView) findViewById(R.id.map_view_cell_four_three_text),
			(TextView) findViewById(R.id.map_view_cell_four_four_text), 
			(TextView) findViewById(R.id.map_view_cell_four_five_text)},
			
			{(TextView) findViewById(R.id.map_view_cell_five_zero_text), 
			(TextView) findViewById(R.id.map_view_cell_five_one_text), 
			(TextView) findViewById(R.id.map_view_cell_five_two_text),
			(TextView) findViewById(R.id.map_view_cell_five_three_text),
			(TextView) findViewById(R.id.map_view_cell_five_four_text), 
			(TextView) findViewById(R.id.map_view_cell_five_five_text)}
		};	
		
		initMapAdWindowComponents();
		setMapCellsOnClickListener();
	}
	
	View adPopupView;
	TextView adPopupDistance;
	ImageView adPopupImage;
	TextView adPopupNextButton;
	TextView adPopupPrevButton;
	TextView adPopupCurrentAdNumber;
	TextView adPopupTitle;
	TextView adPopupTime;
	TextView adPopupDescription;
	ImageView adPopupClosingButton;
	
	/**
	 * Init the ad popup window components
	 */
	private void initMapAdWindowComponents() {
		adPopupView = findViewById(R.id.ad_simple_map_display_container);
		myPopupView = findViewById(R.id.ad_simple_map_display_sub_container);
		setAdPopupViewOnClickListener();
		adPopupDistance = (TextView)findViewById(R.id.ad_simple_map_display_distance);
		adPopupImage = (ImageView)findViewById(R.id.ad_simple_map_display_image);
		adPopupPrevButton = (TextView)findViewById(R.id.ad_simple_map_display_last_ad);
		adPopupNextButton = (TextView)findViewById(R.id.ad_simple_map_display_next_ad);
		adPopupCurrentAdNumber = (TextView)findViewById(R.id.ad_simple_map_display_current_ad_number);
		adPopupTitle = (TextView)findViewById(R.id.ad_simple_map_display_title);
		ViewTreeObserver vto = adPopupTitle.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(this);
		
		adPopupTime = (TextView)findViewById(R.id.ad_simple_map_display_time);
		ViewTreeObserver vto2 = adPopupTime.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(this);
		
		adPopupDescription = (TextView)findViewById(R.id.ad_simple_map_display_description);
		adPopupClosingButton = (ImageView)findViewById(R.id.ad_simple_map_display_hide_content);
		
		setPopupViewOnClickListener();
		setPopupViewPrevButtonOnClickListener();
		setPopupViewNextButtonOnClickListener();
		setPopupViewClosingButtonOnClickListener();
	}
	
	/**
	 * Set up the popup view on click listener. This will take us to the full description page
	 */
	private void setPopupViewOnClickListener() {
		adPopupView.setVisibility(View.GONE);
	}
	
	/**
	 * Handle the even when the prev button is pressed
	 */
	private void setPopupViewPrevButtonOnClickListener() {
		adPopupPrevButton.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentIdIndex == 0) return; //do nothing
				currentIdIndex --;
				displayAdWithIdIndex(currentIdIndex);
			}
		});
	}
	
	/**
	 * Handle the event when the next button is pressed
	 */
	private void setPopupViewNextButtonOnClickListener() {
		adPopupNextButton.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentIdIndex == clickedCellIds.length - 1) return; //do nothing
				currentIdIndex ++;
				displayAdWithIdIndex(currentIdIndex);
			}
		});
	}
	
	private void setPopupViewClosingButtonOnClickListener() {
		adPopupClosingButton.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				adPopupView.setVisibility(View.GONE);
			}			
		});
	}
	
	private void displayAdWithIdIndex(int idIndex) {
		//TODO update the data
		//first update the index
		int index = idIndex + 1;
		if (idIndex == clickedCellIds.length - 1) adPopupNextButton.setTextColor(0xffc2c2c2);
		else adPopupNextButton.setTextColor(0xff0000ff);
		if (idIndex == 0) adPopupPrevButton.setTextColor(0xffc2c2c2);
		else adPopupPrevButton.setTextColor(0xff0000ff);
		adPopupCurrentAdNumber.setText(index + " of " + clickedCellIds.length);
		displayAdInformation(clickedCellIds[idIndex]);
	}
	
	AdInfo currentAd; 
	/**
	 * display basic information of the ad with the given ID
	 * @param aId
	 */
	private void displayAdInformation(int aId) {
		final int mAdId = aId;
		new AsyncTask<Void, Void, String>() {		
			@Override
			protected String doInBackground(Void... params) {
				currentAd = null;
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("adRequest", String.valueOf(mAdId)));
					return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				if (result != null) {
					String[] adDetails = result.split(SpecialCharacters.delimiter);
					//check if the event detail is valid
					if (adDetails.length == 12) {
						//UPDATE THE CURRENT AD
						
						//check if the shortDescription is empty. If it is then make it empty
						String shortDescription = adDetails[6];
						if (shortDescription.equals(SpecialCharacters.empty))
							shortDescription = ""; // set it back to empty
						
						//check if the url is empty
						String imageUrl = adDetails[5];
						if (imageUrl.equals(SpecialCharacters.empty))
							imageUrl = ""; // set it back to empty
						
						//check if the shortDescription is empty. If it is then make it empty
						String longDescription = adDetails[9];
						if (longDescription.equals(SpecialCharacters.empty))
							longDescription = ""; // set it back to empty
						
						//check if the tag is empty. If it is then make it empty
						String tag = adDetails[10];
						if (tag.equals(SpecialCharacters.empty))
							tag = "";
						
						//for the address field. If the parameter are separated by
						//either the ", " or "," then replace them with endline
						String address = adDetails[11];
						address = address.replace(", ", SpecialCharacters.endLn);
						address = address.replace(",", SpecialCharacters.endLn);
						
						currentAd = new AdInfo(Integer.valueOf(adDetails[0]),
								Integer.valueOf(adDetails[1]),
								adDetails[2],
								Double.valueOf(adDetails[3]),
								Double.valueOf(adDetails[4]),
								imageUrl,
								shortDescription,
								adDetails[7],
								adDetails[8],
								longDescription,
								tag,
								address);
						
						displayAdInfo(currentAd);
					}
				}	
			}
		}.execute();
	}
	
	/**
	 * Display the ad information on the popup window
	 * @param ad
	 */
	private void displayAdInfo(AdInfo ad) {
		if (ad == null) {
			adPopupView.setVisibility(View.GONE);
			Toast.makeText(context, "Error retrieving ad information. Please try again", Toast.LENGTH_LONG).show();
			return;
		}
		
		adPopupTitle.setText(ad.getTitle());
		adPopupTitle.setTextSize(14);
		
		//now the time field
		String startDate = TimeFrame.getDisplayDate(ad.getStartDate());
		String endDate = TimeFrame.getDisplayDate(ad.getEndDate());
		if (startDate.equals(endDate)) {
			timeNumLines = 1;
			String startHr = TimeFrame.getDisplayHour(ad.getStartDate());
			String endHr = TimeFrame.getDisplayHour(ad.getEndDate());
			adPopupTime.setText("Time: " + startDate + "   " + startHr + " - " + endHr);
		} else {
			timeNumLines = 2;
			String popupTime = "From: " + TimeFrame.getDisplayTime(ad.getStartDate()) + SpecialCharacters.endLn +
					"To: " + TimeFrame.getDisplayTime(ad.getEndDate());
			adPopupTime.setText(popupTime);
		}
		adPopupTime.setTextSize(13);
		
		//now the description
		adPopupDescription.setTextSize(12);
		adPopupDescription.setText("   " + Html.fromHtml(ad.getShortDescription()));
		
		//now to the distance
		adPopupDistance.setText(String.format("%.2f", computeDistanceToAd(ad)) + " mi");
		
		//image finally
		//imageLoading.displayImage(ad.getImageUrl(), adPopupImage);
		imageLoader.loadImage(ad.getImageUrl(), options, new SimpleImageLoadingListener() {
			final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					boolean firstDisplay = !displayedImages.contains(imageUri);
					if (firstDisplay) {
						displayedImages.add(imageUri);
					}
					ImageDrawable.setImageBackground(adPopupImage, getResizedBitmap(loadedImage, 0, 0));
				}
			}
		});
	}
	
	//resize the fit the horizontal of the screen
	public Drawable getResizedBitmap(Bitmap bm, int leftPadding, int rightPadding) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		int screenWidth = ScreenStat.getViewWidth(context, 5, 5);
		float scale = ((float) screenWidth) / width;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scale, scale);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return new BitmapDrawable(context.getResources(), resizedBitmap);
	}
	
	int timeNumLines;
	@Override
	public void onGlobalLayout() {
	    if (2 < adPopupTitle.getLineCount()) {
	        adPopupTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,
	                adPopupTitle.getTextSize() - 2);
	    }
	    if (timeNumLines == 1 && timeNumLines < adPopupTime.getLineCount()) {
	        adPopupTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,
	                adPopupTime.getTextSize() - 2);
	    }
	    if (timeNumLines == 2) {
	    	if (1 < adPopupTitle.getLineCount()) {
		        adPopupTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,
		                adPopupTitle.getTextSize() - 2);
		    }
	    }
	}
	
	/**
	 * Set the click event when the cell in the map is pressed/touched
	 */
	private void setMapCellsOnClickListener() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				final int row = i;
				final int col = j;
				View currentView = mapCells[row][col];
				currentView.setOnClickListener(new View.OnClickListener() {				
					@Override
					public void onClick(View v) {
						clickedCellIds = idsTable[row][col];
						if (clickedCellIds != null && clickedCellIds.length > 0) {
							adPopupView.setVisibility(View.VISIBLE);
							currentIdIndex = 0;
							displayAdWithIdIndex(currentIdIndex);
						} else {
							adPopupView.setVisibility(View.GONE);
						}
					}
				});
			}
		}
	}
	
	
	/**
	 * Set the map to update the markers when the camare is changed
	 */
	private void setMapOnCameraChangeListener() {
		googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {			
			@Override
			public void onCameraChange(CameraPosition position) {
				requestAdIdsList(currentIsEvent);
				adPopupView.setVisibility(View.GONE);
			}
		});
		googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				requestAdIdsList(currentIsEvent);
				adPopupView.setVisibility(View.GONE);
			}
		});
	}

	View myPopupView;
	/**
	 * Set the listener when the user click on the info window that was 
	 * initiated when he/she press on the marker on map
	 */
	private void setAdPopupViewOnClickListener() {
		myPopupView.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if(currentAd != null) {
					Intent intent = new Intent(context, AdFullDetailActivity.class);
					intent.putExtra("sendFromMap", true);
					intent.putExtra("adId", currentAd.getId());
					intent.putExtra("businessId", currentAd.getBusinessID());
					intent.putExtra("adTitle", currentAd.getTitle());
					intent.putExtra("adImg", currentAd.getImageUrl());
					intent.putExtra("adDesc", currentAd.getShortDescription());
					intent.putExtra("adLongitude", currentAd.getLongitude());
					intent.putExtra("adLatitude", currentAd.getLatitude());
					intent.putExtra("adStartDate", currentAd.getStartDate());
					intent.putExtra("adEndDate", currentAd.getEndDate());
					intent.putExtra("adLongDesc", currentAd.getLongDescription());
					intent.putExtra("adTags", currentAd.getTags());
					intent.putExtra("adAddress", currentAd.getAddress());
					intent.putExtra("adDistance", String.format("%.2f", computeDistanceToAd(currentAd)) + " miles");
					startActivity(intent);
				}
			}
		});
	}
	
	/**
	 * Get the distance between the current location and the location of the ad
	 * @param the ad
	 * @return distance between them
	 */
	private double computeDistanceToAd(AdInfo ad) {
		Location lastKnown = locationManager.getLastKnownLocation(provider);
	    Location adLocation = new Location(provider);
		adLocation.setLatitude(ad.getLatitude());
		adLocation.setLongitude(ad.getLongitude());
		return lastKnown.distanceTo(adLocation)/Global.METERS_IN_MILE;	
	}
	
	/**
	 * Setup the component for the tab bar
	 */
	private void setupTabBarComponents() {
		tabEvent = findViewById(R.id.map_tab_bar_event);
		eventText = (TextView) findViewById(R.id.map_tab_bar_event_text);
		eventUnderline = findViewById(R.id.map_tab_bar_event_text_underline);
		tabPromotion = findViewById(R.id.map_tab_bar_promotion);
		promotionText = (TextView) findViewById(R.id.map_tab_bar_promotion_text);
		promotionUnderline = findViewById(R.id.map_tab_bar_promotion_text_underline);
		
		//and the time navigation bar
		dateOfAds = (TextView) findViewById(R.id.map_view_time_display);
		timeSelection = (ImageView) findViewById(R.id.map_view_calendar_button);
		
		//display the current date
		currentDate = getCurrentDate();
		displayDate = currentDate;
		dateOfAds.setText(TimeFrame.getDisplayDate(currentDate));
		
		setTabEventClickListener();
		setTabPromotionClickListener();
		setTimeSelectionClickListener();
	}
	
	/**
	 * Open a dialog for the user to choose the date once the date text field is clicked
	 */
	private void setTimeSelectionClickListener() {
		timeSelection.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!dialogIsOpen) {
					DialogFragment dateFragment = new DatePickerFragment();
					dateFragment.show(getSupportFragmentManager(), "Date Picker");
				}
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
			dialogIsOpen = true;
			// Create a new instance of DatePickerDialog and return it
			// Month start with 0. Weird, but nothing we can do
			String yMd[] = displayDate.split("-");
			return new DatePickerDialog(getActivity(), (AdsMapActivity)getActivity(), 
					Integer.valueOf(yMd[0]), Integer.valueOf(yMd[1]) -1, Integer.valueOf(yMd[2]));
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			dialogIsOpen = false;
			super.onCancel(dialog);
		}

		@Override
		public void onDismiss(DialogInterface dialog) {
			dialogIsOpen = false;
			super.onDismiss(dialog);
		}
	}

	/**
	 * When the date is set, the map should change and the date in 
	 * the time navigation bar should also change accordingly
	 */
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		displayDate = TimeFrame.getDate(year, monthOfYear + 1, dayOfMonth);
		if (displayDate.compareTo(currentDate) < 0) {
			Toast.makeText(context, "The date " + displayDate + " is out of range", Toast.LENGTH_LONG).show();
			displayDate = currentDate;
		}
		dateOfAds.setText(TimeFrame.getDisplayDate(displayDate));
		requestAdIdsList(currentIsEvent);
		dialogIsOpen = false;
	}
	
	/**
	 * set the listener for then the tab event is click
	 */
	private void setTabEventClickListener() {
		tabEvent.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if (!currentIsEvent) {
					adPopupView.setVisibility(View.GONE);
					eventText.setTypeface(null, Typeface.BOLD);
					promotionText.setTypeface(null, Typeface.NORMAL);
					eventUnderline.setBackgroundColor(0xffc8c8c8);
					promotionUnderline.setBackgroundColor(0xdd000000);
					requestAdIdsList(true);
					currentIsEvent = true;
				}
			}
		});
	}
	
	/**
	 * set the listener for then the tab event is click
	 */
	private void setTabPromotionClickListener() {
		tabPromotion.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if (currentIsEvent) {
					adPopupView.setVisibility(View.GONE);
					eventText.setTypeface(null, Typeface.NORMAL);
					promotionText.setTypeface(null, Typeface.BOLD);
					eventUnderline.setBackgroundColor(0xdd000000);
					promotionUnderline.setBackgroundColor(0xffc8c8c8);
					requestAdIdsList(false);
					currentIsEvent = false;
				}
			}
		});
	}
	
	/**
     * Initiate the map. Obtain the map fragment
     */
    private void initMap() {
        SupportMapFragment mf = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        googleMap = mf.getMap();
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        if (provider != null) {
	        Location lastKnown = locationManager.getLastKnownLocation(provider);	        
	        LatLng adsLocation = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
	        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(adsLocation, 14);
	        googleMap.moveCamera(cameraUpdate);
        }
    }
	
	/**
	 * Create action bar on top of the activity and implement click listener for each of its items
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onCreateOptionsMenu(com.actionbarsherlock.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate( R.menu.map_menu_bar, menu );
		homeItem = menu.findItem(R.id.map_menu_home);
		mapTypeItem = menu.findItem(R.id.map_menu_map_type);
		homeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				startActivity(new Intent(context, HomeActivity.class));
				actionbar.hide();
				return true;
			}
		});
		
		setMapTypeItemClickListener();
		return true; 
	}
	
	//Map type view
    public static final int NORMAL_VIEW = 0;
    public static final int HYBRID_VIEW = 1;
    public static final int SATELLITE_VIEW = 2;
    public static final int TERRAIN_VIEW = 3;
	/**
	 * Create a listener for the map type menu item. Open a dialog where user
	 * can select which type of map he/she wish to view
	 */
	private void setMapTypeItemClickListener() {
		mapTypeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				int type = googleMap.getMapType();
				if (type == GoogleMap.MAP_TYPE_NORMAL) mapType = NORMAL_VIEW;
				if (type == GoogleMap.MAP_TYPE_HYBRID) mapType = HYBRID_VIEW;
				if (type == GoogleMap.MAP_TYPE_SATELLITE) mapType = SATELLITE_VIEW;
				if (type == GoogleMap.MAP_TYPE_TERRAIN) mapType = TERRAIN_VIEW;
				CharSequence[] mapOptions = {"Normal", "Hybrid", "Satellite", "Terrain"};
		    	AlertDialog.Builder builder = new AlertDialog.Builder(AdsMapActivity.this);
		    	builder.setTitle("Select Map Style")
		    	.setSingleChoiceItems(mapOptions, mapType, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mapType = which;
					}
				})
		    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (mapType == NORMAL_VIEW) googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						if (mapType == HYBRID_VIEW) googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
						if (mapType == SATELLITE_VIEW) googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
						if (mapType == TERRAIN_VIEW) googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
						dialog.cancel();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		    	AlertDialog alert = builder.create();
		    	alert.show();
		    	actionbar.hide();
				return false;
			}
		});
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
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg_black));
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
	
	/**
	 * Note that the previous function is a little slow, so we want to
	 * actually sending all the request at once
	 */
	private void requestAdIdsList(boolean isEvent) {
		final boolean mIsEvent = isEvent;
		
		new AsyncTask<Void, Void, String>() {
			double minLng[][];
			double maxLng[][];
			double minLat[][];
			double maxLat[][];
			
			String minLngTag[][];
			String minLatTag[][];
			String maxLngTag[][];
			String maxLatTag[][];
			
			@Override
			protected void onPreExecute() {
				clearMarkers();
				idsTable = new int[6][6][];
				minLng = new double[6][6];
				maxLng = new double[6][6];
				minLat = new double[6][6];
				maxLat = new double[6][6];
				minLngTag = new String[6][6];
				minLatTag = new String[6][6];
				maxLngTag = new String[6][6];
				maxLatTag = new String[6][6];
				for (int row = 0; row < 6; row++) {
					for (int col = 0; col < 6; col++) {
						LatLng[] boundingCoordinates = ScreenToMap.getMapBoundingCoordinate(mapViewContainer, googleMap, row, col);
						minLng[row][col] = boundingCoordinates[0].longitude;
						minLngTag[row][col] = "minLng" + String.valueOf(row) + String.valueOf(col);
						
						maxLng[row][col] = boundingCoordinates[1].longitude;
						maxLngTag[row][col] = "maxLng" + String.valueOf(row) + String.valueOf(col);
						
						minLat[row][col] = boundingCoordinates[0].latitude;
						minLatTag[row][col] = "minLat" + String.valueOf(row) + String.valueOf(col);
						
						maxLat[row][col] = boundingCoordinates[1].latitude;
						maxLatTag[row][col] = "maxLat" + String.valueOf(row) + String.valueOf(col);
					}
				}
				
			}
			
			@Override
			protected String doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					String dateAndTime = "\"" + currentDate + " " + getCurrentTime() + "\"";
					if (displayDate.compareTo(currentDate) > 0) 
						dateAndTime = "\"" + displayDate + " 00:00:00\""; 
					if (mIsEvent) postParameters.add(new BasicNameValuePair("mapeventid", dateAndTime));
					else postParameters.add(new BasicNameValuePair("mapdealid", dateAndTime));
					for (int row = 0; row < 6; row ++) {
						for (int col = 0; col < 6; col++) {
							postParameters.add(new BasicNameValuePair(minLngTag[row][col], String.valueOf(minLng[row][col])));
							postParameters.add(new BasicNameValuePair(maxLngTag[row][col], String.valueOf(maxLng[row][col])));
							postParameters.add(new BasicNameValuePair(minLatTag[row][col], String.valueOf(minLat[row][col])));
							postParameters.add(new BasicNameValuePair(maxLatTag[row][col], String.valueOf(maxLat[row][col])));
						}
					}		
					return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			protected void onPostExecute(String result) {			
				if (result != null) {	
					String[] ids = result.split(SpecialCharacters.endLn);
					if (ids.length == 36) {
						for (int row = 0; row < 6; row++) {
							for (int col = 0; col < 6; col++) {
								int index = 6 * row + col;
								//Log.i("RESPONSE", ids[index]);
								if (!ids[index].equals("-1")) {
									String[] cellIds = ids[index].split(SpecialCharacters.delimiter);
									if (cellIds != null && cellIds.length > 0) {
										idsTable[row][col] = new int[cellIds.length];
										for (int i = 0; i < cellIds.length; i++) {
											idsTable[row][col][i] = Integer.parseInt(cellIds[i]);
										}
									}
								}
								displayCurrentCellOnMap(row, col);
							}
						}
					}			
				}
			}
		}.execute();
	}
	
	private void clearMarkers() {
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				mapCells[row][col].setVisibility(View.INVISIBLE);
			}
		}
	}
	
	/**
	 * Make the marker on a given cell appear on the map if there are some
	 * deal/even around it. Also, if that the case, then display the number 
	 * of deal/event on the marker as well
	 */
	private void displayCurrentCellOnMap(int row, int col) {
		int currentCellIds[] = idsTable[row][col];	
		View currentCell = mapCells[row][col];
		if (currentCellIds != null && currentCellIds.length > 0) {
			TextView currentCellText = mapCellsText[row][col];
			currentCell.setVisibility(View.VISIBLE);
			currentCellText.setText(String.valueOf(currentCellIds.length));
		} else {
			currentCell.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * Execute to attempt to connect to the provider
	 * @param provider
	 */
	public void onProviderDisabled(String provider) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("GPS is disable");
        builder.setCancelable(false);
        
        //take user to turn on the GPS setting
        builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
               Intent startGps = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
               startActivity(startGps);
           } 
        });
        
        //close
        builder.setNegativeButton("Leave GPS off", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        
        //display the alert
        AlertDialog alert = builder.create();
        alert.show();
    }
	
	/**
	 * Get the date in the given format 
	 * @return the current date
	 */
	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		return sdf.format(new Date());
	}
	
	/**
	 * Get the time in the given format 
	 * @return the current date
	 */
	private String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		return sdf.format(new Date());
	}
}
