package com.example.locationbasedvirtualassistant;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AppointmentList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointment_list);
		
		ListView lv = (ListView)findViewById(R.id.appointmentList);
		String [] appointments = new String[]{"Appointment 1","Appointment 2","Appointment 3","Appointment 4","Appointment 5","Appointment 6","Appointment 7","Appointment 8"};
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,appointments));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_appointment_list, menu);
		return true;
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
