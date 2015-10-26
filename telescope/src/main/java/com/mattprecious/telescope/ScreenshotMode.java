package com.mattprecious.telescope;

// Keep in sync with attrs.xml.
public enum ScreenshotMode {
  /**
   * Takes a native screenshot from the OS which includes system bars and keyboards.
   *
   * <p>
   * System screenshots are only available on API 21+. Telescope will automatically fall back to
   * {@link #CANVAS} mode on earlier platforms or if screen recording permission was not granted.
   * {@link #CANVAS} will also be used if Telescope has been configured to screenshot children only
   * or if a different target view has been specified.
   *
   * <p>
   * <i>
   * Requires the
   * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE WRITE_EXTERNAL_STORAGE} permission
   * on API 18 and below.
   * </i>
   */
  SYSTEM,
  /**
   * Uses the drawing cache of the target view to create a screenshot.
   *
   * <p>
   * <i>
   * Requires the
   * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE WRITE_EXTERNAL_STORAGE} permission
   * on API 18 and below.
   * </i>
   */
  CANVAS,
  /** Do not save a screenshot. */
  NONE,
}
