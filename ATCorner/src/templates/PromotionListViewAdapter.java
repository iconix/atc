package templates;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import objects.BasicPromotion;
import objects.HistoryRowItem;
import objects.PromotionRowItem;
import supports.ImageDownloader;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.sapenguins.atc.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.TextUtils;


public class PromotionListViewAdapter extends ArrayAdapter<PromotionRowItem>{

	Context context;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader;
	
	public PromotionListViewAdapter(Context context, int textViewResourceId,
			List<PromotionRowItem> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
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
	}

	/**
	 * Private view holder class
	 */
	private class ViewHolder {
		ImageView imageIcon;
		TextView title;
		TextView distance;
		TextView description;
	}
	
	/**
	 * Get the history item for the given position
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		PromotionRowItem promotionRowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.promotion_list_row, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.promotion_list_view_row_title);
			holder.description = (TextView) convertView.findViewById(R.id.promotion_list_view_row_description);
			holder.distance= (TextView) convertView.findViewById(R.id.promotion_list_view_row_distance);
			holder.imageIcon = (ImageView) convertView.findViewById(R.id.promotion_list_view_row_image);
			convertView.setTag(holder);
		} else holder = (ViewHolder) convertView.getTag();
		
		holder.title.setSingleLine();
		holder.title.setEllipsize(TextUtils.TruncateAt.END);	
		holder.title.setText(promotionRowItem.getTitle());
		
		holder.description.setMaxLines(2);
		holder.description.setEllipsize(TextUtils.TruncateAt.END);
		holder.description.setText(promotionRowItem.getDescription());
		
		holder.distance.setText(promotionRowItem.getDistance());
		
		imageLoader.displayImage(promotionRowItem.getImageUrl(), holder.imageIcon, options, animateFirstListener);
		/*Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream((InputStream)new URL(promotionRowItem.getImageUrl()).getContent());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bitmap != null)	{
			holder.imageIcon.setImageBitmap(bitmap);
		} else holder.imageIcon.setImageResource(R.drawable.no_photo_icon);*/
		return convertView;
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
