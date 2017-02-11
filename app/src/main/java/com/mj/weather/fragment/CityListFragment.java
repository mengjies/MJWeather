package com.mj.weather.fragment;

import android.app.Activity;
import android.content.Context;
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
import com.mj.weather.activity.CityListActivity;
import com.mj.weather.entity.CityItem;
import com.mj.weather.entity.CityUtils;
import com.mj.weather.listener.MyLocationListener;
import com.mj.weather.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/1/20.
 */

public class CityListFragment extends BasicFragment {
    private static final String TAG = "CityListFragment";

    private List<CityItem> dataList = new ArrayList<>();
    private ActionBar actionBar;
    private CityListActivity activity;
    private int parentId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在Fragment中使用菜单
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        activity = (CityListActivity) getActivity();

        Bundle bundle = getArguments();
        parentId = bundle.getInt("parentId");
        String cityName = bundle.getString("cityName");
        dataList = CityUtils.getCityListByParent(parentId);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(cityName);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        ListView listView = (ListView) view.findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                CityUtils.getNameArrayList(dataList));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityItem district = dataList.get(position);
                int count = DataSupport.where("ParentID = ?", district.getAreaID() + "").count(CityItem.class);
                if (count == 0) {
                    CityItem city = DataSupport.where("AreaId = ?", district.getParentID() + "").findFirst(CityItem.class);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
