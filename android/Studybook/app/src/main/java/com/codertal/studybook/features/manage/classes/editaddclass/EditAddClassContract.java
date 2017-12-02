/*
 * Created by Talab Omar on 11/8/17 2:42 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 2:42 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import com.codertal.studybook.base.BaseState;
import com.codertal.studybook.base.presenter.StatefulPresenter;
import com.codertal.studybook.base.presenter.SubscribablePresenter;
import com.codertal.studybook.data.classes.ClassInfo;

import java.util.List;

public interface EditAddClassContract {

    interface View {

        void returnToClassesUi();

        void showRequiredFields();

        void showSaveError();

        void showLoadingIndicator(boolean loading);

        void fillClassInfo(ClassInfo classInfo);

        void fillTeacherOptionsList(List<String> teacherOptionsList);

        void showLoadClassInfoError();

        void showLoadTeachersError();

        void showAddTeacherDialog();

        void selectTeacherPosition(int teacherPosition);

        void showTeacherSaveSuccess();

        void showTeacherSaveError();

        int getSelectedTeacherPosition();

    }

    abstract class Presenter extends SubscribablePresenter implements StatefulPresenter<State>{

        abstract void verifySaveClass(String className);

        abstract void loadClassInfo(long classId);

        abstract void loadAddNewTeacher();

        abstract void saveTeacherPosition(int teacherPosition);

        abstract void saveNewTeacher(String teacherName);
    }

    interface State extends BaseState{
        int getLastTeacherPosition();
    }
}
