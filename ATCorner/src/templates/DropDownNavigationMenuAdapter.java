package templates;

import com.sapenguins.atc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DropDownNavigationMenuAdapter extends BaseAdapter{
	Context context;
	int[] icons;
    String[] titles;
    private LayoutInflater inflator;
    
	public DropDownNavigationMenuAdapter(Context context, int[] icons, String[] titles){
        this.context = context;
        inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icons = icons;
        this.titles = titles;
	}
	
	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Object getItem(int position) {
		return titles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Private view holder class
	 */
	private class ViewHolder {
		ImageView imageIcon;
		TextView title;
	}
	
	
	/**
	 * Get the history item for the given position
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflator.inflate(R.layout.menu_navigation_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.dropdown_menu_text);
			//holder.imageIcon = (ImageView) convertView.findViewById(R.id.dropdown_menu_image);
			convertView.setTag(holder);
		} else holder = (ViewHolder) convertView.getTag();
		holder.title.setText(titles[position]);
		//holder.imageIcon.setImageResource(icons[position]);
		return convertView;
	}
	
	/**
	 * Get the history item for the given position
	 */
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflator.inflate(R.layout.menu_navigation_dropdown_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.dropdown_menu_text);
			holder.imageIcon = (ImageView) convertView.findViewById(R.id.dropdown_menu_image);
			convertView.setTag(holder);
		} else holder = (ViewHolder) convertView.getTag();
		holder.title.setText(titles[position]);
		holder.imageIcon.setImageResource(icons[position]);
		return convertView;
	}

}
