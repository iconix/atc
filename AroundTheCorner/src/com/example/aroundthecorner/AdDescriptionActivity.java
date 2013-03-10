package com.example.aroundthecorner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class AdDescriptionActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad);
        
        TextView companyNameLabel = (TextView)findViewById(R.id.companyNameId);
        companyNameLabel.setText(company);
        
        ImageView companyImage = (ImageView)findViewById(R.id.companyImageId);
        companyImage.setImageBitmap(company_bm);
             
        TextView addressLabel = (TextView)findViewById(R.id.addressId);
        addressLabel.setText(address);
        
        TextView adTitleLabel = (TextView)findViewById(R.id.adTitleId);
        adTitleLabel.setText(adTitle);
        
        ImageView adImage = (ImageView)findViewById(R.id.adImageId);
        adImage.setImageBitmap(ad_bm);
        
        TextView adDescriptionLabel = (TextView)findViewById(R.id.adDescriptionId);
        adDescriptionLabel.setText(adDescription);        
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	} 
}
