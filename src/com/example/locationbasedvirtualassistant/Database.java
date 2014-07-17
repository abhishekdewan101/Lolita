package com.example.locationbasedvirtualassistant;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	private final static int DATABASE_VERSION = 1;
	
	private final static String DATABASE = "assistant";
	
	private final static String REMINDER_TABLE = "reminders";
	
	private final static String LOCATION_TABLE = "locations";
	
	//------ Reminder Table Columns-----------
	private final static String REMINDER_ID = "id";
	private final static String REMINDER_NAME = "reminder";
	private final static String REMINDER_CATEGORY = "category";
	private final static String REMINDER_LONG = "long";
	private final static String REMINDER_LAT = "lat";
	private final static String REMINDER_STATUS = "status";
	
	//------ Location Table Columns-----------
		private final static String LOCATION_ID = "id";
		private final static String LOCATION_NAME = "name";
		private final static String LOCATION_CATEGORY = "category";
		private final static String LOCATION_PROFILE = "profile";
		private final static String LOCATION_LONG = "long";
		private final static String LOCATION_LAT = "lat";
	
	public Database(Context context) {
	super(context, DATABASE, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String CREATE_LOCATIONDB = "CREATE TABLE " + LOCATION_TABLE + " ("
						+ LOCATION_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
                        + LOCATION_NAME+ " TEXT NOT NULL, " 
                        + LOCATION_CATEGORY + " TEXT NOT NULL, "
                        + LOCATION_PROFILE+ " TEXT NOT NULL, "
                        + LOCATION_LONG + " TEXT NOT NULL, "
                        + LOCATION_LAT +" TEXT);";
		

		String CREATE_REMINDERDB = "CREATE TABLE " + REMINDER_TABLE + " ("
						+ REMINDER_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
                        + REMINDER_NAME+ " TEXT NOT NULL, " 
                        + REMINDER_CATEGORY + " TEXT NOT NULL, "
                        + REMINDER_LONG+ " TEXT NOT NULL, "
                        + REMINDER_LAT + " TEXT NOT NULL,"
                        + REMINDER_STATUS + " TEXT NOT NULL);";
		
		 db.execSQL(CREATE_LOCATIONDB);
		 db.execSQL(CREATE_REMINDERDB);
                        
		

	}
	
	public void addReminder(Reminder reminder){
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(REMINDER_NAME,reminder.getReminderName()); // Contact Name
	    values.put(REMINDER_CATEGORY, reminder.getReminderCategory()); // Contact Phone Number
	    values.put(REMINDER_LAT, reminder.getReminderLat());
	    values.put(REMINDER_LONG, reminder.getReminderLong());
	    values.put(REMINDER_STATUS,reminder.getReminderStatus());
	    // Inserting Row
	    db.insert(REMINDER_TABLE, null, values);
	    db.close(); // Closing database connecti
	}
	
	public Reminder getReminder(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(REMINDER_TABLE, new String[] { REMINDER_NAME,
	            REMINDER_CATEGORY, REMINDER_LAT,REMINDER_LONG,REMINDER_STATUS}, REMINDER_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 Reminder reminder = new Reminder(cursor.getString(0),cursor.getString(1),Double.parseDouble(cursor.getColumnName(3)),Double.parseDouble(cursor.getColumnName(2)),cursor.getString(4));
	 return reminder;
	}
	
	public List<Reminder> getAllReminders(){
		List<Reminder> reminderList = new ArrayList<Reminder>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String query = "SELECT * FROM " + REMINDER_TABLE;
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			do{
				Reminder reminder = new Reminder();
				reminder.setReminderName(cursor.getString(1));
				reminder.setReminderCategory(cursor.getString(2));
				reminder.setReminderLong(Double.parseDouble(cursor.getString(3)));
				reminder.setReminderLat(Double.parseDouble(cursor.getString(4)));
				reminder.setReminderStatus(cursor.getString(5));
				reminderList.add(reminder);
			}while(cursor.moveToNext());
		}
		
		return reminderList;
	}
	

	public void addLocation(LocationData location){
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(LOCATION_NAME, location.getLocationName());
	    values.put(LOCATION_CATEGORY, location.getLocationCategory());
	    values.put(LOCATION_PROFILE, location.getLocationProfile());
	    values.put(LOCATION_LONG, location.getLocationLong());
	    values.put(LOCATION_LAT, location.getLocationLat());	    
	    // Inserting Row
	    db.insert(LOCATION_TABLE, null, values);
	    db.close(); // Closing database connecti
	}
	
	public LocationData getLocation(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(LOCATION_TABLE, new String[] { LOCATION_NAME,
	            LOCATION_CATEGORY, LOCATION_PROFILE,LOCATION_LONG,LOCATION_LAT}, LOCATION_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	    
	    LocationData location = new LocationData(cursor.getString(0),cursor.getString(1),cursor.getString(2),Double.parseDouble(cursor.getString(3)),Double.parseDouble(cursor.getString(4)));
	    return location;
	}
	
	public List<LocationData> getAllLocations(){
		List<LocationData> locationList = new ArrayList<LocationData>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String query = "SELECT * FROM " + LOCATION_TABLE;
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			do{
				LocationData location = new LocationData();
				location.setLocationName(cursor.getString(1));
				location.setLocationCategory(cursor.getColumnName(2));
				location.setLocationProfile(cursor.getString(3));
				location.setLocationLong(Double.parseDouble(cursor.getString(4)));
				location.setLocationLat(Double.parseDouble(cursor.getString(5)));				
				locationList.add(location);
			}while(cursor.moveToNext());
		}
		
		return locationList;
	}
	

	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
