package com.sapenguins.atc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import objects.PinMarkerObj;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;

import dataSources.PinMarkerDataSource;
import dataSources.SQLTablesHelper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;
import supports.*;
import staticVariables.*;

public class PhotoRecordingService extends Service{

	private String photoDirectory;
	FileObserver observer;
	PinMarkerDataSource pinMarkerDataSource;
	Context context;
	LocationManager locationManager;
	String provider;
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		pinMarkerDataSource = new PinMarkerDataSource(this);
		pinMarkerDataSource.open();
		
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), true);
		
		photoDirectory = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera";
		
		//init the watching function of photo directory
		observer = initSingleDirectoryObserver(photoDirectory);
		observer.startWatching();
	}
	
	@Override
	public void onDestroy() {
		pinMarkerDataSource.close();
		observer.stopWatching();
		super.onDestroy();
	}
	
	/**
	 * Initiate a single observer of a given directory
	 * @param partial path of the directory to keep watch of.
	 */
	private FileObserver initSingleDirectoryObserver(String directoryPath) {
		final String dirPath = directoryPath;
		FileObserver observer = new FileObserver(directoryPath) {
			@Override
			public void onEvent(int event, String file) {
				String filePath = dirPath + "/" + file;
				if (event == FileObserver.CREATE) {
					Location lastKnown = locationManager.getLastKnownLocation(provider);
					PinMarkerObj pin = new PinMarkerObj(0, file, "empty description", lastKnown.getLongitude(), 
							lastKnown.getLatitude(), getTimeStamp(), SQLTablesHelper.PIN_TYPE_PICTURE, filePath, -1);
					pinMarkerDataSource.addPin(pin);
				} if (event == FileObserver.DELETE) {
					pinMarkerDataSource.removePinWithImgSrc(filePath);
					
				}
			}
		};
		return observer;
	}
    
    /**
	 * Get the current time. This is server as the timestamp for the log
	 * @return the current time represent in the format yyyyMMddHHmmss
	 */
	private String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		return sdf.format(new Date());
	}
    
}