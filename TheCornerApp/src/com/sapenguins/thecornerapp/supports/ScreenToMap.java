package com.sapenguins.thecornerapp.supports;

import android.graphics.Point;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;

public class ScreenToMap {
	//	In the rest of this class, we assume that screen is divide into 6x6 grids.
	//	With the outer grid the buffer zone. So really, we are interest in 4x4 inner
	//	grid in the map. This class will provide the mechanism to obtain the bounding
	//	coordinate rectangle of each of these grid.
	
	//  So we are really interest in 6x6 grids, starting at row 0, col 0. We want to obtain
	//  the screen bounding coordinate of these grids first.
	public static final int NUM_ROW = 6;
	public static final int NUM_COL = 6;
	
	/**
	 * Compute the bounding coordinate on the screen of a given cell
	 * @param context
	 * @param row
	 * @param col
	 * @return the upper left point and the lower right point of the cell
	 */
	public static Point[] getScreenBoundingCoordinate(View view, int row, int col){
		int viewWidth = view.getWidth();
		int viewHeight = view.getHeight();
		int gridWidth = viewWidth/NUM_COL;
		int gridHeight = viewHeight/NUM_ROW;
		Point upperLeftCorner = new Point(col*gridWidth, row*gridHeight);
		Point lowerRightCorner = new Point((col+1)*gridWidth, (row+1)*gridHeight);
		return new Point[] {upperLeftCorner, lowerRightCorner};
	}
	
	/**
	 * Compute the bounding latlng of cell. It must be mention that the map and the view
	 * must have the same dimension. This is possible if we just wrap the view around the
	 * map fragment.
	 * @param view
	 * @param map
	 * @param row
	 * @param col
	 * @return
	 */
	public static LatLng[] getMapBoundingCoordinate(View view, GoogleMap map,  int row, int col) {
		Point[] screenBound = getScreenBoundingCoordinate(view, row, col);
		Point upperLeftScreenCorner = screenBound[0];
		Point lowerRightScreenCorner = screenBound[1];

		Projection projection = map.getProjection();
		LatLng upperLeftMapCoordinate = projection.fromScreenLocation(upperLeftScreenCorner);
		LatLng lowerRightMapCoordinate = projection.fromScreenLocation(lowerRightScreenCorner);
		
		double lowerLat = upperLeftMapCoordinate.latitude;
		double higherLat = lowerRightMapCoordinate.latitude;
		if (lowerLat > higherLat) {
			//swap them
			double temLat = lowerLat;
			lowerLat = higherLat;
			higherLat = temLat;
		}
		
		//do the same for the longitude
		double lowerLng = upperLeftMapCoordinate.longitude;
		double higherLng = lowerRightMapCoordinate.longitude;
		if (lowerLng > higherLng) {
			double temLng = lowerLng;
			lowerLng = higherLng;
			higherLng = temLng;
		}
		
		return new LatLng[] {new LatLng(lowerLat, lowerLng), new LatLng(higherLat, higherLng)};
	}
	
}
