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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class EditAddClassPresenter extends EditAddClassContract.Presenter{

    @NonNull
    EditAddClassContract.View mEditClassView;

    private ClassesRepository mClassesRepository;

    public EditAddClassPresenter(@NonNull EditAddClassContract.View editClassView,
                                 @NonNull ClassesRepository classesRepository) {
        mEditClassView = editClassView;
        mClassesRepository = classesRepository;
    }

    @Override
    public void verifySaveClass(String className) {

        if(className.isEmpty()) {
            mEditClassView.showRequiredFields();
        }else {
            ClassInfo classInfo = new ClassInfo(className);

            mCompositeDisposable.add(mClassesRepository.save(classInfo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            mEditClassView.returnToClassesUi();
                        }

                        @Override
                        public void onError(Throwable e) {
                            //TODO: Show db error toast
                        }
                    }));
        }

    }
}
