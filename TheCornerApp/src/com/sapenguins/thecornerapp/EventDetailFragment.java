package com.sapenguins.thecornerapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sapenguins.thecornerapp.constants.Global;
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.ImageLoading;
import com.sapenguins.thecornerapp.supports.TimeFrame;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailFragment extends Fragment {
	
	Context context;
	View view;
	TextView title;
	TextView startTime;
	TextView endTime;
	ImageView img;
	int eventId;
	
	ImageLoading imageLoading;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		imageLoading = new ImageLoading(context);
		view = inflater.inflate(R.layout.event_detail, container, false);
		initViewComponents();
		return view;
	}

	/**
	 * Set up the view components
	 */
	private void initViewComponents() {
		title = (TextView)view.findViewById(R.id.event_detail_title);
		startTime = (TextView)view.findViewById(R.id.event_detail_start_time);
		endTime = (TextView)view.findViewById(R.id.event_detail_end_time);
		img = (ImageView) view.findViewById(R.id.event_detail_image);
	}
	
	/**
	 * display detail information of the Event. Call from the activity container
	 * @param eventId
	 * @param mtitle
	 * @param mImgSrc
	 */
	public void dislayEventDetail(int mEventId, String mtitle, String mImgSrc) {
		eventId = mEventId;
		if (eventId != -1) {
			title.setText(mtitle);
			imageLoading.displayImage(mImgSrc, img);
			displayTimeInfo();
		} else {
			title.setText(Global.NO_EVENT);
			img.setImageDrawable(getResources().getDrawable(R.drawable.no_photo_icon));
			startTime.setText("");
			endTime.setText("");
		}
	}
	
	/**
	 * Display more info of the event
	 */
	private void displayTimeInfo() {
		new AsyncTask<Void, Void, String>() {		
			@Override
			protected String doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("timeRequestId", String.valueOf(eventId)));
					return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				if (result != null) {
					
					String[] timeDetails = result.split(SpecialCharacters.delimiter);
					//check if the event detail is valid
					if (timeDetails.length == 2) {
						//check if the shortDescription is empty. If it is then make it empty
						startTime.setText(TimeFrame.getDisplayTime(timeDetails[0]));
						endTime.setText(TimeFrame.getDisplayTime(timeDetails[1]));
					}
				}	
			}
		}.execute();
	}
}
