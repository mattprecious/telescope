package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mattprecious.telescope.Lens;
import com.mattprecious.telescope.TelescopeLayout;
import com.mattprecious.telescope.sample.R;
import java.io.File;

public class SampleToastView extends FrameLayout {
  @BindView(R.id.telescope) TelescopeLayout telescopeView;

  public SampleToastView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    telescopeView.setLens(new Lens() {
      @Override public void onCapture(File screenshot) {
        Toast.makeText(getContext(), "Captured!", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
