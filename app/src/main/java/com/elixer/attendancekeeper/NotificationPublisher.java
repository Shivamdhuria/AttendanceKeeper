package com.elixer.attendancekeeper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Admin on 1/26/2018.
 */

public class NotificationPublisher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
        //   Log.e("show Notification","show Notification");
        // Toast.makeText(getContext(),"Alarm sent",Toast.LENGTH_SHORT).show();
        Log.e("show Notification", "show Notification");
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire(30000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Notification.Builder builder;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            String id = "w01", name = "Class Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String desc = "Mark your Attendance";
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build();

            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(desc);
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),audioAttributes);
            notificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(context, id);

            // builder = new Notification.Builder(context);
            builder.setAutoCancel(false);
            builder.setContentTitle("Class Reminder");
            builder.setContentText("Mark your Attendance");



            builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
            // builder.setContentIntent(pendingIntent);

            Notification notification = builder.build();
            notificationManager.notify(0, notification);

        }
        else{

            Intent myIntent = new Intent(context, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Class Reminder")
                            .setContentText("Mark Your Attendance")
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setVibrate(new long[] { 0,1000,1000, 1000, 1000 })
                            .setLights(Color.YELLOW, 3000, 3000);
            mBuilder.setContentIntent(contentIntent);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setAutoCancel(true);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());



        }
    }
    private void showNotification(Context context) {



        }

       /* final int NOTIFICATION_ID = 1;
         final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";



        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
             //   .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Class Reemider")
                .setContentText("Mark your Attendance for today");

        notificationManager.notify(NOTIFICATION_ID, builder.build());*/


       /* Intent myIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Class Reminder")
                .setContentText("Mark Your Attendance")

                .setVibrate(new long[] { 0,1000,1000, 1000, 1000 })
                .setLights(Color.YELLOW, 3000, 3000);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        String channelId = "default_channel_id";
        String channelDescription = "Default Channel";
//Check if notification channel exists and if not create one
            NotificationManager mNotificationManagerO = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = mNotificationManagerO.getNotificationChannel(channelId);
            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelId, channelDescription, importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                mNotificationManagerO.createNotificationChannel(notificationChannel);
            }
            mNotificationManagerO.notify();
        }
    }*/
    }



