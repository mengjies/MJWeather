package com.mj.weather.account.view;

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
import com.mj.weather.account.activity.MainActivity;
import com.mj.weather.account.activity.SignInActivity;
import com.mj.weather.account.contract.LoginContract;
import com.mj.weather.account.model.http.entity.UserBean;
import com.mj.weather.common.base.BaseFragment;
import com.mj.weather.common.util.ToastUtils;
import com.mj.weather.common.util.TxtCheckout;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * Created by MengJie on 2017/2/18.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View, View.OnClickListener {
    private static final String TAG = "LoginFragment";

    private EditText etUsername;
    private EditText etPassword;
    private Button btSubmit;
    private LoginContract.Presenter mPresenter;
    private String username;
    private String password;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

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

    /**
     * 提交
     */
    private void submit() {
        username = etUsername.getText().toString().trim();
        if (!TxtCheckout.isUsername(username)) {
            ToastUtils.showToast("用户名不合法！");
            return;
        }
        password = etPassword.getText().toString().trim();
        if (!TxtCheckout.isPassword(password)) {
            ToastUtils.showToast("密码不合法！大小写字母、下划线、数字、6-16位");
            return;
        }

        mPresenter.login(username, password);

    }


    /**
     * 登录观察者
     *
     * @return
     */
    @Override
    public Observer<UserBean.RspLogin> loginObserver() {
        return new Observer<UserBean.RspLogin>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Logger.d("onSubscribe");
            }

            @Override
            public void onNext(@NonNull UserBean.RspLogin rspLogin) {
                if (rspLogin != null) {
                    if (rspLogin.retCode.equals("200")) {
                        //友盟账号统计
                        MobclickAgent.onProfileSignIn(username);
                        //保存数据
                        mPresenter.saveLoginData(username, password, rspLogin);
                        //跳转到MainActivity
                        MainActivity.actionStart(getActivity());
                        getActivity().finish();
                    } else {
                        Logger.e(rspLogin.msg);
                        ToastUtils.showToast(rspLogin.msg);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.e(e.getMessage());
                ToastUtils.showToast(e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        };
    }
}
