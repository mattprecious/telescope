package com.mattprecious.telescope;

import android.content.Context;
import android.net.Uri;
import java.io.File;

public final class TelescopeFileProvider extends FileProvider {
  /**
   * Calls {@link #getUriForFile(Context, String, File)} using the correct authority for Telescope
   * screenshots.
   */
  public static Uri getUriForFile(Context context, File file) {
    return getUriForFile(context, context.getPackageName() + ".telescope.fileprovider", file);
  }
}
