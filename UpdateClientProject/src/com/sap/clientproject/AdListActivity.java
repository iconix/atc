package com.sap.clientproject;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import staticVariables.RequestParameters;
import staticVariables.ServerVariables;
import staticVariables.SpecialCharacters;


import classesAndManagers.*;
import android.app.*;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

public class AdListActivity extends Activity {
    
    ListView list;
    ListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.adlist);
            
    }
    
    /**
     * 
     */
    public void displayAdvertisementsFromDB(AdvertisementConfig adConfig) {
    	final AdvertisementConfig myAdConfig = adConfig;
    	new AsyncTask<Void, Void, String>() {
    		@Override
    		protected String doInBackground(Void... params) {
    			try {
    				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    				postParameters.add(new BasicNameValuePair(RequestParameters.ADVERTISEMENT_REQUEST_LOWER_LONGITUDE,
    						String.valueOf(myAdConfig.getLowerLongitude())));
    				postParameters.add(new BasicNameValuePair(RequestParameters.ADVERTISEMENT_REQUEST_HIGHER_LONGITUDE,
    						String.valueOf(myAdConfig.getHigherLongitude())));
    				postParameters.add(new BasicNameValuePair(RequestParameters.ADVERTISEMENT_REQUEST_LOWER_LATITUDE,
    						String.valueOf(myAdConfig.getLowerLatitude())));
    				postParameters.add(new BasicNameValuePair(RequestParameters.ADVERTISEMENT_REQUEST_HIGHER_LATITUDE,
    						String.valueOf(myAdConfig.getHigherLatitude())));
    				postParameters.add(new BasicNameValuePair(RequestParameters.ADVERTISEMENT_REQUEST_START_TIME,
    						String.valueOf(myAdConfig.getStartTime())));
    				postParameters.add(new BasicNameValuePair(RequestParameters.ADVERTISEMENT_REQUEST_END_TIME,
    						String.valueOf(myAdConfig.getEndTime())));
    				return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
    			} catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
    		}
    		
    		@Override
    		protected void onPostExecute(String result) {
    			if (result != null) {

    				
    			}
    		}
    	}.execute();
    		
    }
  
}
