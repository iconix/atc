package com.sapenguins.thecornerapp;

import java.util.ArrayList;

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
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.ImageLoading;
import com.sapenguins.thecornerapp.supports.TimeFrame;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteFullDetailActivity extends FragmentActivity{

	TextView title;
	TextView shortDescription;
	TextView address;
	TextView fromTime;
	TextView toTime;
	TextView distance;
	TextView fullDescription;
	TextView direction;
	TextView tags;
	double longitude;
	double latitude;
	ImageView img;
	GoogleMap googleMap;
	Context context;
	
	String favoriteImg;
	int favoriteId;
	
	ImageLoading imageLoading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite_full_detail);
		context = this;
		imageLoading = new ImageLoading(this);
		initViewComponents();
		displayBasicInfo();
		displayDetailInfo();
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
	 * Display the favorite basic info
	 */
	private void displayBasicInfo() {
		Intent i = getIntent();
		longitude = i.getDoubleExtra("favoriteLongitude", 0);
		latitude = i.getDoubleExtra("favoriteLatitude", 0);
		displayEventBasicDetail(i.getIntExtra("favoriteId", 0), i.getStringExtra("favoriteTitle"),
				i.getStringExtra("favoriteDesc"), i.getStringExtra("favoriteImg"), i.getStringExtra("favoriteDistance"));
	}
	
	/**
	 * Display more info of the favorite
	 */
	private void displayDetailInfo() {
		new AsyncTask<Void, Void, String>() {		
			@Override
			protected String doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("adRequestId", String.valueOf(favoriteId)));
					return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				if (result != null) {
					
					String[] favoriteDetails = result.split(SpecialCharacters.delimiter);
					//check if the favorite detail is valid
					if (favoriteDetails.length == 5) {
						//check if the shortDescription is empty. If it is then make it empty
						String longDescription = favoriteDetails[2];
						if (longDescription.equals(SpecialCharacters.empty))
							longDescription = ""; // set it back to empty
						
						//check if the tag is empty. If it is then make it empty
						String tag = favoriteDetails[3];
						if (tag.equals(SpecialCharacters.empty))
							tag = "";
						
						//for the address field. If the parameter are separated by
						//either the ", " or "," then replace them with endline
						String mAddress = favoriteDetails[4];
						mAddress = mAddress.replace(", ", SpecialCharacters.endLn);
						mAddress = mAddress.replace(",", SpecialCharacters.endLn);
						
						fullDescription.setText(Html.fromHtml(longDescription));
						fromTime.setText(TimeFrame.getDisplayTime(favoriteDetails[0]));
						toTime.setText(TimeFrame.getDisplayTime(favoriteDetails[1]));
						address.setText(mAddress);
						if (tag.length() > 0)
							tags.setText("Tags: " + tag);
					}
				}	
			}
		}.execute();
	}
	
	/**
	 * Set up the view components
	 */
	private void initViewComponents() {
		title = (TextView)findViewById(R.id.favorite_full_detail_title);	
		img = (ImageView)findViewById(R.id.favorite_full_detail_image);
		shortDescription = (TextView) findViewById(R.id.favorite_full_detail_short_description);
		address = (TextView) findViewById(R.id.favorite_full_detail_address);
		fromTime = (TextView) findViewById(R.id.favorite_full_detail_from_time);
		toTime = (TextView) findViewById(R.id.favorite_full_detail_to_time);
		distance = (TextView) findViewById(R.id.favorite_full_detail_distance_content);
		fullDescription = (TextView) findViewById(R.id.favorite_full_detail_full_description);
		tags = (TextView) findViewById(R.id.favorite_full_detail_tag);
		direction = (TextView) findViewById(R.id.favorite_full_detail_direction);
		setGetDirectionClickListener();
		
		SupportMapFragment mf = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.favorite_full_detail_map_fragment);
        googleMap = mf.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
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
	 * display detail information of the Event. Call from the activity container
	 * @param favoriteId
	 * @param mtitle
	 * @param mImgSrc
	 */
	public void displayEventBasicDetail(int mEventId, String mtitle, String favoriteDesc, String mImgSrc, String mDistance) {
		favoriteId = mEventId;
		favoriteImg = mImgSrc;
		title.setText(mtitle);
		distance.setText(mDistance);
		shortDescription.setText(Html.fromHtml(favoriteDesc));
		
        LatLng adLocation = new LatLng(latitude, longitude);	        
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(adLocation, 15);
        googleMap.moveCamera(cameraUpdate);
        googleMap.addMarker(new MarkerOptions()
			.position(adLocation)
			.draggable(false)
			.icon(BitmapDescriptorFactory.defaultMarker()));
		
	
		imageLoading.displayImage(mImgSrc, img);
	}
}
