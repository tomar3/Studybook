/*
 * Created by Talab Omar on 11/8/17 12:40 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 12:40 PM
 */

package com.codertal.studybook.features.manage.classes;

import android.support.annotation.NonNull;

import com.codertal.studybook.data.classes.source.ClassesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ClassesPresenter extends ClassesContract.Presenter {

    @NonNull
    private ClassesContract.View mClassesView;

    private ClassesRepository mClassesRepository;

    public ClassesPresenter(@NonNull ClassesContract.View classesView,
                            @NonNull ClassesRepository classesRepository) {
        mClassesView = classesView;
        mClassesRepository = classesRepository;
    }

    @Override
    public void subscribe() {
        mCompositeDisposable.add(mClassesRepository.getAllClasses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(classes -> mClassesView.displayClasses(classes), this::displayError));
    }


    @Override
    void openAddClass() {
        mClassesView.showAddClassUi();
    }

    @Override
    void openEditClass(long classId) {
        mClassesView.showEditClassUi(classId);
    }

    private void displayError(Throwable error) {
        Timber.e(error);

        mClassesView.displayLoadingError();
    }
}
