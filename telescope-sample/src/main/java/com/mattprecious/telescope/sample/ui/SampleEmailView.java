package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mattprecious.telescope.EmailLens;
import com.mattprecious.telescope.TelescopeLayout;
import com.mattprecious.telescope.sample.R;

public class SampleEmailView extends FrameLayout {
  @Bind(R.id.telescope) TelescopeLayout telescopeView;

  public SampleEmailView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    telescopeView.setLens(new EmailLens(getContext(), "Bug report", "bugs@blackhole.io"));
  }
}
