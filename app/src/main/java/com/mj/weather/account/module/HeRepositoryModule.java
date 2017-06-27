package com.mj.weather.account.module;

import com.mj.weather.account.model.repository.HeRepository;

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
