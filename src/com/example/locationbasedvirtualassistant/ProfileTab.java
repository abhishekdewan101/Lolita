package com.example.locationbasedvirtualassistant;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ProfileTab extends Activity {

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_tab);

		final ActionBar actionBar = getActionBar();

		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// initiating both tabs and set text to it.
		ActionBar.Tab GeneralTab = actionBar.newTab().setText("General");
		ActionBar.Tab LoudTab = actionBar.newTab().setText("Loud");
		ActionBar.Tab SilentTab = actionBar.newTab().setText("Silent");

		// create the two fragments we want to use for display content
		Fragment GeneralFragment = new GeneralFragment();
		Fragment LoudFragment = new LoudFragment();
		Fragment SilentFragment = new SilentFragment();

		// set the Tab listener. Now we can listen for clicks.
		GeneralTab.setTabListener(new MyTabsListener(GeneralFragment));
		LoudTab.setTabListener(new MyTabsListener(LoudFragment));
		SilentTab.setTabListener(new MyTabsListener(SilentFragment));

		// add the two tabs to the actionbar
		actionBar.addTab(GeneralTab);
		actionBar.addTab(LoudTab);
		actionBar.addTab(SilentTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_profile_tab, menu);
		return true;
	}



public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
        case R.id.goBack:
        	Intent goBack = new Intent(getApplicationContext(),MainActivity.class);
        	startActivity(goBack);		
            return true;  
        default:
            return super.onOptionsItemSelected(item);
    }
}
}
class MyTabsListener implements ActionBar.TabListener {
	public Fragment fragment;

	public MyTabsListener(Fragment fragment) {
		this.fragment = fragment;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO: DO nothing ??
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.fragment_container, fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
	}

}

/*
 * Utility class for accessing preference files
 */
class utilPrefFile {
	private static final String TAG = "utility";
	
	public static void updateLayout(Activity act, String fileName,
			int volumeDef, boolean vibrationDef, boolean bluetoothDef,
			boolean wifiDef) {
		ToggleButton bluetooth;
		ToggleButton wifi;
		ToggleButton vibration;
		SeekBar volume;

		SharedPreferences settings = act.getSharedPreferences(fileName, 0);
		boolean vibrationVal = settings.getBoolean("vibration", vibrationDef);
		boolean wifiVal = settings.getBoolean("wifi", wifiDef);
		boolean bluetoothVal = settings.getBoolean("bluetooth", bluetoothDef);
		Log.d(TAG, fileName+"volumeDef:"+volumeDef);
		int volumeVal = settings.getInt("volume", volumeDef);
		Log.d(TAG, fileName+"volumeVal:"+volumeVal);
		
		bluetooth = (ToggleButton) act.findViewById(R.id.bluetoothButton);
		wifi = (ToggleButton) act.findViewById(R.id.wifiButton);
		vibration = (ToggleButton) act.findViewById(R.id.vibrationButton);
		volume = (SeekBar) act.findViewById(R.id.volumeBar);

		bluetooth.setChecked(bluetoothVal);
		wifi.setChecked(wifiVal);
		vibration.setChecked(vibrationVal);
		volume.setProgress(volumeVal);
	}

	public static void savePreferences(Activity act, String fileName) {
		ToggleButton bluetooth;
		ToggleButton wifi;
		ToggleButton vibration;
		SeekBar volume;

		bluetooth = (ToggleButton) act.findViewById(R.id.bluetoothButton);
		wifi = (ToggleButton) act.findViewById(R.id.wifiButton);
		vibration = (ToggleButton) act.findViewById(R.id.vibrationButton);
		volume = (SeekBar) act.findViewById(R.id.volumeBar);

		SharedPreferences settings = act.getSharedPreferences(fileName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("vibration", vibration.isChecked());
		editor.putBoolean("wifi", wifi.isChecked());
		editor.putBoolean("bluetooth", bluetooth.isChecked());
		editor.putInt("volume", volume.getProgress());
		Log.d(TAG, fileName+"volume before:"+volume.getProgress());
		boolean result = editor.commit();
		if (result)
		Log.d(TAG, "commit successful");
		settings = act.getSharedPreferences(fileName, 0);
		Log.d(TAG, fileName+"volume after:"+settings.getInt("volume", 102));
	}
}

class GeneralFragment extends Fragment {
	public static final String GENERAL_PREF = "generalSettingsFile";
	
	
	
	String prefFileName = GENERAL_PREF;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.profile_settings, container, false);
		/*View view =inflater.inflate(R.layout.profile_settings, container, false);

	    Button b=(Button)view.findViewById(R.id.profileSaveButton);
	    b.setOnClickListener(new OnClickListener() {
	    	
            @Override
            public void onClick(View v) {
            	utilPrefFile.savePreferences(getActivity(), prefFileName);
            }
        });

	    return(view);*/
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Passing defaults if the preferences do not already exist !!
		utilPrefFile.updateLayout(this.getActivity(), prefFileName, 70, true,
				false, false);
	}

	public void saveProfileSettings(View view) {
		utilPrefFile.savePreferences(this.getActivity(), prefFileName);
	}
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    
	    utilPrefFile.savePreferences(this.getActivity(), prefFileName);
	}
}

class LoudFragment extends Fragment {
	public static final String LOUD_PREF = "loudSettingsFile";	

	String prefFileName =LOUD_PREF;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.profile_settings, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Passing defaults if the preferences do not already exist !!
		utilPrefFile.updateLayout(this.getActivity(), prefFileName, 100, true,
				false, false);
	}

	public void saveProfileSettings(View view) {
		utilPrefFile.savePreferences(this.getActivity(), prefFileName);
	}
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    
	    utilPrefFile.savePreferences(this.getActivity(), prefFileName);
	}
}

class SilentFragment extends Fragment {
	public static final String SILENT_PREF = "silentSettingsFile";
	String prefFileName = SILENT_PREF;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.profile_settings, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Passing defaults if the preferences do not already exist !!
		utilPrefFile.updateLayout(this.getActivity(), prefFileName, 0, true,
				false, false);
	}

	public void saveProfileSettings(View view) {
		utilPrefFile.savePreferences(this.getActivity(), prefFileName);
	}
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    
	    utilPrefFile.savePreferences(this.getActivity(), prefFileName);
	}
}