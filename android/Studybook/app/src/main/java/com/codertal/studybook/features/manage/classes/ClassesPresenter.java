/*
 * Created by Talab Omar on 11/8/17 12:40 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 12:40 PM
 */

package com.codertal.studybook.features.manage.classes;

import android.support.annotation.NonNull;

import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.ClassesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ClassesPresenter extends ClassesContract.Presenter {

    @NonNull
    private ClassesContract.View mClassesView;

    private ClassesRepository mClassesRepository;

    private ClassesContract.State mClassesState;

    public ClassesPresenter(@NonNull ClassesContract.View classesView,
                            @NonNull ClassesRepository classesRepository) {
        mClassesView = classesView;
        mClassesRepository = classesRepository;
    }

    @Override
    public void restoreState(ClassesContract.State state) {
        mClassesState = state;
    }

    @Override
    public ClassesContract.State getState() {
        return new ClassesState(mClassesView.getLayoutManagerPosition());
    }

    @Override
    public void subscribe() {
        mCompositeDisposable.add(mClassesRepository.getAllClassesAlphabetically()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayClasses, this::displayError));
    }


    @Override
    void openAddClass() {
        mClassesView.showAddClassUi();
    }

    @Override
    void openEditClass(long classId) {
        mClassesView.showEditClassUi(classId);
    }


    private void displayClasses(List<ClassInfo> classes) {
        mClassesView.displayClasses(classes);

        if (mClassesState != null) {
            mClassesView.restoreLayoutManagerPosition(mClassesState.getLayoutManagerPosition());
        }
    }

    private void displayError(Throwable error) {
        Timber.e(error);

        mClassesView.displayLoadingError();
    }
}
