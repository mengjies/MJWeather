package com.mj.weather;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by MengJie on 2017/7/3.
 */

public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.mj.weather.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
