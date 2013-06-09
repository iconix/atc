package com.sapenguins.thecornerapp.supports;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenStat {
	//get the width of the screen
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display d = w.getDefaultDisplay();
		Point size = new Point();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			d.getSize(size);
			return size.x;      
	    } else {
	    	return d.getWidth(); 
	    }
	}
	
	//get the height of the screen
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display d = w.getDefaultDisplay();
		Point size = new Point();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			d.getSize(size);
			return size.y;      
	    } else {
	    	return d.getHeight(); 
	    }
	}
	
	//get the width of the view given its side padding in dp
	public static int getViewWidth(Context context, int leftPadding, int rightPadding) {
		//first we transform the padding into pixel
		WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display d = w.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		d.getMetrics(metrics);
		float density = metrics.density;
		int screenWidth = getScreenWidth(context);
		int paddingLeft = (int) (leftPadding * density);
		int paddingRight = (int) (rightPadding * density);
		return screenWidth - paddingLeft - paddingRight;
	}
}
