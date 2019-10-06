package com.mattprecious.telescope;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import static android.os.Build.VERSION_CODES.Q;

@TargetApi(Q)
public class TelescopeProjectionService extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "Telescope Notifications";
    public static final int SERVICE_ID = NOTIFICATION_CHANNEL_ID.hashCode();

    public static final String RESULT_EXTRA_CODE = "code";
    public static final String RESULT_EXTRA_DATA = "data";

    private MediaProjectionManager projectionManager = null;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        startForeground(
                SERVICE_ID,
                new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).build()
        );
        projectionManager =
                (MediaProjectionManager) getApplicationContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int resultCode = intent.getIntExtra(RequestCaptureActivity.RESULT_EXTRA_CODE,
                Activity.RESULT_CANCELED);
        Intent data = intent.getParcelableExtra(RequestCaptureActivity.RESULT_EXTRA_DATA);

        final MediaProjection mediaProjection =
                projectionManager.getMediaProjection(resultCode, data);

        new NativeScreenshotCapturer(getApplicationContext()).capture(mediaProjection,
                new NativeScreenshotCapturer.Listener() {
                    @Override
                    public void onImageCaptureComplete() {
                        
                    }

                    @Override
                    public void onImageCaptureError(Exception exception) {

                    }

                    @Override
                    public void onCaptureBitmapPreparationStarted() {

                    }

                    @Override
                    public void onBitmapReady(Bitmap bitmap) {

                    }
                });

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
