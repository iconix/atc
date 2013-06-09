package com.sapenguins.thecornerapp.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AdsDataSource {
	SQLTablesHelper adsTableHelper;
	SQLiteDatabase db;
	
	String[] columns = {SQLTablesHelper.AD_ID};
	
	public AdsDataSource(Context context) {
		adsTableHelper = new SQLTablesHelper(context);
	}
	
	public void open() {
		if (db == null) db = adsTableHelper.getWritableDatabase();
	}
	
	public void close() {
		if (db != null) db.close();
	}
	
	/**
	 * add an id of favorite ad to the local db
	 */
	public void addAdToFavorite(int id) {
		ContentValues contents = new ContentValues();
		contents.put(SQLTablesHelper.AD_ID, id);

		db.insert(SQLTablesHelper.AD_TABLE_NAME, null, contents);
	}
	
	/**
	 * get the pins stored in the database
	 * @return ArrayList<PinMarkerObj>
	 */
	public String getFavoriteAds() {
		String favorite = "";
		Cursor cursor = db.query(SQLTablesHelper.AD_TABLE_NAME, columns, 
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			favorite += String.valueOf(cursor.getInt(0)) + ",";
			cursor.moveToNext();
		}
		cursor.close();
		return favorite;
	}
}
