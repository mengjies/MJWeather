package com.mj.weather.account.component;

import com.mj.weather.account.model.repository.UserRepository;
import com.mj.weather.account.module.UserRepositoryModule;

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
