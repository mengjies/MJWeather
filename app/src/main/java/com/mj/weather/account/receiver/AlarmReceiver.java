package com.mj.weather.account.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mj.weather.common.common.LocationManager;
import com.mj.weather.common.util.NetWorkUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by MengJie on 2017/7/5.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getApplicationContext().getPackageName()).append("\n");
        sb.append("action:").append(intent.getAction()).append("\n");
        sb.append("category:").append(intent.getCategories()).append("\n");
        Logger.d(sb.toString());
        //开启定位
        if (NetWorkUtils.isNetworkAvailable(context)) {
            LocationManager.startLocation();
        }
    }
}
