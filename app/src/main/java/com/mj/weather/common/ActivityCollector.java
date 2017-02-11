package com.mj.weather.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/1/12.
 * 管理activity
 */

public class ActivityCollector {
    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
