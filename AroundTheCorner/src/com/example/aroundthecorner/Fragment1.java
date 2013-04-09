package com.example.aroundthecorner;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;

public class Fragment1 extends Fragment{
	public View onCreateView(Layout inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment1, container, false);
	}

}
