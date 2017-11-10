/*
 * Created by Talab Omar on 11/8/17 12:33 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 12:33 PM
 */

package com.codertal.studybook.features.manage.classes;

import com.codertal.studybook.base.BaseRxPresenter;

public interface ClassesContract {

    interface View {

        void showAddClassUi();

    }

    abstract class Presenter extends BaseRxPresenter {

        abstract void openAddClass();
    }
}
