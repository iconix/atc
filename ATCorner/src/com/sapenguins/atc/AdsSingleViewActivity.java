package com.sapenguins.atc;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import staticVariables.ServerVariables;
import staticVariables.SpecialCharacters;
import supports.AppHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class AdsSingleViewActivity extends Activity{
	
	String title;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singlead);
		
		getAds();
		
		
		
	}
	
	private void getAds() {
		
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
				if (result != null) {
					
					String[] ads = result.split(SpecialCharacters.endLn);
					String length = "" + ads.length;
					String test = ads[200];
					String[] content = test.split(SpecialCharacters.delimiter);
					String contentLength = "" + content.length;
					
					TextView text = (TextView)findViewById(R.id.singleAdTitle);
					
					text.setText(contentLength);
					
				}
			}
		}.execute();
	}
	

}
