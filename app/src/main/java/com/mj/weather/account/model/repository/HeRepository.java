package com.mj.weather.account.model.repository;

import android.text.TextUtils;

import com.mj.weather.MyApplication;
import com.mj.weather.common.util.JsonUtils;
import com.mj.weather.common.util.NetWorkUtils;
import com.mj.weather.account.model.dp.WeatherCache;
import com.mj.weather.account.model.dp.WeatherCacheDao;
import com.mj.weather.account.model.http.ApiClient;
import com.mj.weather.account.model.http.HeProtocol;
import com.mj.weather.account.model.http.entity.HeBean;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by MengJie on 2017/6/19.
 */

public class HeRepository {

    /**
     * @param city
     * @return
     */
    public Observable<HeBean.RspWeather> getWeather(final String city) {
        Observable<HeBean.RspWeather> observable = null;

        if (!NetWorkUtils.isNetworkAvailable(MyApplication.getInstance().getContext())) {

            return Observable.just(WeatherCacheDao.getWeatherByCity(city))
                    .filter(new Predicate<String>() {
                        @Override
                        public boolean test(@NonNull String s) throws Exception {
                            return !TextUtils.isEmpty(s);
                        }
                    })
                    .map(new Function<String, HeBean.RspWeather>() {
                        @Override
                        public HeBean.RspWeather apply(@NonNull String s) throws Exception {
                            return JsonUtils.toObject(s, HeBean.RspWeather.class);
                        }
                    });
        }

        return ApiClient.heService.getWeather(city, HeProtocol.key)
                .doOnNext(new Consumer<HeBean.RspWeather>() {
                    @Override
                    public void accept(@NonNull HeBean.RspWeather rspWeather) throws Exception {
                        WeatherCacheDao.addWeatherCache(city, JsonUtils.toJson(rspWeather));
                    }
                });
    }

    /**
     * @return
     */
    public Observable<WeatherCache> getWeather() {
        WeatherCache cache = WeatherCacheDao.getLatestWeather();
        if (cache == null) {
            return Observable.empty();
        }
        return Observable.just(cache);
    }

}
