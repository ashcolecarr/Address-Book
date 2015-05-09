package com.finalproject.blackbook;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowLocationActivity extends Activity 
{
	private SharedPreferences preferences;
	
	private Location myLocation;
	
	private LocationManager locationManager;
	
	private double theLatitude;
	private double theLongitude;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		// Get the address information
		String address = preferences.getString("Line1", "") + ", " + preferences.getString("Line2", "") + ", " + preferences.getString("State", "") + ", " + preferences.getString("State", "") + " " + preferences.getString("Zip", "");    
		
		// Find the address using forward geocoding
		Geocoder addressFinder = new Geocoder(this, Locale.US);
		List<Address> locations = null;
		
		LocationListener locationListener = new MyLocationListener();
		
		try
		{
			locations = addressFinder.getFromLocationName(address, 1);
		}
		catch(IOException e)
		{
			// TODO show dialog saying network unavailable
		}
				
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
	    criteria.setAccuracy(Criteria.ACCURACY_FINE);
	    String chosenProvider = locationManager.getBestProvider(criteria, true);
				
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000, 1, locationListener);
		}
		else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 1, locationListener);
		}
		
		theLatitude = 0.0;
		theLongitude = 0.0;
				
		if(locations != null)
		{
			theLatitude = locations.get(0).getLatitude();
			theLongitude = locations.get(0).getLongitude();
		}
				
		// Get current address
		myLocation = locationManager.getLastKnownLocation(chosenProvider);
		double myLatitude = 0.0;
		double myLongitude = 0.0;
		if(myLocation != null)
		{
			myLatitude = myLocation.getLatitude();
			myLongitude = myLocation.getLongitude();
		}
				
		TextView contactLatitude = (TextView)findViewById(R.id.contact_latitude_textView);
		TextView contactLongitude = (TextView)findViewById(R.id.contact_longitude_textView);
		TextView currentLatitude = (TextView)findViewById(R.id.current_latitude_textView);
		TextView currentLongitude = (TextView)findViewById(R.id.current_longitude_textView);
		
		contactLatitude.setText("Latitude: " + theLatitude);
		contactLongitude.setText("Longitude: " + theLongitude);
		currentLatitude.setText("Latitude: " + myLatitude);
		currentLongitude.setText("Longitude: " + myLongitude);
		
		final Button proximityButton = (Button)findViewById(R.id.proximity_button);
		proximityButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				Toast.makeText(view.getContext(), "Setting proximity alert...", Toast.LENGTH_LONG).show();
								
				// Set up proximity alert
				float radius = 1f;
				long expiration = -1;
							
				//Intent intent = new Intent("com.finalproject.blackbook.ProximityAlert");
				Intent intent = new Intent(view.getContext(), ProximityIntentReceiver.class);
				PendingIntent proximityIntent = PendingIntent.getBroadcast(view.getContext(), -1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
										
				//IntentFilter filter = new IntentFilter("com.finalproject.blackbook.ProximityAlert");  
			    //registerReceiver(new ProximityIntentReceiver(), filter);
				
				locationManager.addProximityAlert(theLatitude, theLongitude, radius, expiration, proximityIntent);
			}
		});
		
		final Button backButton = (Button)findViewById(R.id.back_button);
	    backButton.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View view)
	        {
	    		AlertDialog.Builder backDialogBuilder = new AlertDialog.Builder(view.getContext());
		    	 
    			// cancelDialogBuilder.setTitle("Your Title");
    			backDialogBuilder.setMessage(R.string.back_dialog).setCancelable(false).setPositiveButton(R.string.back_dialog_button_confirm, new DialogInterface.OnClickListener() 
    			{
    				public void onClick(DialogInterface dialog, int id) 
    				{
    					closeSelf();
    				}
    			}).setNegativeButton(R.string.back_dialog_button_cancel, new DialogInterface.OnClickListener() 
    			{
    				public void onClick(DialogInterface dialog, int id) 
    				{
    					dialog.cancel();
    				}
    			});
    	 
    			AlertDialog backDialog = backDialogBuilder.create();
    	 
    			backDialog.show();
	        }
	    });
	}
	
	public class MyLocationListener implements LocationListener
	{

		@Override
		public void onLocationChanged(Location lastLocation) 
		{
			//Log.v("proximity receiver", "location changed");
			myLocation.set(lastLocation);
		}

		@Override
		public void onProviderDisabled(String arg0) 
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderEnabled(String provider) 
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) 
		{
			// TODO Auto-generated method stub
		}
	}
	
	public void closeSelf()
	{
		this.finish();
	}
}
