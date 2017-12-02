/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.util;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class ViewUtils {

    public static void turnOffToolbarScrolling(Toolbar toolbar, AppBarLayout appBarLayout) {

        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(0);
        toolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams)
                appBarLayout.getLayoutParams();
        appBarLayoutParams.setBehavior(null);
        appBarLayout.setLayoutParams(appBarLayoutParams);
    }

    public static void turnOnToolbarScrolling(Toolbar toolbar, AppBarLayout appBarLayout) {

        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        toolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams)
                appBarLayout.getLayoutParams();
        appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        appBarLayout.setLayoutParams(appBarLayoutParams);
    }


    public static void rotate360View(View rotateView, Runnable endAction) {
        rotateView.animate()
                .rotationBy(360)
                .withEndAction(endAction)
                .setDuration(1000)
                .setInterpolator(new LinearInterpolator())
                .start();
    }
}
