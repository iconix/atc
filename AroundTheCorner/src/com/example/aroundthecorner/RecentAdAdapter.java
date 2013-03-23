package com.example.aroundthecorner;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class RecentAdAdapter extends BaseExpandableListAdapter{

	private ArrayList<DealsDates> datesList;
	private Context context;
	
	public RecentAdAdapter(Context context, ArrayList<DealsDates> datesList) {
		this.datesList = datesList;
		this.context = context;
	}
	
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<String> dailyDealsList = datesList.get(groupPosition).getArrayChildren();
		return dailyDealsList.get(childPosition);
	}
	
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
		String adString = (String) getChild(groupPosition, childPosition);
		Advertisement ad = new Advertisement(adString);
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.adlist, null);
		}
		// TODO 
		return view;
	}
	
	public int getChildrenCount(int groupPosition) {
		ArrayList<String> dailyDealsList = datesList.get(groupPosition).getArrayChildren();
		return dailyDealsList.size();
	}
	
	public Object getGroup(int groupPosition) {
		return datesList.get(groupPosition);
	}
	
	public int getGroupCount() {
		return datesList.size();
	}
	
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {
		DealsDates dealsDates = (DealsDates) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.deals_date_item, null);
		}
		// TODO 
		return view;
	}
	
	public boolean hasStableIds() {
		return true;
	}
	
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	

}
