/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.classes;

import com.codertal.studybook.base.SubscribablePresenter;
import com.codertal.studybook.data.classes.ClassInfo;

import java.util.List;

public interface ClassesContract {

    interface View {

        void displayClasses(List<ClassInfo> classes);
        void displayLoadingError();
        void showAddClassUi();

    }

    abstract class Presenter extends SubscribablePresenter {

        abstract void openAddClass();
    }
}
