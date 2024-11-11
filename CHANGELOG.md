Change Log
==========

Version 2.4.0 *(2024-11-11)*
----------------------------

* Use `PixelCopy` API for full-screen 'canvas' captures on API 26+. This supports hardware rendering
  so will no longer crash with hardware bitmaps (#142) and is also able to capture additional
  details like shadows from elevation.

Version 2.3.0 *(2024-01-25)*
----------------------------

* Bump compile/target SDK to 34.
* Add `FOREGROUND_SERVICE_MEDIA_PROJECTION` permission.

Version 2.2.0 *(2019-10-29)*
----------------------------

* **Requires compileSdk 29**
* Start a foreground service while capturing on Android 10.
  * Icon can be overridden in your application by adding a drawable named `telescope_service`.
* Avoid activity leak in Android 10.
* Migrate to AndroidX.

Version 2.1.0 *(2016-06-01)*
----------------------------

* Moved anything that touches the disk to a background thread.
  * Note that if you override `EmailLens`' `getBody()` or `getAdditionalAttachments()`, you will now
    be called on a background thread.
* `EmailLens` now shares screenshots using a `content://` URI instead of `file://`
* Added `TelescopeFileProvider` for use in custom lenses that allows you to easily create a
`content://` URI for sharing screenshots. See `EmailLens` for an example.

Version 2.0.0 *(2016-02-10)*
----------------------------

* Screenshots will now be captured natively on API 21+. This method will capture the entire screen
including status bar, navigation bar, and any surface views that didn't work using the old method
(ex: `MapView`). The old method can be forced by setting the screenshot mode to
`ScreenshotMode.CANVAS`.
* Disabling of screenshots has been moved to `ScreenshotMode.NONE`.
* `Lens` has been changed from an interface to an abstract class.
* A new method has been added to `Lens` to optionally pre-process the screenshot before saving.
* Layout attributes are now prefixed with `telescope_` to avoid collisions.

Version 1.5.0 *(2015-09-22)*
----------------------------

* Removed the need for `WRITE_EXTERNAL_STORAGE` permission on KitKat+.
  * Screenshots are now stored in a private app directory on external storage. Ensure that you're
    calling `Telescope.cleanUp()` somewhere in your app lifecycle to remove these screenshots.
* Check for `VIBRATE` permission before attempting to vibrate.

Version 1.4.0 *(2015-02-06)*
----------------------------

* Removed override of `ViewParent#requestDisallowInterceptTouchEvent(boolean)` due to issues with
`ListView` and other views. `TelescopeLayout` seems to obey `requestDisallowInterceptTouchEvent()`
without any modifications.

Version 1.3.0 *(2014-10-07)*
----------------------------

* New: Obey `ViewParent#requestDisallowInterceptTouchEvent(boolean)`. Have your multi-touch views
call this method on their parent during touch events and Telescope will not intercept the events.
* Removed `setInterceptTouchEvents(boolean)` and `attr/interceptTouchEvents` added in 1.2.0. Use
`requestDisallowInterceptTouchEvent` instead.

Version 1.2.0 *(2014-09-24)*
----------------------------

* New: Add ability to not intercept touch events.

Version 1.1.0 *(2014-07-18)*
----------------------------

* New: Support for additional attachments in `EmailLens` and `EmailDeviceInfoLens`.
* New: Convenience constructor for `EmailDeviceInfoLens` to automatically get app version.
* Adjusted `EmailDeviceInfoLens` to add the separator and new lines below the body instead of above.
* `EmailLens` and `EmailDeviceInfoLens` now prefer addresses as varargs instead of an array.

Version 1.0.0 *(2014-05-01)*
----------------------------

Initial version.
