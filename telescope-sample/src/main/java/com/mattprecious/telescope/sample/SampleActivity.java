package com.mattprecious.telescope.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.mattprecious.telescope.TelescopeLayout;
import com.mattprecious.telescope.sample.ui.widget.SlidingTabLayout;

public class SampleActivity extends Activity {
  @InjectView(R.id.tabs) SlidingTabLayout tabView;
  @InjectView(R.id.pager) ViewPager pagerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sample_activity);
    ButterKnife.inject(this);

    getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);

    pagerView.setAdapter(new Adapter());
    tabView.setViewPager(pagerView);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    TelescopeLayout.cleanUp(this);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.sample_menu, menu);
    return true;
  }

  @Override public boolean onMenuItemSelected(int featureId, MenuItem item) {
    if (item.getItemId() == R.id.menu_attributions) {
      showAttributionsDialog();
      return true;
    }

    return false;
  }

  private void showAttributionsDialog() {
    TextView attributionsView =
        (TextView) getLayoutInflater().inflate(R.layout.attributions_view, null);
    attributionsView.setText(Html.fromHtml(getString(R.string.attributions)));
    attributionsView.setMovementMethod(new LinkMovementMethod());

    new AlertDialog.Builder(this).setTitle("Attributions")
        .setView(attributionsView)
        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        })
        .show();
  }

  private class Adapter extends PagerAdapter {
    private static final int PAGE_COUNT = 6;
    private static final int PAGE_DEFAULT = 0;
    private static final int PAGE_DEVICE_INFO = 1;
    private static final int PAGE_STYLED = 2;
    private static final int PAGE_CHILDREN_ONLY = 3;
    private static final int PAGE_THREE_FINGER = 4;
    private static final int PAGE_OTHER_TARGET = 5;

    @Override public int getCount() {
      return PAGE_COUNT;
    }

    @Override public CharSequence getPageTitle(int position) {
      switch (position) {
        case PAGE_DEFAULT:
          return "Default";
        case PAGE_DEVICE_INFO:
          return "Device Info";
        case PAGE_STYLED:
          return "Styled";
        case PAGE_CHILDREN_ONLY:
          return "Children Only";
        case PAGE_THREE_FINGER:
          return "Three Finger";
        case PAGE_OTHER_TARGET:
          return "Other Target";
        default:
          throw new IllegalArgumentException("Unknown position: " + position);
      }
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      View view;
      switch (position) {
        case PAGE_DEFAULT:
          view = getLayoutInflater().inflate(R.layout.default_view, container, false);
          break;
        case PAGE_DEVICE_INFO:
          view = getLayoutInflater().inflate(R.layout.device_info_view, container, false);
          break;
        case PAGE_STYLED:
          view = getLayoutInflater().inflate(R.layout.styled_view, container, false);
          break;
        case PAGE_CHILDREN_ONLY:
          view = getLayoutInflater().inflate(R.layout.children_only_view, container, false);
          break;
        case PAGE_THREE_FINGER:
          view = getLayoutInflater().inflate(R.layout.three_finger_view, container, false);
          break;
        case PAGE_OTHER_TARGET:
          view = getLayoutInflater().inflate(R.layout.other_target_view, container, false);
          break;
        default:
          throw new IllegalArgumentException("Unknown position: " + position);
      }

      container.addView(view);
      return view;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View) object);
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return object == view;
    }
  }
}
