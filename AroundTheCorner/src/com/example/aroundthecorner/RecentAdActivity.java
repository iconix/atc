package com.example.aroundthecorner;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class RecentAdActivity extends ListActivity {
	static final String[] RECENT_LIST = new String[] {"Baume", "Garden Fresh", "Tamarine"};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new RecentAdArrayAdapter(this, RECENT_LIST));
	}

}
