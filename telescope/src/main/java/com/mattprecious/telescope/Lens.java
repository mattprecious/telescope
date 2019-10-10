package com.mattprecious.telescope;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;

/**
 * Interface definition for a callback to be invoked when a capture is triggered from a
 * {@link TelescopeLayout}.
 */
public abstract class Lens {

  /**
   * Called when a capture is triggered but not yet saved to a {@link File}, enabling additional
   * processing before saving. The default implementation immediately calls the {@code listener}
   * with the original screenshot.
   *
   * @param screenshot A reference to the screenshot that was captured. Can be null if screenshots
   * were disabled.
   * @param listener callback for when additional processing has been completed. This listener must
   * be called for the screenshot to be saved to disk.
   */
  public void onCapture(@Nullable Bitmap screenshot, @NonNull BitmapProcessorListener listener) {
    listener.onBitmapReady(screenshot);
  }

  /**
   * Called when a capture is triggered and saved to a {@link File}.
   *
   * @param screenshot A reference to the screenshot that was captured. Can be null if screenshots
   * were disabled.
   */
  public abstract void onCapture(@Nullable File screenshot);
}
