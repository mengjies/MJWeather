package com.mj.weather.account.module;

import com.mj.weather.account.contract.WeatherContract;

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
