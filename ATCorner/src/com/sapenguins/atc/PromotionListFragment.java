package com.sapenguins.atc;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gms.maps.model.LatLng;

import objects.BasicPromotion;
import staticVariables.ServerVariables;
import staticVariables.SpecialCharacters;
import supports.AppHttpClient;
import supports.GpsDistance;
import templates.PromotionListViewAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import objects.*;

public class PromotionListFragment extends ListFragment {
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
	    //displayPromotion();
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
	
	
	private void displayPromotion() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				PromotionListViewAdapter adapter = new PromotionListViewAdapter(context, R.layout.promotion_list_row, promotionRowItems);
				setListAdapter(adapter);
				return null;
			}
		}.execute();
	}
	
	

	
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
	
	private void getPromotionListViewRows() {
		promotionRowItems = new ArrayList<PromotionRowItem>();
		Location lastKnown = locationManager.getLastKnownLocation(provider);
        LatLng latLng = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
		for (BasicPromotion promotion : basicPromotionObjects) {
			double distance = GpsDistance.calculateDistance(latLng.latitude, latLng.longitude,
					promotion.getLatitude(), promotion.getLatitude());
			String displayDistance = String.format("%.2f", distance) + " mi";
			PromotionRowItem promotionRowItem = new PromotionRowItem(promotion.getTitle(),
					promotion.getShortDescription(), 
					displayDistance, promotion.getImageUrl());
			promotionRowItems.add(promotionRowItem);
		}
		PromotionListViewAdapter adapter = new PromotionListViewAdapter(context, R.layout.promotion_list_row, promotionRowItems);
		setListAdapter(adapter);
	}
}
