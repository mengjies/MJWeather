package com.mj.weather.weather.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.mj.weather.weather.activity.LoginActivity;
import com.mj.weather.weather.contract.SignInContract;
import com.mj.weather.weather.model.http.entity.UserBean;
import com.mj.weather.common.base.BaseFragment;
import com.mj.weather.common.util.ToastUtils;
import com.mj.weather.common.util.TxtCheckout;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by MengJie on 2017/6/20.
 */

public class SignInFragment extends BaseFragment implements SignInContract.View, View.OnClickListener {
    private static final String TAG = "SignInFragment";

    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordAgain;
    private EditText etEmail;
    private Button btSubmit;

    private SignInContract.Presenter mPresenter;
    private Context context;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void setPresenter(@NonNull SignInContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        // If true, the fragment has menu items to contribute.
        setHasOptionsMenu(true);
        context = getContext();

        etUsername = (EditText) view.findViewById(R.id.et_username);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        etPasswordAgain = (EditText) view.findViewById(R.id.et_password_again);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        btSubmit = (Button) view.findViewById(R.id.bt_submit);

        btSubmit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_sign, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_in:
                LoginActivity.actionStart(getActivity());
                getActivity().finish();
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
            ToastUtils.showToast("用户名不合法！");
            return;
        }
        String password = etPassword.getText().toString().trim();
        if (!TxtCheckout.isPassword(password)) {
            ToastUtils.showToast("密码不合法！大小写字母、下划线、数字、6-16位");
            return;
        }
        String passwordAgain = etPasswordAgain.getText().toString().trim();
        if (!password.equals(passwordAgain)) {
            ToastUtils.showToast("两次密码不同！");
            return;
        }
        String email = etEmail.getText().toString().trim();
        if (!TxtCheckout.isEmail(email)) {
            ToastUtils.showToast("邮箱不合法！");
            return;
        }

        mPresenter.register(username, password, email);

    }

    @Override
    public Observer<UserBean.RspRegister> registerObserver() {
        return new Observer<UserBean.RspRegister>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull UserBean.RspRegister rspRegister) {
                if (rspRegister != null) {
                    if (rspRegister.retCode.equals("200")) {
                        //
                        ToastUtils.showTestToast(rspRegister.uid);
                        //跳转到登陆页面
                        LoginActivity.actionStart(getActivity());
                        getActivity().finish();
                    } else {
                        Logger.e(rspRegister.msg);
                        ToastUtils.showToast(rspRegister.msg);
                    }
                }
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
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
