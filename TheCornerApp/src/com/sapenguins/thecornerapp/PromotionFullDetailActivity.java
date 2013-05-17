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
import com.sapenguins.thecornerapp.datasources.AdsDataSource;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.ImageLoading;
import com.sapenguins.thecornerapp.supports.TimeFrame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PromotionFullDetailActivity extends FragmentActivity{

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
	
	String promotionImg;
	int promotionId;
	
	ImageLoading imageLoading;
	
	MenuItem favorite;
	AdsDataSource dataSource;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_full_detail);
		context = this;
		imageLoading = new ImageLoading(this);
		dataSource = new AdsDataSource(this);
		dataSource.open();
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
		    builder.setTitle("Would you like to add this event to your favorites?")
		    	.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						dataSource.addAdToFavorite(promotionId);
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
	 * Display the promotion basic info
	 */
	private void displayBasicInfo() {
		Intent i = getIntent();
		longitude = i.getDoubleExtra("promotionLongitude", 0);
		latitude = i.getDoubleExtra("promotionLatitude", 0);
		displayEventBasicDetail(i.getIntExtra("promotionId", 0), i.getStringExtra("promotionTitle"),
				i.getStringExtra("promotionDesc"), i.getStringExtra("promotionImg"), i.getStringExtra("promotionDistance"));
	}
	
	/**
	 * Display more info of the promotion
	 */
	private void displayDetailInfo() {
		new AsyncTask<Void, Void, String>() {		
			@Override
			protected String doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("adRequestId", String.valueOf(promotionId)));
					return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				if (result != null) {
					
					String[] promotionDetails = result.split(SpecialCharacters.delimiter);
					//check if the promotion detail is valid
					if (promotionDetails.length == 5) {
						//check if the shortDescription is empty. If it is then make it empty
						String longDescription = promotionDetails[2];
						if (longDescription.equals(SpecialCharacters.empty))
							longDescription = ""; // set it back to empty
						
						//check if the tag is empty. If it is then make it empty
						String tag = promotionDetails[3];
						if (tag.equals(SpecialCharacters.empty))
							tag = "";
						
						//for the address field. If the parameter are separated by
						//either the ", " or "," then replace them with endline
						String mAddress = promotionDetails[4];
						mAddress = mAddress.replace(", ", SpecialCharacters.endLn);
						mAddress = mAddress.replace(",", SpecialCharacters.endLn);
						
						fullDescription.setText(Html.fromHtml(longDescription));
						fromTime.setText(TimeFrame.getDisplayTime(promotionDetails[0]));
						toTime.setText(TimeFrame.getDisplayTime(promotionDetails[1]));
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
		title = (TextView)findViewById(R.id.promotion_full_detail_title);	
		img = (ImageView)findViewById(R.id.promotion_full_detail_image);
		shortDescription = (TextView) findViewById(R.id.promotion_full_detail_short_description);
		address = (TextView) findViewById(R.id.promotion_full_detail_address);
		fromTime = (TextView) findViewById(R.id.promotion_full_detail_from_time);
		toTime = (TextView) findViewById(R.id.promotion_full_detail_to_time);
		distance = (TextView) findViewById(R.id.promotion_full_detail_distance_content);
		fullDescription = (TextView) findViewById(R.id.promotion_full_detail_full_description);
		tags = (TextView) findViewById(R.id.promotion_full_detail_tag);
		direction = (TextView) findViewById(R.id.promotion_full_detail_direction);
		setGetDirectionClickListener();
		
		SupportMapFragment mf = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.promotion_full_detail_map_fragment);
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
	 * @param promotionId
	 * @param mtitle
	 * @param mImgSrc
	 */
	public void displayEventBasicDetail(int mEventId, String mtitle, String promotionDesc, String mImgSrc, String mDistance) {
		promotionId = mEventId;
		promotionImg = mImgSrc;
		title.setText(mtitle);
		distance.setText(mDistance);
		shortDescription.setText(Html.fromHtml(promotionDesc));
		
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
