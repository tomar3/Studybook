/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers.editaddteacher;

import android.support.annotation.NonNull;

import com.codertal.studybook.data.teachers.Teacher;
import com.codertal.studybook.data.teachers.TeachersRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class EditAddTeacherPresenter extends EditAddTeacherContract.Presenter {

    @NonNull
    private EditAddTeacherContract.View mEditTeacherView;

    private TeachersRepository mTeachersRepository;
    private Teacher mLoadedTeacher;


    public EditAddTeacherPresenter(@NonNull EditAddTeacherContract.View editTeacherView,
                                   @NonNull TeachersRepository teachersRepository) {
        mEditTeacherView = editTeacherView;
        mTeachersRepository = teachersRepository;
    }

    @Override
    public void verifySaveTeacher(String teacherName) {

        if(teacherName.isEmpty()) {
            mEditTeacherView.showRequiredFields();
        }else {
            mEditTeacherView.showLoadingIndicator(true);

            Teacher saveTeacher;

            if(mLoadedTeacher == null) {
                saveTeacher = new Teacher(teacherName);
            }else {
                saveTeacher = mLoadedTeacher;
                saveTeacher.setName(teacherName);
            }

            mCompositeDisposable.add(mTeachersRepository.save(saveTeacher)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::returnToTeachers, this::displaySaveError));
        }

    }

    @Override
    void loadTeacher(long teacherId) {
        mCompositeDisposable.add(mTeachersRepository.getTeacher(teacherId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Teacher>() {
                    @Override
                    public void onSuccess(Teacher teacher) {
                        mLoadedTeacher = teacher;

                        mEditTeacherView.fillTeacherInfo(teacher);
                        loadClassesForTeacher();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);

                        mEditTeacherView.showLoadTeacherError();
                    }
                }));
    }

    @Override
    void loadEditClass(long classId) {
        mEditTeacherView.showEditClassUi(classId);
    }

    @Override
    void loadAllClasses() {
        mEditTeacherView.showClassesUi();
    }

    private void loadClassesForTeacher() {
        mEditTeacherView.displayClasses(mLoadedTeacher.getClasses());
    }

    private void returnToTeachers() {
        mEditTeacherView.showLoadingIndicator(false);
        mEditTeacherView.returnToTeachersUi();
    }

    private void displaySaveError(Throwable error) {
        Timber.d(error);

        mEditTeacherView.showLoadingIndicator(false);
        mEditTeacherView.showSaveError();
    }
}
