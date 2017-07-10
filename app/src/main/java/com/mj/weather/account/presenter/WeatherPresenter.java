package com.mj.weather.account.presenter;

import android.text.TextUtils;

import com.mj.weather.SampleApplicationLike;
import com.mj.weather.account.contract.WeatherContract;
import com.mj.weather.account.model.dp.entity.WeatherCache;
import com.mj.weather.account.model.http.entity.HeBean;
import com.mj.weather.account.model.repository.HeRepository;
import com.mj.weather.common.common.JsonUtils;
import com.mj.weather.common.common.ToastUtils;
import com.mj.weather.common.util.NetWorkUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MengJie on 2017/6/25.
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
    public void getWeather(final String cityName, final String districtName) {
        Observable.just(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        boolean available = NetWorkUtils.isNetworkAvailable(SampleApplicationLike.getContext());
                        if (!available) {
                            ToastUtils.showToast("网络异常");
                        }
                        return available;
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<HeBean.RspWeather>>() {
                    @Override
                    public ObservableSource<HeBean.RspWeather> apply(@NonNull String s) throws Exception {
                        return mHeRepository.getWeather(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<HeBean.RspWeather>() {
                    @Override
                    public boolean test(@NonNull HeBean.RspWeather rspWeather) throws Exception {
                        String status = rspWeather.HeWeather5.get(0).status;
                        if (!status.equals("ok")) {
                            ToastUtils.showToast(status);
                        }
                        return status.equals("ok");
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<HeBean.RspWeather>() {
                    @Override
                    public void accept(@NonNull HeBean.RspWeather rspWeather) throws Exception {
                        mHeRepository.addWeatherCache(cityName, districtName, rspWeather);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<HeBean.RspWeather>() {
                    @Override
                    public void accept(@NonNull HeBean.RspWeather rspWeather) throws Exception {
                        mWeatherView.setCity(cityName, districtName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mWeatherView.weatherObserver());
    }

    @Override
    public void getWeatherCache() {
        mHeRepository.getWeatherCache()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<WeatherCache>() {
                    @Override
                    public void accept(@NonNull WeatherCache weatherCache) throws Exception {
                        mWeatherView.setCity(weatherCache.city, weatherCache.district);
                    }
                })
                .map(new Function<WeatherCache, HeBean.RspWeather>() {
                    @Override
                    public HeBean.RspWeather apply(@NonNull WeatherCache weatherCache) throws Exception {
                        return JsonUtils.toObject(weatherCache.json, HeBean.RspWeather.class);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mWeatherView.weatherCacheObserver());
    }
}
