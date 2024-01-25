package com.mattprecious.telescope;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

import static android.os.Build.VERSION_CODES.Q;

@TargetApi(Q)
public class TelescopeProjectionService extends Service {
  public static final String EXTRA_DATA = "data";
  public static final String NOTIFICATION_CHANNEL_ID = "Telescope Notifications";
  public static final int SERVICE_ID = NOTIFICATION_CHANNEL_ID.hashCode();

  public static String getStartedBroadcastAction(Context context) {
    return context.getPackageName() + ".telescope.SERVICE_STARTED";
  }

  @Override public void onCreate() {
    super.onCreate();

    createNotificationChannel();
    startForeground(
        SERVICE_ID,
        new Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.telescope_service)
            .build()
    );
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (!intent.hasExtra(EXTRA_DATA)) {
        throw new IllegalArgumentException("Service was started without extra: " + EXTRA_DATA);
    }

    Intent broadcastIntent = new Intent(getStartedBroadcastAction(this));
    broadcastIntent.putExtra(EXTRA_DATA, (Intent) intent.getParcelableExtra(EXTRA_DATA));
    sendBroadcast(broadcastIntent);

    return super.onStartCommand(broadcastIntent, flags, startId);
  }

  private void createNotificationChannel() {
    NotificationChannel serviceChannel = new NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        "Telescope",
        NotificationManager.IMPORTANCE_MIN
    );

    NotificationManager notificationManager = getSystemService(NotificationManager.class);
    notificationManager.createNotificationChannel(serviceChannel);
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }
}
