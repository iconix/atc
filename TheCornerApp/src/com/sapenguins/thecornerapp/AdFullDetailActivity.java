package com.sapenguins.thecornerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.datasources.AdsDataSource;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.ImageDrawable;
import com.sapenguins.thecornerapp.supports.ImageLoading;
import com.sapenguins.thecornerapp.supports.ScreenStat;
import com.sapenguins.thecornerapp.supports.TimeFrame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AdFullDetailActivity extends FragmentActivity implements OnGlobalLayoutListener{

	TextView title;
	//TextView shortDescription;
	TextView address;
	TextView time;
	//TextView fromTime;
	//TextView toTime;
	TextView distance;
	TextView fullDescription;
	TextView direction;
	TextView tags;
	double longitude;
	double latitude;
	ImageView img;
	View mapContainer;
	View supportMapContainer;
	ImageView closingMapButton;
	ImageView expandingMapButton;
	GoogleMap googleMap;
	GoogleMap supportGoogleMap;
	
	String adImg;
	int adId;
	
	ImageLoading imageLoading;
	ImageLoader imageLoader;
	DisplayImageOptions options;
	
	MenuItem favorite;
	AdsDataSource dataSource;
	Context context;
	
	View checkboxView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_full_detail);
		context = this;
		imageLoading = new ImageLoading(this);
		imageLoader = imageLoading.getImageLoader();
		options = imageLoading.getDisplayImagesOption();
		dataSource = new AdsDataSource(this);
		dataSource.open();
		initViewComponents();
		displayAdInfo();
	}
	
	/**
	 * Set up the checkbox for the survey
	 */
	private void setupCheckbox() {
		checkboxView = View.inflate(this, R.layout.checkbox, null);
		CheckBox checkBox = (CheckBox) checkboxView.findViewById(R.id.checkbox);
		checkBox.setChecked(false);
		checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
		    @Override
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    	updateCheckboxStatus(isChecked);
		    }
		});
	}
	
	/**
	 * Check to see whether the survey has been completed or permanently postponed. 
	 * If neither is the case, promptly ask user if they want to fill out the survey 
	 */
	private void displaySurvey() {
		if (getSurveyPreference()) return;
		setupCheckbox();
		AlertDialog.Builder builder = new AlertDialog.Builder(AdFullDetailActivity.this);
    	builder.setTitle("Share your thoughts about our app for a chance to win a prize")
    	.setView(checkboxView)
    	.setNegativeButton("Later", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (getCheckboxStatus()) updateSurveyCompleted();
				dialog.cancel();
			}
		})
    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				updateSurveyCompleted();
				Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://thecornerapp.typeform.com/to/gZqAed") );
			    startActivity( browse );
				dialog.cancel();
			}
		});
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	/**
	 * Get the current preference set on the survey
	 * @return whether the survey has been complete or user refused to take it (return true); or false if survey is to keep appear
	 */
	private boolean getSurveyPreference() {
		SharedPreferences settings = getSharedPreferences("app_survey", 0);
		return settings.getBoolean("survey", false);
	}
	
	/**
	 * Get whether the checkbox is checked
	 * @return isChecked
	 */
	private boolean getCheckboxStatus() {
		SharedPreferences settings = getSharedPreferences("app_survey", 0);
		return settings.getBoolean("checkbox", false);
	}
	
	/**
	 * Update the check box in the survey
	 */
	private void updateCheckboxStatus(boolean isChecked) {
		SharedPreferences settings = getSharedPreferences("app_survey", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("checkbox", isChecked);
		editor.commit();
	}
	
	/**
	 * Update the user preference on the survey to completed
	 */
	private void updateSurveyCompleted() {
		SharedPreferences settings = getSharedPreferences("app_survey", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("survey", true);
		editor.commit();
	}
	
	@Override
	public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance().activityStart(this); // Add this method.
	}

	@Override
	protected void onDestroy() {
		dataSource.close();
		EasyTracker.getInstance().activityStop(this);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.full_detail_menu, menu);
		favorite = (MenuItem) findViewById(R.id.favorite);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//if (item.equals(favorite)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
		    builder.setTitle("Would you like to add this ad to your favorites?")
		    	.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						dataSource.addAdToFavorite(adId);
					}
				})
				.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
	    	AlertDialog alert = builder.create();
	    	alert.show();	
			//return false;
		//}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Display the ad basic info
	 */
	private void displayAdInfo() {
		Intent i = getIntent();
		longitude = i.getDoubleExtra("adLongitude", 0);
		latitude = i.getDoubleExtra("adLatitude", 0);
		boolean sendFromMap = i.getBooleanExtra("sendFromMap", false);
		if (sendFromMap) {
			displayAdDetail(i.getIntExtra("adId", 0), i.getStringExtra("adTitle"),
					i.getStringExtra("adDesc"), i.getStringExtra("adImg"), i.getStringExtra("adDistance"),
					i.getStringExtra("adStartDate"), i.getStringExtra("adEndDate"), i.getStringExtra("adLongDesc"),
					i.getStringExtra("adAddress"), i.getStringExtra("adTags"));
		} else {
			//using the id, we will grab the other necessary information
			displayAdInformation(i.getIntExtra("adId", 0), i.getStringExtra("adDistance"));
		}
	}
	
	/**
	 * display basic information of the ad with the given ID
	 * @param aId
	 */
	private void displayAdInformation(int aId, String distance) {
		final int mAdId = aId;
		final String mDistance = distance;
		new AsyncTask<Void, Void, String>() {		
			@Override
			protected String doInBackground(Void... params) {
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
						
						displayAdDetail(mAdId, adDetails[2], shortDescription, imageUrl, 
								mDistance, adDetails[7], adDetails[8], longDescription, address, tag);
					}
				}	
			}
		}.execute();
	}
	
	
	/**
	 * Set up the view components
	 */
	private void initViewComponents() {
		title = (TextView)findViewById(R.id.ad_full_detail_title);	
		img = (ImageView)findViewById(R.id.ad_full_detail_image);
		//shortDescription = (TextView) findViewById(R.id.ad_full_detail_short_description);
		address = (TextView) findViewById(R.id.ad_full_detail_address);
		time = (TextView) findViewById(R.id.ad_full_detail_time);
		//fromTime = (TextView) findViewById(R.id.ad_full_detail_from_time);
		//toTime = (TextView) findViewById(R.id.ad_full_detail_to_time);
		distance = (TextView) findViewById(R.id.ad_full_detail_distance_content);
		fullDescription = (TextView) findViewById(R.id.ad_full_detail_full_description);
		tags = (TextView) findViewById(R.id.ad_full_detail_tag);
		direction = (TextView) findViewById(R.id.ad_full_detail_direction);
		setGetDirectionClickListener();
		
		mapContainer = findViewById(R.id.ad_full_detail_map);
		SupportMapFragment mf = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.ad_full_detail_map_fragment);
		googleMap = mf.getMap();
        
        supportMapContainer = findViewById(R.id.ad_full_detail_support_map_container);
        closingMapButton = (ImageView) findViewById(R.id.ad_full_detail_hide_map);
        SupportMapFragment mf2 = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.ad_full_detail_support_map);
        supportGoogleMap = mf2.getMap();
        setClosingMapButtonClickListener();
        
        expandingMapButton = (ImageView) findViewById(R.id.ad_full_detail_expanding_map);
        setExpandingMapClickListener();
        
		//add observer for the title. This is to make sure title is at most 2 lines
		ViewTreeObserver vto = title.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(this);
		
		ViewTreeObserver vto2 = time.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(this);
	}
	
	int timeNumLines;
	@Override
	public void onGlobalLayout() {
	    if (2 < title.getLineCount()) {
	        title.setTextSize(TypedValue.COMPLEX_UNIT_PX,
	                title.getTextSize() - 2);
	    }
	    if (timeNumLines < time.getLineCount()) {
	        time.setTextSize(TypedValue.COMPLEX_UNIT_PX,
	                time.getTextSize() - 2);
	    }
	}
	
	/**
	 * set it so that when the small map is clicked, a bigger map appear
	 */
	private void setExpandingMapClickListener() {
		expandingMapButton.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				supportMapContainer.setVisibility(View.VISIBLE);
				mapContainer.setVisibility(View.INVISIBLE);
			}
		});
	}
	
	/**
	 * Set the click listener to handle the case then the closing button is pressed
	 */
	private void setClosingMapButtonClickListener() {
		closingMapButton.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				supportMapContainer.setVisibility(View.INVISIBLE);
				mapContainer.setVisibility(View.VISIBLE);
			}	
		});
	}
	
	/**
	 * Set up the click listener for the direction txt
	 */
	private void setGetDirectionClickListener() {
		direction.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
		   	    		 Uri.parse("google.navigation:q=" + latitude + "," + longitude));
		    	startActivity(intent);
			}
		});
	}
	
	/**
	 * display detail information of the Ad. Call from the activity container
	 * @param adId
	 * @param mtitle
	 * @param mImgSrc
	 */
	public void displayAdDetail(int mAdId, String mtitle, String adDesc, String mImgSrc, String mDistance, 
			String adStartDate, String adEndDate, String adLongDesc, String adAddress, String adTag) {
		adId = mAdId;
		adImg = mImgSrc;
		title.setText(mtitle);
		title.setTextSize(20);
		distance.setText(mDistance);
		//shortDescription.setText(Html.fromHtml(adDesc));
		//shortDescription.setMovementMethod(LinkMovementMethod.getInstance());
		
        LatLng adLocation = new LatLng(latitude, longitude);	        
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(adLocation, 15);
        googleMap.moveCamera(cameraUpdate);
        googleMap.addMarker(new MarkerOptions()
			.position(adLocation)
			.draggable(false)
			.icon(BitmapDescriptorFactory.defaultMarker()));
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(adLocation, 14);
        supportGoogleMap.moveCamera(cameraUpdate);
        supportGoogleMap.addMarker(new MarkerOptions()
			.position(adLocation)
			.draggable(false)
			.icon(BitmapDescriptorFactory.defaultMarker()));
        
		//imageLoading.displayImage(mImgSrc, img);
        imageLoader.loadImage(mImgSrc, options, new SimpleImageLoadingListener() {
        	final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					boolean firstDisplay = !displayedImages.contains(imageUri);
					if (firstDisplay) {
						displayedImages.add(imageUri);
					}
					ImageDrawable.setImageBackground(img, getResizedBitmap(loadedImage));
				}
			}
		});
        
        //fromTime.setText(TimeFrame.getDisplayTime(adStartDate));
        //toTime.setText(TimeFrame.getDisplayTime(adEndDate));
        fullDescription.setText(Html.fromHtml(adLongDesc));
        fullDescription.setMovementMethod(LinkMovementMethod.getInstance());
		address.setText(adAddress);
		if (adTag.length() > 0) tags.setText("Tags: " + adTag);
		
		String startDate = TimeFrame.getDisplayDate(adStartDate);
		String endDate = TimeFrame.getDisplayDate(adEndDate);
		if (startDate.equals(endDate)) {
			timeNumLines = 1;
			String startHr = TimeFrame.getDisplayHour(adStartDate);
			String endHr = TimeFrame.getDisplayHour(adEndDate);
			time.setText("Time: " + startDate + "   " + startHr + " - " + endHr);
		} else {
			timeNumLines = 2;
			String mtime = "From: " + TimeFrame.getDisplayTime(adEndDate) + SpecialCharacters.endLn +
					"To: " + TimeFrame.getDisplayTime(adEndDate);
			time.setText(mtime);
		}
		time.setTextSize(14);
		
		
		displaySurvey();
	}
	
	//resize the fit the horizontal of the screen
	public Drawable getResizedBitmap(Bitmap bm) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    int screenWidth = ScreenStat.getScreenWidth(context);
	    float scale = ((float) screenWidth) / width;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scale, scale);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return new BitmapDrawable(getResources(), resizedBitmap);
	}
}
