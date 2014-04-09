package com.babyface.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;


import com.babyface.BabyFaceDAO;
import com.babyface.object.Diary;



public class DiaryModel{
	
private BabyFaceDAO db;
private SQLiteDatabase sqldb;
private  ContentValues contentVal;

	public DiaryModel(BabyFaceDAO db){
		this.db = db;
		contentVal=new ContentValues();
	}
	
	public long addDiary(Diary diary){
		sqldb = db.getWritableDatabase();
	
		
		contentVal.put(BabyFaceDAO.COLUMN_BABY_NAME, diary.getBabyName());
		contentVal.put(BabyFaceDAO.COLUMN_BABY_DOB, diary.getBabyDOB());
		contentVal.put(BabyFaceDAO.COLUMN_BABY_POB, diary.getBabyPOB());
		contentVal.put(BabyFaceDAO.COLUMN_GENDER, diary.getGender());
		contentVal.put(BabyFaceDAO.COLUMN_FRAME, diary.getFrame());
		contentVal.put(BabyFaceDAO.COLUMN_PROFILE_IMAGE, diary.getImagePath());
		
		long res = sqldb.insert(BabyFaceDAO.DIARY_TABLE, null, contentVal);
		sqldb.close();
		return res;
	}
	
	public ArrayList<Diary> getAllDiary(){
		Cursor c;
		ArrayList<Diary> diaryList = new ArrayList<Diary>();
		
		   
		sqldb = db.getReadableDatabase();
		c = sqldb.rawQuery("SELECT * FROM " + BabyFaceDAO.DIARY_TABLE, null);
		c.moveToFirst();
		
		while(!c.isAfterLast()){
			
			Diary diary = new Diary();
			diary.setDiaryID(c.getInt(c.getColumnIndex(BabyFaceDAO.COLUMN_DIARY_ID)));
			diary.setBabyName(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_BABY_NAME)));
			diary.setBabyDOB(c.getInt(c.getColumnIndex(BabyFaceDAO.COLUMN_BABY_DOB)));
			diary.setBabyPOB(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_BABY_POB)));
			diary.setGender(c.getInt(c.getColumnIndex(BabyFaceDAO.COLUMN_GENDER)));
			diary.setFrame(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_FRAME)));
			diary.setImagePath(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_PROFILE_IMAGE)));
			diaryList.add(diary);
			c.moveToNext();
			
		}
		c.close();
		sqldb.close();
		return diaryList;
		
	
	}
	
	public Diary getDiary(int diaryID){
		Diary diary = new Diary();
		
		   
		   String [] columns=new String[]{ BabyFaceDAO.COLUMN_DIARY_ID, BabyFaceDAO.COLUMN_BABY_NAME, 
				   BabyFaceDAO.COLUMN_BABY_DOB, BabyFaceDAO.COLUMN_BABY_POB, BabyFaceDAO.COLUMN_GENDER, BabyFaceDAO.COLUMN_PROFILE_IMAGE};
		Cursor c;	   
		sqldb = db.getReadableDatabase();
	
          c = sqldb.query(BabyFaceDAO.DIARY_TABLE, columns, BabyFaceDAO.COLUMN_DIARY_ID +"=?", new String[]{String.valueOf(diaryID)}, null, null, null);
          
          c.moveToFirst();
          diary.setDiaryID(c.getInt(c.getColumnIndex(BabyFaceDAO.COLUMN_DIARY_ID)));
          diary.setBabyName(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_BABY_NAME)));
          diary.setBabyDOB(c.getInt(c.getColumnIndex(BabyFaceDAO.COLUMN_BABY_DOB)));
          diary.setBabyPOB(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_BABY_POB)));
          diary.setGender(c.getInt(c.getColumnIndex(BabyFaceDAO.COLUMN_GENDER)));
          diary.setImagePath(c.getString(c.getColumnIndex(BabyFaceDAO.COLUMN_PROFILE_IMAGE)));
          c.close();
          sqldb.close();
		return diary;
	}



	
	
}
