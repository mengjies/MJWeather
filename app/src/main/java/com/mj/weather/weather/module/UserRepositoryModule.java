package com.mj.weather.weather.module;

import com.mj.weather.weather.model.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MengJie on 2017/6/23.
 */
@Module
public class UserRepositoryModule {

    @Provides
    @Singleton
    UserRepository provideUserRepository() {
        return new UserRepository();
    }

}
