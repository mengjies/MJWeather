package com.mj.weather.common.base;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.github.moduth.blockcanary.BlockCanaryContext;
import com.mj.weather.WeatherApplicationLike;

/**
 * Created by MengJie on 2017/6/29.
 *
 * 用于BlockCanary
 */

public class AppContext extends BlockCanaryContext {
    private static final String TAG = "AppContext";

    @Override
    public String provideQualifier() {
        String qualifier = "";
        try {
            PackageInfo info = WeatherApplicationLike.getContext().getPackageManager()
                    .getPackageInfo(WeatherApplicationLike.getContext().getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "provideQualifier exception", e);
        }
        return qualifier;
    }

    @Override
    public int provideBlockThreshold() {
        return 1000;
    }
}
