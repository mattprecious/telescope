package com.mattprecious.telescope.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.mattprecious.telescope.TelescopeLayout;
import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sample_activity);

    setSupportActionBar(findViewById(R.id.toolbar));

    Adapter adapter = new Adapter(this);
    adapter.addView(R.layout.default_view, R.string.tab_default);
    adapter.addView(R.layout.device_info_view, R.string.tab_device_info);
    adapter.addView(R.layout.styled_view, R.string.tab_styled);
    adapter.addView(R.layout.children_only_view, R.string.tab_children_only);
    adapter.addView(R.layout.three_finger_view, R.string.tab_three_finger);
    adapter.addView(R.layout.other_target_view, R.string.tab_other_target);
    adapter.addView(R.layout.additional_attachment_view, R.string.tab_additional_attachment);
    adapter.addView(R.layout.maps_view, R.string.tab_maps);

    ViewPager pagerView = findViewById(R.id.pager);
    pagerView.setAdapter(adapter);

    TabLayout tabsView = findViewById(R.id.tabs);
    tabsView.setupWithViewPager(pagerView);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    TelescopeLayout.cleanUp(this);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.sample_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
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

  static class Adapter extends PagerAdapter {
    private final Context context;
    private final LayoutInflater inflater;
    private final List<Integer> layouts = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();

    public Adapter(Context context) {
      this.context = context;
      inflater = LayoutInflater.from(context);
    }

    public void addView(@LayoutRes int layoutResId, @StringRes int titleResId) {
      layouts.add(layoutResId);
      titles.add(context.getString(titleResId));
    }

    @Override public int getCount() {
      return layouts.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return titles.get(position);
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      View view = inflater.inflate(layouts.get(position), container, false);
      container.addView(view);
      return view;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View) object);
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }
  }
}
