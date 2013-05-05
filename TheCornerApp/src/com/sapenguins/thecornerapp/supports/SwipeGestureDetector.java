package com.sapenguins.thecornerapp.supports;
import android.view.MotionEvent;
import android.view.View;

public class SwipeGestureDetector implements View.OnTouchListener {

    public static enum Action {
        RL, // Left to Right
        LR,
        None // when no action was detected
    }
    
    private static final int MIN_DISTANCE = 60;
    private float downX, upX;
    private Action mSwipeDetected = Action.None;

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            downX = event.getX();
	            mSwipeDetected = Action.None;
	            return false; // allow other events like Click to be processed
	        case MotionEvent.ACTION_UP:
	            upX = event.getX();	
	            float deltaX = downX - upX;
	            // horizontal swipe detection
	            if (Math.abs(deltaX) > MIN_DISTANCE) {
	                // left or right
	            	if (deltaX < 0) {    
	                    mSwipeDetected = Action.LR;
	                    return true;
	                }
	                if (deltaX > 0) {    
	                    mSwipeDetected = Action.RL;
	                    return true;
	                }
	            }
	            return false;
        }
        return false;
    }
}