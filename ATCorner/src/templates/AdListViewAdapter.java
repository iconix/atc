package templates;

import staticVariables.SpecialCharacters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdListViewAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final String[] values;
	
	public AdListViewAdapter(Context context, int textViewResourceId, String[] values) {
		super(context, textViewResourceId, values);
		this.context = context;
		this.values = values;
	}
	
	private class ViewHolder {
		ImageView imageIcon;
		TextView title;
		//TextView timeStamp;
		//TextView description;
	}	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(0x7f030002, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(0x7f080002);
			holder.imageIcon = (ImageView) convertView.findViewById(0x7f080001);
			convertView.setTag(holder);
		} else holder = (ViewHolder) convertView.getTag();
		String currentAd = values[position];
		String[] attributes = currentAd.split(SpecialCharacters.delimiter, -1);
		String adTitle = attributes[6];
		holder.title.setText(adTitle);
		
		return convertView;
	}

}
