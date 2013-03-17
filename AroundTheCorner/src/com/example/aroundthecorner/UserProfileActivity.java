package com.example.aroundthecorner;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

public class UserProfileActivity extends Activity{
	static final String[] MOST_VISITED_LIST = new String[] {"Baume", "Coupa Cafe", "Evvia Estiatorio", "Cool Cafe", "Ike's Place"};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userprofile);
		
		ListView companyList = (ListView)findViewById(R.id.companyList);
		companyList.setAdapter(new MostVisitedCompaniesArrayAdapter(this, MOST_VISITED_LIST));	
	}
}
