package com.babyface.object;

import java.util.ArrayList;

public class Event {

	private int eventID;
	private int diaryID;
	private String eventName;
	private String eventDesc;
	private int eventDate;
	private String firstImagePath;
	
	public String getFirstImagePath() {
		return firstImagePath;
	}
	public void setFirstImagePath(String firstImagePath) {
		this.firstImagePath = firstImagePath;
	}
	public int getEventDate() {
		return eventDate;
	}
	public void setEventDate(int eventDate) {
		this.eventDate = eventDate;
	}
	public int getEventID() {
		return eventID;
	}
	public int getDiaryID() {
		return diaryID;
	}
	public String getEventName() {
		return eventName;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public void setDiaryID(int diaryID) {
		this.diaryID = diaryID;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
}
