package com.mj.weather.account.component;

import com.mj.weather.account.activity.LoginActivity;
import com.mj.weather.account.module.LoginViewModule;
import com.mj.weather.common.annotation.PerActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/21.
 */
@PerActivity
@Component(dependencies = UserRepositoryComponent.class, modules = LoginViewModule.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
