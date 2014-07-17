package com.example.locationbasedvirtualassistant;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class VirtualAssistant extends FragmentActivity implements ActionBar.TabListener {
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        			.penaltyLog()
        			.build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_virtual_assistant);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_virtual_assistant, menu);
		return true;
	}

	
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			switch(position){
			
			case 0: return new AppointmentSection();
			
			case 1: return new WeatherForecast();
			
			case 2: return new AlarmClass();
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			default: Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
			
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "Appointment Details".toUpperCase();
			case 1:
				return "Weather Details".toUpperCase();
			case 2:
				return "Alarm Details".toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	
	public static class AppointmentSection extends Fragment{
		
		public AppointmentSection(){
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.activity_appointment_list, container, false);
    		String [] appointments = new String[]{"Appointment 1","Appointment 2","Appointment 3","Appointment 4","Appointment 5","Appointment 6","Appointment 7","Appointment 8","Appointment 9","Appointment 10"};
    		ListView lv = (ListView)rootView.findViewById(R.id.appointmentList);
    		lv.setAdapter(new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,appointments));	
 
    		lv.setOnItemClickListener(new OnItemClickListener(){

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    				Object adapter = arg0.getAdapter().getItem(arg2);
    				Intent intent = new Intent(rootView.getContext(),AppointmentDetails.class);
    				intent.putExtra(AppointmentDetails.appointmentName,adapter.toString());
    				intent.putExtra(AppointmentDetails.newAppointment, false);
    				startActivity(intent);
    				
    			
//    				
//    				Toast.makeText(rootView.getContext(),
//    					      adapter.toString(), Toast.LENGTH_LONG)
//    					      .show();			
   				}
    			
    		});
    		
    		
    		return rootView;
		}	
		
		
	}

	public static class WeatherForecast extends Fragment implements TextToSpeech.OnInitListener{
		TextToSpeech tts;
		String geoLookup = "http://api.wunderground.com/api/a2d0ff06963115b1/forecast/q/51.460,2.6000.json";
		String highTempF = "";
		String highTempC = "";
		String lowTempF = "";
		String lowTempC = "";
		String conditions = "";
		String avgHumdity = "";
		String text = "";
		String avgWindKMPH ="";
		String avgWindMPH = "";
		public WeatherForecast(){
			
		}
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			JSONObject ob1 = new JSONParser().getObjectFromUrl(geoLookup);
			
			try {
				JSONObject ob3 = ob1.getJSONObject("forecast");
				JSONObject ob2 = ob3.getJSONObject("simpleforecast");
				JSONArray array1 = ob2.getJSONArray("forecastday");
				
				JSONObject weatherModel = array1.getJSONObject(0);
				
				highTempF = weatherModel.getJSONObject("high").getString("fahrenheit");
				highTempC = weatherModel.getJSONObject("high").getString("celsius");
				lowTempF = weatherModel.getJSONObject("low").getString("fahrenheit");
				lowTempC = weatherModel.getJSONObject("low").getString("celsius");
				conditions = weatherModel.getString("conditions");
				avgHumdity = weatherModel.getString("avehumidity");
				avgWindKMPH = weatherModel.getJSONObject("avewind").getString("kph");
				avgWindMPH = weatherModel.getJSONObject("avewind").getString("mph");
				text = ob1.getJSONObject("forecast").getJSONObject("txt_forecast").getJSONArray("forecastday").getJSONObject(0).getString("fcttext");
				
				
				
			 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
            final View rootView = inflater.inflate(R.layout.activity_weather, container, false);
            TextView temp = (TextView)rootView.findViewById(R.id.tempreture);
            TextView info1 = (TextView)rootView.findViewById(R.id.info1);
            TextView info2 = (TextView)rootView.findViewById(R.id.info2);
            TextView info3 = (TextView)rootView.findViewById(R.id.info3);
            
            temp.setText(highTempC+"¼");
            info1.setText(text);
            info2.setText("Humidity - "+avgHumdity);
            info3.setText("Wind Speed - "+avgWindKMPH);
            
           rootView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				speakWeather();
				return false;
			}
        	   
           
           });
            tts = new TextToSpeech(this.getActivity(),this);    	
    		
    		return rootView;
	}

		
		public void onInit(int status) {			
		}
		
		public void speakWeather(){
			TextView tv= (TextView)this.getActivity().findViewById(R.id.info1);
			String Text = tv.getText().toString();
			tts.speak(Text,TextToSpeech.QUEUE_FLUSH, null);
		}
	}
	
	
	
	public static class AlarmClass extends Fragment{
		public AlarmClass(){	
		}
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.activity_alarm, container, false);
            ListView alarms = (ListView)rootView.findViewById(R.id.listView1);        
            String [] allAlarms = new String[]{"Alarm 1","Alarm 2","Alarm 3","Alarm 4","Alarm 5","Alarm 6","Alarm 7","Alarm 8"};
            alarms.setOnItemClickListener(new OnItemClickListener(){

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    				Object adapter = arg0.getAdapter().getItem(arg2);
    				Intent intent = new Intent(rootView.getContext(),AlarmChange.class);
    				intent.putExtra(AlarmChange.alarmName, adapter.toString());
    				startActivity(intent);
    				
    			
//    				
//    				Toast.makeText(rootView.getContext(),
//    					      adapter.toString(), Toast.LENGTH_LONG)
//    					      .show();			
   				}
    			
    		});
    		alarms.setAdapter(new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,allAlarms));	
    		return rootView;
	}
	}
	
	
	
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}
		
		
	}

}
