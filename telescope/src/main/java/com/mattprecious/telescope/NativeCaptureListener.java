package com.mattprecious.telescope;

import android.graphics.Bitmap;

public interface NativeCaptureListener {
    /**
     * Called when the image capturing out of the {@link android.media.projection.MediaProjection} starts.
     * This is a good moment to run initialization routines and animation starting.
     */
    void onImageCaptureStarted();

    /**
     * Called when the {@link android.media.projection.MediaProjection} data is
     * stored into the in-memory buffer
     * in form of an {@link android.media.Image}.
     */
    void onImageCaptureComplete();

    /**
     * An error occurred while extracting the data from the {@link android.media.projection.MediaProjection}.
     */
    void onImageCaptureError();

    /**
     * Called when the {@link android.media.Image} is about to be dumped into a {@link android.graphics.Bitmap}.
     */
    void onBitmapPreparationStarted();

    /**
     * Called after the {@link android.media.Image} buffer is dumped and the new {@link android.graphics.Bitmap} is cropped.
     *
     * @param bitmap the cropped {@link android.graphics.Bitmap} to be further processed
     */
    void onBitmapReady(final Bitmap bitmap);

    /**
     * Hook to allow for further resource releasing. Notice this method is destructive,
     * once called, no further operations should be done on this instance of {@link NativeCaptureListener}.
     */
    void dispose();
}
