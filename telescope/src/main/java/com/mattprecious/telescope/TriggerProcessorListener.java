package com.mattprecious.telescope;

import android.support.annotation.Nullable;

public abstract class TriggerProcessorListener {
  public abstract void onTriggerReady(@Nullable ScreenshotMode screenshotMode);

  TriggerProcessorListener() {
    // No external subclasses.
  }
}
