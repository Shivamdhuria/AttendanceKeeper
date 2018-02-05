package com.elixer.attendancekeeper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Admin on 1/26/2018.
 */

public class NotificationPublisher extends BroadcastReceiver {
    @RequiresApi(api = 26)
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
        //   Log.e("show Notification","show Notification");
        // Toast.makeText(getContext(),"Alarm sent",Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = 26)
    private void showNotification(Context context) {
        int NOTIFICATION_ID = 1;
        String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

        Log.e("show Notification", "show Notification");
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire(30000);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// The id of the channel.
        String id = "my_channel_01";
// The user-visible name of the channel.
        CharSequence name = "Reminder";
// The user-visible description of the channel.
        String description = "Reminder";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
// Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mNotificationManager.createNotificationChannel(mChannel);


    }
}

