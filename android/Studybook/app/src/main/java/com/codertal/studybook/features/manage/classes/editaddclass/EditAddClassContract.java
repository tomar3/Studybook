/*
 * Created by Talab Omar on 11/8/17 2:42 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 2:42 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

public interface EditAddClassContract {

    interface View {

        void returnToClassesUi();

        void showRequiredFields();

    }

    interface Presenter {

        void verifySaveClass(String className);
    }
}
