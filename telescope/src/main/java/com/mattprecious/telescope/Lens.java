package com.mattprecious.telescope;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Interface definition for a callback to be invoked when a capture is triggered from a
 * {@link TelescopeLayout}.
 */
public abstract class Lens {

  /**
   * Called when a capture is triggered but not saved to a File yet. This is for processing
   * {@link Bitmap} object before saving. The default implementation does nothing and just returns
   * the original Bitmap to the {@link BitmapProcessorListener}
   *
   * @param screenshot A reference to the screenshot that was captured. Can be null if screenshots
   * were disabled.
   * @param listener {@link BitmapProcessorListener} reference to be used when the possible
   * processes to the original Bitmap are finished.
   */
  public void onCapture(Bitmap screenshot, BitmapProcessorListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("BitmapProcessorListener cannot be null");
    }
    listener.onBitmapReady(screenshot);
  }

  /**
   * Called when a capture is triggered and saved to a {@link File}.
   *
   * @param screenshot A reference to the screenshot that was captured. Can be null if screenshots
   * were disabled.
   */
  public abstract void onCapture(File screenshot);
}
