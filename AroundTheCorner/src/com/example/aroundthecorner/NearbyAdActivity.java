package com.example.aroundthecorner;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class NearbyAdActivity extends ListActivity {
	static final String[] NEARBY_LIST = new String[] {"Baume", "Cool Cafe", "Ike's Place", "Coupa Cafe", "Evvia Estiatorio", "Garden Fresh", "Tamarine"};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new NearbyAdArrayAdapter(this, NEARBY_LIST));
	}

}
