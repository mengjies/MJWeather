package com.mj.weather.weather.module;

import com.mj.weather.weather.model.repository.HeRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MengJie on 2017/6/25.
 */
@Module
public class HeRepositoryModule {

    @Provides
    HeRepository provideHeRepository() {
        return new HeRepository();
    }

}
