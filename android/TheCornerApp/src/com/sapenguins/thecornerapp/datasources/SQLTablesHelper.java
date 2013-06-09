package com.sapenguins.thecornerapp.datasources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLTablesHelper extends SQLiteOpenHelper {

	 public static final String DATABASE_NAME = "myevent.db";
	    public static final int D_VERSION = 1;
	    
	    //coordinate table
	    public static final String AD_TABLE_NAME = "AdTable";
	    public static final String AD_ID = "id";
	    
	    private final String TABLE_AD_CREATE = "create table if not exists " + AD_TABLE_NAME + "( "
	            + AD_ID + " integer);";
	  
	    
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
	        db.execSQL(TABLE_AD_CREATE);
	     
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        db.execSQL("drop table if exists " + AD_TABLE_NAME);
	        onCreate(db);
	    }
}
