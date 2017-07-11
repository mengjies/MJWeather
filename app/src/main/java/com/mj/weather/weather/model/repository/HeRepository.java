package com.mj.weather.weather.model.repository;

import com.mj.weather.weather.model.dp.dao.WeatherCacheDao;
import com.mj.weather.weather.model.dp.entity.WeatherCache;
import com.mj.weather.weather.model.http.ApiClient;
import com.mj.weather.weather.model.http.HeProtocol;
import com.mj.weather.weather.model.http.entity.HeBean;
import com.mj.weather.common.util.JsonUtils;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by MengJie on 2017/6/19.
 */

public class HeRepository {

    /**
     * @param city
     * @return
     */
    public Observable<HeBean.RspWeather> getWeather(final String city) {

        return ApiClient.heService.getWeather(city, HeProtocol.key);
    }

    /**
     * @param city
     * @return
     */
    public Observable<HeBean.RspWeather> getWeatherCache(final String city) {

        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return WeatherCacheDao.getWeatherByCity(city);
            }
        })
                .map(new Function<String, HeBean.RspWeather>() {
                    @Override
                    public HeBean.RspWeather apply(@NonNull String s) throws Exception {
                        return JsonUtils.toObject(s, HeBean.RspWeather.class);
                    }
                });
    }

    /**
     * @return
     */
    public Observable<WeatherCache> getWeatherCache() {

        return Observable.fromCallable(new Callable<WeatherCache>() {
            @Override
            public WeatherCache call() throws Exception {
                return WeatherCacheDao.getLatestWeather();
            }
        });

    }

    /**
     *
     * @param cityName
     * @param district
     * @param rspWeather
     */
    public void addWeatherCache(String cityName, String district, HeBean.RspWeather rspWeather) {
        WeatherCacheDao.addWeatherCache(cityName,district, JsonUtils.toJson(rspWeather));
    }
}
