package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import butterknife.BindView;
import com.mattprecious.telescope.sample.R;

public class OtherTargetView extends SampleEmailView {
  @BindView(R.id.target) View targetView;

  public OtherTargetView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    // Parent handles the injection, don't bother.

    telescopeView.setScreenshotChildrenOnly(true);
    telescopeView.setScreenshotTarget(targetView);
  }
}
