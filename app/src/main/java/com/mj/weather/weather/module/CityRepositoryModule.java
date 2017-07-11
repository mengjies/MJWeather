package com.mj.weather.weather.module;

import com.mj.weather.weather.model.repository.CityRepository;

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
