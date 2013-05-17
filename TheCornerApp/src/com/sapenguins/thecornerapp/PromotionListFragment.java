package com.sapenguins.thecornerapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sapenguins.thecornerapp.constants.Global;
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.objects.BasicPromotion;
import com.sapenguins.thecornerapp.objects.PromotionRowItem;
import com.sapenguins.thecornerapp.supports.GeoLocation;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.ImageLoading;
import com.sapenguins.thecornerapp.templates.PromotionListViewAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class PromotionListFragment extends ListFragment {

	ImageLoading imageLoading;
	ArrayList<BasicPromotion> basicPromotionObjects;
	ArrayList<PromotionRowItem> promotionRowItems;
	Context context;
	LocationManager locationManager; 
	String provider;
	ListView listView;
	String category;
	double distance;
	boolean isSwiping = false;
	boolean isLongClick = false;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();

		imageLoading = new ImageLoading(context);
		//add swipe gesture to our list view
		listView = getListView();

		//get last known location to calculate the bird-eye distance
		locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		provider = locationManager.getBestProvider(new Criteria(), true);
		if (provider == null) onProviderDisabled(provider); 

		category = "All";
        
        //when it first create, query everything in 0.5 miles radius
        distance = 0.5;
		getBasicPromotionsFromDB(distance);

		//add long click listener to list view
		setListViewLongClickListener();
		setListViewSwipeListener();
	}
	
	/**
	 * Set the list view swipe listener. If the swipe action is perform in the list view,
	 * then move to the next category in the top view
	 */
	private void setListViewSwipeListener() {
		listView.setOnTouchListener(new View.OnTouchListener() {
			private float downX, upX;
			private static final int MIN_DISTANCE = 150;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				isSwiping = true;
				switch (event.getAction()) {
			        case MotionEvent.ACTION_DOWN:
			        	isLongClick = false;
			            downX = event.getX();
			            return false; // allow other events like Click to be processed
			        case MotionEvent.ACTION_UP:
			            upX = event.getX();	
			            float deltaX = downX - upX;
			            // horizontal swipe detection
			            if (Math.abs(deltaX) > MIN_DISTANCE) {
			                // left or right
			            	if (deltaX < 0) { 
			            		if (!isLongClick)
			                    swipe(SWIPE_FROM_LEFT_TO_RIGHT);
			                    isSwiping = true;
			                    return true;
			                }
			                if (deltaX > 0) {   
			                	if (!isLongClick)
			                    swipe(SWIPE_FROM_RIGHT_TO_LEFT);
			                    isSwiping = true;
			                    return true;
			                }
			            } else {
			            	isSwiping = false;
			            	return false;
			            }
				}
				isSwiping = false;
				return false;
			}
		});
	}
	
	/**
	 * Set up the long click listener for the list view.
	 * Also handle the case when the action is swipe
	 */
	private void setListViewLongClickListener() {
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
	    	@Override
	    	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) {   		
        		if (!isSwiping) {
		    		final int mposition = position;
	        		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				    builder.setTitle("Would you like to get to this location?")
				    	.setPositiveButton("YES", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
					    		BasicPromotion promotion = basicPromotionObjects.get(mposition);
					    		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					   	    		 Uri.parse("google.navigation:q=" + promotion.getLatitude() + "," + promotion.getLongitude()));
					    		startActivity(intent);
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
			    	isLongClick = true;
		        	return false;
        		}
        		isLongClick = false;
        		return true;
	    	}
	    });
	} 
	
	/**
	 * Main activity use this to send the category for the search down
	 */
	public void setSearchCategory(String cat) {
		category = cat;
		getBasicPromotionsFromDB(distance);
	}
	
	/**
	 * Main activity use this to send the distance for the search down
	 */
	public void setSearchDistance(double d) {
		distance = d;
		getBasicPromotionsFromDB(distance);
	}
	
	/**
	 * Main activity use this to send the distance and category for the search down
	 */
	public void setSearchDistanceAndCategory(double d, String cat) {
		distance = d;
		category = cat;
		getBasicPromotionsFromDB(distance);
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
	 * Get the basic information on promotions from the DB
	 * Input these promotions into BasicPromotion array
	 * @param the distance to consider
	 */
	public void getBasicPromotionsFromDB(double distance) {
		final double mDistance = distance;
		new AsyncTask<Void, Void, String>() {
			double minLng;
			double maxLng;
			double minLat;
			double maxLat;

			@Override
			protected void onPreExecute() {
				Location lastKnown = locationManager.getLastKnownLocation(provider);
				GeoLocation currentLoc = GeoLocation.fromDegrees(lastKnown.getLatitude(), lastKnown.getLongitude());
				GeoLocation[] boundingCoors = currentLoc.boundingCoordinates(mDistance, Global.EARTH_RADIUS_IN_MILES);
				minLng = boundingCoors[0].getLongitudeInDegrees();
				maxLng = boundingCoors[1].getLongitudeInDegrees();
				minLat = boundingCoors[0].getLatitudeInDegrees();
				maxLat = boundingCoors[1].getLatitudeInDegrees();
			}

			@Override
			protected String doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("deal", category));
					postParameters.add(new BasicNameValuePair("minLng", String.valueOf(minLng)));
					postParameters.add(new BasicNameValuePair("maxLng", String.valueOf(maxLng)));
					postParameters.add(new BasicNameValuePair("minLat", String.valueOf(minLat)));
					postParameters.add(new BasicNameValuePair("maxLat", String.valueOf(maxLat)));
					return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				basicPromotionObjects = new ArrayList<BasicPromotion>();
				if (result != null) {
					String[] myPromotions = result.split(SpecialCharacters.endLn);
					for (String promotion : myPromotions) {
						String[] promotionDetails = promotion.split(SpecialCharacters.delimiter);
						//check if the promotion detail is valid
						if (promotionDetails.length == 6) {
							//check if the shortDescription is empty. If it is then make it empty
							String shortDescription = promotionDetails[5];
							if (shortDescription.equals(SpecialCharacters.empty))
								shortDescription = ""; // set it back to empty						
							//check if the url is empty
							String imageUrl = promotionDetails[4];
							if (imageUrl.equals(SpecialCharacters.empty))
								imageUrl = ""; // set it back to empty
							basicPromotionObjects.add(new BasicPromotion(promotionDetails[0], promotionDetails[1],
									promotionDetails[2], promotionDetails[3], imageUrl, shortDescription));
						}
					}
					getPromotionListViewRows();
				}
			}
		}.execute();
	}


	/**
	 * Get the list view row and display it in the list view
	 */
	private void getPromotionListViewRows() {
		promotionRowItems = new ArrayList<PromotionRowItem>();
		if (basicPromotionObjects != null) {
			for (BasicPromotion promotion : basicPromotionObjects) {
				double distance = computeDistanceToPromotion(promotion);
				String displayDistance = String.format("%.2f", distance) + " mi";
				PromotionRowItem promotionRowItem = new PromotionRowItem(promotion.getTitle(),
						promotion.getShortDescription(), 
						displayDistance, promotion.getImageUrl());
				promotionRowItems.add(promotionRowItem);
			}
			//display the first item on the list 
			if (basicPromotionObjects.size() > 0) {
				BasicPromotion promotion = basicPromotionObjects.get(0);
				double distance = computeDistanceToPromotion(promotion);
				String displayDistance = String.format("%.2f", distance) + " mi";
				passDetail(promotion.getId(), promotion.getTitle(), promotion.getImageUrl(),
						promotion.getShortDescription(), promotion.getLongitude(), promotion.getLatitude(), displayDistance);
			} else passDetail(-1, null, null, null, 0, 0, null);
		}
		PromotionListViewAdapter adapter = new PromotionListViewAdapter(context, R.layout.promotion_list_row, promotionRowItems, imageLoading);
		setListAdapter(adapter);
	}

	/**
	 * When the item on the list is clicked, it pass the information to the detail view
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {
		BasicPromotion promotion = basicPromotionObjects.get(position);
		double distance = computeDistanceToPromotion(promotion);
		String displayDistance = String.format("%.2f", distance) + " mi";
		passDetail(promotion.getId(), promotion.getTitle(), promotion.getImageUrl(), 
				promotion.getShortDescription(), promotion.getLongitude(), promotion.getLatitude(), displayDistance);
	}


	/**
	 * Get the distance between the current location and the location of the
	 * promotion
	 * @param the promotion
	 * @return distance between them
	 */
	private double computeDistanceToPromotion(BasicPromotion promotion) {
		Location lastKnown = locationManager.getLastKnownLocation(provider);
		Location promotionLocation = new Location(provider);
		promotionLocation.setLatitude(promotion.getLatitude());
		promotionLocation.setLongitude(promotion.getLongitude());
		return lastKnown.distanceTo(promotionLocation)/Global.METERS_IN_MILE;	
	}

	//////////INTERACTION BETWEEN FRAGMENT AND ACTIVITY//////////////
	private static final boolean SWIPE_FROM_LEFT_TO_RIGHT = true;
	private static final boolean SWIPE_FROM_RIGHT_TO_LEFT = false;
	OnDetailPass detailPasser;
	Swipe swiping;
	/**
	 * Interface to pass the information (coordinate) of selected item back to activity 
	 * containing it
	 */
	public interface OnDetailPass {
	    public void onDetailPass(int dealId, String title, String imageUrl, String desc, double longitude, double latitude, String distance);
	}
	/**
	 * Passing the coordinate from the fragment to activity
	 * @param coordinate
	 */
	public void passDetail(int eventId, String title, String imageUrl, String desc, double longitude, double latitude, String distance) {
	    detailPasser.onDetailPass(eventId, title, imageUrl, desc, longitude, latitude, distance);
	}
	
	public interface Swipe{
	    public void triggerSwipe(boolean fromLeftToRight);
	}
	
	public void swipe(boolean fromLeftToRight) {
		swiping.triggerSwipe(fromLeftToRight);
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		detailPasser = (OnDetailPass) activity;
		swiping = (Swipe) activity;
	}

}
