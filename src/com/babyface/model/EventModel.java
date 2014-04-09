package com.babyface.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.babyface.BabyFaceDAO;
import com.babyface.object.Diary;
import com.babyface.object.Event;

public class EventModel {
private BabyFaceDAO db;
private SQLiteDatabase sqldb;
private ContentValues contentVal;

	public EventModel(BabyFaceDAO db){
		this.db = db;
		contentVal=new ContentValues();;
	}
	
	public void addEvent(Event event, ArrayList<String> imagePath){
		Cursor c;
		ArrayList<Diary> diaryList = new ArrayList<Diary>();
		
		 sqldb = db.getWritableDatabase();
		 sqldb.execSQL("PRAGMA foreign_keys=ON;"); 
		 
		
			
			
			contentVal.put(BabyFaceDAO.COLUMN_DIARY_ID, event.getDiaryID());
			contentVal.put(BabyFaceDAO.COLUMN_EVENT_NAME,event.getEventName());
			contentVal.put(BabyFaceDAO.COLUMN_EVENT_DESC, event.getEventDesc());
			contentVal.put(BabyFaceDAO.COLUMN_EVENT_DATE, event.getEventDate());
			sqldb.insert(BabyFaceDAO.EVENT_TABLE, null, contentVal);
			
			int eventID = getMaxEventID();
			if(imagePath.size()>0)
			  for(String s : imagePath){
				contentVal.clear();
				contentVal.put(BabyFaceDAO.COLUMN_EVENT_ID, eventID);
				contentVal.put(BabyFaceDAO.COLUMN_IMAGE_PATH,s);
				sqldb.insert(BabyFaceDAO.IMAGE_TABLE, null, contentVal);
				
			  }
			
		
			sqldb.close();
		
	}
	
	public void removeEvent(int eventID){
		 sqldb = db.getWritableDatabase();
		 sqldb.delete(BabyFaceDAO.IMAGE_TABLE, "eventID = ?", new String[]{String.valueOf(eventID)} );
		 sqldb.delete(BabyFaceDAO.EVENT_TABLE, "eventID = ?", new String[]{String.valueOf(eventID)} );
		 sqldb.close();
	}
	
	public int getMaxEventID(){
		   
		 sqldb = db.getReadableDatabase();
		   SQLiteStatement stmt =  sqldb.compileStatement("SELECT MAX(eventID) FROM Event");

		    return (int) stmt.simpleQueryForLong();
		
       
	}
	
	public ArrayList<Event> getAllEvents(int diaryID)
	{
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		
		
		
		 String [] columns=new String[]{ BabyFaceDAO.COLUMN_EVENT_ID, BabyFaceDAO.COLUMN_EVENT_NAME, 
				  BabyFaceDAO.COLUMN_EVENT_DESC, BabyFaceDAO.COLUMN_EVENT_DATE};
		Cursor c;	   
		sqldb = db.getReadableDatabase();
	
        c = sqldb.query(BabyFaceDAO.EVENT_TABLE, columns, BabyFaceDAO.COLUMN_DIARY_ID +"=?", new String[]{String.valueOf(diaryID)}, null, null, BabyFaceDAO.COLUMN_EVENT_ID + " DESC");
        
        c.moveToFirst();
        while(!c.isAfterLast()){
        Event event = new Event();
        event.setEventID(c.getInt(c.getColumnIndex(BabyFaceDAO.COLUMN_EVENT_ID)));
        event.setEventName(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_EVENT_NAME)));
        event.setEventDesc(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_EVENT_DESC)));
        event.setEventDate(c.getInt(c.getColumnIndex(BabyFaceDAO.COLUMN_EVENT_DATE)));
        eventList.add(event);
        c.moveToNext();
        }
        c.close();
		return eventList;
		
	}
	/*Returns the title and description of event for entry view class*/
	public Event getTitleAndDescription(int eventID)
	{
		Event evt= new Event();
		Cursor c;	   
		sqldb = db.getReadableDatabase();
		
	    c = sqldb.query(BabyFaceDAO.EVENT_TABLE,new String[]{ BabyFaceDAO.COLUMN_EVENT_NAME, BabyFaceDAO.COLUMN_EVENT_DESC, BabyFaceDAO.COLUMN_EVENT_DATE}, BabyFaceDAO.COLUMN_EVENT_ID +"=?", new String[]{String.valueOf(eventID)}, null, null, null);	          	         
	    if(c.moveToFirst())
	    {
	    	evt.setEventName(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_EVENT_NAME)));
	    	evt.setEventDesc(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_EVENT_DESC)));
	    	evt.setEventDate(c.getInt(c.getColumnIndex(BabyFaceDAO.COLUMN_EVENT_DATE)));
	         		
	    }

	    c.close();
	    sqldb.close();
	    return evt;
	}
	
	public int editDescription(int event_id, String new_description){
		sqldb = db.getWritableDatabase();
		contentVal.put(BabyFaceDAO.COLUMN_EVENT_DESC, new_description);
		int res = sqldb.update(BabyFaceDAO.EVENT_TABLE, contentVal, BabyFaceDAO.COLUMN_EVENT_ID+"=?", new String[]{String.valueOf(event_id)});
		sqldb.close();
		return res;
	}

	
}
