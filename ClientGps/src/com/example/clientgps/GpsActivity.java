package com.example.clientgps;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import classesAndManagers.AppHttpClient;
import classesAndManagers.GpsCoordinate;
import classesAndManagers.GpsCoordinateManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GpsActivity extends Activity {
	private String URL = "http://10.31.32.102:8080/GPSServer/ServerServlet";
	
	//TODO not hardcode username
	private String username = "Minh";
	
	GpsCoordinateManager coorManager;
	//fake information to get
	
	Button sendCoordinate;
	//TODO implement get
	Button getCoordinate;
	
	TextView longitudeInput;
	TextView latitudeInput;
	
	TextView coordinateOutput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		coorManager = new GpsCoordinateManager();
		
		sendCoordinate = (Button)findViewById(R.id.send);
		getCoordinate = (Button)findViewById(R.id.get);
		
		longitudeInput = (TextView)findViewById(R.id.longitudeInput);
		latitudeInput = (TextView)findViewById(R.id.latitudeInput);
		coordinateOutput = (TextView)findViewById(R.id.databaseInfo);
		
		setSendCoordinateButtonListener();
		setGetCoordinateButtonListener();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gps, menu);
		return true;
	}
	
	/**
	 * Set up the button listener to send the coordinate to the server
	 */
	private void setSendCoordinateButtonListener() {
		sendCoordinate.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AsyncTask<Void, Void, Void>() {
	    			@Override
	    			protected Void doInBackground(Void... params) {
			    		try {
			    			GpsCoordinate coordinate = new GpsCoordinate(username, "time", longitudeInput.getText().toString(), latitudeInput.getText().toString());
							ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
							postParameters.add(new BasicNameValuePair("coordinate", coorManager.getGpsCoordinateInStringFormat(coordinate)));
							
							AppHttpClient.executeHttpPost(URL, postParameters);
						} catch (Exception e) {
							e.printStackTrace();
						}
			    		return null;
	    			}
	    		}.execute();
			}
			
		});
	}
	
	/**
	 * Set up the button listener to send the coordinate to the server
	 */
	private void setGetCoordinateButtonListener() {
		getCoordinate.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AsyncTask<Void, Void, Void>() {
	    			@Override
	    			protected Void doInBackground(Void... params) {
			    		try {
							ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
							postParameters.add(new BasicNameValuePair("getCoordinate", username));
							
							String coordinate = AppHttpClient.executeHttpPostWithReturnValue(URL, postParameters);
							coordinateOutput.setText(coordinate);
						} catch (Exception e) {
							e.printStackTrace();
						}
			    		return null;
	    			}
	    		}.execute();
			}
			
		});
	}

}
