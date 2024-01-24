package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.mattprecious.telescope.EmailLens;
import com.mattprecious.telescope.TelescopeLayout;
import com.mattprecious.telescope.sample.R;

public class SampleEmailView extends FrameLayout {
  protected TelescopeLayout telescopeView;

  public SampleEmailView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    telescopeView = findViewById(R.id.telescope);
    telescopeView.setLens(new EmailLens(getContext(), "Bug report", "bugs@example.com"));
  }
}
