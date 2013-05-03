package com.sapenguins.atc;

import supports.TimeFrame;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryDetailFragment extends Fragment {
	
	Context context;
	View view;
	TextView title;
	TextView fulltime;
	TextView description;
	ImageView img;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		view = inflater.inflate(R.layout.history_detail, container, false);
		initViewComponents();
		return view;
	}
	
	/**
	 * Set up the view components
	 */
	private void initViewComponents() {
		title = (TextView)view.findViewById(R.id.history_detail_title);
		fulltime = (TextView)view.findViewById(R.id.history_detail_time);
		description = (TextView)view.findViewById(R.id.history_detail_description);
		img = (ImageView) view.findViewById(R.id.history_detail_image);
	}
	
	/**
	 * display detail information of the time/event. Call from the activity container
	 * @param mtitle
	 * @param mdescription
	 * @param mtime
	 * @param mImgSrc
	 */
	public void dislayHistoryDetail(String mtitle, String mdescription, long mtime, String mImgSrc) {
		title.setText(mtitle);
		description.setText(mdescription);
		String date = TimeFrame.getDateInString(mtime);
		String time = TimeFrame.getTimeInString(mtime);
		String dateAndTime = date + " " + time;
		fulltime.setText(dateAndTime);
		img.setImageResource(R.drawable.history);
	}
}
