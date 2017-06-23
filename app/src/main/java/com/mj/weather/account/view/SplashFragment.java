package com.mj.weather.account.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mj.weather.account.contract.SplashContract;
import com.mj.weather.common.base.BaseFragment;

/**
 * Created by MengJie on 2017/6/23.
 */

public class SplashFragment extends BaseFragment implements SplashContract.View {
    private SplashContract.Presenter mSplashPresenter;

    public static SplashFragment newInstance() {

        Bundle args = new Bundle();

        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mSplashPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSplashPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
