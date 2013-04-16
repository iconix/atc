package com.sapenguins.atc;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import staticVariables.ServerVariables;
import staticVariables.SpecialCharacters;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class AdsSingleViewActivity extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adlist);
		
		getAds();
	}
	
	private void getAds() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("ad", "everything"));
					
					return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			
			protected void onPostExecute(String result) {
				if (result != null) {
					String[] ads = result.split(SpecialCharacters.endLn);
					
				}
			}
		}.execute();
	}
	

}
