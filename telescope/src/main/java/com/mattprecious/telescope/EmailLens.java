package com.mattprecious.telescope;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

  /** @deprecated Use {@link #EmailLens(Context, String, String...)}. */
  @Deprecated
  public EmailLens(Context context, String[] addresses, String subject) {
    this(context, subject, addresses);
  }

  public EmailLens(Context context, String subject, String... addresses) {
    this.context = context;
    this.addresses = addresses == null ? null : addresses.clone();
    this.subject = subject;
  }

  /** Create the email body. */
  protected String getBody() {
    return null;
  }

  @Override public void onCapture(File screenshot) {
    Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
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

    ArrayList<Uri> attachments = new ArrayList<>();
    attachments.addAll(getAdditionalAttachments());

    if (screenshot != null) {
      attachments.add(Uri.fromFile(screenshot));
    }

    if (!attachments.isEmpty()) {
      intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, attachments);
    }

    context.startActivity(intent);
  }

  protected Set<Uri> getAdditionalAttachments() {
    return new HashSet<>();
  }
}
