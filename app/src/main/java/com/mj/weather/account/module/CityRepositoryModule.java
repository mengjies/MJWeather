package com.mj.weather.account.module;

import com.mj.weather.account.model.repository.CityRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MengJie on 2017/6/23.
 */
@Module
public class CityRepositoryModule {
    @Provides
    CityRepository provideCityRepository() {
        return CityRepository.getInstance();
    }
}
