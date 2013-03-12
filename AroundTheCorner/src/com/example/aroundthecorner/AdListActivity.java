package com.example.aroundthecorner;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class AdListActivity extends ListActivity {
	static final String[] AD_LIST = new String[] {"Baume", "Cool Cafe", "Ike's Place", "Coupa Cafe", "Evvia Estiatorio", "Garden Fresh", "Tamarine"};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new AdListArrayAdapter(this, AD_LIST));
	}

}
