package com.mj.weather.account.module;

import com.mj.weather.account.contract.CityListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MengJie on 2017/6/27.
 */
@Module
public class CityListViewModule {

    private CityListContract.View mView;

    public CityListViewModule(CityListContract.View mView) {
        this.mView = mView;
    }

    @Provides
    CityListContract.View provideCityListContractView() {
        return mView;
    }

}
