package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mattprecious.telescope.EmailLens;
import com.mattprecious.telescope.TelescopeLayout;
import com.mattprecious.telescope.sample.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class SampleAdditionalAttachmentEmailView extends FrameLayout {
  private static final String SHAKESPEARE = "A glooming peace this morning with it brings;\n"
      + "The sun, for sorrow, will not show his head:\n"
      + "Go hence, to have more talk of these sad things;\n"
      + "Some shall be pardon'd, and some punished:\n"
      + "For never was a story of more woe\n"
      + "Than this of Juliet and her Romeo.";

  @BindView(R.id.telescope) TelescopeLayout telescopeView;

  public SampleAdditionalAttachmentEmailView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    File filesDir = getContext().getExternalFilesDir(null);
    final File file = new File(filesDir, "shakespeare.txt");
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(file);
      out.write(SHAKESPEARE.getBytes());
    } catch (java.io.IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException ignored) {
        }
      }
    }

    telescopeView.setLens(
        new EmailLens(getContext(), "Bug report", "bugs@blackhole.io") {
          @Override protected Set<Uri> getAdditionalAttachments() {
            // TODO: This should be using a FileProvider.
            return Collections.singleton(Uri.fromFile(file));
          }
        }
    );
  }
}
