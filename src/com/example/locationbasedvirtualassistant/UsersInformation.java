package com.example.locationbasedvirtualassistant;



import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class UsersInformation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users_information);
		
		ListView lv = (ListView)findViewById(R.id.firstScreenProfiles);
	
		Database db = new Database(this);
		
		List<LocationData> listOfReminders = db.getAllLocations();
		String[] reminders = new String[listOfReminders.size()];
		
		for(int i=0;i<reminders.length;i++){
			LocationData tempReminder = listOfReminders.get(i);
			
			reminders[i] = tempReminder.getLocationName();
		}
		
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,reminders));

		final EditText userName = (EditText)findViewById(R.id.userName);
		final EditText userEmail = (EditText)findViewById(R.id.userEmail);
		
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(userName.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
			imm.hideSoftInputFromWindow(userEmail.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
				
				
			
		userName.setOnEditorActionListener(new OnEditorActionListener() {
		      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		         if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
		            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		            in.hideSoftInputFromWindow(userName.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		         }
		         return false;
		      }
		   });
		
		Bundle extra = getIntent().getExtras();
		String nameOfUser = extra.getString("nameOfUser");
		String emailOfUser = extra.getString("emailOfUser");
		
		TextView name = (TextView)findViewById(R.id.userName);
		TextView email = (TextView)findViewById(R.id.userEmail);
		
		name.setText(nameOfUser);
		email.setText(emailOfUser);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_users_information, menu);
		return true;
	}
	
	public void onPlusButtonClick(View v){

		TextView name = (TextView)findViewById(R.id.userName);
		TextView email = (TextView)findViewById(R.id.userEmail);
		
		
		Intent addLocationIntent = new Intent(getApplicationContext(),AddLocations.class);
		addLocationIntent.putExtra("fromMainActivity", false);
		addLocationIntent.putExtra("nameOfUser",name.getText().toString());
		addLocationIntent.putExtra("emailOfUser",email.getText().toString());
		startActivity(addLocationIntent);
	}
	
	public void userInformationCollected(View v){
		Intent doneCollection = new Intent(getApplicationContext(),MainActivity.class);
		startActivity(doneCollection);
	}
	
	public void openMedia(View w){
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, 10);
	}
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	      
	     if (requestCode == 10 && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,
	                 filePathColumn, null, null, null);
	         cursor.moveToFirst();
	 
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         String picturePath = cursor.getString(columnIndex);
	         cursor.close();
	                      
	         ImageButton ib1 = (ImageButton)findViewById(R.id.imageButton1);
	         ib1.setImageURI(selectedImage);
	     }
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
