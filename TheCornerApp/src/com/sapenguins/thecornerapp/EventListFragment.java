package com.sapenguins.thecornerapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sapenguins.thecornerapp.constants.Global;
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.objects.BasicEvent;
import com.sapenguins.thecornerapp.objects.EventRowItem;
import com.sapenguins.thecornerapp.supports.GeoLocation;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.templates.EventListViewAdapter;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class EventListFragment extends ListFragment {
	
	ArrayList<BasicEvent> basicEventObjects;
	ArrayList<EventRowItem> eventRowItems;
	Context context;
	LocationManager locationManager; 
	String provider;
	ListView listView;
	String category;
	double distance;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    context = getActivity();
	    
	    //add swipe gesture to our list view
	    listView = getListView();
	    
	    //get last known location to calculate the bird-eye distance
	    locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
	    provider = locationManager.getBestProvider(new Criteria(), true);
        if (provider == null) onProviderDisabled(provider); 
        
        //when it first create, query everything in 0.5 miles radius
        distance = 0.5;
        category = "All";
        getBasicEventsFromDB(distance);
        
	    //add long click listener to list view
	    setListViewLongClickListener();
	}
	
	/**
	 * Set up the long click listener for the list view.
	 * Also handle the case when the action is swipe
	 */
	private void setListViewLongClickListener() {
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
	    	@Override
	    	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) {   		
        		final int mposition = position;
        		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			    builder.setTitle("Would you like to get to this location?")
			    	.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
				    		BasicEvent event = basicEventObjects.get(mposition);
				    		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				   	    		 Uri.parse("google.navigation:q=" + event.getLatitude() + "," + event.getLongitude()));
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
	        	
	        	return false;
	    	}
	    });
	} 
	
	/**
	 * Main activity use this to send the category for the search down
	 */
	public void setSearchCategory(String cat) {
		category = cat;
		getBasicEventsFromDB(distance);
	}
	
	/**
	 * Main activity use this to send the distance for the search down
	 */
	public void setSearchDistance(double d) {
		distance = d;
		getBasicEventsFromDB(distance);
	}
	
	/**
	 * Main activity use this to send the distance and category for the search down
	 */
	public void setSearchDistanceAndCategory(double d, String cat) {
		distance = d;
		category = cat;
		getBasicEventsFromDB(distance);
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
	 * Get the basic information on events from the DB
	 * Input these events into BasicEvent array
	 * @param the distance to consider
	 */
	public void getBasicEventsFromDB(double distance) {
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
					postParameters.add(new BasicNameValuePair("event", category));
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
				basicEventObjects = new ArrayList<BasicEvent>();
				if (result != null) {
					String[] myEvents = result.split(SpecialCharacters.endLn);
					for (String event : myEvents) {
						String[] eventDetails = event.split(SpecialCharacters.delimiter);
						//check if the event detail is valid
						if (eventDetails.length == 6) {
							//check if the shortDescription is empty. If it is then make it empty
							String shortDescription = eventDetails[5];
							if (shortDescription.equals(SpecialCharacters.empty))
								shortDescription = ""; // set it back to empty
							//check if the url is empty
							String imageUrl = eventDetails[4];
							if (imageUrl.equals(SpecialCharacters.empty))
								imageUrl = ""; // set it back to empty
							basicEventObjects.add(new BasicEvent(eventDetails[0], eventDetails[1],
									eventDetails[2], eventDetails[3], imageUrl, shortDescription));
						}
					}
					getEventListViewRows();
				}
			}
		}.execute();
	}
	
	
	/**
	 * Get the list view row and display it in the list view
	 */
	private void getEventListViewRows() {
		eventRowItems = new ArrayList<EventRowItem>();
		if (basicEventObjects != null) {
			for (BasicEvent event : basicEventObjects) {
				double distance = computeDistanceToEvent(event);
				String displayDistance = String.format("%.2f", distance) + " mi";
				EventRowItem eventRowItem = new EventRowItem(event.getTitle(),
						event.getShortDescription(), 
						displayDistance, event.getImageUrl());
				eventRowItems.add(eventRowItem);
			}
			 //display the first item on the list 
		    if (basicEventObjects.size() > 0) {
		    	BasicEvent event = basicEventObjects.get(0);
		    	double distance = computeDistanceToEvent(event);
		 		String displayDistance = String.format("%.2f", distance) + " mi";
		    	passDetail(event.getId(), event.getTitle(), event.getImageUrl(), event.getShortDescription(), event.getLongitude(), event.getLatitude(), displayDistance);
		    }
		}
		EventListViewAdapter adapter = new EventListViewAdapter(context, R.layout.event_list_row, eventRowItems);
		setListAdapter(adapter);
	}
	
	/**
	 * When the item on the list is clicked, it pass the information to the detail view
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {
	    BasicEvent event = basicEventObjects.get(position);
	    double distance = computeDistanceToEvent(event);
		String displayDistance = String.format("%.2f", distance) + " mi";
		passDetail(event.getId(), event.getTitle(), event.getImageUrl(), event.getShortDescription(), event.getLongitude(), event.getLatitude(), displayDistance);
	}
	
	
	/**
	 * Get the distance between the current location and the location of the
	 * event
	 * @param the event
	 * @return distance between them
	 */
	private double computeDistanceToEvent(BasicEvent event) {
		Location lastKnown = locationManager.getLastKnownLocation(provider);
	    Location eventLocation = new Location(provider);
		eventLocation.setLatitude(event.getLatitude());
		eventLocation.setLongitude(event.getLongitude());
		return lastKnown.distanceTo(eventLocation)/Global.METERS_IN_MILE;	
	}

	//////////INTERACTION BETWEEN FRAGMENT AND ACTIVITY//////////////
	/**
	 * Interface to pass the information (coordinate) of selected item back to activity 
	 * containing it
	 * @author minhthaonguyen
	 */
	public interface OnDetailPass {
	    public void onDetailPass(int dealId, String title, String imageUrl, String desc, double longitude, double latitude, String distance);
	}
	
	OnDetailPass detailPasser;
	
	/**
	 * Passing the coordinate from the fragment to activity
	 * @param coordinate
	 */
	public void passDetail(int eventId, String title, String imageUrl, String desc, double longitude, double latitude, String distance) {
	    detailPasser.onDetailPass(eventId, title, imageUrl, desc, longitude, latitude, distance);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		detailPasser = (OnDetailPass) activity;
	}
	
}
