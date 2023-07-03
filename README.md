[![Build Status](https://travis-ci.com/SanojPunchihewa/InAppUpdater.svg?branch=master)](https://travis-ci.com/SanojPunchihewa/InAppUpdater)
[![](https://jitpack.io/v/rahul-mobiledev/appcenter-library.svg)](https://jitpack.io/#rahul-mobiledev/appcenter-library)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/SanojPunchihewa/InAppUpdater/blob/master/LICENSE)

<h1 align="center">App Update Center</h1>
<p align="center">Android Library to easily implement in-app updates</p>

## :pencil2: Usage

### Step 1: Add it in your root build.gradle
```Gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
### Step 2: Add the dependency
```Gradle
dependencies {
    implementation 'com.github.rahul-mobiledev:appcenter-library:1.0.2'
}
```

### Step 3: Initialize the UpdateManager
Initialize the UpdateManager in your `onCreate` method of the Activity
```kotlin
    // Initialize the Update Manager with the Activity and the Update Mode
     AppUpdateCenter.getInstance()
            .setApiKey(this@MainActivity, "64a27c4d5f91b851c3f609e9")
            .listen(object : AppUpdateCallback {
                override fun onFinishCheckForUpdate() {
                // move to other screen if needed
                }
            })
```

**Update Mode**

There are two modes
* Flexible(`FLEXIBLE`) *(default)* - User can use the app during update download, installation and restart needs to be triggered by user
* Immediate(`IMMEDIATE`) - User will be blocked until download and installation is finished, restart is triggered automatically

**Additionally** You have to setup an account and project to website https://appcenter-1.web.app/ and get API key of project from Detail Screen.

## :exclamation: Troubleshoot
- In-app updates works only with devices running **Android 5.0 (API level 21) or higher**

## :open_hands: Contributions
Any contributions are welcome!

## :page_facing_up: License
```
MIT License

Copyright (c) 2019 Sanoj Punchihewa

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
