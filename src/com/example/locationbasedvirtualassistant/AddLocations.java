package com.example.locationbasedvirtualassistant;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class AddLocations extends MapActivity {
	boolean fromMainActivity = false;
	
	MapView mapView; 
    MapController mc;
    GeoPoint p;
    GPSTracker gps;
    double lat, lng;
    Geocoder geocoder;
	String nameOfUser;
	String emailOfUser;
	
	
	class MapOverlay extends com.google.android.maps.Overlay
    {
        @Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts);
 
            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.marker);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-60, null);         
            return true;
        }
    }
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_locations);
		
		Bundle extra = getIntent().getExtras();
		fromMainActivity = extra.getBoolean("fromMainActivity");
		nameOfUser = extra.getString("nameOfUser");
		emailOfUser = extra.getString("emailOfUser");
		
		Spinner categories = (Spinner)findViewById(R.id.category);
		Spinner profiles = (Spinner)findViewById(R.id.profile);
		
		final EditText search = (EditText)findViewById(R.id.search);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.categories, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categories.setAdapter(adapter);
		
		ArrayAdapter<CharSequence> profileadapter = ArrayAdapter.createFromResource(this,R.array.profiles, android.R.layout.simple_spinner_item);
		profileadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		profiles.setAdapter(profileadapter);
		
	        geocoder = new Geocoder(this);
	        mapView = (MapView) findViewById(R.id.locationsMap);
	       LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
	        mapView.setBuiltInZoomControls(true);
	        mapView.displayZoomControls(true);
	        mc = mapView.getController();
	        gps = new GPSTracker(AddLocations.this);
	        
	        				// check if GPS enabled		
	        		        if(gps.canGetLocation()){
	        		        	
	        		        	double latitude = gps.getLatitude();
	        		        	double longitude = gps.getLongitude();
	        		        	lat = gps.getLatitude();
	        		        	lng = gps.getLongitude();
	        		        	p = new GeoPoint(
	        		                    (int) (lat * 1E6), 
	        		                    (int) (lng * 1E6));
	        		         
	        		                mc.animateTo(p);
	        		                mc.setZoom(15); 
	        		                
	        		                MapOverlay mapOverlay = new MapOverlay();
							        List<Overlay> listOfOverlays = mapView.getOverlays();
							        listOfOverlays.clear();
							        listOfOverlays.add(mapOverlay);
	        		                
	        		                mapView.invalidate();
	        		        	
	        		        	// \n is for new line
	        		        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
	        		        }else{
	        		        	// can't get location
	        		        	// GPS or Network is not enabled
	        		        	// Ask user to enable GPS/network in settings
	        		        	gps.showSettingsAlert();
	        		        }
	        		 search.setOnKeyListener(new OnKeyListener() {

	        					@Override
	        					public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
	        						// TODO Auto-generated method stub
	        						
	        						if (keyCode==KeyEvent.KEYCODE_ENTER) { 
	        							if(search.getText().toString()!=null){
	        								try {
	        									List<Address> returnedaddresses = geocoder.getFromLocationName(search.getText().toString(), 1);
	        									
	        									if(returnedaddresses != null){
	        										lat = returnedaddresses.get(0).getLatitude();
	        								        lng = returnedaddresses.get(0).getLongitude();
//	        		        								        myLatitude.setText(String.valueOf(returnedaddresses.get(0).getLatitude()));
//	        		        								        myLongitude.setText(String.valueOf(returnedaddresses.get(0).getLongitude()));
	        									}
	        									
	        									p = new GeoPoint(
	        								            (int) (lat * 1E6), 
	        								            (int) (lng * 1E6));
	        								 
	        								        mc.animateTo(p);
	        								        mc.setZoom(15); 
	        								        
	        								        MapOverlay mapOverlay = new MapOverlay();
	        								        List<Overlay> listOfOverlays = mapView.getOverlays();
	        								        listOfOverlays.clear();
	        								        listOfOverlays.add(mapOverlay);
	        								        
	        								        
	        								        mapView.invalidate();
	        								        
	        									} catch (IOException e) {
	        										// TODO Auto-generated catch block
	        										e.printStackTrace();
	        									}
	        								
	        							}
	        							
	        					      }
	        						return false;
	        					}
	        					
	        				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_locations, menu);
		return true;
	}
	
	public void saveData(View v){
		
		EditText name = (EditText)findViewById(R.id.Name);
		Spinner  category = (Spinner)findViewById(R.id.category);
		Spinner  profile = (Spinner)findViewById(R.id.profile);
		
		LocationData location = new LocationData(name.getText().toString(),category.getSelectedItem().toString(),profile.getSelectedItem().toString(),lng,lat);
		Database db = new Database(this);
		db.addLocation(location);
		
		if(fromMainActivity == true){
			Intent backIntent = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(backIntent);
		}else{	
		Intent backIntent = new Intent(getApplicationContext(),UsersInformation.class);
		backIntent.putExtra("nameOfUser",nameOfUser);
		backIntent.putExtra("emailOfUser",emailOfUser);
		startActivity(backIntent);
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

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
