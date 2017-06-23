package com.mj.weather.account.component;

import com.mj.weather.account.model.repository.CityRepository;
import com.mj.weather.account.module.CityRepositoryModule;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/23.
 */
@Component(modules = CityRepositoryModule.class)
public interface CityRepositoryComponent {

    CityRepository proviceCityRepository();

}
