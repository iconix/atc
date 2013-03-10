package com.example.aroundthecorner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class AndroidMenusActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
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
				Toast.makeText(AndroidMenusActivity.this, "Upload is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_timeline:
				Toast.makeText(AndroidMenusActivity.this, "Timeline is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_radius:
				Toast.makeText(AndroidMenusActivity.this, "Radius is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_timezone:
				Toast.makeText(AndroidMenusActivity.this, "Time Zone is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_distance:
				Toast.makeText(AndroidMenusActivity.this, "Min Distance is Selected", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_time:
				Toast.makeText(AndroidMenusActivity.this, "Min Time is Selected", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
}
