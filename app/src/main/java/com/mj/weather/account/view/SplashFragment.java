package com.mj.weather.account.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mj.weather.R;
import com.mj.weather.account.contract.SplashContract;
import com.mj.weather.common.base.BaseFragment;
import com.mj.weather.common.util.LogUtils;
import com.mj.weather.account.activity.MainActivity;
import com.tbruyelle.rxpermissions2.Permission;

import io.reactivex.CompletableObserver;
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
        if (mSplashPresenter.isInitCityDb()) {
            mSplashPresenter.initCityDb(getContext());
        } else {
            onDbFinished();
        }

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
    public CompletableObserver initDbObserver() {
        return new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                onDbFinished();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.e(TAG, e.getMessage());
            }
        };
    }

    @Override
    public Observer<? super Permission> initPermsObserver() {
        return new Observer<Permission>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Permission permission) {
                if (permission.granted) {
                    // `permission.name` is granted !
                } else if (permission.shouldShowRequestPermissionRationale) {
                    // Denied permission without ask never again
                } else {
                    // Denied permission with ask never again
                    // Need to go to the settings
                    //这里暂不处理 用到相关权限时在处理
                    //showSetPermsDialog();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.e(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.e(TAG, "onComplete");
                onPermsFinished();
            }
        };
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

    private void onDbFinished() {
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
}
