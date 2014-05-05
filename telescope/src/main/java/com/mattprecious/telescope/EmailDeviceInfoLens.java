package com.mattprecious.telescope;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * A basic {@link Lens} implementation that composes an email with the provided addresses and
 * subject (optional). The body will be pre-populated with app and device info:
 *
 * <ul>
 *   <li>App version</li>
 *   <li>App version code</li>
 *   <li>Device manufacturer</li>
 *   <li>Device model</li>
 *   <li>Screen resolution</li>
 *   <li>Screen density</li>
 *   <li>Android version</li>
 *   <li>Android API level</li>
 * </ul>
 */
public class EmailDeviceInfoLens extends EmailLens {
  private static final String TAG = "EmailDeviceInfoLens";

  private final Context context;
  private final String version;
  private final int versionCode;
  private final String body;

  public EmailDeviceInfoLens(Context context, String[] addresses, String subject) {
    super(context, addresses, subject);
    this.context = context;

    PackageInfo packageInfo = null;
    try {
      packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      Log.e(TAG, "Unable to get app info");
    }

    if (packageInfo == null) {
      version = "0";
      versionCode = 0;
    } else {
      version = packageInfo.versionName;
      versionCode = packageInfo.versionCode;
    }

    this.body = makeBody();
  }

  @Override protected String getBody() {
    return body;
  }

  private String makeBody() {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    String densityBucket = getDensityString(displayMetrics);

    return "\n\n--------------------\n" //
        + "Version: " + version + '\n' //
        + "Version code: " + versionCode + '\n' //
        + "Make: " + Build.MANUFACTURER + '\n' //
        + "Model: " + Build.MODEL + '\n' //
        + "Resolution: " + displayMetrics.heightPixels + 'x' + displayMetrics.widthPixels + '\n' //
        + "Density: " + displayMetrics.densityDpi + "dpi (" + densityBucket + ")\n" //
        + "Release: " + Build.VERSION.RELEASE + '\n' //
        + "API: " + Build.VERSION.SDK_INT + '\n'; //
  }

  public static String getDensityString(DisplayMetrics displayMetrics) {
    switch (displayMetrics.densityDpi) {
      case DisplayMetrics.DENSITY_LOW:
        return "ldpi";
      case DisplayMetrics.DENSITY_MEDIUM:
        return "mdpi";
      case DisplayMetrics.DENSITY_HIGH:
        return "hdpi";
      case DisplayMetrics.DENSITY_XHIGH:
        return "xhdpi";
      case DisplayMetrics.DENSITY_XXHIGH:
        return "xxhdpi";
      case DisplayMetrics.DENSITY_XXXHIGH:
        return "xxxhdpi";
      case DisplayMetrics.DENSITY_TV:
        return "tvdpi";
      default:
        return "unknown";
    }
  }
}
