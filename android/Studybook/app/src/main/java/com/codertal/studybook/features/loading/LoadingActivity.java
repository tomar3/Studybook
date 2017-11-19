/*
 * Created by Talab Omar on 10/30/17 3:26 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/30/17 3:26 PM
 */

package com.codertal.studybook.features.loading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.codertal.studybook.R;
import com.codertal.studybook.data.database.DatabaseRepository;
import com.codertal.studybook.data.users.UsersRepository;


public class LoadingActivity extends AppCompatActivity implements LoadingContract.View{

    //@InjectExtra
    String mUserId;

    private LoadingContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        //Dart.inject(this);
        mUserId = "";

        mPresenter = new LoadingPresenter(this, mUserId, new UsersRepository(), new DatabaseRepository());

        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void showDashboardUi() {

    }
}
