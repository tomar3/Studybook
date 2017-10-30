/*
 * Created by Talab Omar on 10/27/17 4:39 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/27/17 4:39 PM
 */

package com.codertal.studybook.features.authentication.login;


import android.support.annotation.NonNull;

import com.codertal.studybook.data.users.User;
import com.codertal.studybook.data.users.source.UsersRepository;
import com.codertal.studybook.features.authentication.login.domain.LoginResponse;
import com.codertal.studybook.util.ClickManager;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.LOGIN_CANCELLED;
import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.LOGIN_SUCCESS;
import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.NETWORK_ERROR;

public class LoginPresenter extends LoginContract.Presenter {

    @NonNull
    private LoginContract.View mLoginView;

    private UsersRepository mUsersRepository;

    private final Scheduler mMainScheduler;

    private final ClickManager mClickManager;

    public LoginPresenter(@NonNull LoginContract.View loginView, @NonNull UsersRepository usersRepository,
                          @NonNull Scheduler mainScheduler, @NonNull ClickManager clickManager) {
        mLoginView = loginView;
        mUsersRepository = usersRepository;
        mMainScheduler = mainScheduler;
        mClickManager = clickManager;
    }

    @Override
    public void subscribe() {
        loadCurrentUser();
    }

    @Override
    public void loadLogin(int viewId) {
        if (mClickManager.isClickable(viewId)) {
            mLoginView.showLoginUi();
        }
    }

    @Override
    public void loadSkipLogin(int viewId) {
        if (mClickManager.isClickable(viewId)) {
            mLoginView.showSplashScreen();
        }
    }


    public void loadCurrentUser() {
        mCompositeDisposable.add(mUsersRepository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<User>(){
                    @Override
                    public void onSuccess(User currentUser) {
                        mLoginView.showSplashScreen();
                    }

                    @Override
                    public void onError(Throwable e) {
                        /* No need to update ui if there is no current user or an error retrieving it
                        *  The user can simply login again
                        */
                    }
                }));
    }

    @Override
    public void processLoginResult(LoginResponse loginResponse) {

        // Successfully logged in
        if (loginResponse.getLoginResult() == LOGIN_SUCCESS) {
            mLoginView.showSplashScreen();
        } else {
            // Log in failed

            //Determine which error message to show
            if (loginResponse.getLoginResult() == LOGIN_CANCELLED) {
                mLoginView.showCancelledMessage();

            }else if (loginResponse.getLoginResult() == NETWORK_ERROR) {
                mLoginView.showNetworkErrorMessage();

            }else {
                mLoginView.showUnknownErrorMessage();
            }
        }
    }
}
