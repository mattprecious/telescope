Change Log
==========

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
