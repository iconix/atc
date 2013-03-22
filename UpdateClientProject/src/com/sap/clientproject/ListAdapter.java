package com.sap.clientproject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import classesAndManagers.Advertisement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> data;
	private static LayoutInflater inflater = null;
	
	public ListAdapter(Context cntxt, ArrayList<String> d) {
		context = cntxt;
		data = d;
		inflater = LayoutInflater.from(context);
	}
	
	public int getCount() {
		return data.size();
	}
	
	public Object getItem(int position) {
		return position;
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (convertView == null)
			rowView = inflater.inflate(R.layout.adlist, null);
		
		TextView businessName = (TextView)rowView.findViewById(R.id.companyNameId);
		TextView adTitle = (TextView)rowView.findViewById(R.id.adTitleId);
		TextView distanceTitle = (TextView)rowView.findViewById(R.id.adListDistanceId);
		ImageView adImage = (ImageView)rowView.findViewById(R.id.adImageId);
		adImage.setImageResource(R.drawable.minh);

		
		final String adString = data.get(position);
		Advertisement ad = new Advertisement(adString);

		final double distance = ad.getDistance(0, 0); //Need to grab GPS location
		
		// Setting values
		businessName.setText(ad.getBusinessName());
		adTitle.setText(ad.getTitle());
		distanceTitle.setText(String.valueOf(distance));

		try {
			  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(ad.getImageUrl()).getContent());
			  adImage.setImageBitmap(bitmap); 
		} catch (MalformedURLException e) {
			  e.printStackTrace();
		} catch (IOException e) {
			  e.printStackTrace();
		} 
		
		rowView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(context, AdDescriptionActivity.class);
				i.putExtra("advertisementString", adString);
				i.putExtra("distance", String.valueOf(distance));
				context.startActivity(i);
			}
		});		
		return rowView;
		
	}
	

}
