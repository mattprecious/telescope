package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.mattprecious.telescope.EmailDeviceInfoLens;
import com.mattprecious.telescope.TelescopeLayout;
import com.mattprecious.telescope.sample.R;

public class SampleEmailDeviceInfoView extends FrameLayout {
  public SampleEmailDeviceInfoView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();

    TelescopeLayout telescopeView = findViewById(R.id.telescope);
    telescopeView.setLens(new EmailDeviceInfoLens(getContext(), "Bug report", "bugs@example.com"));
  }
}
