package com.mj.weather.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.mj.weather.R;
import com.mj.weather.common.base.BaseActivity;
import com.mj.weather.account.view.CityListFragment;

public class CityListActivity extends BaseActivity {
    private static final String TAG = "CityListActivity";
    private ActionBar actionBar;

    public static void actionStart(Activity act, int requestCode) {
        Intent intent = new Intent(act, CityListActivity.class);
        act.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citylist_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        //加载Fragment
        replaceFragment(0, "中国");

    }

    public void replaceFragment(int parentId, String theName) {
        //actionBar.setTitle(theName);
        CityListFragment fragment = new CityListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("parentId", parentId);
        bundle.putString("cityName", theName);
        fragment.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment,parentId+"");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (parentId != 0) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
