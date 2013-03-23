package com.example.aroundthecorner;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class RecentAdActivity extends ListActivity {
	
	private ExpandableListView expandableList;
	
	static final String[] RECENT_LIST = new String[] {"Baume", "Garden Fresh", "Tamarine"};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

}
