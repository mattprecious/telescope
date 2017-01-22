package com.mattprecious.telescope;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

/**
 * Interface definition for a callback to be invoked when additional processing on the screenshot
 * has been completed.
 */
public abstract class BitmapProcessorListener {
  /** Called when additional processing on the screenshot has been completed. */
  public abstract void onBitmapReady(@Nullable Bitmap screenshot);

  BitmapProcessorListener() {
    // No external subclasses.
  }
}
