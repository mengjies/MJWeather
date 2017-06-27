package com.mj.weather.account.component;

import com.mj.weather.account.activity.SplashActivity;
import com.mj.weather.account.module.CityRepositoryModule;
import com.mj.weather.account.module.SplashViewModule;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/23.
 */
@Component(modules = {SplashViewModule.class, CityRepositoryModule.class})
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
