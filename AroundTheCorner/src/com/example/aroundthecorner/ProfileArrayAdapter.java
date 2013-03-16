package com.example.aroundthecorner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileArrayAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final String[] values;
	
	public ProfileArrayAdapter(Context context, String[] values) {
		super(context, R.layout.mostvisitedcompanies, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.mostvisitedcompanies, parent, false);
		TextView companyLabel = (TextView) rowView.findViewById(R.id.companyId);
		ImageView companyImage = (ImageView) rowView.findViewById(R.id.companyImageId);
		TextView numberOfDeals = (TextView) rowView.findViewById(R.id.numberDealsId);
		
		companyLabel.setText(values[position]);
		
		String s = values[position];
		
		if (s.equals("Baume")) {
			companyImage.setImageResource(R.drawable.baume);
			numberOfDeals.setText("Free Dessert");
		} else if (s.equals("Cool Cafe")) {
			companyImage.setImageResource(R.drawable.coolcafe);
			numberOfDeals.setText("Lunch Special");
		} else if (s.equals("Ike's Place")) {
			companyImage.setImageResource(R.drawable.ikes);
			numberOfDeals.setText("Free Drink");
		} else if (s.equals("Coupa Cafe")) {
			companyImage.setImageResource(R.drawable.coupa);
			numberOfDeals.setText("$2 Off Coffee");
		} else if (s.equals("Evvia Estiatorio")) {
			companyImage.setImageResource(R.drawable.evvia);
			numberOfDeals.setText("20% Off Dinner Items");
		} else if (s.equals("Garden Fresh")) {
			companyImage.setImageResource(R.drawable.gardenfresh);
			numberOfDeals.setText("Free Appetizer");
		} else if (s.equals("Tamarine")){
			companyImage.setImageResource(R.drawable.tamarine);
			numberOfDeals.setText("Happy Hour");
		}
		
		return rowView;
		
	}
	
}
