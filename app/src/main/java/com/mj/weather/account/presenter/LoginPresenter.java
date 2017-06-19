package com.mj.weather.account.presenter;

import android.support.annotation.NonNull;

import com.mj.weather.account.contract.LoginContract;
import com.mj.weather.account.model.http.entity.UserBean;
import com.mj.weather.account.model.repository.UserRepository;
import com.mj.weather.account.model.dp.User;

import org.litepal.crud.DataSupport;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mj.weather.utils.Proconditions.checkNotNull;

/**
 * Created by MengJie on 2017/2/18.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginView = checkNotNull(loginView);
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    /**
     * 登录
     * @param username
     * @param password
     */
    @Override
    public void login(String username, String password) {
        UserRepository.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mLoginView.loginObserver());

    }

    /**
     * 保存登录数据
     * @param username
     * @param password
     * @param rspLogin
     */
    @Override
    public void saveLoginData(String username, String password, UserBean.RspLogin rspLogin) {
        User user = DataSupport.findFirst(User.class);
        if (user == null) {
            user = new User();
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setToken(rspLogin.result.token);
        user.setUid(rspLogin.result.uid);
        user.save();
    }
}
