/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers;

import com.codertal.studybook.base.BaseState;
import com.codertal.studybook.base.presenter.StatefulPresenter;
import com.codertal.studybook.base.presenter.SubscribablePresenter;
import com.codertal.studybook.data.teachers.Teacher;

import java.util.List;

public interface TeachersContract {
    interface View {

        void displayTeachers(List<Teacher> teachers);
        void displayLoadingError();
        void showAddTeacherUi();
        void showEditTeacherUi(long teacherId);
        int getLayoutManagerPosition();
        void restoreLayoutManagerPosition(int layoutManagerPosition);
    }

    abstract class Presenter extends SubscribablePresenter implements StatefulPresenter<State> {

        abstract void openAddTeacher();
        abstract void openEditTeacher(long teacherId);
    }

    interface State extends BaseState {
        int getLayoutManagerPosition();
    }
}
