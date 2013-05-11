package com.sapenguins.thecornerapp;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailFragment extends Fragment {
	
	Context context;
	View view;
	TextView title;
	ImageView img;
	int eventId;
	String eventTitle;
	String eventImg;
	
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		view = inflater.inflate(R.layout.event_detail, container, false);
		initViewComponents();
		setViewOnClickListener();
		return view;
	}

	/**
	 * Set up the view components
	 */
	private void initViewComponents() {
		title = (TextView)view.findViewById(R.id.event_detail_title);	
		img = (ImageView) view.findViewById(R.id.event_detail_image);
	}
	
	/**
	 * Set the click listener for the view
	 */
	private void setViewOnClickListener() {
		view.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				passClick();
			}
		});
	}
	
	/**
	 * display detail information of the Event. Call from the activity container
	 * @param eventId
	 * @param mtitle
	 * @param mImgSrc
	 */
	public void dislayEventDetail(int mEventId, String mtitle, String mImgSrc) {
		eventId = mEventId;
		eventTitle = mtitle;
		eventImg = mImgSrc;
		title.setText(mtitle);
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.no_photo_icon)
		.showImageForEmptyUri(R.drawable.no_photo_icon)
		.showImageOnFail(R.drawable.no_photo_icon)
		.cacheOnDisc()
		.displayer(new RoundedBitmapDisplayer(20))
		.resetViewBeforeLoading()
		.build();
		imageLoader = ImageLoader.getInstance();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.memoryCache(new WeakMemoryCache())
		.denyCacheImageMultipleSizesInMemory()
		.build();
		
		imageLoader.init(config);
		
		imageLoader.displayImage(mImgSrc, img, options, animateFirstListener);
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	
	//////////INTERACTION BETWEEN FRAGMENT AND ACTIVITY//////////////	
	/**
	 * Interface to pass the click to the activity
	 */
	public interface OnClickPass {
	    public void onClickPass();
	}
	
	OnClickPass clickPasser;
	
	/**
	 * Passing the click to the activity
	 */
	public void passClick() {
	    clickPasser.onClickPass();
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		clickPasser = (OnClickPass) activity;
	}
}
