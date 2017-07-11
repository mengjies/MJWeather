package com.mj.weather.weather.component;

import com.mj.weather.weather.module.CityRepositoryModule;
import com.mj.weather.weather.view.fragment.CityListFragment;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/23.
 */
@Component(modules = CityRepositoryModule.class)
public interface CityRepositoryComponent {

    void inject(CityListFragment cityListFragment);

}
