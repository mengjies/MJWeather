package com.mj.weather.weather.presenter;

import com.mj.weather.weather.contract.SignInContract;
import com.mj.weather.weather.model.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MengJie on 2017/6/20.
 */

public class SignInPresenter implements SignInContract.Presenter {
    private final SignInContract.View mSignInView;
    private UserRepository mUserRepository;

    @Inject
    SignInPresenter(SignInContract.View signInFragment, UserRepository userRepository) {
        mSignInView = signInFragment;
        mUserRepository = userRepository;

        mSignInView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void register(String username, String password, String email) {
        mUserRepository.register(username, password, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSignInView.registerObserver());
    }
}
