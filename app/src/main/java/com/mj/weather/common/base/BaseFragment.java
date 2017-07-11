package com.mj.weather.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by MengJie on 2017/1/20.
 *
 * BaseFragment
 */

public class BaseFragment extends Fragment {
    private String TAG = "BaseFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
