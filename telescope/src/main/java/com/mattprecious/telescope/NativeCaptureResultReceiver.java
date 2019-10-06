package com.mattprecious.telescope;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;

public class NativeCaptureResultReceiver extends ResultReceiver {

    private NativeCaptureListener listener;

    NativeCaptureResultReceiver() {
        super(new Handler(Looper.getMainLooper()));
    }

    void setReceiver(NativeCaptureListener nativeCaptureListener) {
        listener = nativeCaptureListener;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
    }

}
