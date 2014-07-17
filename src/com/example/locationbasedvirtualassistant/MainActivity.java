package com.example.locationbasedvirtualassistant;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.locationbasedvirtualassistant.AddLocations.MapOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends MapActivity {
	MapView mapView;
	MapController mc;
	GeoPoint p;
	GPSTracker gps;
	double lat, lng = 0;
	Geocoder geocoder;
	Button locationButton, reminderButton;
	private MyItemizedOverlay userPicOverlay;
	private Drawable userPic, atmPic;
	private OverlayItem nearatms[];// = new OverlayItem[50];
	public static Context context;

	class MapOverlay extends com.google.android.maps.Overlay {
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);

			// ---translate the GeoPoint to screen pixels---
			Point screenPts = new Point();
			mapView.getProjection().toPixels(p, screenPts);

			// ---add the marker---
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.marker_purple);
			canvas.drawBitmap(bmp, screenPts.x, screenPts.y - 60, null);
			return true;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// -- allow for multiple threads to run at the same time
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.permitAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().penaltyLog()
				.build());

		// ----------------------------------------------------------MAIN
		// ACTIVITY-------------------------------------------

		// check to see if the application has been enetered once and if not
		// then go to the main screen :)
		String FILENAME = "Setup";
		String setupText = "YES";
		StringBuilder onceEntered = null;
		context = getApplicationContext();
		try {
			FileInputStream fis = this.openFileInput(FILENAME);
			InputStreamReader is = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(is);
			onceEntered = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				onceEntered.append(line);
			}
			is.close();

		} catch (FileNotFoundException e) {
			FileOutputStream fos;
			try {
				fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
				fos.write(setupText.getBytes());
				fos.close();
			} catch (FileNotFoundException y) {
				// TODO Auto-generated catch block

			} catch (IOException er) {
				// TODO Auto-generated catch block

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}

		if (onceEntered == null) {
			super.onCreate(savedInstanceState);
			Intent getUsersInformation = new Intent(this,UsersInformation.class);
			getUsersInformation.putExtra("nameOfUser", "");
			getUsersInformation.putExtra("emailOfUser", "");
			startActivity(getUsersInformation);

		} else {
			super.onCreate(savedInstanceState);
			// Intent getUsersInformation = new
			// Intent(this,UsersInformation.class);
			// startActivity(getUsersInformation);
			setContentView(R.layout.activity_main);

			// ------------------------------GPS
			// ACTIVITY----------------------------------------------------------------------------------

			geocoder = new Geocoder(this);
			mapView = (MapView) findViewById(R.id.mapView);
			LinearLayout zoomLayout = (LinearLayout) findViewById(R.id.zoom);
			// View zoomView = mapView.getZoomControls();
			mapView.setBuiltInZoomControls(true);
			// zoomLayout.addView(zoomView,
			// new LinearLayout.LayoutParams(
			// LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT));
			mapView.displayZoomControls(true);

			mc = mapView.getController();

			gps = new GPSTracker(MainActivity.this);

			// check if GPS enabled
			if (gps.canGetLocation()) {

				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();
				lat = gps.getLatitude();
				lng = gps.getLongitude();
				p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

				mc.animateTo(p);
				mc.setZoom(16);
				userPic = this.getResources().getDrawable(
						R.drawable.marker_purple);
				userPicOverlay = new MyItemizedOverlay(userPic);
				OverlayItem overlayItem = new OverlayItem(p, "I'm Here!!!",
						null);
				userPicOverlay.addOverlay(overlayItem);
				mapView.getOverlays().add(userPicOverlay);

				// MapOverlay mapOverlay = new MapOverlay();
				// List<Overlay> listOfOverlays = mapView.getOverlays();
				// listOfOverlays.clear();
				// listOfOverlays.add(mapOverlay);

				mapView.invalidate();

				// \n is for new line
				Toast.makeText(
						getApplicationContext(),
						"Your Location is - \nLat: " + latitude + "\nLong: "
								+ longitude, Toast.LENGTH_LONG).show();
			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				gps.showSettingsAlert();
			}

			// ---------------------------------------------------READING
			// DATABASES-------------------------------------------------
			locationButton = (Button) findViewById(R.id.showProfiles);
			locationButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					

					mc.setZoom(8);
					Database db = new Database(getApplicationContext());
					ArrayList<LocationData> list = new ArrayList<LocationData>();
					list = (ArrayList<LocationData>) db.getAllLocations();
					// Toast.makeText(getApplicationContext(),Integer.toString(list.size()),Toast.LENGTH_LONG).show();
					
					nearatms = new OverlayItem[50];
					
					
					//mapView.getOverlays().add(userPicOverlay);
					//Toast.makeText(getApplicationContext(),Integer.toString(nearPicOverlay.size()),Toast.LENGTH_LONG).show();
					
					for (int i=0; i<mapView.getOverlays().size(); i++){
						mapView.getOverlays().remove(i);
					}
					mapView.getOverlays().add(userPicOverlay);
//					nearatms[0] = new OverlayItem(p, "You are here!!",null);
//					nearPicOverlay.addOverlay(nearatms[0]);
					Iterator ite = list.iterator();
					atmPic = mapView.getResources().getDrawable(R.drawable.marker);
					MyItemizedOverlay nearPicOverlay = new MyItemizedOverlay(atmPic);
					
					for (int i = 1; ite.hasNext(); i++) {
						LocationData curr = (LocationData) ite.next();
						lat = curr.getLocationLat();
						lng = curr.getLocationLong();
						p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

						nearatms[i] = new OverlayItem(p, curr.getLocationName(),null);
						nearPicOverlay.addOverlay(nearatms[i]);
					}
					
					mapView.getOverlays().add(nearPicOverlay);
					mapView.postInvalidate();

				}

			});
			
			
			//------------------------------------------------Populate Reminders---------------------
			reminderButton = (Button) findViewById(R.id.showReminders);
			reminderButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					

					mc.setZoom(8);
					Database db = new Database(getApplicationContext());
					ArrayList<Reminder> list = new ArrayList<Reminder>();
					list = (ArrayList<Reminder>) db.getAllReminders();
					// Toast.makeText(getApplicationContext(),Integer.toString(list.size()),Toast.LENGTH_LONG).show();
					
					nearatms = new OverlayItem[50];
					
					
					//mapView.getOverlays().add(userPicOverlay);
					//Toast.makeText(getApplicationContext(),Integer.toString(nearPicOverlay.size()),Toast.LENGTH_LONG).show();
					
					for (int i=0; i<mapView.getOverlays().size(); i++){
						mapView.getOverlays().remove(i);
					}
					mapView.getOverlays().add(userPicOverlay);
//					nearatms[0] = new OverlayItem(p, "You are here!!",null);
//					nearPicOverlay.addOverlay(nearatms[0]);
					Iterator ite = list.iterator();
					atmPic = mapView.getResources().getDrawable(R.drawable.marker_green);
					MyItemizedOverlay nearPicOverlay = new MyItemizedOverlay(atmPic);
					
					for (int i = 1; ite.hasNext(); i++) {
						Reminder curr = (Reminder) ite.next();
						lat = curr.getReminderLat();
						lng = curr.getReminderLong();
						p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

						nearatms[i] = new OverlayItem(p, curr.getReminderName(),null);
						nearPicOverlay.addOverlay(nearatms[i]);
					}
					
					mapView.getOverlays().add(nearPicOverlay);
					mapView.postInvalidate();

				}

			});
			
			

		}

				
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.addFriends:
			Intent friendIntent = new Intent(getApplicationContext(),
					ListFriends.class);
			startActivity(friendIntent);
			return true;
		case R.id.addProfiles:
			Intent locationIntent = new Intent(getApplicationContext(),
					AddLocations.class);
			locationIntent.putExtra("fromMainActivity", true);
			startActivity(locationIntent);
			return true;
		case R.id.addReminder:
			Intent reminderIntent = new Intent(getApplicationContext(),
					ListReminders.class);
			startActivity(reminderIntent);
			return true;
		case R.id.virtualAssistant:
			Intent virtualAssistant = new Intent(getApplicationContext(),
					VirtualAssistant.class);
			startActivity(virtualAssistant);
			return true;
		case R.id.editprofiles:
			Intent editProfile = new Intent(getApplicationContext(),
					ProfileTab.class);
			startActivity(editProfile);
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
