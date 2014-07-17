package com.example.locationbasedvirtualassistant;

public class LocationData {
	
	String locationName;
	String locationCategory;
	String locationProfile;
	double locationLong;
	double locationLat;
	
	public LocationData(String name,String category,String profile,double longitude,double latitude){
		locationName = name;
		locationCategory = category;
		locationProfile = profile;
		locationLong = longitude;
		locationLat = latitude;
	}

	public LocationData() {
		// TODO Auto-generated constructor stub
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationCategory() {
		return locationCategory;
	}

	public void setLocationCategory(String locationCategory) {
		this.locationCategory = locationCategory;
	}

	public String getLocationProfile() {
		return locationProfile;
	}

	public void setLocationProfile(String locationProfile) {
		this.locationProfile = locationProfile;
	}

	public double getLocationLong() {
		return locationLong;
	}

	public void setLocationLong(double locationLong) {
		this.locationLong = locationLong;
	}

	public double getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(double locationLat) {
		this.locationLat = locationLat;
	}

	
	
}
