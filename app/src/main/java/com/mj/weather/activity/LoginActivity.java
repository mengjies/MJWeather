package com.mj.weather.activity;

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

public class LoginActivity extends BasicActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btSubmit;

    public static void actionStart(Activity act) {
        Intent intent = new Intent(act, LoginActivity.class);
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("登录");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        initView();
    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btSubmit = (Button) findViewById(R.id.bt_submit);

        btSubmit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_sign_in:
                SignInActivity.actionStart(this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                submit();
                break;
        }
    }

    private void submit() {
        final String username = etUsername.getText().toString().trim();
        if (!TxtCheckout.isUsername(username)) {
            ToastUtils.showToast(this, "用户名不合法！");
            return;
        }
        final String password = etPassword.getText().toString().trim();
        if (!TxtCheckout.isPassword(password)) {
            ToastUtils.showToast(this, "密码不合法！");
            return;
        }

        UserProtocol.login(username, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, e.getMessage());
                ToastUtils.showToast(LoginActivity.this, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                UserProtocol.RspLogin rspLogin = JsonUtils.toObject(json, UserProtocol.RspLogin.class);
                if (rspLogin != null) {
                    if (rspLogin.retCode.equals("200")) {
                        //友盟账号统计
                        MobclickAgent.onProfileSignIn(username);
                        //保存数据
                        User user = DataSupport.findFirst(User.class);
                        if (user == null) {
                            user = new User();
                        }
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setToken(rspLogin.result.token);
                        user.setUid(rspLogin.result.uid);
                        user.save();
                        //跳转到MainActivity
                        MainActivity.actionStart(LoginActivity.this);
                        finish();
                    } else {
                        LogUtils.e(TAG, rspLogin.msg);
                        ToastUtils.showToast(LoginActivity.this, rspLogin.msg);
                    }
                }
            }
        });

    }
}
