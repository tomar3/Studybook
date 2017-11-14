/*
 * Created by Talab Omar on 11/8/17 2:42 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 2:42 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import com.codertal.studybook.base.BaseRxPresenter;

public interface EditAddClassContract {

    interface View {

        void returnToClassesUi();

        void showRequiredFields();

        void showSaveError();

        void showLoadingIndicator(boolean loading);

    }

    abstract class Presenter extends BaseRxPresenter {

        abstract void verifySaveClass(String className);
    }
}
