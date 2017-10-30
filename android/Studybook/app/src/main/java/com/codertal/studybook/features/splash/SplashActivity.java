/*
 * Created by Talab Omar on 10/30/17 3:26 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/30/17 3:26 PM
 */

package com.codertal.studybook.features.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.codertal.studybook.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
