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

import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.LOGIN_CANCELLED;
import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.LOGIN_SUCCESS;
import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.NETWORK_ERROR;

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
        mLoginView.enableButtons(false);
        mLoginView.showLoginUi();
    }

    @Override
    public void loadSkipLogin() {
        mLoginView.enableButtons(false);
        mLoginView.showDashboardUi();
    }

    @Override
    public void loadCurrentUser() {
        User currentUser = mUsersRepository.getCurrentUser();

        if(currentUser != null){
            mLoginView.showDashboardUi();
        }
    }

    @Override
    public void processLoginResult(LoginResponse loginResponse) {

        // Successfully logged in
        if (loginResponse.getLoginResult() == LOGIN_SUCCESS) {
            mLoginView.showDashboardUi();
        } else {
            // Log in failed

            mLoginView.enableButtons(true);

            if (loginResponse.getLoginResult() == LOGIN_CANCELLED) {
                mLoginView.showMessage("Login cancelled");
            }else if (loginResponse.getLoginResult() == NETWORK_ERROR) {
                mLoginView.showMessage("Network error");
            }else {
                mLoginView.showMessage("Unable to login, please try again");
            }
        }
    }
}
