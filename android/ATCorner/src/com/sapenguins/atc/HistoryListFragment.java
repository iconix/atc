package com.sapenguins.atc;

import java.util.ArrayList;

import objects.HistoryRowItem;
import objects.PinMarkerObj;

import com.google.android.gms.maps.model.LatLng;

import supports.*;
import templates.HistoryListViewAdapter;

import dataSources.PinMarkerDataSource;
import dataSources.SQLTablesHelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
	SwipeGestureDetector swipeDetector; 
	HistoryListViewAdapter adapter;
	ListView listView;

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    context = getActivity();
	    pinMarkerDataSource = new PinMarkerDataSource(context);
		pinMarkerDataSource.open();
	    addPinFromDB();
	    if (pinMarkerObjects.size() > 0) {
	    	PinMarkerObj pinObj = pinMarkerObjects.get(0); 
	    	passDetail(pinObj);
	    }
	    
	    
	    //here is the added gesture listener
	    listView = getListView();
	    swipeDetector = new SwipeGestureDetector();
	    listView.setOnTouchListener(swipeDetector);
	    setListViewLongClickListener();	    
	}
	
	
	@Override
	public void onDestroyView() {
		pinMarkerDataSource.close();
		super.onDestroyView();
	}



	/**
	 * Set a long click listener to the item list view. On long click, the user
	 * can choose to delete or modify the current information
	 */
	private void setListViewLongClickListener() {
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
	    	@Override
	    	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) {
	        	if (swipeDetector.swipeDetected()) {
	        		PinMarkerObj pinObj = pinMarkerObjects.get(position);
	        		if (swipeDetector.getAction() == SwipeGestureDetector.Action.LR) {
	        			//action from left to right then open the view with detail
	        			Intent intent = new Intent(context, PinDetailActivity.class);
	        			intent.putExtra("pinId", pinObj.getPinID());
	        			startActivity(intent);
	        		} else if (swipeDetector.getAction() == SwipeGestureDetector.Action.RL) {
	        			//if the gesture move from right to left. It mean delete
	        			pinMarkerDataSource.removePinWithId(pinObj.getPinID());
	        			pinMarkerObjects.remove(position);
	        			adapter.notifyDataSetChanged();
	        			listView.removeAllViewsInLayout();
	        			listView.refreshDrawableState();
	        		}
	        	} else {
	        		PinMarkerObj pinObj = pinMarkerObjects.get(position);
	        		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
	   	   	    		 Uri.parse("google.navigation:q=" + pinObj.getLatitude() + "," + pinObj.getLongitude()));
	        		startActivity(intent);
	        	}
	        	return false;
	    	}
	    });
	}
	
	/**
	 * Add pin from the database to the list view
	 */
	private void addPinFromDB() {
		ArrayList<HistoryRowItem> historyRowItems = new ArrayList<HistoryRowItem>();
		pinMarkerObjects = pinMarkerDataSource.getPins();
		for (PinMarkerObj pinObj : pinMarkerObjects) {
			if (pinObj.getPinType().equals(SQLTablesHelper.PIN_TYPE_MARK)) {
				HistoryRowItem rowItem = new HistoryRowItem(R.drawable.pin_history_icon, pinObj.getTitle(), pinObj.getDescription(), String.valueOf(pinObj.getTime()));
				historyRowItems.add(rowItem);
			} else {
				HistoryRowItem rowItem = new HistoryRowItem(R.drawable.photo_history_icon, pinObj.getTitle(), pinObj.getDescription(), String.valueOf(pinObj.getTime()));
				historyRowItems.add(rowItem);
			}
		}
		adapter = new HistoryListViewAdapter(context, R.layout.history_list_row, historyRowItems);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (swipeDetector.swipeDetected()) {
    		if (swipeDetector.getAction() == SwipeGestureDetector.Action.LR) {
    			//if the gesture is from left to right
    			//take them to detail view
    			//TODO create that detail view
    		} else if (swipeDetector.getAction().equals(SwipeGestureDetector.Action.RL)) {
    			//if the gesture move from right to left. It mean delete
    			PinMarkerObj pinObj = pinMarkerObjects.get(position);
    			pinMarkerDataSource.removePinWithId(pinObj.getPinID());
    		}
    	} else {
		    PinMarkerObj pinObj = pinMarkerObjects.get(position);
		    passCoordinate(new LatLng(pinObj.getLatitude(), pinObj.getLongitude()));
		    passDetail(pinObj);
    	}
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
		detailPasser = (OnDetailPass) activity;
	}
	
	/**
	 * Passing the coordinate from the fragment to activity
	 * @param coordinate
	 */
	public void passCoordinate(LatLng coordinate) {
	    coordinatePasser.onCoordinatePass(coordinate);
	}
	
	/**
	 * Interface to pass the information (coordinate) of selected item back to activity 
	 * containing it
	 * @author minhthaonguyen
	 */
	public interface OnDetailPass {
	    public void onDetailPass(PinMarkerObj pinObj);
	}
	
	OnDetailPass detailPasser;
	
	/**
	 * Passing the coordinate from the fragment to activity
	 * @param coordinate
	 */
	public void passDetail(PinMarkerObj pinObj) {
	    detailPasser.onDetailPass(pinObj);
	}

	
}
