package com.mattprecious.telescope.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.mattprecious.telescope.TelescopeLayout;

public class SampleActivity extends Activity {
  private static final int PAGE_DEFAULT = 0;
  private static final int PAGE_DEVICE_INFO = 1;
  private static final int PAGE_STYLED = 2;
  private static final int PAGE_CHILDREN_ONLY = 3;
  private static final int PAGE_THREE_FINGER = 4;
  private static final int PAGE_OTHER_TARGET = 5;

  @InjectView(R.id.drawer) DrawerLayout drawerView;
  @InjectView(R.id.drawer_list) ListView drawerListView;
  @InjectView(R.id.content) ViewGroup contentView;

  private ActionBarDrawerToggle drawerToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sample_activity);
    ButterKnife.inject(this);

    getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);

    drawerToggle =
        new ActionBarDrawerToggle(this, drawerView, R.drawable.ic_drawer, R.string.drawer_open,
            R.string.drawer_close);
    drawerView.setDrawerListener(drawerToggle);
    drawerView.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

    drawerListView.setAdapter(
        ArrayAdapter.createFromResource(this, R.array.navigation, R.layout.drawer_item_view));

    onItemClick(0);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    TelescopeLayout.cleanUp(this);
  }

  @OnItemClick(R.id.drawer_list) void onItemClick(int position) {
    View view;
    switch (position) {
      case PAGE_DEFAULT:
        view = getLayoutInflater().inflate(R.layout.default_view, contentView, false);
        break;
      case PAGE_DEVICE_INFO:
        view = getLayoutInflater().inflate(R.layout.device_info_view, contentView, false);
        break;
      case PAGE_STYLED:
        view = getLayoutInflater().inflate(R.layout.styled_view, contentView, false);
        break;
      case PAGE_CHILDREN_ONLY:
        view = getLayoutInflater().inflate(R.layout.children_only_view, contentView, false);
        break;
      case PAGE_THREE_FINGER:
        view = getLayoutInflater().inflate(R.layout.three_finger_view, contentView, false);
        break;
      case PAGE_OTHER_TARGET:
        view = getLayoutInflater().inflate(R.layout.other_target_view, contentView, false);
        break;
      default:
        throw new IllegalArgumentException("Unknown position: " + position);
    }

    drawerListView.setItemChecked(position, true);
    swapView(view);
    drawerView.closeDrawer(drawerListView);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.sample_menu, menu);
    return true;
  }

  @Override public boolean onMenuItemSelected(int featureId, MenuItem item) {
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    } else if (item.getItemId() == R.id.menu_attributions) {
      showAttributionsDialog();
      return true;
    }

    return false;
  }

  private void swapView(View view) {
    contentView.removeAllViews();
    contentView.addView(view);
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
}
