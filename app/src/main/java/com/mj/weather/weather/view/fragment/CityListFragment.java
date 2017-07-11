package com.mj.weather.weather.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.mj.weather.R;
import com.mj.weather.weather.activity.CityListActivity;
import com.mj.weather.weather.contract.CityListContract;
import com.mj.weather.weather.model.dp.entity.CityItem;
import com.mj.weather.common.base.BaseFragment;
import com.mj.weather.common.util.NetWorkUtils;
import com.mj.weather.common.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/1/20.
 */

public class CityListFragment extends BaseFragment implements CityListContract.View {
    private static final String TAG = "CityListFragment";

    private List<CityItem> dataList = new ArrayList<>();
    private CityListActivity activity;
    private int parentId;

    private CityListContract.Presenter mCityListPresenter;

    public static CityListFragment newInstance(int parentId, String theName) {
        Bundle args = new Bundle();
        args.putInt("parentId", parentId);
        args.putString("cityName", theName);
        CityListFragment fragment = new CityListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(CityListContract.Presenter presenter) {
        mCityListPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citylist, container, false);
        activity = (CityListActivity) getActivity();

        //在Fragment中使用菜单
        setHasOptionsMenu(true);

        //getArguments
        Bundle bundle = getArguments();
        parentId = bundle.getInt("parentId");
        String cityName = bundle.getString("cityName");

        //toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(cityName);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //listView
        dataList = mCityListPresenter.getCityListByParent(parentId);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                mCityListPresenter.getNameArrayList(dataList));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityItem district = dataList.get(position);
                if (!mCityListPresenter.hasChildren(district.getAreaID())) {
                    // 天气接口只能查到市级城市
                    CityItem city = mCityListPresenter.getCityById(district.getParentID());
                    setResult(city.getTheName(), district.getTheName());
                } else {
                    activity.replaceFragment(district.getAreaID(), district.getTheName());
                }
            }
        });
        return view;
    }

    public void setResult(String cityName, String districtName) {
        Intent intent = new Intent();
        intent.putExtra("cityName", cityName);
        intent.putExtra("districtName", districtName);
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_city, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (parentId == 0) {
                    activity.finish();
                } else {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.menu_location:
                BDLocation location = activity.mLocation;
                if (!NetWorkUtils.isNetworkAvailable(getContext())) {
                    ToastUtils.showToast("网络异常！");
                } else if (location != null) {
                    setResult(location.getCity(), location.getDistrict());
                } else {
                    setResult("", "");
                }
                break;
        }
        return true;
    }
}
