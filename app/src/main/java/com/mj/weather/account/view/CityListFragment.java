package com.mj.weather.account.view;

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
import com.mj.weather.account.activity.CityListActivity;
import com.mj.weather.account.component.DaggerCityRepositoryComponent;
import com.mj.weather.account.model.dp.CityItem;
import com.mj.weather.account.model.repository.CityRepository;
import com.mj.weather.common.base.BaseFragment;
import com.mj.weather.common.common.MyLocationListener;
import com.mj.weather.common.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/1/20.
 */

public class CityListFragment extends BaseFragment {
    private static final String TAG = "CityListFragment";

    private List<CityItem> dataList = new ArrayList<>();
    private CityListActivity activity;
    private int parentId;
    private CityRepository cityRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在Fragment中使用菜单
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.citylist_fragment, container, false);
        activity = (CityListActivity) getActivity();

        cityRepository = DaggerCityRepositoryComponent.builder()
                .build()
                .proviceCityRepository();

        Bundle bundle = getArguments();
        parentId = bundle.getInt("parentId");
        String cityName = bundle.getString("cityName");
        dataList = cityRepository.getCityListByParent(parentId);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(cityName);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        ListView listView = (ListView) view.findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                cityRepository.getNameArrayList(dataList));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityItem district = dataList.get(position);
                if (!cityRepository.hasChildren(district.getAreaID())) {
                    // 天气接口只能查到市级城市
                    CityItem city = cityRepository.getCityById(district.getParentID());
                    setResult(city.getTheName(), district.getTheName());
                } else {
                    activity.replaceFragment(district.getAreaID(), district.getTheName());
                }
            }
        });
        return view;
    }

    private void setResult(String cityName, String districtName) {
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
                BDLocation location = MyLocationListener.location;
                if (location != null) {
                    setResult(location.getCity(), location.getDistrict());
                } else {
                    ToastUtils.showToast(getActivity(), "定位失败！");
                }
                break;
        }
        return true;
    }
}
