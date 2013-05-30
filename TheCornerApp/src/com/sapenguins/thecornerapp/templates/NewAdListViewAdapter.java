package com.sapenguins.thecornerapp.templates;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.sapenguins.thecornerapp.R;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.objects.AdRowItem;
import com.sapenguins.thecornerapp.supports.ImageDrawable;
import com.sapenguins.thecornerapp.supports.ImageLoading;
import com.sapenguins.thecornerapp.supports.ScreenStat;
import com.sapenguins.thecornerapp.supports.TimeFrame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Html;


public class NewAdListViewAdapter extends ArrayAdapter<AdRowItem>{

	Context context;
	ImageLoading imageLoading; 
	ImageLoader imageLoader;
	DisplayImageOptions options;

	public NewAdListViewAdapter(Context context, int textViewResourceId,
			List<AdRowItem> objects, ImageLoading imageLoading) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.imageLoading = imageLoading;
		imageLoader = imageLoading.getImageLoader();
		options = imageLoading.getDisplayImagesOption();
	}

	/**
	 * Private view holder class
	 */
	private class ViewHolder {
		ImageView imageIcon;
		TextView title;
		TextView distance;
		TextView description;
		TextView time;
	}

	/**
	 * Get the history item for the given position
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		AdRowItem adRowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.new_ad_list_row, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.new_ad_list_row_title);
			holder.description = (TextView) convertView.findViewById(R.id.new_ad_list_row_short_description);
			holder.distance= (TextView) convertView.findViewById(R.id.new_ad_list_row_distance);
			holder.imageIcon = (ImageView) convertView.findViewById(R.id.new_ad_list_row_image);
			holder.time =(TextView) convertView.findViewById(R.id.new_ad_list_row_time);
			convertView.setTag(holder);
		} else holder = (ViewHolder) convertView.getTag();

		holder.title.setText(adRowItem.getTitle());
		holder.title.setTextSize(16);
		resizeToFit(holder.title, 2);

		holder.description.setText(Html.fromHtml(adRowItem.getDescription()));

		holder.distance.setText(adRowItem.getDistance());

		final ViewHolder mHolder = holder;
		imageLoader.loadImage(adRowItem.getImageUrl(), options, new SimpleImageLoadingListener() {
			final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					boolean firstDisplay = !displayedImages.contains(imageUri);
					if (firstDisplay) {
						displayedImages.add(imageUri);
					}
					ImageDrawable.setImageBackground(mHolder.imageIcon, getResizedBitmap(loadedImage, 5, 5));
				}
			}
		});

		String startDate = TimeFrame.getDisplayDate(adRowItem.getBeginTime());
		String endDate = TimeFrame.getDisplayDate(adRowItem.getEndTime());
		if (startDate.equals(endDate)) {
			timeNumLines = 1;
			String startHr = TimeFrame.getDisplayHour(adRowItem.getBeginTime());
			String endHr = TimeFrame.getDisplayHour(adRowItem.getEndTime());
			holder.time.setText("Time: " + startDate + "   " + startHr + " - " + endHr);
		} else {
			timeNumLines = 2;
			String mtime = "From: " + TimeFrame.getDisplayTime(adRowItem.getBeginTime()) + SpecialCharacters.endLn +
					"To: " + TimeFrame.getDisplayTime(adRowItem.getEndTime());
			holder.time.setText(mtime);
		}
		holder.time.setTextSize(13);
		resizeToFit(holder.time, timeNumLines);

		return convertView;
	}

	//resize the fit the horizontal of the screen
	public Drawable getResizedBitmap(Bitmap bm, int leftPadding, int rightPadding) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		int screenWidth = ScreenStat.getViewWidth(context, 5, 5);
		float scale = ((float) screenWidth) / width;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scale, scale);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return new BitmapDrawable(context.getResources(), resizedBitmap);
	}

	int timeNumLines;
	/**
	 * resize the size in the text view so that it is at most a given number of line
	 * @param view
	 * @param numLine
	 */
	private void resizeToFit(TextView view, int numLine) {
		while (numLine < view.getLineCount())
			view.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.getTextSize() - 2);
	}

}
