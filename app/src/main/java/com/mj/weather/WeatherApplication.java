package com.mj.weather;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by MengJie on 2017/7/3.
 */

public class WeatherApplication extends TinkerApplication {
    public WeatherApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.mj.weather.WeatherApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
