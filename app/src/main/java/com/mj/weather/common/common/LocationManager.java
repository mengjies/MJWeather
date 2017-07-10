package com.mj.weather.common.common;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.mj.weather.SampleApplicationLike;
import com.mj.weather.account.model.event.WEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;


/**
 * 定位管理
 */
public class LocationManager implements BDLocationListener {
    private static final String TAG = "PositionManager";


    /**
     * 开启百度定位
     */
    public static void startLocation() {
        Logger.d("startLocation!");
        SampleApplicationLike.getInstance().mLocationClient.start();
    }

    /**
     * 关闭定位
     */
    private static void stopLocation() {
        Logger.d("stopLocation!");
        SampleApplicationLike.getInstance().mLocationClient.stop();
    }

    /**
     * 是否已经开启定位
     * 已开启 则从MainActivity中获取定位数据
     *
     * @return
     */
    private static boolean isStart() {
        return SampleApplicationLike.getInstance().mLocationClient.isStarted();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Logger.d(bdLocation.getCity() + bdLocation.getDistrict());
        //获取定位 发布定位事件
        EventBus.getDefault().post(new WEvent.LocationEvent(bdLocation));
        //关闭定位
        stopLocation();
    }
}
