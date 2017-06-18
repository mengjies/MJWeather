package com.mj.weather.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mj.weather.R;
import com.mj.weather.base.BaseActivity;
import com.mj.weather.utils.ActivityUtils;
import com.mj.weather.weather.MainActivity;
import com.mj.weather.weather.SignInActivity;
import com.mj.weather.entity.User;
import com.mj.weather.http.UserProtocol;
import com.mj.weather.utils.JsonUtils;
import com.mj.weather.utils.LogUtils;
import com.mj.weather.utils.ToastUtils;
import com.mj.weather.utils.TxtCheckout;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity{
    private static final String TAG = "LoginActivity";


    public static void actionStart(Activity act) {
        Intent intent = new Intent(act, LoginActivity.class);
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("登录");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //加载view
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }

        //创建presenter
        new LoginPresenter(loginFragment);

    }

    //后退
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
