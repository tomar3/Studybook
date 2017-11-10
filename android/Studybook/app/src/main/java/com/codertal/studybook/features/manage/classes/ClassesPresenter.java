/*
 * Created by Talab Omar on 11/8/17 12:40 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 12:40 PM
 */

package com.codertal.studybook.features.manage.classes;

import android.support.annotation.NonNull;

public class ClassesPresenter extends ClassesContract.Presenter {

    @NonNull
    ClassesContract.View mClassesView;

    public ClassesPresenter(@NonNull ClassesContract.View classesView) {
        mClassesView = classesView;
    }

    @Override
    public void subscribe() {

    }


    @Override
    void openAddClass() {
        mClassesView.showAddClassUi();
    }
}
