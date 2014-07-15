package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.mattprecious.telescope.EmailLens;
import com.mattprecious.telescope.TelescopeLayout;
import com.mattprecious.telescope.sample.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SampleAdditionalAttachmentEmailView extends FrameLayout {
  private static final String SHAKESPEARE = "A glooming peace this morning with it brings;\n"
      + "The sun, for sorrow, will not show his head:\n"
      + "Go hence, to have more talk of these sad things;\n"
      + "Some shall be pardon'd, and some punished:\n"
      + "For never was a story of more woe\n"
      + "Than this of Juliet and her Romeo.";

  @InjectView(R.id.telescope) TelescopeLayout telescopeView;

  public SampleAdditionalAttachmentEmailView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);

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
        new EmailLens(getContext(), new String[] { "bugs@blackhole.io" }, "Bug report") {
          @Override protected Set<Uri> getAdditionalAttachments() {
            return Collections.singleton(Uri.fromFile(file));
          }
        }
    );
  }
}
