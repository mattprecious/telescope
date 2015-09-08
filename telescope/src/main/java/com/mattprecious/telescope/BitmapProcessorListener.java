package com.mattprecious.telescope;

import android.graphics.Bitmap;

/**
 * Interface definition for a callback to be invoked when a custom process on the {@link Bitmap}
 * reference to the original screenshot finishes.
 */
public interface BitmapProcessorListener {

    /**
     * Called when the custom process of the original {@link Bitmap} finishes.
     *
     * @param screenshot A new {@link Bitmap} object to use and save.
     */
    void onBitmapReady(Bitmap screenshot);
}
