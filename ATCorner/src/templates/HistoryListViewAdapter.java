package templates;

import java.util.List;

import objects.HistoryRowItem;
import supports.TimeFrame;

import com.sapenguins.atc.R;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class HistoryListViewAdapter extends ArrayAdapter<HistoryRowItem>{

	Context context;
	
	public HistoryListViewAdapter(Context context, int textViewResourceId,
			List<HistoryRowItem> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
	}

	/**
	 * Private view holder class
	 */
	private class ViewHolder {
		ImageView imageIcon;
		TextView title;
		TextView timeStamp;
		TextView description;
	}
	
	/**
	 * Get the history item for the given position
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		HistoryRowItem historyRowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.history_list_row, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.history_list_view_row_title);
			holder.description = (TextView) convertView.findViewById(R.id.history_list_view_row_description);
			holder.timeStamp= (TextView) convertView.findViewById(R.id.history_list_view_row_date);
			holder.imageIcon = (ImageView) convertView.findViewById(R.id.history_list_view_row_image);
			convertView.setTag(holder);
		} else holder = (ViewHolder) convertView.getTag();
		
		holder.title.setSingleLine();
		holder.title.setEllipsize(TextUtils.TruncateAt.END);
		holder.title.setText(historyRowItem.getTitle());
		
		holder.description.setSingleLine();
		holder.description.setEllipsize(TextUtils.TruncateAt.END);
		holder.description.setText(historyRowItem.getDescription());
		
		holder.timeStamp.setText(TimeFrame.getDateInString(Long.valueOf(historyRowItem.getTimeStamp())));
		
		holder.imageIcon.setImageResource(historyRowItem.getImageId());	
		
		return convertView;
	}
}
