package com.mj.weather.weather.model.dp.dao;

import com.mj.weather.weather.model.dp.entity.WeatherCache;

import org.litepal.crud.DataSupport;

/**
 * Created by MengJie on 2017/6/27.
 */

public class WeatherCacheDao {

    /**
     * @return
     */
    public static WeatherCache getLatestWeather() {
        WeatherCache last = DataSupport.findLast(WeatherCache.class);
        return last;
    }

    /**
     * @param city
     * @return
     */
    public static String getWeatherByCity(String city) {
        WeatherCache cache = DataSupport.where("city=?", city + "").findFirst(WeatherCache.class);
        String json = null;
        if (cache != null) {
            json = cache.json;
        }
        return json;
    }

    /**
     * 先删除或添加（使最新数据始终在最后一条）
     *
     * @param city
     * @param district
     * @param json
     */
    public static boolean addWeatherCache(String city, String district, String json) {
        int i = DataSupport.deleteAll(WeatherCache.class, "city=?", city + "");
        WeatherCache cache = new WeatherCache();
        cache.city = city;
        cache.district = district;
        cache.json = json;
        return cache.save();
    }

    /**
     * 清空该表
     */
    public static void clear() {
        DataSupport.deleteAll(WeatherCache.class);
    }

}
