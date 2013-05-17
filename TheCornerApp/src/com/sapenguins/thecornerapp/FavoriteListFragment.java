package com.sapenguins.thecornerapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sapenguins.thecornerapp.constants.Global;
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.datasources.AdsDataSource;
import com.sapenguins.thecornerapp.objects.BasicFavorite;
import com.sapenguins.thecornerapp.objects.FavoriteRowItem;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.ImageLoading;
import com.sapenguins.thecornerapp.templates.FavoriteListViewAdapter;

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

public class FavoriteListFragment extends ListFragment {

	ImageLoading imageLoading;
	ArrayList<BasicFavorite> basicFavoriteObjects;
	ArrayList<FavoriteRowItem> favoriteRowItems;
	Context context;
	LocationManager locationManager; 
	String provider;
	ListView listView;
	
	String favoriteIds;
	AdsDataSource dataSource;
	
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

		dataSource = new AdsDataSource(context);
		dataSource.open();
		favoriteIds = dataSource.getFavoriteAds();
		
		getBasicFavoritesFromDB(true);

		//add long click listener to list view
		setListViewLongClickListener();
		setListViewSwipeListener();
	}
	
	@Override
	public void onDestroy() {
		dataSource.close();
		super.onDestroy();
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
		            		if (!isLongClick)
		            			swipe();
		                    isSwiping = true;
		                    return true;
			               
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
					    		BasicFavorite favorite = basicFavoriteObjects.get(mposition);
					    		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					   	    		 Uri.parse("google.navigation:q=" + favorite.getLatitude() + "," + favorite.getLongitude()));
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
	 * Get the basic information on favorites from the DB
	 * Input these favorites into BasicFavorite array
	 * @param boolean whether if we wish to display past or ongoing
	 */
	public void getBasicFavoritesFromDB(boolean onGoing) {
		final boolean isOnGoing = onGoing;
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					if (isOnGoing)
						postParameters.add(new BasicNameValuePair("favorite", "isOnGoing"));
					else postParameters.add(new BasicNameValuePair("favorite", "isNotOnGoing"));
					postParameters.add(new BasicNameValuePair("id", favoriteIds));
					//Log.i("FAVORITE", favoriteIds);
					return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				basicFavoriteObjects = new ArrayList<BasicFavorite>();
				if (result != null) {
					String[] myFavorites = result.split(SpecialCharacters.endLn);
					for (String favorite : myFavorites) {
						//Log.i("MY_FAVORITE", favorite);
						String[] favoriteDetails = favorite.split(SpecialCharacters.delimiter);
						//check if the favorite detail is valid
						if (favoriteDetails.length == 6) {
							//check if the shortDescription is empty. If it is then make it empty
							String shortDescription = favoriteDetails[5];
							if (shortDescription.equals(SpecialCharacters.empty))
								shortDescription = ""; // set it back to empty		
							//check if the url is empty
							String imageUrl = favoriteDetails[4];
							if (imageUrl.equals(SpecialCharacters.empty))
								imageUrl = ""; // set it back to empty}
							basicFavoriteObjects.add(new BasicFavorite(favoriteDetails[0], favoriteDetails[1],
									favoriteDetails[2], favoriteDetails[3], imageUrl, shortDescription));
						}
					}
					getFavoriteListViewRows();
				}
			}
		}.execute();
	}


	/**
	 * Get the list view row and display it in the list view
	 */
	private void getFavoriteListViewRows() {
		favoriteRowItems = new ArrayList<FavoriteRowItem>();
		if (basicFavoriteObjects != null) {
			for (BasicFavorite favorite : basicFavoriteObjects) {
				double distance = computeDistanceToFavorite(favorite);
				String displayDistance = String.format("%.2f", distance) + " mi";
				FavoriteRowItem favoriteRowItem = new FavoriteRowItem(favorite.getTitle(),
						favorite.getShortDescription(), 
						displayDistance, favorite.getImageUrl());
				favoriteRowItems.add(favoriteRowItem);
			}
			//display the first item on the list 
			if (basicFavoriteObjects.size() > 0) {
				BasicFavorite favorite = basicFavoriteObjects.get(0);
				double distance = computeDistanceToFavorite(favorite);
				String displayDistance = String.format("%.2f", distance) + " mi";
				passDetail(favorite.getId(), favorite.getTitle(), favorite.getImageUrl(),
						favorite.getShortDescription(), favorite.getLongitude(), favorite.getLatitude(), displayDistance);
			} else {
				passDetail(-1, null, null, null, 0, 0, null);
			}
		}
		FavoriteListViewAdapter adapter = new FavoriteListViewAdapter(context, R.layout.favorite_list_row, favoriteRowItems, imageLoading);
		setListAdapter(adapter);
	}

	/**
	 * When the item on the list is clicked, it pass the information to the detail view
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {
		BasicFavorite favorite = basicFavoriteObjects.get(position);
		double distance = computeDistanceToFavorite(favorite);
		String displayDistance = String.format("%.2f", distance) + " mi";
		passDetail(favorite.getId(), favorite.getTitle(), favorite.getImageUrl(), 
				favorite.getShortDescription(), favorite.getLongitude(), favorite.getLatitude(), displayDistance);
	}


	/**
	 * Get the distance between the current location and the location of the
	 * favorite
	 * @param the favorite
	 * @return distance between them
	 */
	private double computeDistanceToFavorite(BasicFavorite favorite) {
		Location lastKnown = locationManager.getLastKnownLocation(provider);
		Location favoriteLocation = new Location(provider);
		favoriteLocation.setLatitude(favorite.getLatitude());
		favoriteLocation.setLongitude(favorite.getLongitude());
		return lastKnown.distanceTo(favoriteLocation)/Global.METERS_IN_MILE;	
	}

	//////////INTERACTION BETWEEN FRAGMENT AND ACTIVITY//////////////

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
	    public void triggerSwipe();
	}
	
	public void swipe() {
		swiping.triggerSwipe();
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		detailPasser = (OnDetailPass) activity;
		swiping = (Swipe) activity;
	}
	

}
