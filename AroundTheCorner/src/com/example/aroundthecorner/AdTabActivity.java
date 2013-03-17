package com.example.aroundthecorner;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
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
		nearbySpec.setIndicator(NEARBY_SPEC, getResources().getDrawable(R.drawable.nearby_icon));
		Intent nearbyIntent = new Intent(this, NearbyAdActivity.class);
		nearbySpec.setContent(nearbyIntent);
		
		TabSpec recentSpec = tabHost.newTabSpec(RECENT_SPEC);
		recentSpec.setIndicator(RECENT_SPEC, getResources().getDrawable(R.drawable.recent_icon));
		Intent recentIntent = new Intent(this, RecentAdActivity.class);
		recentSpec.setContent(recentIntent);
		
		TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
		profileSpec.setIndicator(PROFILE_SPEC, getResources().getDrawable(R.drawable.userprofile_icon));
		Intent profileIntent = new Intent(this, UserProfileActivity.class);
		profileSpec.setContent(profileIntent);
		
		tabHost.addTab(nearbySpec);
		tabHost.addTab(recentSpec);
		tabHost.addTab(profileSpec);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_upload:
				Toast.makeText(AdTabActivity.this, "Upload is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_timeline:
				Toast.makeText(AdTabActivity.this, "Timeline is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_radius:
				Toast.makeText(AdTabActivity.this, "Radius is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_timezone:
				Toast.makeText(AdTabActivity.this, "Time Zone is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_distance:
				Toast.makeText(AdTabActivity.this, "Min Distance is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_time:
				Toast.makeText(AdTabActivity.this, "Min Time is Selected", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
}
