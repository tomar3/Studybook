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

public class LoginPresenter implements LoginContract.Presenter {

    @NonNull
    private LoginContract.View mLoginView;

    @NonNull
    private UsersRepository mUsersRepository;

    public LoginPresenter(@NonNull LoginContract.View loginView, @NonNull UsersRepository usersRepository) {
        mLoginView = loginView;
        mUsersRepository = usersRepository;
    }

    @Override
    public void loadLogin() {
        mLoginView.showLoginUi();
    }

    @Override
    public void loadSkipLogin() {
        mLoginView.showDashboardUi();
    }

    @Override
    public void loadCurrentUser() {
        User currentUser = mUsersRepository.getCurrentUser();

        if(currentUser != null){
            mLoginView.showDashboardUi();
        }
    }
}
