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
		//companyNameLabel.setText(company);

		ImageView companyImage = (ImageView)findViewById(R.id.companyImageId);
		//companyImage.setImageBitmap(company_bm);

		TextView addressLabel = (TextView)findViewById(R.id.addressId);
		//addressLabel.setText(address);

		TextView hoursLabel = (TextView)findViewById(R.id.hoursId);
		//addressLabel.setText(hours);

		TextView adTitleLabel = (TextView)findViewById(R.id.adTitleId);
		//adTitleLabel.setText(adTitle);

		ImageView adImage = (ImageView)findViewById(R.id.adImageId);
		//adImage.setImageBitmap(ad_bm);

		TextView adDescriptionLabel = (TextView)findViewById(R.id.adDescriptionId);
		//aDescriptionLabel.setText(adDescription);   

		TextView distanceLabel = (TextView)findViewById(R.id.companyDistanceId);
		
		TextView companyDescriptionLabel = (TextView)findViewById(R.id.companyDescriptionId);

		Intent i = getIntent();
		//String adTitleString = i.getStringExtra("ad_title");
		//String distanceString = i.getStringExtra("distance");
		String companyString = i.getStringExtra("company");

		companyNameLabel.setText(companyString);
		//adTitleLabel.setText(adTitleString);
		//distanceLabel.setText(distanceString);

		if (companyString.equals("Baume")) {
			companyImage.setImageResource(R.drawable.baume);
			adImage.setImageResource(R.drawable.baume);
			addressLabel.setText("123 Something Lane\nStanford, CA");
			hoursLabel.setText("Hours: 10:00AM - 11:00PM");
			adDescriptionLabel.setText("With an entree");
			companyDescriptionLabel.setText("Baume is a really cool place. Get fancy french food and stuff. Brace yourself for a hefty check though.");
			adTitleLabel.setText("Free Dessert");
			distanceLabel.setText("1.2 mi");
		} else if (companyString.equals("Cool Cafe")) {
			companyImage.setImageResource(R.drawable.coolcafe);
			adImage.setImageResource(R.drawable.coolcafe);
			addressLabel.setText("562 Cool Cats\nStanford, CA");
			hoursLabel.setText("Hours: 10:30AM - 9:00PM");
			adDescriptionLabel.setText("No kids allowed");
			companyDescriptionLabel.setText("Cool Cafe is a nice little cafe by the Cantor Art Musuem. A great place to go on a date.");
			adTitleLabel.setText("Lunch Special");
			distanceLabel.setText("0.6 mi");
		} else if (companyString.equals("Ike's Place")) {
			companyImage.setImageResource(R.drawable.ikes);
			adImage.setImageResource(R.drawable.ikes);
			addressLabel.setText("24 Food Lane\nStanford, CA");
			hoursLabel.setText("Hours: 11:00AM - 3:00PM");
			adDescriptionLabel.setText("One per sandwich");
			companyDescriptionLabel.setText("Get some awesome sandwiches at Ike's. Wide variety of vegan and vegetarian options as well.");
			adTitleLabel.setText("Free Drink");
			distanceLabel.setText("0.3 mi");
		} else if (companyString.equals("Coupa Cafe")) {
			companyImage.setImageResource(R.drawable.coupa);
			adImage.setImageResource(R.drawable.coupa);
			addressLabel.setText("224 Darin St\nStanford, CA");
			hoursLabel.setText("Hours: 9:00AM - 12:00AM");
			adDescriptionLabel.setText("With purchase of crepe");
			companyDescriptionLabel.setText("Get some delicious cafe as well as pastries. Not your average Starbucks. Get something good.");
			adTitleLabel.setText("$2 Off Coffee");
			distanceLabel.setText("0.5 mi");
		} else if (companyString.equals("Evvia Estiatorio")) {
			companyImage.setImageResource(R.drawable.evvia);
			adImage.setImageResource(R.drawable.evvia);
			addressLabel.setText("1 Fancy Drive\nStanford, CA");
			hoursLabel.setText("Hours: 5:00PM - 11:00PM");
			adDescriptionLabel.setText("With an entree");
			companyDescriptionLabel.setText("Prepare for a delicious meal while submersed in a classy ambiance. Cool beans.");
			adTitleLabel.setText("20% Off Dinner Items");
			distanceLabel.setText("1.3 mi");
		} else if (companyString.equals("Garden Fresh")) {
			companyImage.setImageResource(R.drawable.gardenfresh);
			adImage.setImageResource(R.drawable.gardenfresh);
			addressLabel.setText("31 Yeah Lane\nStanford, CA");
			hoursLabel.setText("Hours: 11:00AM - 11:00PM");
			adDescriptionLabel.setText("1 per person");
			companyDescriptionLabel.setText("Garden Fresh is legit. A favorite of local vegetarians.");
			adTitleLabel.setText("Free Appetizer");
			distanceLabel.setText("1.2 mi");
		} else if (companyString.equals("Tamarine")){
			companyImage.setImageResource(R.drawable.tamarine);
			adImage.setImageResource(R.drawable.tamarine);
			addressLabel.setText("987 Hungry Street\nStanford, CA");
			hoursLabel.setText("Hours: 3:00PM - 11:00PM");
			adDescriptionLabel.setText("With an entree");
			companyDescriptionLabel.setText("Get delicious asian cuisine at Tamarine. If you're sick of eating Panda Express, treat yourself to a delicious meal.");
			adTitleLabel.setText("Happy Hour");
			distanceLabel.setText("1.4 mi");
		}       
	}

}
