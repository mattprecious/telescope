package com.mattprecious.telescope;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.io.File;
import java.util.Arrays;

/**
 * <p>
 * A basic {@link Lens} implementation that composes an email with the provided addresses and
 * subject (optional).
 * </p>
 *
 * <p>The {@link #getBody()} method can be overridden to pre-populate the body of the email.</p>
 */
public class EmailLens implements Lens {
  private final Context context;
  private final String subject;
  private final String[] addresses;

  public EmailLens(Context context, String[] addresses, String subject) {
    this.context = context;
    this.addresses = addresses == null ? null : Arrays.copyOf(addresses, addresses.length);
    this.subject = subject;
  }

  /** Create the email body. */
  protected String getBody() {
    return null;
  }

  @Override public void onCapture(File screenshot) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("message/rfc822");

    if (subject != null) {
      intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    }

    if (addresses != null) {
      intent.putExtra(Intent.EXTRA_EMAIL, addresses);
    }

    String body = getBody();
    if (body != null) {
      intent.putExtra(Intent.EXTRA_TEXT, body);
    }

    if (screenshot != null) {
      intent.setType("image/png");
      intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(screenshot));
    }

    context.startActivity(intent);
  }
}
