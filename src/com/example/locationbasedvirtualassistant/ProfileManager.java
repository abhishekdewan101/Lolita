package com.example.locationbasedvirtualassistant;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

public class ProfileManager {

	public Service service;

	public ProfileManager(Service service) {
		this.service = service;
	}

	public void setProfile(String profileFileName) {
		SharedPreferences settings = service.getSharedPreferences(
				profileFileName, 0);
		boolean vibrationVal = settings.getBoolean("vibration", false);
		boolean wifiVal = settings.getBoolean("wifi", false);
		boolean bluetoothVal = settings.getBoolean("bluetooth", false);
		int volumeVal = settings.getInt("volume", 100);

		/*
		 * Set volume
		 */
		AudioManager audio;
		audio = (AudioManager) service.getSystemService(Context.AUDIO_SERVICE);
		int index = volumeVal / 100
				* audio.getStreamMaxVolume(AudioManager.STREAM_RING);
		index = Math.round(index);
		audio.setStreamVolume(AudioManager.STREAM_RING, index, 0);

		/*
		 * Set vibration
		 */
		if (vibrationVal) {
			audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
					AudioManager.VIBRATE_SETTING_ON);
			audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
					AudioManager.VIBRATE_SETTING_ON);
		} else {
			audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
					AudioManager.VIBRATE_SETTING_OFF);
			audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
					AudioManager.VIBRATE_SETTING_OFF);
		}

		/*
		 * Set wifi
		 */
		WifiManager wifiManager = (WifiManager) service
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiVal)
			wifiManager.setWifiEnabled(true);
		else
			wifiManager.setWifiEnabled(false);
		
		/*
		 * Set bluetooth
		 */
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();    
	    if (bluetoothVal)
	    	bluetoothAdapter.enable();
	    else
	        bluetoothAdapter.disable(); 
	     
	}
}
