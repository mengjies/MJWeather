package com.mj.weather.weather.model.http.service;

import com.mj.weather.weather.model.http.entity.HeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MengJie on 2017/6/18.
 */

public interface HeService {

    /**
     * 天气预报
     * 包括7-10天预报、实况天气、每小时天气、灾害预警、生活指数、空气质量，一次获取足量数据
     * @param city
     * @param key
     * @return
     */
    @GET("weather")
    Observable<HeBean.RspWeather> getWeather(@Query("city") String city, @Query("key") String key);

    /**
     * 7-10天预报
     * 最长10天天气预报数据（大客户可达14天），天气预报已经包含日出日落，月升月落等常规数据
     * @param city
     * @param key
     * @return
     */
    @GET("forecast")
    Observable<HeBean.Daily_forecast> getForecast(@Query("city") String city, @Query("key") String key);

    /**
     * 实况天气
     * 包括多种气象指数的实况天气，每小时更新
     * @param city
     * @param key
     * @return
     */
    @GET("Now")
    Observable<HeBean.Now> getNow(@Query("city") String city, @Query("key") String key);

    /**
     * 每小时预报（逐小时预报），最长10天
     * @param city
     * @param key
     * @return
     */
    @GET("hourly")
    Observable<HeBean.Hourly_forecast> getHourly(@Query("city") String city, @Query("key") String key);

    /**
     * 生活指数
     * 目前提供7大生活指数，每三小时更新
     * @param city
     * @param key
     * @return
     */
    @GET("suggestion")
    Observable<HeBean.Suggestion> getSuggestion(@Query("city") String city, @Query("key") String key);

    /**
     * 灾害预警
     * 为全国2560个城市灾害预警信息，包括台风、暴雨、暴雪、寒潮、大风、沙尘暴、高温、干旱、雷电、冰雹、
     * 霜冻、霾、道路结冰、寒冷、灰霾、雷电大风、森林火险、降温、道路冰雪、干热风、低温、冰冻等灾害类型。
     * 每15分钟更新一次，建议用户每30-60分钟获取一下信息。
     * @param city
     * @param key
     * @return
     */
    @GET("alarm")
    Observable<HeBean.Alarms> getAlarm(@Query("city") String city, @Query("key") String key);

    /**
     * 景点天气
     * 全国4A和5A级景点共2000＋的7天天气预报
     * "scenic"
     */


    /**
     * 历史天气
     * 支持2010年1月1日至今的全国城市历史天气数据
     * "historical"
     */

    /**
     * 城市查询
     * 通过此接口获取城市信息，例如通过名称获取城市ID，建议使用城市ID获取天气数据，避免重名城市导致的混淆
     * "search"
     */


}
