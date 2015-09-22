Telescope
======

A simple tool to allow easy bug report capturing within your app.

![](images/sample.gif)



Usage
-----

Place a `TelescopeLayout` at the root of your hierarchy.

Add a callback to the view group with `setLens(Lens)`. Telescope provides two default
implementations:

* `EmailLens`: Compose an email with the provided addresses and subject (optional).
* `EmailDeviceInfoLens`: Enhances `EmailLens` by pre-populating the email body with app and device
  info

Screenshots will be stored on the external storage in `/Telescope/com.app.package/`. To have
Telescope clean up the screenshots folder, call `TelescopeLayout.cleanUp(Context)`. Ideally, this
would be called in the `onDestroy()` method of your `Activity` or `Fragment`.

If you are using the Gradle-based build system, you can wrap this view group around your activity
layouts only in the debug builds.



Permissions
-----------

Pre-KitKat, `WRITE_EXTERNAL_STORAGE` is required for saving screenshots. Screenshots can be disabled
using the configuration options below.



Configuration
-------------

The view group can be configured as follows:

* Set the number of fingers to trigger with `app:pointerCount` / `setPointerCount(int)`
* Set the progress color with `app:progressColor` / `setProgressColor(int)`
* Disable screenshots with `app:screenshot` / `setScreenshot(boolean)`
* Screenshot children only with `app:screenshotChildrenOnly` / `setScreenshotChildrenOnly(boolean)`
* Set the screenshot target with`setScreenshotTarget(View)`
* Disable vibration with `app:vibrate` / `setVibrate(boolean)`



Download
--------

Download [the latest JAR][1] or grab via Gradle:
```groovy
compile 'com.mattprecious.telescope:telescope:1.5.0@aar'
```
or Maven:
```xml
<dependency>
  <groupId>com.mattprecious.telescope</groupId>
  <artifactId>telescope</artifactId>
  <version>1.5.0</version>
  <type>apklib</type>
</dependency>
```


License
--------

    Copyright 2014 Matthew Precious

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.mattprecious.telescope&a=telescope&v=LATEST
