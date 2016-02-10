package com.mattprecious.telescope;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

/**
 * Interface definition for a callback to be invoked when additional processing on the screenshot
 * has been completed.
 */
public interface BitmapProcessorListener {
  /** Called when additional processing on the screenshot has been completed. */
  void onBitmapReady(@Nullable Bitmap screenshot);
}
