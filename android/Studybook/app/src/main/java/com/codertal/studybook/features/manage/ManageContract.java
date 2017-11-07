/*
 * Created by Talab Omar on 11/7/17 11:18 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/7/17 11:18 AM
 */

package com.codertal.studybook.features.manage;

public interface ManageContract  {

    interface View {
        void showClassesUi();
    }

    interface Presenter{
        void openClasses();
    }
}
