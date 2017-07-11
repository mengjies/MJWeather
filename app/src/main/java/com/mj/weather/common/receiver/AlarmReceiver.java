package com.mj.weather.common.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mj.weather.common.util.LocationManager;
import com.mj.weather.common.util.NetWorkUtils;
import com.mj.weather.common.util.ToastUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by MengJie on 2017/7/5.
 * 定时广播接收器
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String sb = context.getApplicationContext().getPackageName() + "\n" +
                "action:" + intent.getAction() + "\n" +
                "category:" + intent.getCategories() + "\n";
        Logger.d(sb);
        //开启定位
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            ToastUtils.showToast("网络异常");
            return;
        }
        //
        LocationManager.startLocation();
    }
}
