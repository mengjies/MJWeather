package com.mj.weather.account.component;

import com.mj.weather.account.activity.MainActivity;
import com.mj.weather.account.module.HeRepositoryModule;
import com.mj.weather.account.module.WeatherViewModule;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/25.
 */
@Component(modules = {WeatherViewModule.class, HeRepositoryModule.class})
public interface WeatherComponent {

    void inject(MainActivity mainActivity);

}
