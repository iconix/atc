package com.sap.clientproject;

import android.app.Activity;
import android.os.Bundle;

public class SingleAdActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_ad);
        /*
        TextView companyNameLabel = (TextView)findViewById(R.id.companyNameId);
        companyNameLabel.setText(company);
        
        ImageView companyImage = (ImageView)findViewById(R.id.companyImageId);
        companyImage.setImageBitmap(company_bm);
             
        TextView addressLabel = (TextView)findViewById(R.id.addressId);
        addressLabel.setText(address);

        TextView hoursLabel = (TextView)findViewById(R.id.hoursId);
        addressLabel.setText(hours);
        
        TextView adTitleLabel = (TextView)findViewById(R.id.adTitleId);
        adTitleLabel.setText(adTitle);
        
        ImageView adImage = (ImageView)findViewById(R.id.adImageId);
        adImage.setImageBitmap(ad_bm);
        
        RatingBar ratingLabel = (RatingBar)findViewById(R.id.ratingBarId);
        ratingLabel.setRating(rating);
        
        TextView adDescriptionLabel = (TextView)findViewById(R.id.adDescriptionId);
        adDescriptionLabel.setText(adDescription);  
        */      
    }

}
