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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.locationbasedvirtualassistant.AddLocations.MapOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class AddReminder extends MapActivity {
	MapView mapView; 
    MapController mc;
    GeoPoint p;
    GPSTracker gps;
    double lat, lng;
    Geocoder geocoder;
    
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
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.marker_green);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);         
            return true;
        }
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_reminder);
		String [] categories = {"HOME","WORK","UNIVERSITY"};
		Spinner category = (Spinner)findViewById(R.id.spinner1);
		ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);
		category.setAdapter(adapter);
		final EditText searchField = (EditText)findViewById(R.id.editText2);
		
		 geocoder = new Geocoder(this);
	        mapView = (MapView) findViewById(R.id.remindersMap);
	       //LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom1);  
	        mapView.setBuiltInZoomControls(true);
	        mapView.displayZoomControls(true);
	        mc = mapView.getController();
	        mc = mapView.getController();
	        gps = new GPSTracker(AddReminder.this);
	        
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
	        		                mc.setZoom(17); 
	        		                
	        		                MapOverlay mapOverlay = new MapOverlay();
							        List<Overlay> listOfOverlays = mapView.getOverlays();
							        listOfOverlays.clear();
							        listOfOverlays.add(mapOverlay);
	        		                mapView.invalidate();
	        		        	
	        		        		        		         	
	        		        }else{
	        		        	// can't get location
	        		        	// GPS or Network is not enabled
	        		        	// Ask user to enable GPS/network in settings
	        		        	gps.showSettingsAlert();
	        		        }
	
	        		        
	        
	       
	        searchField.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
					// TODO Auto-generated method stub
					
					if (keyCode==KeyEvent.KEYCODE_ENTER) { 
						if(searchField.getText().toString()!=null){
							try {
								List<Address> returnedaddresses = geocoder.getFromLocationName(searchField.getText().toString(), 1);
								
								if(returnedaddresses != null){
									lat = returnedaddresses.get(0).getLatitude();
							        lng = returnedaddresses.get(0).getLongitude();
//	        								        myLatitude.setText(String.valueOf(returnedaddresses.get(0).getLatitude()));
//	        								        myLongitude.setText(String.valueOf(returnedaddresses.get(0).getLongitude()));
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
		getMenuInflater().inflate(R.menu.activity_add_reminder, menu);
		return true;
	}
	
	public void getBackToMain(View v){
		
		EditText reminderName = (EditText)findViewById(R.id.editText1);
		Spinner category  = (Spinner)findViewById(R.id.spinner1);
		double longitude = lng;
		double lattitude = lat;
		String status = "FALSE";
		
		Reminder reminder = new Reminder(reminderName.getText().toString(),category.getSelectedItem().toString(),longitude,lattitude,status);
		
		Database db = new Database(this);
		db.addReminder(reminder);
		Intent getBackToMain = new Intent(getApplicationContext(),MainActivity.class);
		startActivity(getBackToMain);
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
