package com.mj.weather.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mj.weather.R;
import com.mj.weather.account.UserProtocol;
import com.mj.weather.base.BaseActivity;
import com.mj.weather.utils.JsonUtils;
import com.mj.weather.utils.LogUtils;
import com.mj.weather.utils.ToastUtils;
import com.mj.weather.utils.TxtCheckout;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SignInActivity";

    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordAgain;
    private EditText etEmail;
    private Button btSubmit;

    public static void actionStart(Activity act) {
        Intent intent = new Intent(act, SignInActivity.class);
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("注册");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        initView();
    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPasswordAgain = (EditText) findViewById(R.id.et_password_again);
        etEmail = (EditText) findViewById(R.id.et_email);
        btSubmit = (Button) findViewById(R.id.bt_submit);

        btSubmit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_login:
                LoginActivity.actionStart(this);
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
        String username = etUsername.getText().toString().trim();
        if (!TxtCheckout.isUsername(username)) {
            ToastUtils.showToast(this, "用户名不合法！");
            return;
        }
        String password = etPassword.getText().toString().trim();
        if (!TxtCheckout.isPassword(password)) {
            ToastUtils.showToast(this, "密码不合法！");
            return;
        }
        String passwordAgain = etPasswordAgain.getText().toString().trim();
        if (!password.equals(passwordAgain)) {
            ToastUtils.showToast(this, "两次密码不同！");
            return;
        }
        String email = etEmail.getText().toString().trim();
        if (!TxtCheckout.isEmail(email)) {
            ToastUtils.showToast(this, "邮箱不合法！");
            return;
        }

        UserProtocol.register(username, password, email, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, e.getMessage());
                ToastUtils.showToast(SignInActivity.this, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                UserProtocol.RspRegister rspRegister = JsonUtils.toObject(json, UserProtocol.RspRegister.class);
                if (rspRegister != null) {
                    if (rspRegister.retCode.equals("200")) {
                        //
                        ToastUtils.showTestToast(SignInActivity.this, rspRegister.uid);
                        //跳转到登陆页面
                        LoginActivity.actionStart(SignInActivity.this);
                        finish();
                    }else{
                        LogUtils.e(TAG, rspRegister.msg);
                        ToastUtils.showToast(SignInActivity.this, rspRegister.msg);
                    }
                }
            }
        });

    }
}
