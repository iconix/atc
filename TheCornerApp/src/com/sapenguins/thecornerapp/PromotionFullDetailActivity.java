package com.sapenguins.thecornerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.TimeFrame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PromotionFullDetailActivity extends SherlockFragmentActivity{

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
	
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_full_detail);
		initViewComponents();
		displayBasicInfo();
		displayDetailInfo();
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
		
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.no_photo_icon)
		.showImageForEmptyUri(R.drawable.no_photo_icon)
		.showImageOnFail(R.drawable.no_photo_icon)
		.cacheInMemory()
		.cacheOnDisc()
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		
		imageLoader.displayImage(mImgSrc, img, options, animateFirstListener);
	}
	
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
