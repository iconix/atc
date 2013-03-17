package com.example.aroundthecorner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MostVisitedCompaniesArrayAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final String[] values;
	
	public MostVisitedCompaniesArrayAdapter(Context context, String[] values) {
		super(context, R.layout.adlist, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.mostvisitedcompanies_list_item, parent, false);
		TextView companyLabel = (TextView) rowView.findViewById(R.id.companyId);
		TextView numberDealsLabel = (TextView) rowView.findViewById(R.id.numberDealsId);
		ImageView companyImage = (ImageView) rowView.findViewById(R.id.companyImageId);
		
		companyLabel.setText(values[position]);
		
		String s = values[position];
		
		if (s.equals("Baume")) {
			companyImage.setImageResource(R.drawable.baume);
			numberDealsLabel.setText("3");
		} else if (s.equals("Cool Cafe")) {
			companyImage.setImageResource(R.drawable.coolcafe);
			numberDealsLabel.setText("4");
		} else if (s.equals("Ike's Place")) {
			companyImage.setImageResource(R.drawable.ikes);
			numberDealsLabel.setText("5");
		} else if (s.equals("Coupa Cafe")) {
			companyImage.setImageResource(R.drawable.coupa);
			numberDealsLabel.setText("6");
		} else if (s.equals("Evvia Estiatorio")) {
			companyImage.setImageResource(R.drawable.evvia);
			numberDealsLabel.setText("7");
		} else if (s.equals("Garden Fresh")) {
			companyImage.setImageResource(R.drawable.gardenfresh);
			numberDealsLabel.setText("8");
		} else if (s.equals("Tamarine")){
			companyImage.setImageResource(R.drawable.tamarine);
			numberDealsLabel.setText("9");
		}
		rowView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(context, AdDescriptionActivity.class);
				//String adLabelString = ((TextView) view.findViewById(R.id.adListTitleId)).getText().toString();
				//String distanceLabelString = ((TextView) view.findViewById(R.id.adListDistanceId)).getText().toString();
				String companyLabelString = ((TextView) view.findViewById(R.id.companyId)).getText().toString();
				
				//i.putExtra("ad_title", adLabelString);
				//i.putExtra("distance", distanceLabelString);
				i.putExtra("company", companyLabelString);
				//i.putExtra("ad_image", ((ImageView) view.findViewById(R.id.adListImageId)).getDrawable());
			}
		});		
		return rowView;
		
	}
	
}
