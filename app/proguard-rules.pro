#Koin
-keepclassmembers class * { public <init>(...); }
-keepnames class androidx.lifecycle.ViewModel
-keepclassmembers public class * extends androidx.lifecycle.ViewModel { public <init>(...); }

#Onesignal
-dontwarn com.onesignal.**
-keep class com.onesignal.ActivityLifecycleListenerCompat** {*;}

#Coroutines
-keep class kotlinx.coroutines.**
-dontwarn kotlinx.coroutines.**


#AppsFlyer
-keep class com.appsflyer.** { *; }
-keep public class com.android.installreferrer.** { *; }
# OkHttp
-keep class com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**