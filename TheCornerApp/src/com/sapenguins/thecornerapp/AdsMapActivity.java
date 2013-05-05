package com.sapenguins.thecornerapp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.sapenguins.thecornerapp.constants.Global;
import com.sapenguins.thecornerapp.constants.MenuSpinnerItems;
import com.sapenguins.thecornerapp.constants.ServerVariables;
import com.sapenguins.thecornerapp.constants.SpecialCharacters;
import com.sapenguins.thecornerapp.objects.BasicAd;
import com.sapenguins.thecornerapp.supports.AppHttpClient;
import com.sapenguins.thecornerapp.supports.GeoLocation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;

public class AdsMapActivity extends SherlockFragmentActivity implements ActionBar.OnNavigationListener{
	
	public static final int DEVICE_VERSION = android.os.Build.VERSION.SDK_INT;
	public static final int HONEYCOMB_VERSION = android.os.Build.VERSION_CODES.HONEYCOMB;
	
	public static final int MAP_PADDING = 2;
	
	ActionBar actionbar;
	Context context;
	String[] maximumDistances;
	LocationManager locationManager; 
	String provider;
	
	DisplayImageOptions options;
	ImageLoader imageLoader;
	
	MenuItem homeItem;
	MenuItem mapTypeItem;
	
	GoogleMap googleMap;
	ArrayList<BasicAd> basicAdObjects;
	double distance;
	LatLng adsLocation; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.ads_map);
		context = this;
		
		 //get last known location to calculate the bird-eye distance
	    locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    provider = locationManager.getBestProvider(new Criteria(), true);
        if (provider == null) onProviderDisabled(provider); 
        
        initMap();
		initActionBar();
		
		//moveMapToAds();
		getBasicAdsFromDB(true);
	}
	
	/**
     * Initiate the map. Obtain the map fragment
     */
    private void initMap() {
    	distance = 0.5;
        SupportMapFragment mf = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        googleMap = mf.getMap();
        googleMap.setMyLocationEnabled(true);
        if (provider != null) {
	        Location lastKnown = locationManager.getLastKnownLocation(provider);
	        adsLocation = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());	        
	        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(adsLocation, 14);
	        googleMap.moveCamera(cameraUpdate);
        }
    }
    
    /**
     * Move the camera to a specific location and make it zoom in bag enough
     * to contain all the info
     */
    private void moveMapToAds() {
    	 CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(getLatLngBounds(distance), MAP_PADDING);
	     googleMap.moveCamera(cameraUpdate);
    }
    
    /**
     * Get the rectangular bound that is a fix distance from the adsLocation
     * @param distance
     * @return the LatLngBounds
     */
    private LatLngBounds getLatLngBounds(double distance) {
    	GeoLocation currentLoc = GeoLocation.fromDegrees(adsLocation.latitude, adsLocation.longitude);
        GeoLocation[] boundingCoors = currentLoc.boundingCoordinates(distance, Global.EARTH_RADIUS_IN_MILES);
        LatLng southwest = new LatLng(boundingCoors[0].getLatitudeInDegrees(), boundingCoors[0].getLongitudeInDegrees());
        LatLng northeast = new LatLng(boundingCoors[1].getLatitudeInDegrees(), boundingCoors[1].getLongitudeInDegrees());
        return new LatLngBounds(southwest, northeast);
    }
	
	/**
	 * Create action bar on top of the activity and implement click listener for each of its items
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onCreateOptionsMenu(com.actionbarsherlock.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate( R.menu.map_menu_bar, menu );
		homeItem = menu.findItem(R.id.map_menu_home);
		mapTypeItem = menu.findItem(R.id.map_menu_map_type);
		homeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				startActivity(new Intent(context, HomeActivity.class));
				return true;
			}
		});
		return true; 
	}
	

	/**
	 * Init the actionbar
	 */
	private void initActionBar() {
		setTheme(R.style.Theme_Sherlock);
		if (DEVICE_VERSION >= HONEYCOMB_VERSION) {
			try {
				ViewConfiguration config = ViewConfiguration.get(this);
				Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
				if(menuKeyField != null) {
					menuKeyField.setAccessible(true);
					menuKeyField.setBoolean(config, false);
				}
			} catch (Exception ex) {
				// Ignore
			}
		}
		actionbar = getSupportActionBar();
		actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg_black));
        maximumDistances = getResources().getStringArray(R.array.distances);
        Context actionbarContext = actionbar.getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(actionbarContext, R.array.distances, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionbar.setListNavigationCallbacks(list, this);
        actionbar.hide();
	}
	
	/**
	 * Snarf the menu key.
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (DEVICE_VERSION < HONEYCOMB_VERSION) 
				openOptionsMenu();
			if (actionbar.isShowing()) actionbar.hide();
			else actionbar.show();
			return true; //always eat it!
		}
		return super.onKeyDown(keyCode, event); 
	}

	/**
	 * Action when the item in the spinner is selected
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (itemPosition == MenuSpinnerItems.HALF_MILES_ITEM) {
			distance = 0.5;
			moveMapToAds();
			getBasicAdsFromDB(true);
		}
		if (itemPosition == MenuSpinnerItems.ONE_MILES_ITEM) {
			distance = 1;
			moveMapToAds();
			getBasicAdsFromDB(true);
		}
		if (itemPosition == MenuSpinnerItems.TWO_MILES_ITEM) {
			distance = 2;
			moveMapToAds();
			getBasicAdsFromDB(true);
		}
		if (itemPosition == MenuSpinnerItems.FIVE_MILES_ITEM) {
			distance = 5;
			moveMapToAds();
			getBasicAdsFromDB(true);
		}
		if (itemPosition == MenuSpinnerItems.TEN_MILES_ITEM) {
			distance = 10;
			moveMapToAds();
			getBasicAdsFromDB(true);
		}

		return false;
	} 
	
	/**
	 * Get the basic information on ads from the DB
	 * Input these ads into BasicAd array
	 * @param the coordinate location where we want to do search
	 * @param the distance to consider
	 * @param whether we want to get events
	 */
	public void getBasicAdsFromDB(boolean isEvent) {
		final boolean mIsEvent = isEvent;
		new AsyncTask<Void, Void, String>() {
			
			double minLng;
			double maxLng;
			double minLat;
			double maxLat;
			
			@Override
			protected void onPreExecute() {
				GeoLocation currentLoc = GeoLocation.fromDegrees(adsLocation.latitude, adsLocation.longitude);
				GeoLocation[] boundingCoors = currentLoc.boundingCoordinates(distance, Global.EARTH_RADIUS_IN_MILES);
				minLng = boundingCoors[0].getLongitudeInDegrees();
				maxLng = boundingCoors[1].getLongitudeInDegrees();
				minLat = boundingCoors[0].getLatitudeInDegrees();
				maxLat = boundingCoors[1].getLatitudeInDegrees();
			}
			
			@Override
			protected String doInBackground(Void... params) {
				try {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					if (mIsEvent) postParameters.add(new BasicNameValuePair("event", "all"));
					else postParameters.add(new BasicNameValuePair("deals", "all"));
					postParameters.add(new BasicNameValuePair("minLng", String.valueOf(minLng)));
					postParameters.add(new BasicNameValuePair("maxLng", String.valueOf(maxLng)));
					postParameters.add(new BasicNameValuePair("minLat", String.valueOf(minLat)));
					postParameters.add(new BasicNameValuePair("maxLat", String.valueOf(maxLat)));
					return AppHttpClient.executeHttpPostWithReturnValue(ServerVariables.URL, postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				basicAdObjects = new ArrayList<BasicAd>();
				if (result != null) {
					String[] myAds = result.split(SpecialCharacters.endLn);
					for (String ad : myAds) {
						String[] adDetails = ad.split(SpecialCharacters.delimiter);
						//check if the event detail is valid
						if (adDetails.length == 6)
							basicAdObjects.add(new BasicAd(adDetails[0], adDetails[1],
									adDetails[2], adDetails[3], adDetails[4], adDetails[5]));
					}
				}
				addAdsToMap();
			}
		}.execute();
	}
	
	private void addAdsToMap() {
		googleMap.clear();
		for (BasicAd basicAd : basicAdObjects) {
			final BasicAd myAd = basicAd;
			options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.no_photo_icon)
			.showImageForEmptyUri(R.drawable.no_photo_icon)
			.showImageOnFail(R.drawable.no_photo_icon)
			.cacheInMemory()
			.cacheOnDisc()
			.displayer(new RoundedBitmapDisplayer(20))
			.build();
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(context));
			imageLoader.loadImage(basicAd.getImageUrl(), options, new SimpleImageLoadingListener() {
				final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					if (loadedImage != null) {
						boolean firstDisplay = !displayedImages.contains(imageUri);
						if (firstDisplay) {
							displayedImages.add(imageUri);
						}
						Marker marker = googleMap.addMarker(new MarkerOptions()
							.title(myAd.getTitle())
							.snippet(myAd.getShortDescription())
							.draggable(false)
							.position(new LatLng(myAd.getLatitude(), myAd.getLongitude()))
							.icon(BitmapDescriptorFactory.fromBitmap(getResizedBitmap(loadedImage, 100, 100))));
					}
				}
			});
			
		}
	}

	public Bitmap getResizedBitmap(Bitmap bm, int maxHeight, int maxWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) maxWidth) / width;
	    float scaleHeight = ((float) maxHeight) / height;
	    float scale = scaleWidth;
	    if (scaleHeight < scaleWidth) scale = scaleHeight;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scale, scale);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	
	/**
	 * Execute to attempt to connect to the provider
	 * @param provider
	 */
	public void onProviderDisabled(String provider) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        builder.setTitle("GPS is disable");
	        builder.setCancelable(false);
	        
	        //take user to turn on the GPS setting
	        builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int which) {
	               Intent startGps = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	               startActivity(startGps);
	           } 
	        });
	        
	        //close
	        builder.setNegativeButton("Leave GPS off", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                dialog.cancel();
	            }
	        });
	        
	        //display the alert
	        AlertDialog alert = builder.create();
	        alert.show();
    }
}
