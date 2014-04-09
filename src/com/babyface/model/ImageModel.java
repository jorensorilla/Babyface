package com.babyface.model;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.babyface.BabyFaceDAO;
import com.babyface.object.Image;

public class ImageModel {
	private	BabyFaceDAO db;
	private SQLiteDatabase sqldb;
	private ContentValues contentVal;
	
		public ImageModel(BabyFaceDAO db){
			this.db = db;
			contentVal=new ContentValues();;
		}
		
		public String getFirstImagePath(int eventID){
			String path=null;
			
			 sqldb = db.getReadableDatabase();
			   SQLiteStatement stmt =  sqldb.compileStatement("SELECT imagePath from Image where imageID in (select min(imageID) from Image where eventID = " + eventID + ")");

			path = stmt.simpleQueryForString();
			stmt.close();
//				Cursor c;	   
//				sqldb = db.getReadableDatabase();
//			
//		          c = sqldb.query(BabyFaceDAO.IMAGE_TABLE,new String[]{ BabyFaceDAO.COLUMN_IMAGE_PATH}, BabyFaceDAO.COLUMN_EVENT_ID +"=?", new String[]{String.valueOf(eventID)}, null, null, null);
//		          
//		         
//		          if(c.moveToFirst()){
//		         path = c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_IMAGE_PATH));
//		         		Log.v("PATH_>>>>>>", path);
//		          }
//
//		          c.close();
//		          sqldb.close();
			sqldb.close();
		          return path;
			}

		public ArrayList<Image> getImages(int event_id)
		{
			  ArrayList<Image> images = new ArrayList<Image>();
			  sqldb = db.getReadableDatabase();
			  Cursor cursor = sqldb.query( BabyFaceDAO.IMAGE_TABLE, 
					  new String[] {BabyFaceDAO.COLUMN_IMAGE_ID, BabyFaceDAO.COLUMN_IMAGE_PATH}, BabyFaceDAO.COLUMN_EVENT_ID +"=?", new String[]{String.valueOf(event_id)}, null, null, null);
			  cursor.moveToFirst();
			 
			 while(!cursor.isAfterLast())
			 {
				  Image img = new Image();
				  img.setImageID(cursor.getInt(cursor.getColumnIndex(db.COLUMN_IMAGE_ID)));
				  img.setImagePath(cursor.getString(cursor.getColumnIndex(BabyFaceDAO.COLUMN_IMAGE_PATH)));
				  images.add(img);
				  cursor.moveToNext();
			  }
			 
			  cursor.close();
			  sqldb.close();
			return images;  
		}	
			
		
}
