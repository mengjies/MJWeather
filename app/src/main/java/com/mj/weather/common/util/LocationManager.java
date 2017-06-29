package com.mj.weather.common.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.mj.weather.MyApplication;


/**
 * 定位管理
 */
public class LocationManager implements BDLocationListener {
    private static final String TAG = "PositionManager";

    private static OnLocationListener onLocationListener;

    /**
     * 开启百度定位
     */
    public static void startLocation(OnLocationListener listener) {
        onLocationListener = listener;
        MyApplication.getInstance().mLocationClient.start();
    }

    /**
     * 关闭定位
     */
    public static void stopLocation() {
        if (isStart()) {
            MyApplication.getInstance().mLocationClient.stop();
            onLocationListener = null;
        }
    }

    /**
     * 是否已经开启定位
     * 已开启 则从MainActivity中获取定位数据
     *
     * @return
     */
    public static boolean isStart() {
        return MyApplication.getInstance().mLocationClient.isStarted();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (onLocationListener != null) {
            onLocationListener.onLocation(bdLocation);
        }
    }

    public interface OnLocationListener {
        /**
         * 当定位回调方法结束，定位结果出来时，调用此方法
         */
        void onLocation(BDLocation location);
    }
}
