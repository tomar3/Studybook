/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers.editaddteacher;

import com.codertal.studybook.base.presenter.SubscribablePresenter;
import com.codertal.studybook.data.teachers.Teacher;

public interface EditAddTeacherContract {

    interface View {

        void returnToTeachersUi();

        void showRequiredFields();

        void showSaveError();

        void showLoadingIndicator(boolean loading);

        void fillTeacherInfo(Teacher teacher);

        void showLoadTeacherError();


    }

    abstract class Presenter extends SubscribablePresenter {

        abstract void verifySaveTeacher(String teacherName);

        abstract void loadTeacher(long teacherId);

    }

}
