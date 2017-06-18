package com.mj.weather.utils;

import android.content.Context;
import android.widget.Toast;

import com.mj.weather.base.AppConfig;


/**
 * Created by Administrator on 2016/3/23.
 * Toast管理
 */
public class ToastUtils {
    private static Toast toast = null;

    public static void showToast(Context ctx, String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(ctx, msg, duration);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showToast(Context ctx, String msg) {
        showToast(ctx, msg, Toast.LENGTH_SHORT);
    }

    public static void showTestToast(Context ctx, String msg) {
        if (AppConfig.isDebug) {
            showToast(ctx, msg);
        }
    }
}
