package com.mj.weather.account.component;

import com.mj.weather.account.activity.CityListActivity;
import com.mj.weather.account.module.CityListViewModule;
import com.mj.weather.account.module.CityRepositoryModule;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/27.
 */
@Component(modules = {CityListViewModule.class, CityRepositoryModule.class})
public interface CityListComponent {

    void inject(CityListActivity cityListActivity);

}
