package com.mattprecious.telescope;

import android.graphics.Bitmap;

public interface NativeCaptureListener {
    void onImageCaptureStarted();
    void onImageCaptureComplete();
    void onImageCaptureError(Exception exception);
    void onCaptureBitmapPreparationStarted();
    void onBitmapReady(final Bitmap bitmap);
}
