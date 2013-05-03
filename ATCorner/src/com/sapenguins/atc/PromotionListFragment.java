package com.sapenguins.atc;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import objects.BasicPromotion;
import staticVariables.ServerVariables;
import staticVariables.SpecialCharacters;
import supports.AppHttpClient;
import templates.PromotionListViewAdapter;
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
import objects.*;

public class PromotionListFragment extends ListFragment {
	
	private static final double METERS_IN_MILE = 1609.344;
	
	ArrayList<BasicPromotion> basicPromotionObjects;
	ArrayList<PromotionRowItem> promotionRowItems;
	Context context;
	LocationManager locationManager; 
	String provider;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    context = getActivity();
	    
	    //get last known location to calculate the bird-eye distance
	    locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
	    provider = locationManager.getBestProvider(new Criteria(), true);
        if (provider == null) onProviderDisabled(provider); 
        
	    getBasicPromotionsFromDB();
	    
	    getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
	    	/* (non-Javadoc)
	    	 * @see android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)
	    	 */
	    	@Override
	    	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) {
	    		BasicPromotion promotion = basicPromotionObjects.get(position);
	    		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
	   	    		 Uri.parse("google.navigation:q=" + promotion.getLatitude() + "," + promotion.getLongitude()));
	    		startActivity(intent);
	        	return false;
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
	 * Get the basic information on promotions from the DB
	 * Input these promotions into BasicPromotion array
	 */
	private void getBasicPromotionsFromDB() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("deal", "everything"));
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
						basicPromotionObjects.add(new BasicPromotion(promotionDetails[0], promotionDetails[1],
								promotionDetails[2], promotionDetails[3], promotionDetails[4], promotionDetails[5]));
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
		Location lastKnown = locationManager.getLastKnownLocation(provider);
		for (BasicPromotion promotion : basicPromotionObjects) {
			Location promotionLocation = new Location(provider);
			promotionLocation.setLatitude(promotion.getLatitude());
			promotionLocation.setLongitude(promotion.getLongitude());
			double distance = lastKnown.distanceTo(promotionLocation)/METERS_IN_MILE;	
			String displayDistance = String.format("%.2f", distance) + " mi";
			PromotionRowItem promotionRowItem = new PromotionRowItem(promotion.getTitle(),
					promotion.getShortDescription(), 
					displayDistance, promotion.getImageUrl());
			promotionRowItems.add(promotionRowItem);
		}
		PromotionListViewAdapter adapter = new PromotionListViewAdapter(context, R.layout.promotion_list_row, promotionRowItems);
		setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
	    BasicPromotion promotion = basicPromotionObjects.get(position);
	    Location lastKnown = locationManager.getLastKnownLocation(provider);
	    Location promotionLocation = new Location(provider);
		promotionLocation.setLatitude(promotion.getLatitude());
		promotionLocation.setLongitude(promotion.getLongitude());
		double distance = lastKnown.distanceTo(promotionLocation)/METERS_IN_MILE;	
		String displayDistance = String.format("%.2f", distance) + " mi";
		passDetail(promotion.getTitle(), promotion.getShortDescription(), displayDistance, promotion.getImageUrl());
		/*final String promotionTitle = promotion.getTitle();
		final String promotionDescription = promotion.getShortDescription();
		final String promotionDistance = "1mi";
		final String promotionImageUrl = promotion.getImageUrl();
			
	
			Intent i = new Intent(context, PromotionDetailActivity.class);
			i.putExtra("promotionTitle", promotionTitle);
			i.putExtra("promotionDescription", promotionDescription);
			i.putExtra("promotionDistance", promotionDistance);
			i.putExtra("promotionImageUrl", promotionImageUrl);
			context.startActivity(i);*/
	}
	
	/**
	 * Interface to pass the information (coordinate) of selected item back to activity 
	 * containing it
	 * @author minhthaonguyen
	 */
	public interface OnDetailPass {
	    public void onDetailPass(String title, String description, String distance, String imageUrl);
	}
	
	OnDetailPass detailPasser;
	
	/**
	 * Passing the coordinate from the fragment to activity
	 * @param coordinate
	 */
	public void passDetail(String title, String description, String distance, String imageUrl) {
	    detailPasser.onDetailPass(title, description, distance, imageUrl);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		detailPasser = (OnDetailPass) activity;
	}
	
}
