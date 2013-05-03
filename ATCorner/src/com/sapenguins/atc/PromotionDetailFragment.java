package com.sapenguins.atc;

import supports.TimeFrame;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PromotionDetailFragment extends Fragment {
	
	Context context;
	View view;
	TextView title;
	TextView distance;
	TextView description;
	ImageView img;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View view = inflater.inflate(R.layout.promotion_detail, container, false);
		initViewComponents();
		return view;
	}
	
	
	/**
	 * Set up the view components
	 */
	private void initViewComponents() {
		title = (TextView)view.findViewById(R.id.promotion_detail_title);
		distance = (TextView)view.findViewById(R.id.promotion_detail_distance);
		description = (TextView)view.findViewById(R.id.promotion_detail_description);
		img = (ImageView) view.findViewById(R.id.promotion_detail_image);
	}
	
	/**
	 * display detail information of the promotion. Call from the activity container
	 * @param mtitle
	 * @param mdescription
	 * @param mDistance
	 * @param mImgSrc
	 */
	public void dislayPromotionDetail(String mtitle, String mdescription, String mDistance, String mImgSrc) {
		title.setText(mtitle);
		description.setText(mdescription);
		distance.setText(mDistance);
		img.setImageResource(R.drawable.history);
	}

}
