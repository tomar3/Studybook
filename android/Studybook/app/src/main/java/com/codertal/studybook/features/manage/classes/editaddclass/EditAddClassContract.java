/*
 * Created by Talab Omar on 11/8/17 2:42 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 2:42 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import com.codertal.studybook.base.BaseRxPresenter;
import com.codertal.studybook.data.classes.ClassInfo;

public interface EditAddClassContract {

    interface View {

        void returnToClassesUi();

        void showRequiredFields();

        void showSaveError();

        void showLoadingIndicator(boolean loading);

        void fillClassInfo(ClassInfo classInfo);

        void showLoadError();

    }

    abstract class Presenter extends BaseRxPresenter {

        abstract void verifySaveClass(String className);

        abstract void loadClassInfo(long classId);
    }
}
