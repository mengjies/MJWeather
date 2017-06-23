package com.mj.weather.account.presenter;

import com.mj.weather.account.contract.SplashContract;
import com.mj.weather.account.model.repository.CityRepository;

import javax.inject.Inject;

/**
 * Created by MengJie on 2017/6/23.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mSplashView;
    private CityRepository mCityRepository;

    @Inject
    public SplashPresenter(SplashContract.View splashView, CityRepository cityRepository) {
        mSplashView = splashView;
        mCityRepository = cityRepository;

        mSplashView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
