# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#   OkHttp
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault


#   Retrofit
-dontwarn okio.**
-dontwarn javax.annotation.**


#   Litepal
-keep class org.litepal.** {
    *;
}
-keep class * extends org.litepal.crud.DataSupport {
    *;
}


#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


#   EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
#support-v4
 -keep class android.support.**{*;}


# nuwa
-keep class cn.jiajixin.nuwa.** { *; }


