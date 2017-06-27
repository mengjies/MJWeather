package com.mj.weather.account.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.baidu.location.BDLocation;
import com.mj.weather.R;
import com.mj.weather.account.component.DaggerCityListComponent;
import com.mj.weather.account.contract.CityListContract;
import com.mj.weather.account.module.CityListViewModule;
import com.mj.weather.account.presenter.CityListPresenter;
import com.mj.weather.account.view.CityListFragment;
import com.mj.weather.common.base.BaseActivity;
import com.mj.weather.common.common.ActivityUtils;
import com.mj.weather.common.util.LocationManager;

import javax.inject.Inject;

public class CityListActivity extends BaseActivity {
    public static final int PARENT_ID = 0;
    public static final String THE_NAME = "中国";
    private static final String TAG = "CityListActivity";
    public BDLocation mLocation;
    @Inject
    CityListPresenter mCityPresenter;
    private ActionBar actionBar;
    public ProgressDialog proDialog;
    private CityListFragment mFragment;

    public static void actionStart(Activity act, int requestCode) {
        Intent intent = new Intent(act, CityListActivity.class);
        act.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        //加载Fragment
        replaceFragment(PARENT_ID, THE_NAME);

        //getLocation
        initLocation();

        proDialog = new ProgressDialog(this);
        proDialog.setMessage("加载中...");

    }

    private void initLocation() {
        LocationManager.startLocation(new LocationManager.OnLocationListener() {
            @Override
            public void onLocation(BDLocation location) {
                mLocation = location;
                if (proDialog.isShowing()) {
                    proDialog.dismiss();
                    mFragment.setResult(location.getCity(), location.getDistrict());
                }
                //关闭
                if (LocationManager.isStart()) {
                    LocationManager.stopLocation();
                }
            }
        });
    }

    /**
     * replaceFragment
     *
     * @param parentId
     * @param theName
     */
    public void replaceFragment(int parentId, String theName) {
        mFragment = CityListFragment.newInstance(parentId, theName);
        ActivityUtils.replaceFragment(getSupportFragmentManager(), mFragment, R.id.frame_layout, parentId);

        DaggerCityListComponent.builder()
                .cityListViewModule(new CityListViewModule(mFragment))
                .build()
                .inject(this);
    }
}
