package com.example.aroundthecorner;

import android.content.Context;
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
		
		adLabel.setText(values[position]);
		
		String s = values[position];
		
		if (s.equals("")) {
			
		} else if (s.equals("")) {
			
		} else if (s.equals("")) {
			
		}
		
		return rowView;
		
	}
	
}
