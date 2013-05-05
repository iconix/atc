package com.sapenguins.thecornerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends Activity {
	
	ImageView map;
	ImageView promotion;
	ImageView event;
	Intent intent;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		context = this;
		map = (ImageView)findViewById(R.id.main_menu_map);
		promotion = (ImageView)findViewById(R.id.main_menu_promotion);
		event = (ImageView)findViewById(R.id.main_menu_event);
		setPromotionImageListener();
		setEventImageListener();
		setMapImageListener();
	}
	
	private void setPromotionImageListener() {
		promotion.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent(context, PromotionListAndDetailActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void setEventImageListener() {
		event.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent(context, EventListAndDetailActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void setMapImageListener() {
		map.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent(context, AdsMapActivity.class);
				startActivity(intent);
			}
		});
	}

}
