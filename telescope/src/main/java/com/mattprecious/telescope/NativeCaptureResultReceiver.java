package com.mattprecious.telescope;

import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;

abstract class NativeCaptureResultReceiver extends ResultReceiver {

    final static int RESULT_CODE_IMAGE_CAPTURE_STARTED = 127;
    static final int RESULT_CODE_IMAGE_CAPTURE_COMPLETED = 128;
    static final int RESULT_CODE_IMAGE_CAPTURE_ERROR = 129;
    static final int RESULT_CODE_BITMAP_PREP_STARTED = 130;
    static final int RESULT_CODE_BITMAP_READY = 131;
    static final String EXTRA_RESULT_BITMAP = "CapturedBitmap";

    NativeCaptureResultReceiver() {
        super(new Handler(Looper.getMainLooper()));
    }
}
