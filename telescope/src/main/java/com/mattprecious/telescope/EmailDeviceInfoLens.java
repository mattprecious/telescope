package com.mattprecious.telescope;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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
  private final String versionCode;

  /**
   * @deprecated Use {@link #EmailDeviceInfoLens(Context, String, String...)} or {@link
   * #EmailDeviceInfoLens(Context, String, String, int, String...)}.
   */
  @Deprecated
  public EmailDeviceInfoLens(Context context, String[] addresses, String subject, String version,
      int versionCode) {
    this(context, subject, version, versionCode, addresses);
  }

  public EmailDeviceInfoLens(Context context, String subject, String... addresses) {
    super(context, subject, addresses);
    this.context = context;

    PackageInfo packageInfo = null;
    try {
      packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      Log.e(TAG, "Unable to get app info");
    }

    if (packageInfo == null) {
      version = "0";
      versionCode = String.valueOf(0);
    } else {
      version = packageInfo.versionName;
      versionCode = String.valueOf(packageInfo.versionCode);
    }
  }

  public EmailDeviceInfoLens(Context context, String subject, String version, int versionCode,
      String... addresses) {
    super(context, subject, addresses);
    this.context = context;
    this.version = version;
    this.versionCode = String.valueOf(versionCode);
  }

  @Override protected String getBody() {
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    String densityBucket = getDensityString(dm);

    Map<String, String> info = new LinkedHashMap<>();
    info.put("Version", version);
    info.put("Version code", versionCode);
    info.put("Make", Build.MANUFACTURER);
    info.put("Model", Build.MODEL);
    info.put("Resolution", dm.heightPixels + "x" + dm.widthPixels);
    info.put("Density", dm.densityDpi + "dpi (" + densityBucket + ")");
    info.put("Release", Build.VERSION.RELEASE);
    info.put("API", String.valueOf(Build.VERSION.SDK_INT));
    info.putAll(getInfo());

    StringBuilder builder = new StringBuilder();
    for (Map.Entry entry : info.entrySet()) {
      builder.append(entry.getKey()).append(": ").append(entry.getValue()).append('\n');
    }

    return builder.append("-------------------\n\n").toString(); //
  }

  /**
   * Pairs of additional data to be included in the email body. Called every time a new email is
   * created.
   */
  protected Map<String, String> getInfo() {
    return Collections.emptyMap();
  }

  public static String getDensityString(DisplayMetrics displayMetrics) {
    switch (displayMetrics.densityDpi) {
      case DisplayMetrics.DENSITY_LOW:
        return "ldpi";
      case DisplayMetrics.DENSITY_MEDIUM:
        return "mdpi";
      case DisplayMetrics.DENSITY_TV:
        return "tvdpi";
      case DisplayMetrics.DENSITY_HIGH:
        return "hdpi";
      case DisplayMetrics.DENSITY_260:
      case DisplayMetrics.DENSITY_280:
      case DisplayMetrics.DENSITY_300:
      case DisplayMetrics.DENSITY_XHIGH:
        return "xhdpi";
      case DisplayMetrics.DENSITY_340:
      case DisplayMetrics.DENSITY_360:
      case DisplayMetrics.DENSITY_400:
      case DisplayMetrics.DENSITY_420:
      case DisplayMetrics.DENSITY_XXHIGH:
        return "xxhdpi";
      case DisplayMetrics.DENSITY_560:
      case DisplayMetrics.DENSITY_XXXHIGH:
        return "xxxhdpi";
      default:
        return "unknown";
    }
  }
}
