/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers.editaddteacher;

import com.codertal.studybook.base.presenter.BaseRxPresenter;
import com.codertal.studybook.base.presenter.SubscribablePresenter;
import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.teachers.Teacher;

import java.util.List;

public interface EditAddTeacherContract {

    interface View {

        void returnToTeachersUi();

        void showRequiredFields();

        void showSaveError();

        void showLoadingIndicator(boolean loading);

        void fillTeacherInfo(Teacher teacher);

        void showLoadTeacherError();

        void displayClasses(List<ClassInfo> classes);

        void showLoadClassesError();

    }

    abstract class Presenter extends BaseRxPresenter {

        abstract void verifySaveTeacher(String teacherName);

        abstract void loadTeacher(long teacherId);

    }

}
