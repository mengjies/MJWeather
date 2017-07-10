package com.mj.weather.account.model.event;

import com.baidu.location.BDLocation;

/**
 * Created by MengJie on 2017/7/5.
 */

public class WEvent {

    /**
     * LocationEvent
     */
    public static class LocationEvent {
        public BDLocation location;

        public LocationEvent(BDLocation location) {
            this.location = location;
        }
    }


}
