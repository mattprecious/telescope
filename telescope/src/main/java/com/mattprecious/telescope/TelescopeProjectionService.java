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
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import static android.os.Build.VERSION_CODES.Q;
import static com.mattprecious.telescope.Preconditions.*;

@TargetApi(Q)
public class TelescopeProjectionService extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "Telescope Notifications";
    public static final int SERVICE_ID = NOTIFICATION_CHANNEL_ID.hashCode();

    public static final String RESULT_RECEIVER = "receiver";
    public static final String RESULT_EXTRA_CODE = "code";
    public static final String RESULT_EXTRA_DATA = "data";

    private MediaProjectionManager projectionManager = null;

    @Override public void onCreate() {
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

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        final ResultReceiver resultReceiver =
                intent.getParcelableExtra(TelescopeProjectionService.RESULT_RECEIVER);
        int resultCode = intent.getIntExtra(TelescopeProjectionService.RESULT_EXTRA_CODE,
                Activity.RESULT_CANCELED);
        Intent data = intent.getParcelableExtra(TelescopeProjectionService.RESULT_EXTRA_DATA);
        checkNotNull(resultReceiver, "resultReceiver == null");

        if (data == null) {
            // user did not grant screen capturing permission
            resultReceiver.send(NativeCaptureResultReceiver.RESULT_CODE_IMAGE_CAPTURE_ERROR, null);
            stopSelf();
            return START_NOT_STICKY;
        }

        final MediaProjection mediaProjection =
                projectionManager.getMediaProjection(resultCode, data);

        new NativeScreenshotCapturer(getApplicationContext()).capture(mediaProjection,
                new NativeCaptureListener() {
                    @Override public void onImageCaptureStarted() {
                        resultReceiver.send(NativeCaptureResultReceiver.RESULT_CODE_IMAGE_CAPTURE_STARTED, null);
                    }

                    @Override public void onImageCaptureComplete() {
                        resultReceiver.send(NativeCaptureResultReceiver.RESULT_CODE_IMAGE_CAPTURE_COMPLETED, null);
                    }

                    @Override public void onImageCaptureError() {
                        resultReceiver.send(NativeCaptureResultReceiver.RESULT_CODE_IMAGE_CAPTURE_ERROR, null);
                    }

                    @Override public void onBitmapPreparationStarted() {
                        resultReceiver.send(NativeCaptureResultReceiver.RESULT_CODE_BITMAP_PREP_STARTED, null);
                    }

                    @Override public void onBitmapReady(Bitmap bitmap) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(NativeCaptureResultReceiver.EXTRA_RESULT_BITMAP, bitmap);
                        resultReceiver.send(NativeCaptureResultReceiver.RESULT_CODE_BITMAP_READY, bundle);
                    }

                    @Override public void dispose() {
                        stopSelf();
                    }
                });

        return Service.START_NOT_STICKY;
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

}
