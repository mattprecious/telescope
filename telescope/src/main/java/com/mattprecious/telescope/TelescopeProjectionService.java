package com.mattprecious.telescope;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import static android.os.Build.VERSION_CODES.Q;

@TargetApi(Q)
public class TelescopeProjectionService extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "Telescope Notifications";
    public static final int SERVICE_ID = NOTIFICATION_CHANNEL_ID.hashCode();

    @Override public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        startForeground(
                SERVICE_ID,
                new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).build()
        );
    }

    private void createNotificationChannel() {
        NotificationChannel serviceChannel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Telescope Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(serviceChannel);
    }


    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

}
