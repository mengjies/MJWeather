package com.mj.weather.weather.component;

import com.mj.weather.weather.activity.LoginActivity;
import com.mj.weather.weather.module.LoginViewModule;
import com.mj.weather.common.annotation.PerActivity;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/21.
 */
@PerActivity
@Component(dependencies = UserRepositoryComponent.class, modules = LoginViewModule.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
