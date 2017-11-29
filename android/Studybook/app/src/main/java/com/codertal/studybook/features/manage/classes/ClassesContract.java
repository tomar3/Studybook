/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.classes;

import android.os.Parcelable;

import com.codertal.studybook.base.BaseState;
import com.codertal.studybook.base.StatefulPresenter;
import com.codertal.studybook.base.SubscribablePresenter;
import com.codertal.studybook.data.classes.ClassInfo;

import java.util.List;

public interface ClassesContract {

    interface View {

        void displayClasses(List<ClassInfo> classes);
        void displayLoadingError();
        void showAddClassUi();
        void showEditClassUi(long classId);
        int getLayoutManagerPosition();
        void restoreLayoutManagerPosition(int layoutManagerPosition);
    }

    abstract class Presenter extends SubscribablePresenter implements StatefulPresenter<State>{

        abstract void openAddClass();
        abstract void openEditClass(long classId);
    }

    interface State extends BaseState {
        int getLayoutManagerPosition();
    }
}
