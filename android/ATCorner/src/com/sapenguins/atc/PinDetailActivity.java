package com.sapenguins.atc;

import objects.PinMarkerObj;
import supports.TimeFrame;
import dataSources.PinMarkerDataSource;
import dataSources.SQLTablesHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PinDetailActivity extends Activity{
	
	PinMarkerDataSource pinMarkerDataSource;
	PinMarkerObj pinMarkerObj;
	TextView type;
	TextView title;
	TextView fulltime;
	TextView description;
	ImageView img;
	TextView edit;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_detail);
		context = this;
		initViewComponents();
		Intent intent = getIntent();
		int pinId = intent.getIntExtra("pinId", -1);
		pinMarkerDataSource = new PinMarkerDataSource(this);
		pinMarkerDataSource.open();
		if (pinId != -1) {
			pinMarkerObj = pinMarkerDataSource.getPin(pinId);
			if (pinMarkerObj != null) {
				displayPinInfo(pinMarkerObj);
				setEditButtonClickListener();
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
		if (pinObj.getPinType().equals(SQLTablesHelper.PIN_TYPE_MARK)) {
			//no image
			type.setText("Check-in");
		} else {
			BitmapDrawable drawable = new BitmapDrawable(getResources(), pinObj.getImageUrl());
			img.setImageDrawable(drawable);
			type.setText("My Photo");
		}
	}
	
	/**
	 * Set up the view components
	 */
	private void initViewComponents() {
		type = (TextView)findViewById(R.id.history_detail_type);
		title = (TextView)findViewById(R.id.history_detail_title);
		fulltime = (TextView)findViewById(R.id.history_detail_time);
		description = (TextView)findViewById(R.id.history_detail_description);
		img = (ImageView)findViewById(R.id.history_detail_image);
		edit = (TextView)findViewById(R.id.history_detail_edit);
	}
	
	/**
	 * Set up the edit button click listener. 
	 * Allow the user to change the title and the description of the pin
	 */
	private void setEditButtonClickListener() {
		edit.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayEditDialog();
			}
		});
	}
	
	/**
	 * Display edit dialog
	 */
	private void displayEditDialog() {
		LayoutInflater li = LayoutInflater.from(context);
        final View view = li.inflate(R.layout.inputpin, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setTitle("Edit title and description");
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                EditText newTitle = (EditText)view.findViewById(R.id.pin_title);
                EditText newDescription = (EditText)view.findViewById(R.id.pin_description);
                title.setText(newTitle.getText().toString());
                description.setText(newDescription.getText().toString());
                pinMarkerDataSource.addPin(new PinMarkerObj(-1, title.getText().toString(), description.getText().toString(),
                		pinMarkerObj.getLongitude(), pinMarkerObj.getLatitude(), pinMarkerObj.getTime(),
                		pinMarkerObj.getPinType(), pinMarkerObj.getImageUrl(), pinMarkerObj.getNearbyLoc()));
                pinMarkerDataSource.removePinWithId(pinMarkerObj.getPinID());
            }
        });
        
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();                 
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		pinMarkerDataSource.close();
	}
	
}
