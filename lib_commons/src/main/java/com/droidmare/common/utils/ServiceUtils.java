package com.droidmare.common.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.droidmare.common.R;

//Utils to ensures the correct behaviour of any service
//@author Eduardo on 10/09/2019.

public class ServiceUtils {

    //Method that executes the necessary operations after a service is started (based on the current Android version):
    public static void startService(Context context, Intent serviceIntent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(serviceIntent);

        else context.startService(serviceIntent);
    }

    //Method that initializes a service if the Android version is equal or superior to Oreo, according to the new background service's policy:
    public static void initService(Service service) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String NOTIFICATION_CHANNEL_ID = "com.droidmare.services";
            String channelName = "Background Services";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);

            if (manager != null) {
                manager.createNotificationChannel(chan);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID);
                Notification notification = notificationBuilder.setOngoing(true)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("")
                        .setPriority(NotificationManager.IMPORTANCE_MIN)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .build();

                service.startForeground(1, notification);
            }
        }
    }
}