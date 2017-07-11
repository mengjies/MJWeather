package com.mj.weather.weather.module;

import com.mj.weather.weather.contract.WeatherContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MengJie on 2017/6/25.
 */
@Module
public class WeatherViewModule {
    private WeatherContract.View mView;

    public WeatherViewModule(WeatherContract.View mView) {
        this.mView = mView;
    }

    @Provides
    WeatherContract.View provideWeatherContractView() {
        return mView;
    }

}
