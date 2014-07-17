package com.example.locationbasedvirtualassistant;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListReminders extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_reminders);
		Database db = new Database(this);
		
		List<Reminder> listOfReminders = db.getAllReminders();
		ListView reminderList = (ListView)findViewById(R.id.reminderList);
		String[] reminders = new String[listOfReminders.size()];
		
		for(int i=0;i<reminders.length;i++){
			Reminder tempReminder = listOfReminders.get(i);
			
			reminders[i] = tempReminder.getReminderName()+"-"+tempReminder.getReminderCategory() + "-"+ tempReminder.getReminderStatus();
		}
		
		reminderList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,reminders));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list_reminders, menu);
		return true;
	}

	public void	showAddRemoinder(View v){
		Intent addReminderIntent = new Intent(getApplicationContext(),AddReminder.class);
		startActivity(addReminderIntent);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.backHome:
	        	Intent goBack = new Intent(getApplicationContext(),MainActivity.class);
	        	startActivity(goBack);		
	            return true;  
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
