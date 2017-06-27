package com.mj.weather.account.presenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.mj.weather.account.contract.SplashContract;
import com.mj.weather.account.model.repository.CityRepository;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    /**
     * @param context
     */
    @Override
    public void initCityDb(Context context) {
        mCityRepository.initCityDb(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSplashView.initDbObserver());
    }

    @Override
    public void initPermissions(FragmentActivity activity, String[] permissions) {
        new RxPermissions(activity)
                .requestEach(permissions)
                .subscribe(mSplashView.initPermsObserver());
    }

    @Override
    public boolean isInitCityDb() {
        return mCityRepository.isInitCityDb();
    }
}
