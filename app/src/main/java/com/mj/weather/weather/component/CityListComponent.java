package com.mj.weather.weather.component;

import com.mj.weather.weather.activity.CityListActivity;
import com.mj.weather.weather.module.CityListViewModule;
import com.mj.weather.weather.module.CityRepositoryModule;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/27.
 */
@Component(modules = {CityListViewModule.class, CityRepositoryModule.class})
public interface CityListComponent {

    void inject(CityListActivity cityListActivity);

}
