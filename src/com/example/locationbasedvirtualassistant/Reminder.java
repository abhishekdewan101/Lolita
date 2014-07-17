package com.example.locationbasedvirtualassistant;

public class Reminder {

	String reminderName;
	String reminderCategory;
	double reminderLong;
	double reminderLat;
	String reminderStatus = "FALSE";
	
	public Reminder(String name,String category,double longitude,double latitude,String status){
		reminderName = name;
		reminderCategory = category;
		reminderLong = longitude;
		reminderLat = latitude;
		reminderStatus = status;
	}
	
	public String getReminderStatus() {
		return reminderStatus;
	}

	public void setReminderStatus(String reminderStatus) {
		this.reminderStatus = reminderStatus;
	}

	public Reminder(){
		
	}

	public String getReminderName() {
		return reminderName;
	}

	public void setReminderName(String reminderName) {
		this.reminderName = reminderName;
	}

	public String getReminderCategory() {
		return reminderCategory;
	}

	public void setReminderCategory(String reminderCategory) {
		this.reminderCategory = reminderCategory;
	}

	public double getReminderLong() {
		return reminderLong;
	}

	public void setReminderLong(double reminderLong) {
		this.reminderLong = reminderLong;
	}

	public double getReminderLat() {
		return reminderLat;
	}

	public void setReminderLat(double reminderLat) {
		this.reminderLat = reminderLat;
	}
	
	
	
}
