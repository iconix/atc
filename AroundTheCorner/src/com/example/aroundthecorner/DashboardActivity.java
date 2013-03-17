package com.example.aroundthecorner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class DashboardActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_main);
		
		ImageView dealsImage = (ImageView)findViewById(R.id.dealsIcon);
		
		dealsImage.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), AdTabActivity.class);
		        startActivity(i);	
			}
		});		
	}

}
