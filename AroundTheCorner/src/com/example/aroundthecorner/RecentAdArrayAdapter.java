package com.example.aroundthecorner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecentAdArrayAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final String[] values;
	
	public RecentAdArrayAdapter(Context context, String[] values) {
		super(context, R.layout.recent_list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.recent_list_item, parent, false);
		TextView adLabel = (TextView) rowView.findViewById(R.id.adListTitleId);
		ImageView adImage = (ImageView) rowView.findViewById(R.id.adListImageId);
		TextView companyLabel = (TextView) rowView.findViewById(R.id.adListCompanyId);
		TextView distanceLabel = (TextView) rowView.findViewById(R.id.adListDistanceId);
		
		companyLabel.setText(values[position]);
		
		String s = values[position];
		
		if (s.equals("Baume")) {
			adImage.setImageResource(R.drawable.baume);
			adLabel.setText("Free Dessert");
			distanceLabel.setText("1.2 mi");
		} else if (s.equals("Garden Fresh")) {
			adImage.setImageResource(R.drawable.gardenfresh);
			adLabel.setText("Free Appetizer");
			distanceLabel.setText("1.2 mi");
		} else if (s.equals("Tamarine")){
			adImage.setImageResource(R.drawable.tamarine);
			adLabel.setText("Happy Hour");
			distanceLabel.setText("1.4 mi");
		}
		rowView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(context, AdDescriptionActivity.class);
				//String adLabelString = ((TextView) view.findViewById(R.id.adListTitleId)).getText().toString();
				//String distanceLabelString = ((TextView) view.findViewById(R.id.adListDistanceId)).getText().toString();
				String companyLabelString = ((TextView) view.findViewById(R.id.adListCompanyId)).getText().toString();
				
				//i.putExtra("ad_title", adLabelString);
				//i.putExtra("distance", distanceLabelString);
				i.putExtra("company", companyLabelString);
				context.startActivity(i);
				//i.putExtra("ad_image", ((ImageView) view.findViewById(R.id.adListImageId)).getDrawable());
			}
		});			
		return rowView;
		
	}
	
}
