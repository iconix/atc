package com.sapenguins.atc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PromotionDetailActivity extends Activity{

	Context context;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_detail);
		this.context = getBaseContext();
		displayPromotionDetail();
	}

	private void displayPromotionDetail() {
		Intent i = getIntent();
		String promotionTitle = i.getStringExtra("promotionTitle");
		String promotionDescription = i.getStringExtra("promotionDescription");
		String promotionDistance = i.getStringExtra("promotionDistance");
		String promotionImageUrl = i.getStringExtra("promotionImageUrl");

		TextView promotionTitleLabel = (TextView)findViewById(R.id.promotion_detail_title);
		promotionTitleLabel.setText(promotionTitle);

		TextView promotionDescriptionLabel = (TextView)findViewById(R.id.promotion_detail_description);
		promotionDescriptionLabel.setText(promotionDescription);

		TextView promotionDistanceLabel = (TextView)findViewById(R.id.promotion_detail_distance);
		promotionDistanceLabel.setText(promotionDistance);
		
		ImageView promotionImage = (ImageView)findViewById(R.id.promotion_detail_image);

		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.no_photo_icon)
		.showImageForEmptyUri(R.drawable.no_photo_icon)
		.showImageOnFail(R.drawable.no_photo_icon)
		.cacheInMemory()
		.cacheOnDisc()
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		
		imageLoader.displayImage(promotionImageUrl, promotionImage, options, animateFirstListener);
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


}
