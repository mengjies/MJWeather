package com.mj.weather.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.mj.weather.R;
import com.mj.weather.account.component.DaggerWeatherComponent;
import com.mj.weather.account.model.dp.entity.User;
import com.mj.weather.account.module.WeatherViewModule;
import com.mj.weather.account.presenter.WeatherPresenter;
import com.mj.weather.account.view.WeatherFragment;
import com.mj.weather.common.base.BaseActivity;
import com.mj.weather.common.common.ActivityUtils;
import com.mj.weather.common.util.LocationManager;
import com.mj.weather.common.util.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * MainActivity
 * 实现双击退出
 * 定位
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private ActionBar actionBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.weatherFrame)
    FrameLayout weatherFrame;
    @Inject
    WeatherPresenter mWeatherPresenter;
    private ImageView ivHead;
    private TextView tvUserName;
    private TextView tvSignOut;
    private int[] imageIds = {R.drawable.img_jessica, R.drawable.img_seohyun, R.drawable.img_yoona,
            R.drawable.img_hyoyeon, R.drawable.img_sooyoung, R.drawable.img_sunny,
            R.drawable.img_taeyeon, R.drawable.img_tiffany, R.drawable.img_yuri};
    private WeatherFragment weatherFragment;
    private long firstClick = 0;
    private String cityName;
    private String districtName;

    public static void actionStart(Activity act) {
        Intent intent = new Intent(act, MainActivity.class);
        act.startActivity(intent);
        act.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.weatherFrame);
        if (weatherFragment == null) {
            weatherFragment = WeatherFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), weatherFragment, R.id.weatherFrame);
        }

        DaggerWeatherComponent.builder()
                .weatherViewModule(new WeatherViewModule(weatherFragment))
                .build()
                .inject(this);

        //初始化toolbar
        initToolbar();
        //初始化侧滑栏
        initNavView();

        //加载缓存
        mWeatherPresenter.getWeatherCache();
        //加载背景
        loadBackground();
        //开始定位
        startLocation();

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            actionBar.setIcon(R.mipmap.ic_location);
        }

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

    private void initNavView() {
        View headerView = navView.getHeaderView(0);
        ivHead = (ImageView) headerView.findViewById(R.id.iv_head);
        tvUserName = (TextView) headerView.findViewById(R.id.tv_username);
        tvSignOut = (TextView) headerView.findViewById(R.id.tv_sign_out);

        ivHead.setOnClickListener(this);
        tvUserName.setOnClickListener(this);
        tvSignOut.setOnClickListener(this);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_settings:
                        //ToastUtils.showToast("设置");
                        CrashReport.testJavaCrash();
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head:
                //设置头像
                ToastUtils.showToast("设置头像！");
                break;
            case R.id.tv_username:
                LoginActivity.actionStart(this);
                break;
            case R.id.tv_sign_out:
                signOut();
                break;
            case android.R.id.title:
            case android.R.id.icon:
                CityListActivity.actionStart(this, 1);
                break;
            default:
                break;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 定位
     */
    public void startLocation() {
        LocationManager.startLocation(new LocationManager.OnLocationListener() {
            @Override
            public void onLocation(BDLocation location) {
                cityName = location.getCity();
                districtName = location.getDistrict();
                onLocationChanged();
                //关闭
                LocationManager.stopLocation();
            }
        });
    }

    /**
     * 加载背景
     */
    public void loadBackground() {
        int index = new Random().nextInt(imageIds.length);
        Glide.with(this).load(imageIds[index]).bitmapTransform(new BlurTransformation(this, 10)).into(ivBackground);
    }

    /**
     * 位置改变
     */
    public void onLocationChanged() {
        actionBar.setTitle(districtName);
        mWeatherPresenter.getWeather(cityName);
    }

    /**
     * loadUserData
     */
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
        Glide.with(this).load(R.drawable.img_head_default)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivHead);
    }

    /**
     * 退出登录
     */
    private void signOut() {
        //停止账号统计
        MobclickAgent.onProfileSignOff();
        //删除用户数据
        DataSupport.deleteAll(User.class);
        loadUserData();
    }

    //双击退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick > 1000) {
                ToastUtils.showToast("再次点击退出");
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
    protected void onPause() {
        super.onPause();
        LocationManager.stopLocation();
    }
}
