/*
 * Created by Talab Omar on 10/27/17 4:39 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/27/17 4:39 PM
 */

package com.codertal.studybook.features.authentication.login;


import android.support.annotation.NonNull;

public class LoginPresenter implements LoginContract.Presenter {

    @NonNull
    private LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginView = loginView;
    }

    @Override
    public void loadLogin() {
        mLoginView.showLoginUi();
    }

    @Override
    public void loadSkipLogin() {
        mLoginView.showDashboardUi();
    }
}
