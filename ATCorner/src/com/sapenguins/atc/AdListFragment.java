package com.sapenguins.atc;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import staticVariables.ServerVariables;
import staticVariables.SpecialCharacters;
import supports.AppHttpClient;
import templates.AdListViewAdapter;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class AdListFragment extends ListFragment {
	Context context;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    context = getActivity();
	    getAds();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

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
					AdListViewAdapter adapter = new AdListViewAdapter(context, R.layout.ad_list_row, ads);
					setListAdapter(adapter);
				}
			}
		}.execute();
	}
}
