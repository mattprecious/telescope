package com.mattprecious.telescope;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.WindowManager;

import java.nio.ByteBuffer;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

@TargetApi(LOLLIPOP)
class NativeScreenshotCapturer {

    private final WindowManager windowManager;
    private static Handler backgroundHandler;

    NativeScreenshotCapturer(Context context) {
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * Captures a Bitmap out of a MediaProjection, notifying the listener instance
     * for individual steps
     *
     * @param listener
     *            The listener that will be run.
     */
    void capture(final MediaProjection projection, final NativeCaptureListener listener) {
        listener.onImageCaptureStarted();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        final int width = displayMetrics.widthPixels;
        final int height = displayMetrics.heightPixels;

        @SuppressLint("WrongConstant")
        ImageReader imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2);
        Surface surface = imageReader.getSurface();

        final VirtualDisplay display =
                projection.createVirtualDisplay("telescope", width, height, displayMetrics.densityDpi,
                        DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION, surface, null, null);

        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override public void onImageAvailable(ImageReader reader) {
                Image image = null;
                Bitmap bitmap = null;

                try {
                    image = reader.acquireLatestImage();

                    listener.onImageCaptureComplete();

                    if (image == null) {
                        return;
                    }

                    listener.onCaptureBitmapPreparationStarted();

                    Image.Plane[] planes = image.getPlanes();
                    ByteBuffer buffer = planes[0].getBuffer();
                    int pixelStride = planes[0].getPixelStride();
                    int rowStride = planes[0].getRowStride();
                    int rowPadding = rowStride - pixelStride * width;

                    bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height,
                            Bitmap.Config.ARGB_8888);
                    bitmap.copyPixelsFromBuffer(buffer);

                    // Trim the screenshot to the correct size.
                    final Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);

                    listener.onBitmapReady(croppedBitmap);
                } catch (UnsupportedOperationException e) {
                    listener.onImageCaptureError(e);
                } finally {
                    if (bitmap != null) {
                        bitmap.recycle();
                    }

                    if (image != null) {
                        image.close();
                    }

                    reader.close();
                    display.release();
                    projection.stop();
                }
            }
        }, getBackgroundHandler());
    }

    private static Handler getBackgroundHandler() {
        if (backgroundHandler == null) {
            HandlerThread backgroundThread =
                    new HandlerThread("telescope", Process.THREAD_PRIORITY_BACKGROUND);
            backgroundThread.start();
            backgroundHandler = new Handler(backgroundThread.getLooper());
        }

        return backgroundHandler;
    }
}
