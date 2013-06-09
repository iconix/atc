package com.sapenguins.thecornerapp.supports;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

public class ImageDrawable {
	@SuppressWarnings("deprecation")
	public static void setImageBackground(ImageView view, Drawable drawable) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
	}
}
