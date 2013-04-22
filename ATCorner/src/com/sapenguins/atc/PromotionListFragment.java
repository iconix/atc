package com.sapenguins.atc;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import objects.BasicPromotion;
import staticVariables.ServerVariables;
import staticVariables.SpecialCharacters;
import supports.AppHttpClient;
import templates.PromotionListViewAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import objects.*;

public class PromotionListFragment extends ListFragment {
	ArrayList<BasicPromotion> basicPromotionObjects;
	ArrayList<PromotionRowItem> promotionRowItems;
	Context context;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    context = getActivity();
	    getBasicPromotionsFromDB();
	    displayPromotion();
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
		for (BasicPromotion promotion : basicPromotionObjects) {
			PromotionRowItem promotionRowItem = new PromotionRowItem(promotion.getTitle(),
					promotion.getShortDescription(), 
					"2 km", promotion.getImageUrl());
			promotionRowItems.add(promotionRowItem);
		}
	}
}
