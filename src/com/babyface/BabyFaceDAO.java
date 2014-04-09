package com.babyface;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class BabyFaceDAO extends SQLiteOpenHelper{
	private static BabyFaceDAO dao = null;
	final static int DATABASE_VERSION = 2;
	public static final String DBNAME="babyface.db";
	
	public static final String DIARY_TABLE="Diary";
	public static final String COLUMN_DIARY_ID = "diaryID";
	public static final String COLUMN_BABY_NAME = "babyName";
	public static final String COLUMN_BABY_DOB = "babyDOB";
	public static final String COLUMN_BABY_POB = "babyPOB";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_FRAME ="frame";
	public static final String COLUMN_PROFILE_IMAGE ="profile_image";

	public static final String EVENT_TABLE = "Event";
	public static final String COLUMN_EVENT_ID = "eventID";
	public static final String COLUMN_EVENT_NAME = "eventName";
	public static final String COLUMN_EVENT_DESC = "eventDesc";
	public static final String COLUMN_EVENT_DATE = "event_date";
	
	public static final String IMAGE_TABLE = "Image";
	public static final String COLUMN_IMAGE_ID = "imageID";
	public static final String COLUMN_IMAGE_PATH = "imagePath";
	
	
	
	public static final String CREATE_DIARY_TABLE =
	        "CREATE TABLE "+ DIARY_TABLE + " ("+
	        	COLUMN_DIARY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
	            + COLUMN_BABY_NAME + " text not null,"
	            + COLUMN_BABY_DOB + " int not null,"
	            + COLUMN_BABY_POB +" text not null,"
	            + COLUMN_GENDER + " int not null,"
	            + COLUMN_FRAME +" text," 
	            + COLUMN_PROFILE_IMAGE + " text not null"
	            +");";

	public static final String CREATE_EVENT_TABLE =
	        "CREATE TABLE "+ EVENT_TABLE + " (" 
	        	+COLUMN_EVENT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
	            + COLUMN_EVENT_NAME + " TEXT not null,"
	            + COLUMN_EVENT_DESC + " TEXT not null,"
	            + COLUMN_DIARY_ID + " INTEGER, " 
	            + COLUMN_EVENT_DATE + " integer, "
	            + "FOREIGN KEY (diaryID) REFERENCES Diary(diaryID)"
	            +");";
	
	
	public static final String CREATE_IMAGE_TABLE =
	        "CREATE TABLE "+ IMAGE_TABLE + " (" 
	        	+COLUMN_IMAGE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
	            + COLUMN_IMAGE_PATH + " TEXT not null,"
	            + COLUMN_EVENT_ID + " INTEGER, " 
	            + "FOREIGN KEY ("+ COLUMN_EVENT_ID +") REFERENCES Event(eventID)"
	            +");";
	
	
	public static BabyFaceDAO getInstance(Context context){
		if(dao == null)
			dao = new BabyFaceDAO(context.getApplicationContext());
		return dao;
	}
	
	public BabyFaceDAO(Context context) {
		  super(context, DBNAME, null,DATABASE_VERSION); 
		  
		  }

	public BabyFaceDAO(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_DIARY_TABLE);
		db.execSQL(CREATE_EVENT_TABLE);
		db.execSQL(CREATE_IMAGE_TABLE);
		db.execSQL("PRAGMA foreign_keys=ON;"); 

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS "+ IMAGE_TABLE);
		 db.execSQL("DROP TABLE IF EXISTS "+ EVENT_TABLE);
		 db.execSQL("DROP TABLE IF EXISTS "+ DIARY_TABLE);
		 
		 onCreate(db);
	}
	



}
