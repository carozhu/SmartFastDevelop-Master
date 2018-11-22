#### ERROR 1 Invoke-customs are only supported starting with Android O (--min-api 26)
```
出现以上错误时，尝试添加以下配置进行sync
compileOptions {
   sourceCompatibility JavaVersion.VERSION_1_8
   targetCompatibility JavaVersion.VERSION_1_8
}
```

## ERROR 2
```
Manifest merger failed : Attribute application@appComponentFactory value=(android.support.v4.app.CoreComponentFactory) from [com.android.support:support-compat:28.0.0] AndroidManifest.xml:22:18-91
is also present at [androidx.core:core:1.0.0] AndroidManifest.xml:22:18-86 value=(androidx.core.app.CoreComponentFactory).
Suggestion: add 'tools:replace="android:appComponentFactory"' to <application> element at AndroidManifest.xml:8:5-42:19 to override.
```
解决办法：
在你的项目grade.properties添加以下选项：
android.enableJetifier=true
android.useAndroidX=true

参考：https://github.com/material-components/material-components-android/issues/103
It looks like this was in issue in Jetifier that was fixed in the latest canary version of Android Studio. 
I just reproduced your error and then downloaded Android Studio C15 and it was resolved - https://developer.android.com/studio/preview. 
Try installing that and running with android.enableJetifier=true in your gradle.properties file.

