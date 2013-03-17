package com.sap.clientproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdListArrayAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final String[] values;
	
	public AdListArrayAdapter(Context context, String[] values) {
		super(context, R.layout.adlist, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.adlist, parent, false);
		TextView adLabel = (TextView) rowView.findViewById(R.id.adListTitleId);
		ImageView adImage = (ImageView) rowView.findViewById(R.id.adListImageId);
		TextView companyLabel = (TextView) rowView.findViewById(R.id.adListCompanyId);
		TextView distanceLabel = (TextView) rowView.findViewById(R.id.adListDistanceId);
		
		companyLabel.setText(values[position]);
		
		String s = values[position];
		
		if (s.equals("Baume")) {
			adImage.setImageResource(R.drawable.baume);
			adImage.setOnClickListener(new ImageView.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(context, TestBaume.class);
			        context.startActivity(i);	
				}
			});
			adLabel.setText("Free Dessert");
			distanceLabel.setText("1.2 mi");
		} else if (s.equals("Cool Cafe")) {
			adImage.setImageResource(R.drawable.coolcafe);
			adImage.setOnClickListener(new ImageView.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(context, TestCoolCafe.class);
			        context.startActivity(i);	
				}
			});
			adLabel.setText("Lunch Special");
			distanceLabel.setText("0.6 mi");
		} else if (s.equals("Ike's Place")) {
			adImage.setImageResource(R.drawable.ikes);
			adImage.setOnClickListener(new ImageView.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(context, SingleAdActivity.class);
			        context.startActivity(i);	
				}
			});
			adLabel.setText("Free Drink");
			distanceLabel.setText("0.3 mi");
		} else if (s.equals("Coupa Cafe")) {
			adImage.setImageResource(R.drawable.coupa);
			adLabel.setText("$2 Off Coffee");
			distanceLabel.setText("0.5 mi");
		} else if (s.equals("Evvia Estiatorio")) {
			adImage.setImageResource(R.drawable.evvia);
			adLabel.setText("20% Off Dinner Items");
			distanceLabel.setText("1.3 mi");
		} else if (s.equals("Garden Fresh")) {
			adImage.setImageResource(R.drawable.gardenfresh);
			adLabel.setText("Free Appetizer");
			distanceLabel.setText("1.2 mi");
		} else if (s.equals("Tamarine")){
			adImage.setImageResource(R.drawable.tamarine);
			adLabel.setText("Happy Hour");
			distanceLabel.setText("1.4 mi");
		}
		
		return rowView;
		
	}
	
}