package com.example.gpstest2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private TextView mLongitudeTextView;
	private TextView mLatitudeTextView;
	private Button mUpdateButton;
	private LocationService mLocationService;
	private Geocoder mGeoCoder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mGeoCoder = new Geocoder(this, Locale.US);
		mLocationService = new LocationService(this);
		mLongitudeTextView = (TextView) findViewById(R.id.longitude_textView);
		mLatitudeTextView = (TextView) findViewById(R.id.latitude_textView);
		
		mUpdateButton = (Button) findViewById(R.id.update_button);
		mUpdateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mLocationService.canGetLocation()) {
					Location location = mLocationService.getLocation();
					if(location != null){
						mLongitudeTextView.setText(Double.toString(location.getLongitude()));
						mLatitudeTextView.setText(Double.toString(location.getLatitude()));
						List<Address> list = new ArrayList<Address>();
						if(mGeoCoder.isPresent()) {
							Log.d(TAG,"Present");
						} else {
							Log.d(TAG,"Not Present");
						}
//						try {
//							list = mGeoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
						try {
							list = mGeoCoder.getFromLocationName("15050 SW Koll ParkWay, Beaverton, OR 97006", 5);
						} catch (IOException e) {
							
						}
						Log.d(TAG, list.toString());
					}
				} else {
					mLocationService.showSettingsAlert();
				}
				// TODO Auto-generated method stub
				
			}
		});
	}

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d(TAG, "In on Start");
		super.onStart();
		mLocationService.startLocationService();
		if(mLocationService.canGetLocation()) {
			Location location = mLocationService.getLocation();
			if(location != null){
				mLongitudeTextView.setText(Double.toString(location.getLongitude()));
				mLatitudeTextView.setText(Double.toString(location.getLatitude()));
			}
		} else {
			mLocationService.showSettingsAlert();
		}
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mLocationService.stopLocationService();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
