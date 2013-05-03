package com.sapenguins.atc;

import java.io.File;

import dataSources.SQLTablesHelper;

import objects.PinMarkerObj;
import supports.TimeFrame;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryDetailFragment extends Fragment {
	
	Context context;
	View view;
	TextView type;
	TextView title;
	TextView fulltime;
	TextView description;
	ImageView img;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		view = inflater.inflate(R.layout.history_detail, container, false);
		initViewComponents();
		return view;
	}
	
	/**
	 * Set up the view components
	 */
	private void initViewComponents() {
		type = (TextView)view.findViewById(R.id.history_detail_type);
		title = (TextView)view.findViewById(R.id.history_detail_title);
		fulltime = (TextView)view.findViewById(R.id.history_detail_time);
		description = (TextView)view.findViewById(R.id.history_detail_description);
		img = (ImageView) view.findViewById(R.id.history_detail_image);
	}
	
	/**
	 * display detail information of the time/event. Call from the activity container
	 * @param PinMarkerObj
	 */
	public void dislayHistoryDetail(PinMarkerObj pinObj) {
		title.setText(pinObj.getTitle());
		description.setText(pinObj.getDescription());
		String time = TimeFrame.getTimeInString(pinObj.getTime());
		fulltime.setText(time);
		if (pinObj.getPinType().equals(SQLTablesHelper.PIN_TYPE_MARK)) { 
			//no image
			type.setText("Check-in");
		}
		else {
			BitmapDrawable drawable = new BitmapDrawable(getResources(), pinObj.getImageUrl());
			img.setImageDrawable(drawable);
			type.setText("My Photo");
		}
		
	}
}
