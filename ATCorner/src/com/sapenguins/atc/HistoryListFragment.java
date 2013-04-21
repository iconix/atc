package com.sapenguins.atc;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
import templates.HistoryListViewAdapter;
import templates.HistoryRowItem;

import dataSources.PinMarkerDataSource;
import dataSources.PinMarkerObj;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class HistoryListFragment extends ListFragment{
	Context context;
	PinMarkerDataSource pinMarkerDataSource;
	ArrayList<PinMarkerObj> pinMarkerObjects;
	ArrayList<HistoryRowItem> historyRowItems;
	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    context = getActivity();
	    pinMarkerDataSource = new PinMarkerDataSource(context);
		pinMarkerDataSource.open();
	    addPinFromDB();
	    getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
	    	/* (non-Javadoc)
	    	 * @see android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)
	    	 */
	    	@Override
	    	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) {
	        	CharSequence[] mapOptions = {"Remove Marked Event", "Get To Marked Event"};
	        	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        	builder.setTitle("Would you like to:")
	        	.setSingleChoiceItems(mapOptions, -1, new DialogInterface.OnClickListener() {
	    			@Override
	    			public void onClick(DialogInterface dialog, int which) {
	    				if (which == 1) {
	    					//TODO invoke gmap to get to the current coordinate
	    				} else {
	    					//TODO remove the marked event, change the map and update the list view
	    				}
	    			}
	    		})
	    		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(DialogInterface dialog, int which) {
	    				dialog.cancel();
	    			}
	    		});
	        	AlertDialog alert = builder.create();
	        	alert.show();
	        	return false;
	    	}
	    });
	}
	
	private void addPinFromDB() {
		historyRowItems = new ArrayList<HistoryRowItem>();
		pinMarkerObjects = pinMarkerDataSource.getPins();
		for (PinMarkerObj pinObj : pinMarkerObjects) {
			HistoryRowItem rowItem = new HistoryRowItem(R.drawable.pin_history_icon, pinObj.getTitle(), pinObj.getDescription(), String.valueOf(pinObj.getTime()));
			historyRowItems.add(rowItem);
		}
		HistoryListViewAdapter adapter = new HistoryListViewAdapter(context, R.layout.history_list_row, historyRowItems);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	    PinMarkerObj pinObj = pinMarkerObjects.get(position);
	    passCoordinate(new LatLng(pinObj.getLatitude(), pinObj.getLongitude()));
	}

	/**
	 * Interface to pass the information (coordinate) of selected item back to activity 
	 * containing it
	 * @author minhthaonguyen
	 */
	public interface OnCoordinatePass {
	    public void onCoordinatePass(LatLng coordinate);
	}
	
	OnCoordinatePass coordinatePasser;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		coordinatePasser = (OnCoordinatePass) activity;
	}
	
	/**
	 * Passing the coordinate from the fragment to activity
	 * @param coordinate
	 */
	public void passCoordinate(LatLng coordinate) {
	    coordinatePasser.onCoordinatePass(coordinate);
	}

	
}
