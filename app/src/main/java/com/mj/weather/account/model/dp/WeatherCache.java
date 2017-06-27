package com.mj.weather.account.model.dp;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by MengJie on 2017/6/27.
 */

public class WeatherCache extends DataSupport {

    @Column
    public String city;
    public String json;

}
