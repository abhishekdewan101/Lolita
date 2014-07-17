package com.example.locationbasedvirtualassistant;



import java.io.IOException;
import java.util.List;
import java.util.Locale;



import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;  
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class Maps extends MapActivity {
	
	MapView mapView; 
    MapController mc;
    GeoPoint p;
    GPSTracker gps;
    Button btnShowLocation;
    double lat, lng;
    Geocoder geocoder;
    TextView myLatitude, myLongitude;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        
        myLatitude = (TextView)findViewById(R.id.mylatitude);
        myLongitude = (TextView)findViewById(R.id.mylongitude);
        final EditText address = (EditText)findViewById(R.id.editText1);
        geocoder = new Geocoder(this);
        mapView = (MapView) findViewById(R.id.mapView);
       LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        //View zoomView = mapView.getZoomControls(); 
        mapView.setBuiltInZoomControls(true);
//        zoomLayout.addView(zoomView, 
//            new LinearLayout.LayoutParams(
//                LayoutParams.WRAP_CONTENT, 
//                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
 
        mc = mapView.getController();
        String coordinates[] = {"1.352566007", "103.78921587"};
        lat = Double.parseDouble(coordinates[0]);
        lng = Double.parseDouble(coordinates[1]);
        
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {		
				// create class object
//		        gps = new GPSTracker(MainActivity.this);
//
//				// check if GPS enabled		
//		        if(gps.canGetLocation()){
//		        	
//		        	double latitude = gps.getLatitude();
//		        	double longitude = gps.getLongitude();
//		        	lat = gps.getLatitude();
//		        	lng = gps.getLongitude();
//		        	p = new GeoPoint(
//		                    (int) (lat * 1E6), 
//		                    (int) (lng * 1E6));
//		         
//		                mc.animateTo(p);
//		                mc.setZoom(17); 
//		                mapView.invalidate();
//		        	
//		        	// \n is for new line
//		        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
//		        }else{
//		        	// can't get location
//		        	// GPS or Network is not enabled
//		        	// Ask user to enable GPS/network in settings
//		        	gps.showSettingsAlert();
//		        }
				
				
				//address.setHint("BS1 5DH");
//				try {
//					List<Address> returnedaddresses = geocoder.getFromLocationName(address.getText().toString(), 1);
//					
//					
//					
//					if(returnedaddresses != null){
//						lat = returnedaddresses.get(0).getLatitude();
//				        lng = returnedaddresses.get(0).getLongitude();
//				        myLatitude.setText(String.valueOf(returnedaddresses.get(0).getLatitude()));
//				        myLongitude.setText(String.valueOf(returnedaddresses.get(0).getLongitude()));
//					}
//					
//					p = new GeoPoint(
//				            (int) (lat * 1E6), 
//				            (int) (lng * 1E6));
//				 
//				        mc.animateTo(p);
//				        mc.setZoom(17); 
//				        mapView.invalidate();
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
				
				
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
				Uri.parse("http://maps.google.com/maps?saddr=51.4579714,-2.6014743&daddr=51.449542,-2.5810719"));
				startActivity(intent);
				
				
				

				
				
			}
		});
 
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
 
        mc.animateTo(p);
        mc.setZoom(17); 
        mapView.invalidate();
        
        
	}


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
