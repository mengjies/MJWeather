package com.mj.weather.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.mj.weather.R;
import com.mj.weather.adapter.AirAdapter;
import com.mj.weather.adapter.ForecastAdapter;
import com.mj.weather.adapter.TipAdapter;
import com.mj.weather.common.MyNestedScrollView;
import com.mj.weather.entity.JsonCache;
import com.mj.weather.entity.JsonCacheUtils;
import com.mj.weather.entity.User;
import com.mj.weather.http.HeProtocol;
import com.mj.weather.http.HttpUtils;
import com.mj.weather.listener.MyLocationListener;
import com.mj.weather.utils.JsonUtils;
import com.mj.weather.utils.LogUtils;
import com.mj.weather.utils.NetWorkUtils;
import com.mj.weather.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * MainActivity
 * 实现双击退出
 * 定位
 */
public class MainActivity extends BasicActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private long firstClick = 0;

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private CircleImageView ivHead;
    private TextView tvUserName;
    private TextView tvSignOut;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int[] imageIds = {R.drawable.img_jessica, R.drawable.img_seohyun, R.drawable.img_yoona, R.drawable.img_hyoyeon,
            R.drawable.img_sooyoung, R.drawable.img_sunny, R.drawable.img_taeyeon, R.drawable.img_tiffany, R.drawable.img_yuri};
    private ImageView ivBackground;
    private Toolbar toolbar;
    private ActionBar actionBar;

    //百度地图
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private TextView tvTmpNow;
    private TextView tvTxtNow;
    private TextView tvWindNow;

    private RecyclerView rvForecast;
    private RecyclerView rvAqi;
    private RecyclerView rvSuggestion;
    private List<HeProtocol.Daily_forecast> forecastList = new ArrayList<>();
    private List<HeProtocol.AirQuality> aqiList = new ArrayList<>();
    private List<HeProtocol.Tip> tipList = new ArrayList<>();
    private MyNestedScrollView scrollView;

    private String cityName;
    private String districtName;
    private ForecastAdapter forecastAdapter;
    private AirAdapter airAdapter;
    private TipAdapter tipAdapter;

    public static void actionStart(Activity act) {
        Intent intent = new Intent(act, MainActivity.class);
        act.startActivity(intent);
        act.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivBackground = (ImageView) findViewById(R.id.iv_background);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        ivHead = (CircleImageView) headerView.findViewById(R.id.iv_head);
        tvUserName = (TextView) headerView.findViewById(R.id.tv_username);
        tvSignOut = (TextView) headerView.findViewById(R.id.tv_sign_out);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        scrollView = (MyNestedScrollView) findViewById(R.id.nested_scroll_view);

        tvTmpNow = (TextView) findViewById(R.id.tv_tmp_now);
        tvTxtNow = (TextView) findViewById(R.id.tv_txt_now);
        tvWindNow = (TextView) findViewById(R.id.tv_wind_now);

        rvForecast = (RecyclerView) findViewById(R.id.recycler_view_forecast);
        rvAqi = (RecyclerView) findViewById(R.id.recycler_view_aqi);
        rvSuggestion = (RecyclerView) findViewById(R.id.recycler_view_suggestion);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            actionBar.setIcon(R.mipmap.ic_location);
        }

        ivHead.setOnClickListener(this);
        tvUserName.setOnClickListener(this);
        tvSignOut.setOnClickListener(this);

        forecastAdapter = new ForecastAdapter(forecastList);
        LinearLayoutManager forecastLayoutManager = new LinearLayoutManager(this);
        rvForecast.setLayoutManager(forecastLayoutManager);
        rvForecast.setAdapter(forecastAdapter);

        airAdapter = new AirAdapter(aqiList);
        GridLayoutManager airLayoutManager = new GridLayoutManager(this, 2);
        rvAqi.setLayoutManager(airLayoutManager);
        rvAqi.setAdapter(airAdapter);

        tipAdapter = new TipAdapter(tipList);
        GridLayoutManager tipLayoutManager = new GridLayoutManager(this, 2);
        rvSuggestion.setLayoutManager(tipLayoutManager);
        rvSuggestion.setAdapter(tipAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.this.onRefresh();
            }
        });

        onTitleClick();

        onNavItemSelected();
    }

    private void initData() {
        loadBackground();
        //先加载缓存数据
        User user = DataSupport.findFirst(User.class);
        if (user != null) {
            actionBar.setTitle(user.getDistrictName());
            String url = HeProtocol.getUrl(HeProtocol.port_weather, user.getCityName());
            JsonCache cache = JsonCacheUtils.getJsonCache(url);
            if (cache != null) {
                loadWeatherData(cache.getJson());
            }
        }

        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        //配置LocationClientOption
        initLocation();
        //开始定位
        startLocate();
        //定位完成
        onLocationComplete();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }

    private void loadUserData() {
        User user = DataSupport.findFirst(User.class);
        if (user != null) {
            tvUserName.setText(user.getUsername());
            tvUserName.setEnabled(false);
            tvSignOut.setVisibility(View.VISIBLE);
        } else {
            tvUserName.setText("登录/注册");
            tvUserName.setEnabled(true);
            tvSignOut.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head:
                //设置头像
                ToastUtils.showToast(this, "设置头像！");
                break;
            case R.id.tv_username:
                LoginActivity.actionStart(this);
                break;
            case R.id.tv_sign_out:
                signOut();
                break;
            default:
                break;
        }
    }

    private void signOut() {
        //停止账号统计
        MobclickAgent.onProfileSignOff();
        //删除用户数据
        DataSupport.deleteAll(User.class);
        loadUserData();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 10000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private void onLocationComplete() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final BDLocation location = MyLocationListener.location;
                    if (location != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cityName = location.getCity();
                                districtName = location.getDistrict();
                                onLocationChanged();
                            }
                        });
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void onLocationChanged() {
        //设置标题
        actionBar.setTitle(districtName);
        //缓存位置信息
        User user = DataSupport.findFirst(User.class);
        if (user == null) {
            user = new User();
        }
        user.setDistrictName(districtName);
        user.setCityName(cityName);
        user.save();
        //请求天气
        HttpUtils httpsUtils = HttpUtils.getHttpsUtils();
        final String url = HeProtocol.getUrl(HeProtocol.port_weather, cityName);
        //判断是否有网络
        if (!NetWorkUtils.isNetworkAvailable(this)) {
            ToastUtils.showToast(this, "网络不可用！");
            //使用缓存
            JsonCache jsonCache = JsonCacheUtils.getJsonCache(url);
            if (jsonCache != null) {
                loadWeatherData(jsonCache.getJson());
            }
            return;
        }
        httpsUtils.sendRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, e.getMessage());
                ToastUtils.showToast(MainActivity.this, "天气获取失败!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                //缓存json数据
                JsonCacheUtils.save(url, json);
                //加载数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadWeatherData(json);
                    }
                });
            }
        });
    }

    private void loadWeatherData(String json) {
        HeProtocol.Weather weather = JsonUtils.toObject(json, HeProtocol.Weather.class);
        HeProtocol.HeWeather5 heWeather5 = weather.getHeWeather5().get(0);
        if (heWeather5.getStatus().equals("ok")) {
            HeProtocol.Now now = heWeather5.getNow();
            if (now != null) {
                tvTmpNow.setText(now.getTmp() + "");
                tvTxtNow.setText(now.getCond().getTxt());
                tvWindNow.setText("湿度：" + now.getHum() + "    " + now.getWind().getDir() + "：" + now.getWind().getSc());
            } else {
                LogUtils.e(TAG, "now=null");
            }

            List<HeProtocol.Daily_forecast> forecasts = heWeather5.getDaily_forecast();
            forecastList.clear();
            if (forecasts != null) {
                forecastList.addAll(forecasts);
            } else {
                LogUtils.e(TAG, "forecasts=null");
            }
            forecastAdapter.notifyDataSetChanged();

            HeProtocol.Aqi aqi = heWeather5.getAqi();
            aqiList.clear();
            if (aqi != null) {
                aqiList.addAll(HeProtocol.getAqiList(aqi.getCity()));
            } else {
                ToastUtils.showTestToast(this, "aqi=null");
                LogUtils.e(TAG, "aqi=null");
            }
            airAdapter.notifyDataSetChanged();

            HeProtocol.Suggestion suggestion = heWeather5.getSuggestion();
            tipList.clear();
            if (suggestion != null) {
                tipList.addAll(HeProtocol.getSuggestionList(suggestion));
            } else {
                LogUtils.e(TAG, "suggestion=null");
            }
            tipAdapter.notifyDataSetChanged();

        } else {
            ToastUtils.showToast(this, heWeather5.getStatus());
        }
    }

    private void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //重新获取 当前选中位置的 天气数据
                        onLocationChanged();
                        //重新加载背景
                        loadBackground();
                        //停止刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void loadBackground() {
        int index = new Random().nextInt(imageIds.length);
        Glide.with(this).load(imageIds[index]).bitmapTransform(new BlurTransformation(this, 10)).into(ivBackground);
    }

    private void onTitleClick() {
        try {
            Field field = toolbar.getClass().getDeclaredField("mTitleTextView");
            field.setAccessible(true);
            TextView tvTitle = (TextView) field.get(toolbar);
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CityListActivity.actionStart(MainActivity.this, 1);
                }
            });
            field.set(toolbar, tvTitle);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterPermissionGranted(1)
    private void startLocate() {
        String[] perms = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            //有权限 继续业务 开启定位
            mLocationClient.start();
            ToastUtils.showTestToast(this, "定位开启");
        } else {
            //无权限 申请权限
            EasyPermissions.requestPermissions(this, getString(R.string.permission_location_alert), 1, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //some permissions granted
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // some permission denied
        // 判断是否有权限被永久禁止，如果有提示到应用管理界面设置
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.permission_location_not_granted))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.bt_setting))
                    .setNegativeButton(getString(R.string.bt_cancel), null)
                    .setRequestCode(2)
                    .build()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    cityName = data.getStringExtra("cityName");
                    districtName = data.getStringExtra("districtName");
                    onLocationChanged();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case android.R.id.title:
            case android.R.id.icon:
                CityListActivity.actionStart(this, 1);
                break;
            case R.id.menu_backup:
                ToastUtils.showToast(this, "分享");
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onNavItemSelected() {

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_settings:
                        ToastUtils.showToast(MainActivity.this, "设置");
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    //双击退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick > 1000) {
                ToastUtils.showToast(this, "再次点击退出");
                firstClick = secondClick;
                return true;
            } else {
                //退出应用
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
