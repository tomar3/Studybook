/*
 * Created by Talab Omar on 11/7/17 11:21 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/7/17 11:21 AM
 */

package com.codertal.studybook.features.manage;

import android.support.annotation.NonNull;

public class ManagePresenter implements ManageContract.Presenter {

    @NonNull
    private ManageContract.View mManageView;

    public ManagePresenter(@NonNull ManageContract.View manageView) {
        mManageView = manageView;
    }

    @Override
    public void openClasses() {
        mManageView.showClassesUi();
    }

    @Override
    public void openTeachers() {
        mManageView.showTeachersUi();
    }
}
