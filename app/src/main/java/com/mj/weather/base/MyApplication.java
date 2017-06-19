package com.mj.weather.base;

import android.app.Application;
import android.content.Context;

import com.mj.weather.account.model.http.UserApiClient;
import com.mj.weather.mvp.model.http.ApiClient;
import com.umeng.analytics.MobclickAgent;

import org.litepal.LitePal;

/**
 * Created by MengJie on 2017/1/11.
 * 获取全局Context
 * 初始化LitePal
 * 友盟场景类型设置接口
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        //初始化LitePal
        LitePal.initialize(context);
        //友盟场景类型设置接口
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //初始化自定义APIClient
        ApiClient.init();
        UserApiClient.init();
    }

    public static Context getContext() {
        return context;
    }
}
