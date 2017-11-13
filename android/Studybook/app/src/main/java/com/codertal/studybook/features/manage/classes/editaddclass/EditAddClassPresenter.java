/*
 * Created by Talab Omar on 11/8/17 2:43 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 2:43 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import android.support.annotation.NonNull;

import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.source.ClassesRepository;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class EditAddClassPresenter extends EditAddClassContract.Presenter{

    @NonNull
    private EditAddClassContract.View mEditClassView;

    private ClassesRepository mClassesRepository;

    private Scheduler mMainScheduler;

    public EditAddClassPresenter(@NonNull EditAddClassContract.View editClassView,
                                 @NonNull ClassesRepository classesRepository,
                                 @NonNull Scheduler mainScheduler) {
        mEditClassView = editClassView;
        mClassesRepository = classesRepository;
        mMainScheduler = mainScheduler;
    }

    @Override
    public void verifySaveClass(String className) {

        if(className.isEmpty()) {
            mEditClassView.showRequiredFields();
        }else {
            ClassInfo classInfo = new ClassInfo(className);

            mCompositeDisposable.add(mClassesRepository.save(classInfo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(mMainScheduler)
                    .subscribe(mEditClassView::returnToClassesUi, mEditClassView::showSaveError));
        }

    }
}
