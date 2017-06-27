package com.mj.weather.account.presenter;

import android.os.Process;
import android.support.annotation.NonNull;

import com.mj.weather.account.contract.LoginContract;
import com.mj.weather.account.model.http.entity.UserBean;
import com.mj.weather.account.model.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by MengJie on 2017/2/18.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mLoginView;
    private UserRepository mUserRepository;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    LoginPresenter(@NonNull LoginContract.View loginView, UserRepository userRepository) {
        mLoginView = loginView;
        mUserRepository = userRepository;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    @Override
    public void login(String username, String password) {
        String s = Process.myPid() + " - " + Process.myTid() + " - " + Process.myUid();
        mUserRepository.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mLoginView.loginObserver());
    }

    /**
     * 保存登录数据
     *
     * @param username
     * @param password
     * @param rspLogin
     */
    @Override
    public void saveLoginData(String username, String password, UserBean.RspLogin rspLogin) {
        mUserRepository.saveUserData(username, password, rspLogin);
    }
}
