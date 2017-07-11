package com.mj.weather.weather.component;

import com.mj.weather.weather.activity.SignInActivity;
import com.mj.weather.weather.module.SignInViewModule;
import com.mj.weather.common.annotation.PerActivity;

import dagger.Component;

/**
 * Created by MengJie on 2017/6/23.
 */
@PerActivity
@Component(dependencies = UserRepositoryComponent.class, modules = SignInViewModule.class)
public interface SignInComponent {

    void inject(SignInActivity signInActivity);

}
