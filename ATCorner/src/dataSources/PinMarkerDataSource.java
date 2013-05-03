package dataSources;

import java.util.ArrayList;

import objects.PinMarkerObj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PinMarkerDataSource {
	SQLTablesHelper pinTableHelper;
	SQLiteDatabase db;
	
	String[] columns = {SQLTablesHelper.PIN_ID, SQLTablesHelper.PIN_TITLE, SQLTablesHelper.PIN_DESCRIPTION,
			SQLTablesHelper.PIN_LONGITUDE, SQLTablesHelper.PIN_LATITUDE, SQLTablesHelper.PIN_TIME, 
			SQLTablesHelper.PIN_TYPE, SQLTablesHelper.PIN_IMG_URL, SQLTablesHelper.PIN_NEARBY_LOC};
	
	public PinMarkerDataSource(Context context) {
		pinTableHelper = new SQLTablesHelper(context);
	}
	
	public void open() {
		if (db == null) db = pinTableHelper.getWritableDatabase();
	}
	
	public void close() {
		if (db != null) db.close();
	}
	
	/**
	 * add pin to the database
	 * @param PinMarkerObj
	 */
	public void addPin(PinMarkerObj pin) {
		ContentValues contents = new ContentValues();
		contents.put(SQLTablesHelper.PIN_TITLE, pin.getTitle());
		contents.put(SQLTablesHelper.PIN_DESCRIPTION, pin.getDescription());
		contents.put(SQLTablesHelper.PIN_LONGITUDE, pin.getLongitude());
		contents.put(SQLTablesHelper.PIN_LATITUDE, pin.getLatitude());
		contents.put(SQLTablesHelper.PIN_TIME, pin.getTime());
		contents.put(SQLTablesHelper.PIN_TYPE, pin.getPinType());
		contents.put(SQLTablesHelper.PIN_IMG_URL, pin.getImageUrl());
		contents.put(SQLTablesHelper.PIN_NEARBY_LOC, pin.getNearbyLoc());
		db.insert(SQLTablesHelper.PIN_TABLE_NAME, null, contents);
	}
	
	/**
	 * get the pins stored in the database
	 * @return ArrayList<PinMarkerObj>
	 */
	public ArrayList<PinMarkerObj> getPins() {
		ArrayList<PinMarkerObj> pins = new ArrayList<PinMarkerObj>();
		Cursor cursor = db.query(SQLTablesHelper.PIN_TABLE_NAME, columns, 
				null, null, null, null, SQLTablesHelper.PIN_TIME);
		if (cursor!= null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				PinMarkerObj pin = cursorToPin(cursor);
				pins.add(pin);
				cursor.moveToNext();
			}
		}
		cursor.close();
		return pins;
	}
	
	/**
	 * get the pins stored in the database 
	 * @param the earliest considered time stamp
	 * @return ArrayList<PinMarkerObj>
	 */
	public ArrayList<PinMarkerObj> getPins(String beginTime) {
		ArrayList<PinMarkerObj> pins = new ArrayList<PinMarkerObj>();
		Cursor cursor = db.query(SQLTablesHelper.PIN_TABLE_NAME, columns, 
				SQLTablesHelper.PIN_TIME + " > ?", 
				new String[] {beginTime}, 
				null, null, SQLTablesHelper.PIN_TIME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PinMarkerObj pin = cursorToPin(cursor);
			pins.add(pin);
			cursor.moveToNext();
		}
		cursor.close();
		return pins;
	}
	
	/**
	 * get the pins stored in the database 
	 * @param the earliest considered time stamp
	 * @return ArrayList<PinMarkerObj>
	 */
	public ArrayList<PinMarkerObj> getPins(long beginTime) {
		ArrayList<PinMarkerObj> pins = new ArrayList<PinMarkerObj>();
		Cursor cursor = db.query(SQLTablesHelper.PIN_TABLE_NAME, columns, 
				SQLTablesHelper.PIN_TIME + " > ?", 
				new String[] {String.valueOf(beginTime)}, 
				null, null, SQLTablesHelper.PIN_TIME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PinMarkerObj pin = cursorToPin(cursor);
			pins.add(pin);
			cursor.moveToNext();
		}
		cursor.close();
		return pins;
	}
	
	/**
	 * get the pins stored in the database 
	 * @param the earliest considered time stamp
	 * @param the latest considered time stamp
	 * @return ArrayList<PinMarkerObj>
	 */
	public ArrayList<PinMarkerObj> getPins(long beginTime, long endTime) {
		ArrayList<PinMarkerObj> pins = new ArrayList<PinMarkerObj>();
		Cursor cursor = db.query(SQLTablesHelper.PIN_TABLE_NAME, columns, 
				SQLTablesHelper.PIN_TIME + " > ? and " + SQLTablesHelper.PIN_TIME + " < ?", 
				new String[] {String.valueOf(beginTime), String.valueOf(endTime)}, 
				null, null, SQLTablesHelper.PIN_TIME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PinMarkerObj pin = cursorToPin(cursor);
			pins.add(pin);
			cursor.moveToNext();
		}
		cursor.close();
		return pins;
	}
	
	/**
	 * get the pins stored in the database 
	 * @param the earliest considered time stamp
	 * @param the latest considered time stamp
	 * @return ArrayList<PinMarkerObj>
	 */
	public ArrayList<PinMarkerObj> getPins(String beginTime, String endTime) {
		ArrayList<PinMarkerObj> pins = new ArrayList<PinMarkerObj>();
		Cursor cursor = db.query(SQLTablesHelper.PIN_TABLE_NAME, columns, 
				SQLTablesHelper.PIN_TIME + " > ? and " + SQLTablesHelper.PIN_TIME + " < ?", 
				new String[] {beginTime, endTime}, 
				null, null, SQLTablesHelper.PIN_TIME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PinMarkerObj pin = cursorToPin(cursor);
			pins.add(pin);
			cursor.moveToNext();
		}
		cursor.close();
		return pins;
	}
	
	/**
	 * Transform the result from the cursor to the PinMarkerObj
	 * @param Cursor
	 * @return PinMarkerObj
	 */
	private PinMarkerObj cursorToPin(Cursor cursor) {
		return new PinMarkerObj(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
				cursor.getDouble(3), cursor.getDouble(4), cursor.getLong(5), 
				cursor.getString(6), cursor.getString(7), cursor.getInt(8));
	}
}
