package com.mattprecious.telescope;

import java.io.File;

/**
 * Interface definition for a callback to be invoked when a capture is triggered from a
 * {@link TelescopeLayout}.
 */
public interface Lens {
  /**
   * Called when a capture is triggered.
   *
   * @param screenshot A reference to the screenshot that was captured. Can be null if screenshots
   * were disabled.
   */
  void onCapture(File screenshot);
}
