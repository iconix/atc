package templates;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import objects.BasicPromotion;
import objects.HistoryRowItem;
import objects.PromotionRowItem;

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


public class PromotionListViewAdapter extends ArrayAdapter<PromotionRowItem>{

	Context context;
	
	public PromotionListViewAdapter(Context context, int textViewResourceId,
			List<PromotionRowItem> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
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
		holder.title.setText(promotionRowItem.getTitle());
		holder.description.setText(promotionRowItem.getDescription());
		holder.distance.setText(promotionRowItem.getDistance());
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
		} else*/ holder.imageIcon.setImageResource(R.drawable.no_photo_icon);
		return convertView;
	}
	
	
}
