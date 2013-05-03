package dataSources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLTablesHelper extends SQLiteOpenHelper {

	 public static final String DATABASE_NAME = "LocationalDB.db";
	    public static final int D_VERSION = 1;
	    
	    //coordinate table
	    public static final String COORDINATE_TABLE_NAME = "CoordinateTable";
	    public static final String COORDINATE_LONGITUDE = "longitude";
	    public static final String COORDINATE_LATITUDE = "latitude";
	    public static final String COORDINATE_TIME = "time";
	    
	    //pin table
	    public static final String PIN_TABLE_NAME = "PinAndImageTable";
	    public static final String PIN_ID = "id";
	    public static final String PIN_TITLE = "title";
	    public static final String PIN_DESCRIPTION = "description";
	    public static final String PIN_LONGITUDE = "longitude";
	    public static final String PIN_LATITUDE = "latitude";
	    public static final String PIN_TIME = "time";
	    public static final String PIN_TYPE = "type";
	    public static final String PIN_IMG_URL = "imageUrl";
	    public static final String PIN_NEARBY_LOC = "nearbyLoc";
	    
	    //type of pin
	    public static final String PIN_TYPE_MARK = "mark";
	    public static final String PIN_TYPE_PICTURE = "picture";
	    //default image for the mark type pin
	    public static final String PIN_MARK_IMG = "imageForMark";
	    
	    private final String TABLE_COORDINATE_CREATE = "create table if not exists " + COORDINATE_TABLE_NAME + "( "
	            + COORDINATE_LONGITUDE + " real, " 
	            + COORDINATE_LATITUDE + " real, " 
	            + COORDINATE_TIME + " integer);";
	    
	    private final String TABLE_PIN_CREATE = "create table if not exists " +  PIN_TABLE_NAME + "( "
	    		+ PIN_ID + " integer primary key autoincrement not null, "
	    		+ PIN_TITLE + " text, "
	    		+ PIN_DESCRIPTION + " text, "
	    		+ PIN_LONGITUDE + " real, " 
	    		+ PIN_LATITUDE + " real, "
	    		+ COORDINATE_TIME + " integer, " 
	    		+ PIN_TYPE + " text, " 
	    		+ PIN_IMG_URL + " text, " 
	    		+ PIN_NEARBY_LOC + " integer);";
	    
	    
	    //default constructor
	    public SQLTablesHelper(Context context) {
	    	super(context, DATABASE_NAME, null, D_VERSION);
	    }
	    
	    //constructor, very self-explanatory
	    public SQLTablesHelper(Context context, String name, CursorFactory factory, int version) {
	        super(context, name, factory, version);
	    }
	    
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(TABLE_COORDINATE_CREATE);
	        db.execSQL(TABLE_PIN_CREATE);
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        db.execSQL("drop table if exists " + COORDINATE_TABLE_NAME);
	        db.execSQL("drop table if exists " + PIN_TABLE_NAME);
	        onCreate(db);
	    }
}
