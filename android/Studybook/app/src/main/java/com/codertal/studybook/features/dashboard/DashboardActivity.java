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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codertal.studybook.R;
import com.codertal.studybook.base.BaseTabFragment;
import com.codertal.studybook.features.homework.HomeworkFragment;
import com.codertal.studybook.features.manage.ManageFragment;
import com.f2prateek.dart.HensonNavigable;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import timber.log.Timber;

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
        bottomBar.setOnTabSelectListener(tabId -> {
            BaseTabFragment selectedFragment;

            switch (tabId){
                case R.id.tab_home:
                    selectedFragment = HomeworkFragment.newInstance();
                    break;

                case R.id.tab_manage:
                    selectedFragment = ManageFragment.newInstance();
                    break;

                default:
                    //TODO: Change to null after creating cases for all tabs
                    selectedFragment = ManageFragment.newInstance();
            }

            if(selectedFragment == null){
                throw new RuntimeException("Selected tab has an unchecked res id");
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_dash_fragment, selectedFragment, selectedFragment.getFragmentTag());
            transaction.commit();

            Timber.d("Selected Fragment Tag: " + selectedFragment.getFragmentTag());
        });

        //Manually displaying the first fragment - one time only
        BaseTabFragment defaultFragment = HomeworkFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_dash_fragment, defaultFragment, defaultFragment.getFragmentTag());
        transaction.commit();
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
