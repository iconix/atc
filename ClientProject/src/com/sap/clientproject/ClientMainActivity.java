package com.sap.clientproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class ClientMainActivity extends Activity {

	 private static final int STOPSPLASH = 0;
     //time in milliseconds
     private static final long SPLASHTIME = 1500;
    
     private static ImageView splash;
    
     //handler for splash screen
	private static Handler splashHandler = new Handler() {
         @Override
         public void handleMessage(Message msg) {
             switch (msg.what) {
             	case STOPSPLASH:
                     //remove SplashScreen from view
                     splash.setVisibility(View.GONE);
                     break;
             }
             super.handleMessage(msg);
         }
     };
	    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	    setContentView(R.layout.activity_client_main);
	    splash = (ImageView) findViewById(R.id.splashscreen);
	    Message msg = new Message();
	    msg.what = STOPSPLASH;
	    splashHandler.sendMessageDelayed(msg, SPLASHTIME);
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_main, menu);
		return true;
	}

}
