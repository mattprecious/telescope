package com.mattprecious.telescope.sample.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.mattprecious.telescope.BitmapProcessorListener;
import com.mattprecious.telescope.EmailLens;
import com.mattprecious.telescope.TelescopeLayout;
import com.mattprecious.telescope.sample.R;

public class SampleMapsView extends FrameLayout {
  @BindView(R.id.telescope) TelescopeLayout telescopeView;
  @BindView(R.id.map) MapView mapView;

  public SampleMapsView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    // Necessary to make MapView work.
    mapView.onCreate(null);
    mapView.onResume();

    telescopeView.setLens(new MapsEmailLens(getContext(), mapView));
  }

  static class MapsEmailLens extends EmailLens {

    final MapView mapView;

    public MapsEmailLens(Context context, MapView mapView) {
      super(context, "Bug report", "bugs@blackhole.io");
      this.mapView = mapView;
    }

    @Override public void onCapture(final Bitmap originalBitmap,
        @NonNull final BitmapProcessorListener bitmapProcessorListener) {
      mapView.getMapAsync(new OnMapReadyCallback() {
        @Override public void onMapReady(GoogleMap googleMap) {
          googleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override public void onSnapshotReady(Bitmap snapshot) {
              int[] location = new int[2];
              mapView.getLocationOnScreen(location);

              Bitmap bmOverlay = mergeBitmaps(originalBitmap, snapshot, location);

              bitmapProcessorListener.onBitmapReady(bmOverlay);
            }
          });
        }
      });
    }

    @NonNull private Bitmap mergeBitmaps(Bitmap background, Bitmap overlay, int[] overlayLocation) {
      final int width = background.getWidth();
      final int height = background.getHeight();
      int left = overlayLocation[0];
      int top = overlayLocation[1];

      Bitmap bmOverlay = Bitmap.createBitmap(width, height, background.getConfig());
      Canvas canvas = new Canvas(bmOverlay);

      Paint paint = new Paint();
      paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

      canvas.drawBitmap(background, 0, 0, paint);
      canvas.drawBitmap(overlay, null,
          new Rect(left, top, left + overlay.getWidth(), top + overlay.getHeight()), paint);
      return bmOverlay;
    }
  }
}
