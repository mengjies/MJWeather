package com.mj.weather.weather.component;

import com.mj.weather.weather.activity.SplashActivity;
import com.mj.weather.weather.module.CityRepositoryModule;
import com.mj.weather.weather.module.SplashViewModule;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/23.
 */
@Component(modules = {SplashViewModule.class, CityRepositoryModule.class})
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
