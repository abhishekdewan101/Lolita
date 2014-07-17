package com.example.locationbasedvirtualassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListFriends extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_friends);
		
		ListView reminderList = (ListView)findViewById(R.id.friendList);
		String [] appointments = new String[] {"Mac","Dell","Windows","Mac","Dell","Windows","Mac","Dell","Windows","Mac","Dell","Windows","Mac","Dell","Windows","Mac","Dell","Windows","Mac","Dell","Windows","Mac","Dell","Windows",};
		reminderList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,appointments));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list_friends, menu);
		return true;
	}

	
	public void showAddFriend(View v){
		Intent showFriendsIntent = new Intent(getApplicationContext(),AddFriends.class);
		startActivity(showFriendsIntent);
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
