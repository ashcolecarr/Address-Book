package com.finalproject.blackbook;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class ProximityService extends Service 
{
	private NotificationManager notificationManager;
	private Notification myNotification;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification.Builder builder = new Notification.Builder(this);
		
		builder.setTicker("Getting Warmer!").setWhen(System.currentTimeMillis());
		
		Notification myNotification = builder.build(); 
		
		notificationManager.notify(1, myNotification);
		
		return Service.START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
