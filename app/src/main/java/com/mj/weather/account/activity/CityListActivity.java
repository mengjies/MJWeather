package com.mj.weather.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.baidu.location.BDLocation;
import com.mj.weather.R;
import com.mj.weather.account.component.DaggerCityListComponent;
import com.mj.weather.account.model.event.WEvent;
import com.mj.weather.account.module.CityListViewModule;
import com.mj.weather.account.presenter.CityListPresenter;
import com.mj.weather.account.view.CityListFragment;
import com.mj.weather.common.base.BaseActivity;
import com.mj.weather.common.util.ActivityUtils;
import com.mj.weather.common.common.LocationManager;
import com.mj.weather.common.util.NetWorkUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

public class CityListActivity extends BaseActivity {
    public static final int PARENT_ID = 0;
    public static final String THE_NAME = "中国";
    private static final String TAG = "CityListActivity";
    public BDLocation mLocation;
    @Inject
    CityListPresenter mCityPresenter;
    private ActionBar actionBar;
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
        if (NetWorkUtils.isNetworkAvailable(this)) {
            LocationManager.startLocation();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationEvent(WEvent.LocationEvent event) {
        Logger.d(event.location);
        mLocation = event.location;
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
