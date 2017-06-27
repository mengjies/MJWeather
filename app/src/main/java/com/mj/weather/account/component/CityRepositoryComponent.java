package com.mj.weather.account.component;

import com.mj.weather.account.module.CityRepositoryModule;
import com.mj.weather.account.view.CityListFragment;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/23.
 */
@Component(modules = CityRepositoryModule.class)
public interface CityRepositoryComponent {

    void inject(CityListFragment cityListFragment);

}
