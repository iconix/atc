package com.sapenguins.atc;

import java.io.File;

import dataSources.PinMarkerDataSource;
import dataSources.SQLTablesHelper;

import objects.PinMarkerObj;
import supports.TimeFrame;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryDetailFragment extends Fragment {
	
	PinMarkerDataSource pinMarkerDataSource;
	Context context;
	View view;
	TextView type;
	TextView title;
	TextView fulltime;
	TextView description;
	ImageView img;
	TextView edit;
	PinMarkerObj pinMarkerObj;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		view = inflater.inflate(R.layout.history_detail, container, false);
		pinMarkerDataSource = new PinMarkerDataSource(context);
		pinMarkerDataSource.open();
		initViewComponents();
		setEditButtonClickListener();
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
		edit = (TextView)view.findViewById(R.id.history_detail_edit);
	}
	
	/**
	 * display detail information of the time/event. Call from the activity container
	 * @param PinMarkerObj
	 */
	public void dislayHistoryDetail(PinMarkerObj pinObj) {
		pinMarkerObj = pinObj;
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

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		pinMarkerDataSource.close();
	}
	
	
}
