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

        void showEditClassUi(long classId);

        void showClassesUi();
    }

    abstract class Presenter extends SubscribablePresenter{

        abstract void verifySaveTeacher(String teacherName);

        abstract void loadTeacher(long teacherId);

        abstract void loadEditClass(long classId);

        abstract void loadAllClasses();
    }

}
