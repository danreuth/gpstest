package com.example.gpstest2;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
