package com.mj.weather.account.presenter;

import com.mj.weather.common.util.JsonUtils;
import com.mj.weather.account.contract.WeatherContract;
import com.mj.weather.account.model.dp.entity.WeatherCache;
import com.mj.weather.account.model.http.entity.HeBean;
import com.mj.weather.account.model.repository.HeRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MengJie on 2017/6/25.
 *
 */

public class WeatherPresenter implements WeatherContract.Presenter {
    private HeRepository mHeRepository;
    private WeatherContract.View mWeatherView;

    @Inject
    public WeatherPresenter(HeRepository mHeRepository, WeatherContract.View mWeatherView) {
        this.mHeRepository = mHeRepository;
        this.mWeatherView = mWeatherView;

        mWeatherView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWeather(String cityName) {
        mHeRepository.getWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mWeatherView.weatherObserver());
    }

    @Override
    public void getWeatherCache() {
        mHeRepository.getWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<WeatherCache, HeBean.RspWeather>() {
                    @Override
                    public HeBean.RspWeather apply(@NonNull WeatherCache weatherCache) throws Exception {
                        mWeatherView.setTitle(weatherCache.city);
                        return JsonUtils.toObject(weatherCache.json, HeBean.RspWeather.class);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mWeatherView.weatherObserver());
    }
}
