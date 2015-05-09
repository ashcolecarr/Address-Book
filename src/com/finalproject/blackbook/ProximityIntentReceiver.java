package com.finalproject.blackbook;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class ProximityIntentReceiver extends BroadcastReceiver 
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Log.v("proximity receiver", "received");
		
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		
		Boolean entering = intent.getBooleanExtra(key, false);
		
		// Make a notification that the user has entered near the contact's address
		//context.startService(new Intent(context, ProximityService.class));
		//Intent blackBookIntent = new Intent(context, BlackBookActivity.class);
		//context.startActivity(blackBookIntent);
		
		if(entering)
		{
			Log.v("proximity receiver", "alert received");
		}
		
		//PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, null, 0);        
	        
	    // Fire notification when user draws near their desired address
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification.Builder builder = new Notification.Builder(context);
		builder.setTicker("Getting Warmer!").setWhen(System.currentTimeMillis());
		
		Notification myNotification = builder.build();
		
		notificationManager.notify(1, myNotification);
	}
}
