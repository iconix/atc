package com.sapenguins.thecornerapp.templates;


import java.util.List;

import com.sapenguins.thecornerapp.R;
import com.sapenguins.thecornerapp.objects.PromotionRowItem;
import com.sapenguins.thecornerapp.supports.ImageLoading;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Html;
import android.text.TextUtils;


public class PromotionListViewAdapter extends ArrayAdapter<PromotionRowItem>{

	Context context;
	ImageLoading imageLoading;
	
	public PromotionListViewAdapter(Context context, int textViewResourceId,
			List<PromotionRowItem> objects, ImageLoading imageLoading) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.imageLoading = imageLoading;
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
		holder.description.setText(Html.fromHtml(promotionRowItem.getDescription()));
		
		holder.distance.setText(promotionRowItem.getDistance());	
		imageLoading.displayImage(promotionRowItem.getImageUrl(), holder.imageIcon);
		return convertView;
	}
}
