package com.sapenguins.atc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

	private String pictureDirectory;
	private String screenshotDirectory;
	FileObserver observer;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Screenshot Service Create", Toast.LENGTH_SHORT).show();
		Log.i("Screenshot", "Directory Watcher Starts");
		
		//location the directory of the screenshot
		pictureDirectory = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
		screenshotDirectory = pictureDirectory + "/Camera";
		
		//init the watching function of screenshot directory
		observer = initSingleDirectoryObserver(screenshotDirectory);
		observer.startWatching();
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
					
					//TODO record the file to our database;	
				} if (event == FileObserver.DELETE) {
					
					//TODO remove the file from our db
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
    
	/**
	 * Function: onDestroy
	 * -----------------------------------
	 * Override the onDestroy function in the service interface.
	 * When the service is terminate, it must sever the binding with the aslService.
	 * Also, any components that tie with the service need to be destroy
	 */
	@Override
	public void onDestroy() {
		observer.stopWatching();
	}
	
}