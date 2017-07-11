package com.mj.weather.weather.component;

import com.mj.weather.weather.activity.MainActivity;
import com.mj.weather.weather.module.HeRepositoryModule;
import com.mj.weather.weather.module.WeatherViewModule;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/25.
 */
@Component(modules = {WeatherViewModule.class, HeRepositoryModule.class})
public interface WeatherComponent {

    void inject(MainActivity mainActivity);

}
