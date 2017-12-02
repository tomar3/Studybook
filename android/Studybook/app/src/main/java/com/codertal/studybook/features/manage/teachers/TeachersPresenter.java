/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers;

import android.support.annotation.NonNull;

import com.codertal.studybook.data.teachers.Teacher;
import com.codertal.studybook.data.teachers.TeachersRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeachersPresenter extends TeachersContract.Presenter {

    @NonNull
    private TeachersContract.View mTeachersView;

    private TeachersRepository mTeachersRepository;

    private TeachersContract.State mTeachersState;

    public TeachersPresenter(@NonNull TeachersContract.View teachersView,
                             @NonNull TeachersRepository teachersRepository) {
        mTeachersView = teachersView;
        mTeachersRepository = teachersRepository;
    }

    @Override
    public void restoreState(TeachersContract.State state) {
        mTeachersState = state;
    }

    @Override
    public TeachersContract.State getState() {
        return new TeachersState(mTeachersView.getLayoutManagerPosition());
    }

    @Override
    public void subscribe() {
        mCompositeDisposable.add(mTeachersRepository.getAllTeachersAlphabetically()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayTeachers, this::displayError));
    }


    @Override
    void openAddTeacher() {
        mTeachersView.showAddTeacherUi();
    }

    @Override
    void openEditTeacher(long teacherId) {
        mTeachersView.showEditTeacherUi(teacherId);
    }


    private void displayTeachers(List<Teacher> teachers) {
        mTeachersView.displayTeachers(teachers);

        if (mTeachersState != null) {
            mTeachersView.restoreLayoutManagerPosition(mTeachersState.getLayoutManagerPosition());
        }
    }

    private void displayError(Throwable error) {
        Timber.e(error);

        mTeachersView.displayLoadingError();
    }
}
