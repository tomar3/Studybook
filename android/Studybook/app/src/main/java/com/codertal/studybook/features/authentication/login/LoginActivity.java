/*
 * Created by Talab Omar on 10/26/17 1:38 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/26/17 1:18 PM
 */

package com.codertal.studybook.features.authentication.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codertal.studybook.R;

import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void showLoginUi() {

    }


    @OnClick(R.id.btn_login)
    public void onLoginClick(){
        mPresenter.loadLogin();
    }
}
