package com.sapenguins.atc;

import objects.PinMarkerObj;
import supports.TimeFrame;
import dataSources.PinMarkerDataSource;
import dataSources.SQLTablesHelper;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PinDetailActivity extends Activity{
	
	PinMarkerDataSource pinMarkerDataSource;
	TextView title;
	TextView fulltime;
	TextView description;
	ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_detail);
		initViewComponents();
		Intent intent = getIntent();
		int pinId = intent.getIntExtra("pinId", -1);
		pinMarkerDataSource = new PinMarkerDataSource(this);
		pinMarkerDataSource.open();
		if (pinId != -1) {
			PinMarkerObj pinMarkerObj = pinMarkerDataSource.getPin(pinId);
			if (pinMarkerObj != null) {
				displayPinInfo(pinMarkerObj);
			}
		}
	}
	
	/**
	 * Display the information from the PinMarkerObj to the view
	 * @param PinMarkerObj
	 */
	private void displayPinInfo(PinMarkerObj pinObj) {
		title.setText(pinObj.getTitle());
		description.setText(pinObj.getDescription());
		String time = TimeFrame.getTimeInString(pinObj.getTime());
		fulltime.setText(time);
		if (pinObj.getPinType().equals(SQLTablesHelper.PIN_TYPE_MARK))
			img.setImageResource(R.drawable.history);
		else {
			BitmapDrawable drawable = new BitmapDrawable(getResources(), pinObj.getImageUrl());
			img.setImageDrawable(drawable);
		}
	}
	
	/**
	 * Set up the view components
	 */
	private void initViewComponents() {
		title = (TextView)findViewById(R.id.history_detail_title);
		fulltime = (TextView)findViewById(R.id.history_detail_time);
		description = (TextView)findViewById(R.id.history_detail_description);
		img = (ImageView)findViewById(R.id.history_detail_image);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		pinMarkerDataSource.close();
	}
	
}
