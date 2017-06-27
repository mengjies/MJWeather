package com.mj.weather.account.module;

import com.mj.weather.account.model.repository.UserRepository;

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
