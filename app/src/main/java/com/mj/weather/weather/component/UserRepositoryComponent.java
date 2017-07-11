package com.mj.weather.weather.component;

import com.mj.weather.weather.model.repository.UserRepository;
import com.mj.weather.weather.module.UserRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/23.
 */
@Singleton
@Component(modules = UserRepositoryModule.class)
public interface UserRepositoryComponent {

    UserRepository provideUserRepository();

}
