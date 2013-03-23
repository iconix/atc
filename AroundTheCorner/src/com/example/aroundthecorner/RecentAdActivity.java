package com.example.aroundthecorner;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class RecentAdActivity extends ListActivity {
	
	private ExpandableListView expandableList;
	private RecentAdAdapter adapter;
	
	static final String[] RECENT_LIST = new String[] {"Baume", "Garden Fresh", "Tamarine"};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recent_ads_layout);
		
		ArrayList<DealsDates> datesList = new ArrayList<DealsDates>();
		expandableList = (ExpandableListView) findViewById(R.id.recent_list);
		adapter = new RecentAdAdapter(getApplicationContext(), datesList);
		expandableList.setAdapter(adapter);
	}
	
	private void expandAll() {
		int count = adapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			expandableList.expandGroup(i);
		}
	}
	
	private void collapseAll() {
		int count = adapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			expandableList.collapseGroup(i);
		}
	}
	
	

}
