package dataSources;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GpsLocationDataSource {
	SQLTablesHelper gpsLocationTableHelper;
	SQLiteDatabase db;
	
	String[] columns = {SQLTablesHelper.COORDINATE_LONGITUDE, 
			SQLTablesHelper.COORDINATE_LATITUDE,
			SQLTablesHelper.COORDINATE_TIME};
	
	public GpsLocationDataSource(Context context) {
		gpsLocationTableHelper = new SQLTablesHelper(context);
	}
	
	public void open() {
		if (db == null) db = gpsLocationTableHelper.getWritableDatabase();
	}
	
	public void close() {
		if (db != null) db.close();
	}
	
	/**
	 * add pin to the database
	 * @param PinMarkerObj
	 */
	public void addGpsLocation(GpsLocationObj location) {
		ContentValues contents = new ContentValues();
		contents.put(SQLTablesHelper.COORDINATE_LONGITUDE, location.getLongitude());
		contents.put(SQLTablesHelper.COORDINATE_LATITUDE, location.getLatitude());
		contents.put(SQLTablesHelper.COORDINATE_TIME, location.getTimeStamp());
		
		db.insert(SQLTablesHelper.COORDINATE_TABLE_NAME, null, contents);
	}
	
	/**
	 * get the pins stored in the database
	 * @return ArrayList<PinMarkerObj>
	 */
	public ArrayList<GpsLocationObj> getGpsLocations() {
		ArrayList<GpsLocationObj> locations = new ArrayList<GpsLocationObj>();
		Cursor cursor = db.query(SQLTablesHelper.COORDINATE_TABLE_NAME, columns, 
				null, null, null, null, SQLTablesHelper.COORDINATE_TIME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			GpsLocationObj location = cursorToGpsLocation(cursor);
			locations.add(location);
			cursor.moveToNext();
		}
		cursor.close();
		return locations;
	}
	
	/**
	 * get the Gps location stored in the database 
	 * @param the earliest considered time stamp
	 * @param the latest considered time stamp
	 * @return ArrayList<GpsLocationObj>
	 */
	public ArrayList<GpsLocationObj> getGpsLocations(long beginTime) {
		ArrayList<GpsLocationObj> locations = new ArrayList<GpsLocationObj>();
		Cursor cursor = db.query(SQLTablesHelper.COORDINATE_TABLE_NAME, columns, 
				SQLTablesHelper.COORDINATE_TIME + " > ?", 
				new String[] {String.valueOf(beginTime)}, 
				null, null, SQLTablesHelper.PIN_TIME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			GpsLocationObj location = cursorToGpsLocation(cursor);
			locations.add(location);
			cursor.moveToNext();
		}
		cursor.close();
		return locations;
	}
	
	/**
	 * get the Gps location stored in the database 
	 * @param the earliest considered time stamp
	 * @param the latest considered time stamp
	 * @return ArrayList<GpsLocationObj>
	 */
	public ArrayList<GpsLocationObj> getGpsLocations(String beginTime) {
		ArrayList<GpsLocationObj> locations = new ArrayList<GpsLocationObj>();
		Cursor cursor = db.query(SQLTablesHelper.COORDINATE_TABLE_NAME, columns, 
				SQLTablesHelper.COORDINATE_TIME + " > ?", 
				new String[] {beginTime}, 
				null, null, SQLTablesHelper.PIN_TIME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			GpsLocationObj location = cursorToGpsLocation(cursor);
			locations.add(location);
			cursor.moveToNext();
		}
		cursor.close();
		return locations;
	}
	
	/**
	 * get the Gps location stored in the database 
	 * @param the earliest considered time stamp
	 * @param the latest considered time stamp
	 * @return ArrayList<GpsLocationObj>
	 */
	public ArrayList<GpsLocationObj> getGpsLocations(long beginTime, long endTime) {
		ArrayList<GpsLocationObj> locations = new ArrayList<GpsLocationObj>();
		Cursor cursor = db.query(SQLTablesHelper.COORDINATE_TABLE_NAME, columns, 
				SQLTablesHelper.COORDINATE_TIME + " > ? and " + SQLTablesHelper.COORDINATE_TIME + " < ?", 
				new String[] {String.valueOf(beginTime), String.valueOf(endTime)}, 
				null, null, SQLTablesHelper.PIN_TIME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			GpsLocationObj location = cursorToGpsLocation(cursor);
			locations.add(location);
			cursor.moveToNext();
		}
		cursor.close();
		return locations;
	}
	
	/**
	 * get the Gps location stored in the database 
	 * @param the earliest considered time stamp
	 * @param the latest considered time stamp
	 * @return ArrayList<GpsLocationObj>
	 */
	public ArrayList<GpsLocationObj> getGpsLocations(String beginTime, String endTime) {
		ArrayList<GpsLocationObj> locations = new ArrayList<GpsLocationObj>();
		Cursor cursor = db.query(SQLTablesHelper.COORDINATE_TABLE_NAME, columns, 
				SQLTablesHelper.COORDINATE_TIME + " > ? and " + SQLTablesHelper.COORDINATE_TIME + " < ?", 
				new String[] {beginTime, endTime}, 
				null, null, SQLTablesHelper.PIN_TIME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			GpsLocationObj location = cursorToGpsLocation(cursor);
			locations.add(location);
			cursor.moveToNext();
		}
		cursor.close();
		return locations;
	}
	
	/**
	 * Transform the result from the cursor to the PinMarkerObj
	 * @param Cursor
	 * @return PinMarkerObj
	 */
	private GpsLocationObj cursorToGpsLocation(Cursor cursor) {
		return new GpsLocationObj(cursor.getDouble(0), cursor.getDouble(1), cursor.getLong(2));
	}
}
