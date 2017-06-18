package com.mj.weather.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mj.weather.R;
import com.mj.weather.base.BaseFragment;
import com.mj.weather.entity.User;
import com.mj.weather.http.UserProtocol;
import com.mj.weather.utils.JsonUtils;
import com.mj.weather.utils.LogUtils;
import com.mj.weather.utils.ToastUtils;
import com.mj.weather.utils.TxtCheckout;
import com.mj.weather.weather.MainActivity;
import com.mj.weather.weather.SignInActivity;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mj.weather.utils.Proconditions.checkNotNull;

/**
 * Created by MengJie on 2017/2/18.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View, View.OnClickListener {
    private static final String TAG = "LoginFragment";

    private EditText etUsername;
    private EditText etPassword;
    private Button btSubmit;
    private LoginContract.Presenter mPresenter;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        // If true, the fragment has menu items to contribute.
        setHasOptionsMenu(true);

        etUsername = (EditText) view.findViewById(R.id.et_username);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btSubmit = (Button) view.findViewById(R.id.bt_submit);

        btSubmit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                submit();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_login, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_in:
                SignInActivity.actionStart(getActivity());
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit() {
        final String username = etUsername.getText().toString().trim();
        if (!TxtCheckout.isUsername(username)) {
            ToastUtils.showToast(getContext(), "用户名不合法！");
            return;
        }
        final String password = etPassword.getText().toString().trim();
        if (!TxtCheckout.isPassword(password)) {
            ToastUtils.showToast(getContext(), "密码不合法！");
            return;
        }

        UserProtocol.login(username, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, e.getMessage());
                ToastUtils.showToast(getContext(), e.getMessage());
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
                        MainActivity.actionStart(getActivity());
                        getActivity().finish();
                    } else {
                        LogUtils.e(TAG, rspLogin.msg);
                        ToastUtils.showToast(getContext(), rspLogin.msg);
                    }
                }
            }
        });

    }
}
