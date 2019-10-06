package com.mattprecious.telescope;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import static android.os.Build.VERSION_CODES.Q;

@TargetApi(Q)
public class TelescopeProjectionService extends Service {
    public static final String RESULT_EXTRA_CODE = "code";
    public static final String RESULT_EXTRA_DATA = "data";

    private MediaProjectionManager projectionManager = null;

    @Override
    public void onCreate() {
        super.onCreate();

        projectionManager =
                (MediaProjectionManager) getApplicationContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int resultCode = intent.getIntExtra(RequestCaptureActivity.RESULT_EXTRA_CODE,
                Activity.RESULT_CANCELED);
        Intent data = intent.getParcelableExtra(RequestCaptureActivity.RESULT_EXTRA_DATA);

        final MediaProjection mediaProjection =
                projectionManager.getMediaProjection(resultCode, data);
        
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
