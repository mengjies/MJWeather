package com.mj.weather.common.util;

import android.widget.Toast;

import com.mj.weather.BuildConfig;
import com.mj.weather.MyApplication;


/**
 * Created by Administrator on 2016/3/23.
 * Toast管理
 */
public class ToastUtils {
    private static Toast toast = null;

    public static void showToast(String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), msg, duration);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showTestToast(String msg) {
        if (BuildConfig.DEBUG) {
            showToast(msg);
        }
    }
}
