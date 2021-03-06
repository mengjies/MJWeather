package com.mj.weather.weather.view.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mj.weather.R;
import com.mj.weather.weather.activity.MainActivity;
import com.mj.weather.weather.contract.SplashContract;
import com.mj.weather.common.base.BaseFragment;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by MengJie on 2017/6/23.
 */

public class SplashFragment extends BaseFragment implements SplashContract.View {
    private static final String TAG = "SplashFragment";
    private static final int RC_SETTING_SCREEN = 11;
    private SplashContract.Presenter mSplashPresenter;
    private boolean isPermsFinished;
    private boolean isDbFinished;

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
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        // 初始化城市列表到数据库
        mSplashPresenter.initCityDb();

        // 初始化权限申请
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        };
        mSplashPresenter.initPermissions(getActivity(), permissions);

        return view;
    }

    @Override
    public Observer<? super Boolean> initPermsObserver() {
        return new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Boolean aBoolean) {
                if (aBoolean) {
                    //全部同意
                } else {
                    //至少一个没同意
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete!");
                onPermsFinished();
            }
        };
    }

    @Override
    public Observer<? super Object> initDbObserver() {
        return new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                String s = Process.myPid() + "--" + Process.myTid() + "--" + Process.myUid();
                Logger.d(o.toString());
                onDbFinished();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.e(e.getMessage());
                onDbFinished();
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete!");
                onDbFinished();
            }
        };
    }

    @Override
    public void onDbFinished() {
        isDbFinished = true;
        onNext();
    }

    private void onPermsFinished() {
        isPermsFinished = true;
        SplashFragment.this.onNext();
    }

    private void onNext() {
        if (isDbFinished && isPermsFinished) {
            MainActivity.actionStart(getActivity());
        }
    }

    private void showSetPermsDialog() {
        new AlertDialog.Builder(getContext()).setMessage(getString(R.string.permission_init_not_granted))
                .setTitle(getString(R.string.title_settings_dialog))
                .setPositiveButton(getString(R.string.bt_setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToSetPermission();
                    }
                })
                .setNegativeButton(getString(R.string.bt_cancel), null)
                .setCancelable(false)
                .create()
                .show();
    }

    private void goToSetPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getContext().getPackageName(), null));
        startActivityForResult(intent, RC_SETTING_SCREEN);
    }
}
