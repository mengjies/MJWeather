package com.mj.weather.account.module;

import com.mj.weather.account.contract.SplashContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MengJie on 2017/6/23.
 */
@Module
public class SplashViewModule {

    private SplashContract.View mView;

    public SplashViewModule(SplashContract.View mView) {
        this.mView = mView;
    }

    @Provides
    SplashContract.View provideSplashContractView() {
        return mView;
    }
}
