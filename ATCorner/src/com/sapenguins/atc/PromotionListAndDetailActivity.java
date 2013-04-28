package com.sapenguins.atc;

import com.sapenguins.atc.PromotionListFragmentTest.OnUpdateSelectedPromotion;

import android.app.Activity;
import android.os.Bundle;

public class PromotionListAndDetailActivity extends Activity implements OnUpdateSelectedPromotion{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_list_and_detail);
	}
	
	@Override
	public void onUpdateSelectedPromotion() {
	    
	}
}
