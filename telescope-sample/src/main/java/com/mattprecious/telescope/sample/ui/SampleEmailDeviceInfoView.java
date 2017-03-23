package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mattprecious.telescope.EmailDeviceInfoLens;
import com.mattprecious.telescope.TelescopeLayout;
import com.mattprecious.telescope.sample.R;

public class SampleEmailDeviceInfoView extends FrameLayout {
  @BindView(R.id.telescope) TelescopeLayout telescopeView;

  public SampleEmailDeviceInfoView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    telescopeView.setLens(new EmailDeviceInfoLens(getContext(), "Bug report", "bugs@blackhole.io"));
  }
}
