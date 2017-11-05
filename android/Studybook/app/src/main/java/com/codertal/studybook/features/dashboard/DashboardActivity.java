/*
 * Created by Talab Omar on 11/5/17 12:47 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/5/17 12:47 PM
 */

package com.codertal.studybook.features.dashboard;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codertal.studybook.R;
import com.f2prateek.dart.HensonNavigable;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

@HensonNavigable
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withMenuLayout(R.layout.drawer_menu_dashboard)
                .inject();

        BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(tabId -> {});


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        FlowingDrawer drawer = findViewById(R.id.drawerlayout);
//        if (drawer.is(GravityCompat.START)) {
//            drawer.close(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

}
