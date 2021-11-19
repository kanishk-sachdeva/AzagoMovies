# AzagoMovies

[![Platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Gradle Version](https://img.shields.io/badge/gradle-4.0-green.svg)](https://docs.gradle.org/current/release-notes)
[![Awesome Badge](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://java-lang.github.io/awesome-java)

🎥 <b>AzagoMovies</b> is an android streaming application for <i>movies, web series and tv shows</i> such as Netflix, Zee5 and many much.
<br><br>This app is built in <strong>Android Studio</strong> using <i>ExoPlayer, Firebase and Bubble Navigation(for navbar)</i>

## Gradle

```
android {
    compileSdkVersion 30
    buildToolsVersion "30.0.0"

    defaultConfig {
        applicationId "com.kanucreator.azagomovies"
        minSdkVersion 23
        targetSdkVersion 30
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.2'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.viewpager2:viewpager2:1.0.0'
    implementation 'com.google.android.material:material:1.2.1'
    testImplementation 'junit:junit:4.13'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
    implementation platform('com.google.firebase:firebase-bom:25.12.0')
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.firebase:firebase-firestore'
    implementation 'com.google.firebase:firebase-crashlytics'
    implementation 'com.gauravk.bubblenavigation:bubblenavigation:1.0.7'
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation 'me.spark:submitbutton:1.0.1'
    implementation 'com.android.volley:volley:1.1.0'
    implementation 'com.google.firebase:firebase-firestore'
    implementation "com.airbnb.android:lottie:3.4.4"
    implementation 'com.google.android.exoplayer:exoplayer:2.12.0'
    implementation 'com.google.android.exoplayer:exoplayer-ui:2.12.0'
    implementation 'com.google.android.exoplayer:exoplayer-core:2.12.0'
    implementation 'com.google.android.exoplayer:exoplayer-dash:2.12.0'
    implementation 'com.google.android.exoplayer:extension-mediasession:2.12.0'
    implementation 'com.andrognito.flashbar:flashbar:1.0.3'

}
```

## Demo Application

<p align="center">
    <a href="https://github.com/kanishk-sachdeva/AzagoMovies/raw/master/AzagoMovies.apk">
        <img width="250px" height="auto" src="https://cdn.pixabay.com/photo/2013/07/13/01/16/download-155425_1280.png"></img>
    </a>
 </p>
    
## Application Screenshots

<table style="width:100%">
  <tr>
    <th>Example 1</th>
    <th>Example 2</th>
  </tr>
  <tr>
    <td><img src="screenshots/1.gif"/></td>
    <td><img src="screenshots/2.jpg"/></td>
  </tr>
  <tr>
    <th>Example 3</th>
    <th>Example 4</th>
  </tr>
  <tr>
    <td><img src="screenshots/3.jpg"/></td>
    <td><img src="screenshots/4.jpg"/></td>
  </tr>
</table>
    
    
# License

```
    Copyright (C) Kanishk Sachdeva

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```    
