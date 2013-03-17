package com.sap.clientproject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class MapActivity extends FragmentActivity{

		private static final String MAP_FRAG_NAME = "MyMap";
		SupportMapFragment mMapFragment;
		

		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
                    setContentView(R.layout.map);
		   /* SupportMapFragment fragment = new SupportMapFragment();
	        getSupportFragmentManager().beginTransaction()
	                .add(android.R.id.content, fragment).commit();*/
		    //setContentView(R.layout.map);
		}
/*		    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		    // Try to obtain the map from the SupportMapFragment.
		    mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag(MAP_FRAG_NAME);

		    // Not found so make a new instance and add it to the transaction for swapping
		    if (mMapFragment == null) {
		        mMapFragment = SupportMapFragment.newInstance();
		        ft.add(R.id.singlemap, mMapFragment, MAP_FRAG_NAME);
		    }

		    ft.commit();
		}

		@Override
		    public void onAttachedToWindow() {
		        // Load the map here such that the fragment has a chance to completely load or else the GoogleMap value may be null
		        GoogleMap googleMap;
		googleMap = (mMapFragment).getMap();
		LatLng latLng = new LatLng(-33.796923, 150.922433);
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.addMarker(new MarkerOptions()
		        .position(latLng)
		        .title("My Spot")
		        .snippet("This is my spot!")
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

		        super.onAttachedToWindow();
		}*/
}
