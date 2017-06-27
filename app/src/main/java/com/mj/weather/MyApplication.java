package com.mj.weather;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.mj.weather.account.component.DaggerUserRepositoryComponent;
import com.mj.weather.account.component.UserRepositoryComponent;
import com.mj.weather.account.model.http.UserApiClient;
import com.mj.weather.common.util.LocationManager;
import com.mj.weather.account.model.http.ApiClient;
import com.umeng.analytics.MobclickAgent;

import org.litepal.LitePal;

/**
 * Created by MengJie on 2017/1/11.
 * 获取全局Context
 * 初始化LitePal
 * 友盟场景类型设置接口
 */

public class MyApplication extends Application {
    private Context context;
    private static MyApplication instance;
    //Repository
    private static UserRepositoryComponent userRepositoryComponent;
    //百度定位
    public LocationClient mLocationClient;

    public static MyApplication getInstance() {
        return instance;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        context = this;
        instance = this;

        //初始化LitePal
        LitePal.initialize(context);

        //友盟场景类型设置接口
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);

        //初始化自定义APIClient
        ApiClient.init();
        UserApiClient.init();

        // 初始化repository
        userRepositoryComponent = DaggerUserRepositoryComponent.builder().build();

        //初始化定位
        //声明LocationClient类
        mLocationClient = new LocationClient(context);
        //配置LocationClientOption
        initLocation();
        //注册监听函数
        BDLocationListener myListener = new LocationManager();
        mLocationClient.registerLocationListener(myListener);

    }

    public UserRepositoryComponent getUserRepositoryComponent() {
        return userRepositoryComponent;
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        //int span = 10000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
}
