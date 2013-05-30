package com.sapenguins.thecornerapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sapenguins.thecornerapp.constants.Global;
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.objects.AdRowItem;
import com.sapenguins.thecornerapp.objects.BasicAd;
import com.sapenguins.thecornerapp.supports.GeoLocation;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.ImageLoading;
import com.sapenguins.thecornerapp.templates.NewAdListViewAdapter;

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

public class DealListFragment extends ListFragment {
	
	ImageLoading imageLoading;
	ArrayList<BasicAd> basicAdObjects;
	ArrayList<AdRowItem> adRowItems;
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
	    listView.setDivider(null);
	    listView.setDividerHeight(0);
	    
	    //get last known location to calculate the bird-eye distance
	    locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
	    provider = locationManager.getBestProvider(new Criteria(), true);
        if (provider == null) onProviderDisabled(provider); 
        
        //when it first create, query everything in 0.5 miles radius
        distance = 0.5;
        category = "All";
        getBasicAdsFromDB(distance);
        
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
			private static final int MIN_DISTANCE = 400;
			
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
					    		BasicAd ad = basicAdObjects.get(mposition);
					    		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					   	    		 Uri.parse("google.navigation:q=" + ad.getLatitude() + "," + ad.getLongitude()));
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
		getBasicAdsFromDB(distance);
	}
	
	/**
	 * Main activity use this to send the distance for the search down
	 */
	public void setSearchDistance(double d) {
		distance = d;
		getBasicAdsFromDB(distance);
	}
	
	/**
	 * Main activity use this to send the distance and category for the search down
	 */
	public void setSearchDistanceAndCategory(double d, String cat) {
		distance = d;
		category = cat;
		getBasicAdsFromDB(distance);
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
	 * Get the basic information on ads from the DB
	 * @param the distance to consider
	 */
	public void getBasicAdsFromDB(double distance) {
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
					postParameters.add(new BasicNameValuePair("basicAds", "deal"));
					postParameters.add(new BasicNameValuePair("category", category));
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
				basicAdObjects = new ArrayList<BasicAd>();
				if (result != null) {
					String[] myAds = result.split(SpecialCharacters.endLn);
					for (String ad : myAds) {
						String[] adDetails = ad.split(SpecialCharacters.delimiter);
						//check if the event detail is valid
						if (adDetails.length == 9) {
							//check if the shortDescription is empty. If it is then make it empty
							String shortDescription = adDetails[6];
							if (shortDescription.equals(SpecialCharacters.empty))
								shortDescription = ""; // set it back to empty
							//check if the url is empty
							String imageUrl = adDetails[5];
							if (imageUrl.equals(SpecialCharacters.empty))
								imageUrl = ""; // set it back to empty
							basicAdObjects.add(new BasicAd(Integer.valueOf(adDetails[0]), 
									Integer.valueOf(adDetails[1]),
									adDetails[2], 
									Double.valueOf(adDetails[3]),
									Double.valueOf(adDetails[4]),
									imageUrl, 
									shortDescription,
									adDetails[7],
									adDetails[8]));
						}
					}
					getAdListViewRows();
				}
			}
		}.execute();
	}
	
	
	/**
	 * Get the list view row and display it in the list view
	 */
	private void getAdListViewRows() {
		adRowItems = new ArrayList<AdRowItem>();
		if (basicAdObjects != null) {
			for (BasicAd ad : basicAdObjects) {
				double distance = computeDistanceToAd(ad);
				String displayDistance = String.format("%.2f", distance) + " mi";
				AdRowItem adRowItem = new AdRowItem(ad.getTitle(),
						ad.getShortDescription(), 
						displayDistance, 
						ad.getImageUrl(),
						ad.getStartDate(),
						ad.getEndDate());
				adRowItems.add(adRowItem);
			}
		}
		NewAdListViewAdapter adapter = new NewAdListViewAdapter(context, R.layout.new_ad_list_row, adRowItems, imageLoading);
		setListAdapter(adapter);
	}
	
	/**
	 * When the item on the list is clicked, it pass the information to the detail view
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {
	    BasicAd ad = basicAdObjects.get(position);
	    double distance = computeDistanceToAd(ad);
		String displayDistance = String.format("%.2f", distance) + " mi";
		Intent intent = new Intent(context, AdFullDetailActivity.class);	
		intent.putExtra("adId", ad.getId());
		intent.putExtra("adDistance", displayDistance);
		intent.putExtra("adLongitude", ad.getLongitude());
		intent.putExtra("adLatitude", ad.getLatitude());
		startActivity(intent);
	}
	
	
	/**
	 * Get the distance between the current location and the location of the ad
	 * @param the ad
	 * @return distance between them
	 */
	private double computeDistanceToAd(BasicAd ad) {
		Location lastKnown = locationManager.getLastKnownLocation(provider);
	    Location adLocation = new Location(provider);
		adLocation.setLatitude(ad.getLatitude());
		adLocation.setLongitude(ad.getLongitude());
		return lastKnown.distanceTo(adLocation)/Global.METERS_IN_MILE;	
	}

	//////////INTERACTION BETWEEN FRAGMENT AND ACTIVITY//////////////
	
	private static final boolean SWIPE_FROM_LEFT_TO_RIGHT = true;
	private static final boolean SWIPE_FROM_RIGHT_TO_LEFT = false;
	Swipe swiping;
	
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
		swiping = (Swipe) activity;
	}
	
}
