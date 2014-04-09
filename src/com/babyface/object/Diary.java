package com.babyface.object;

public class Diary {
	
	private int diaryID;
	private String babyName;
	private int babyDOB;
	private String babyPOB;
	private int gender;
	private String frame_resource_name;
	private String image_path;
	
	
	
	public String getImagePath() {
		return image_path;
	}
	public void setImagePath(String image_path) {
		this.image_path = image_path;
	}
	public int getDiaryID() {
		return diaryID;
	}
	public String getBabyName() {
		return babyName;
	}
	public int getBabyDOB() {
		return babyDOB;
	}
	public String getBabyPOB() {
		return babyPOB;
	}
	public int getGender() {
		return gender;
	}
	public String getFrame() {
		return frame_resource_name;
	}
	public void setDiaryID(int diaryID) {
		this.diaryID = diaryID;
	}
	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	public void setBabyDOB(int babyDOB) {
		this.babyDOB = babyDOB;
	}
	public void setBabyPOB(String babyPOB) {
		this.babyPOB = babyPOB;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public void setFrame(String frame) {
		this.frame_resource_name = frame;
	}
	
}
