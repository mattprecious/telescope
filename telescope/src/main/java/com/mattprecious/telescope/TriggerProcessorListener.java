package com.mattprecious.telescope;

public abstract class TriggerProcessorListener {
  public abstract void onTriggerReady();

  TriggerProcessorListener() {
    // No external subclasses.
  }
}
