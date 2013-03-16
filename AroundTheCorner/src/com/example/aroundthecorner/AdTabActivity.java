package com.example.aroundthecorner;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AdTabActivity extends TabActivity {
	
	private static final String NEARBY_SPEC = "Nearby Ads";
	private static final String RECENT_SPEC = "Recent Ads";
	private static final String PROFILE_SPEC = "Profile";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adtabmain);
		
		TabHost tabHost = getTabHost();
		
		TabSpec nearbySpec = tabHost.newTabSpec(NEARBY_SPEC);
		//nearbySpec.setIndicator()
		Intent nearbyIntent = new Intent(this, NearbyAdActivity.class);
		nearbySpec.setContent(nearbyIntent);
		
		TabSpec recentSpec = tabHost.newTabSpec(RECENT_SPEC);
		//recentSpec.setIndicator();
		Intent recentIntent = new Intent(this, RecentAdActivity.class);
		recentSpec.setContent(recentIntent);
		
		TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
		//profileSpec.setIndicator();
		Intent profileIntent = new Intent(this, UserProfileActivity.class);
		profileSpec.setContent(profileIntent);
		
		tabHost.addTab(nearbySpec);
		tabHost.addTab(recentSpec);
		tabHost.addTab(profileSpec);
		
	}

}
