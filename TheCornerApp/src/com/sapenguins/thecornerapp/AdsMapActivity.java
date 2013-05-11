package com.sapenguins.thecornerapp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
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
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
	Map<Marker, Integer> markers;
	
	double distance;
	LatLng adsLocation; 
	
	//component of the tab bar
	View tabEvent;
	TextView eventText;
	View eventUnderline;
	View tabPromotion;
	TextView promotionText;
	View promotionUnderline;
	boolean currentIsEvent; 
	int mapType;
	
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
		setupTabBarComponents();
		
		//moveMapToAds();
		currentIsEvent = true;
		getBasicAdsFromDB(currentIsEvent);
		setInfoWindowClickListener();
	}
	
	@Override
	public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance().activityStart(this); // Add this method.
	}

	
	@Override
	protected void onDestroy() {
		EasyTracker.getInstance().activityStop(this);
		super.onDestroy();
	}



	/**
	 * Set the listener when the user click on the info window that was 
	 * initiated when he/she press on the marker on map
	 */
	private void setInfoWindowClickListener() {
		googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {	
			@Override
			public void onInfoWindowClick(Marker marker) {
				int index = markers.get(marker);
				BasicAd ad = basicAdObjects.get(index);
				double distance = computeDistanceToAd(ad);
				String displayDistance = String.format("%.2f", distance) + " mi";
				if (currentIsEvent) {
					Intent intent = new Intent(context, EventFullDetailActivity.class);
					intent.putExtra("eventId", ad.getId());
					intent.putExtra("eventTitle", ad.getTitle());
					intent.putExtra("eventImg", ad.getImageUrl());
					intent.putExtra("eventDesc", ad.getShortDescription());
					intent.putExtra("eventLongitude", ad.getLongitude());
					intent.putExtra("eventLatitude", ad.getLatitude());
					intent.putExtra("eventDistance", displayDistance);
					startActivity(intent);
				} else {
					Intent intent = new Intent(context, PromotionFullDetailActivity.class);	
					intent.putExtra("promotionId", ad.getId());
					intent.putExtra("promotionTitle", ad.getTitle());
					intent.putExtra("promotionImg", ad.getImageUrl());
					intent.putExtra("promotionDesc", ad.getShortDescription());
					intent.putExtra("promotionLongitude", ad.getLongitude());
					intent.putExtra("promotionLatitude", ad.getLatitude());
					intent.putExtra("promotionDistance", displayDistance);
					startActivity(intent);
				}
			}
		});
	}
	
	/**
	 * Get the distance between the current location and the location of the ad
	 * @param the ad
	 * @return distance between them
	 */
	private double computeDistanceToAd(BasicAd ad) {
		Location lastKnown = locationManager.getLastKnownLocation(provider);
	    Location eventLocation = new Location(provider);
		eventLocation.setLatitude(ad.getLatitude());
		eventLocation.setLongitude(ad.getLongitude());
		return lastKnown.distanceTo(eventLocation)/Global.METERS_IN_MILE;	
	}
	
	/**
	 * Setup the component for the tab bar
	 */
	private void setupTabBarComponents() {
		tabEvent = findViewById(R.id.map_tab_bar_event);
		eventText = (TextView) findViewById(R.id.map_tab_bar_event_text);
		eventUnderline = findViewById(R.id.map_tab_bar_event_text_underline);
		tabPromotion = findViewById(R.id.map_tab_bar_promotion);
		promotionText = (TextView) findViewById(R.id.map_tab_bar_promotion_text);
		promotionUnderline = findViewById(R.id.map_tab_bar_promotion_text_underline);
		setTabEventClickListener();
		setTabPromotionClickListener();
	}
	
	/**
	 * set the listener for then the tab event is click
	 */
	private void setTabEventClickListener() {
		tabEvent.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if (!currentIsEvent) {
					eventText.setTypeface(null, Typeface.BOLD);
					promotionText.setTypeface(null, Typeface.NORMAL);
					eventUnderline.setBackgroundColor(0xffa2a2ff);
					promotionUnderline.setBackgroundColor(0xffcfcfcf);
					getBasicAdsFromDB(true);
					currentIsEvent = true;
				}
			}
		});
	}
	
	/**
	 * set the listener for then the tab event is click
	 */
	private void setTabPromotionClickListener() {
		tabPromotion.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if (currentIsEvent) {
					eventText.setTypeface(null, Typeface.NORMAL);
					promotionText.setTypeface(null, Typeface.BOLD);
					eventUnderline.setBackgroundColor(0xffcfcfcf);
					promotionUnderline.setBackgroundColor(0xffa2a2ff);
					getBasicAdsFromDB(false);
					currentIsEvent = false;
				}
			}
		});
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
				actionbar.hide();
				return true;
			}
		});
		
		setMapTypeItemClickListener();
		return true; 
	}
	
	//Map type view
    public static final int NORMAL_VIEW = 0;
    public static final int HYBRID_VIEW = 1;
    public static final int SATELLITE_VIEW = 2;
    public static final int TERRAIN_VIEW = 3;
	/**
	 * Create a listener for the map type menu item. Open a dialog where user
	 * can select which type of map he/she wish to view
	 */
	private void setMapTypeItemClickListener() {
		mapTypeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				int type = googleMap.getMapType();
				if (type == GoogleMap.MAP_TYPE_NORMAL) mapType = NORMAL_VIEW;
				if (type == GoogleMap.MAP_TYPE_HYBRID) mapType = HYBRID_VIEW;
				if (type == GoogleMap.MAP_TYPE_SATELLITE) mapType = SATELLITE_VIEW;
				if (type == GoogleMap.MAP_TYPE_TERRAIN) mapType = TERRAIN_VIEW;
				CharSequence[] mapOptions = {"Normal", "Hybrid", "Satellite", "Terrain"};
		    	AlertDialog.Builder builder = new AlertDialog.Builder(AdsMapActivity.this);
		    	builder.setTitle("Select Map Style")
		    	.setSingleChoiceItems(mapOptions, mapType, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mapType = which;
					}
				})
		    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (mapType == NORMAL_VIEW) googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						if (mapType == HYBRID_VIEW) googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
						if (mapType == SATELLITE_VIEW) googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
						if (mapType == TERRAIN_VIEW) googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
						dialog.cancel();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		    	AlertDialog alert = builder.create();
		    	alert.show();
		    	actionbar.hide();
				return false;
			}
		});
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
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg_black));
        maximumDistances = getResources().getStringArray(R.array.distances);
        Context actionbarContext = actionbar.getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(actionbarContext, R.array.distances, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionbar.setListNavigationCallbacks(list, this);
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
			getBasicAdsFromDB(currentIsEvent);
		}
		if (itemPosition == MenuSpinnerItems.ONE_MILES_ITEM) {
			distance = 1;
			moveMapToAds();
			getBasicAdsFromDB(currentIsEvent);
		}
		if (itemPosition == MenuSpinnerItems.TWO_MILES_ITEM) {
			distance = 2;
			moveMapToAds();
			getBasicAdsFromDB(currentIsEvent);
		}
		if (itemPosition == MenuSpinnerItems.FIVE_MILES_ITEM) {
			distance = 5;
			moveMapToAds();
			getBasicAdsFromDB(currentIsEvent);
		}
		if (itemPosition == MenuSpinnerItems.TEN_MILES_ITEM) {
			distance = 10;
			moveMapToAds();
			getBasicAdsFromDB(currentIsEvent);
		}
		actionbar.hide();

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
				googleMap.clear();
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
					if (mIsEvent) postParameters.add(new BasicNameValuePair("event", "map_view"));
					else postParameters.add(new BasicNameValuePair("deal", "map_view"));
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
						if (adDetails.length == 6) {
							//check if the shortDescription is empty. If it is then make it empty
							String shortDescription = adDetails[5];
							if (shortDescription.equals(SpecialCharacters.empty))
								shortDescription = ""; // set it back to empty
							//check if the url is empty
							String imageUrl = adDetails[4];
							if (imageUrl.equals(SpecialCharacters.empty))
								imageUrl = ""; // set it back to empty
							basicAdObjects.add(new BasicAd(adDetails[0], adDetails[1],
									adDetails[2], adDetails[3], imageUrl, shortDescription));
						}
					}
				}
				addAdsToMap();
			}
		}.execute();
	}
	
	private void addAdsToMap() {
		markers = new HashMap<Marker, Integer>();
		for (int i = 0; i < basicAdObjects.size(); i++) {//BasicAd basicAd : basicAdObjects) {
			final int index = i;
			final BasicAd myAd = basicAdObjects.get(i);
			options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.no_photo_icon)
			.showImageForEmptyUri(R.drawable.no_photo_icon)
			.showImageOnFail(R.drawable.no_photo_icon)
			.cacheOnDisc()
			.displayer(new RoundedBitmapDisplayer(20))
			.resetViewBeforeLoading()
			.build();
			imageLoader = ImageLoader.getInstance();
			
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
			.memoryCache(new WeakMemoryCache())
			.denyCacheImageMultipleSizesInMemory()
			.build();
			imageLoader.init(config);
			imageLoader.loadImage(myAd.getImageUrl(), options, new SimpleImageLoadingListener() {
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
						markers.put(marker, index);
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
