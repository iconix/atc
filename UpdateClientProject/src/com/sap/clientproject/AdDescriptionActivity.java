package com.sap.clientproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import classesAndManagers.*;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.*;

public class AdDescriptionActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_ad);

		TextView companyNameLabel = (TextView)findViewById(R.id.companyNameId);
		//companyNameLabel.setText(company);

		ImageView companyImage = (ImageView)findViewById(R.id.companyImageId);
		//companyImage.setImageBitmap(company_bm);

		//TextView addressLabel = (TextView)findViewById(R.id.addressId);
		//addressLabel.setText(address);

		//TextView hoursLabel = (TextView)findViewById(R.id.hoursId);
		//addressLabel.setText(hours);

		//TextView adTitleLabel = (TextView)findViewById(R.id.adTitleId);
		//adTitleLabel.setText(adTitle);

		ImageView adImage = (ImageView)findViewById(R.id.adImageId);
		//adImage.setImageBitmap(ad_bm);

		//TextView adDescriptionLabel = (TextView)findViewById(R.id.adDescriptionId);

		TextView distanceLabel = (TextView)findViewById(R.id.companyDistanceId);
		
		//TextView companyDescriptionLabel = (TextView)findViewById(R.id.companyDescriptionId);

		Intent i = getIntent();
		String adString = i.getStringExtra("advertisementString");
		String distance = i.getStringExtra("distance");
		
		Advertisement ad = new Advertisement(adString);
		
		companyNameLabel.setText(ad.getBusinessName());
		distanceLabel.setText(distance);
		
		companyImage.setImageResource(R.drawable.minh);

		try {
			  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(ad.getImageUrl()).getContent());
			  adImage.setImageBitmap(bitmap); 
		} catch (MalformedURLException e) {
			  e.printStackTrace();
		} catch (IOException e) {
			  e.printStackTrace();
		} 
    
	}

}
