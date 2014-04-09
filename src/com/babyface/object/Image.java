package com.babyface.object;

public class Image {
	private int imageID;
	private int eventID;
	private String imagePath;
	
	
	public int getImageID() {
		return imageID;
	}
	public int getEventID() {
		return eventID;
	}

	public String getImagePath() {
		return imagePath;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
}
