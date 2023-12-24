package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.util.AttributeSet;
import com.mattprecious.telescope.sample.R;

public class OtherTargetView extends SampleEmailView {
  public OtherTargetView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    // Parent handles the injection, don't bother.

    telescopeView.setScreenshotChildrenOnly(true);
    telescopeView.setScreenshotTarget(findViewById(R.id.target));
  }
}
