/*
 * Created by Talab Omar on 11/8/17 2:43 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 2:43 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import android.support.annotation.NonNull;

import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.ClassesRepository;
import com.codertal.studybook.data.teachers.Teacher;
import com.codertal.studybook.data.teachers.TeachersRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class EditAddClassPresenter extends EditAddClassContract.Presenter{

    @NonNull
    private EditAddClassContract.View mEditClassView;

    private ClassesRepository mClassesRepository;
    private TeachersRepository mTeachersRepository;
    private ClassInfo mLoadedClassInfo;


    public EditAddClassPresenter(@NonNull EditAddClassContract.View editClassView,
                                 @NonNull ClassesRepository classesRepository,
                                 @NonNull TeachersRepository teachersRepository) {
        mEditClassView = editClassView;
        mClassesRepository = classesRepository;
        mTeachersRepository = teachersRepository;
    }


    @Override
    public void subscribe() {
        mCompositeDisposable.add(mTeachersRepository.getAllTeachers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Teacher>>() {
                    @Override
                    public void onSuccess(List<Teacher> teachers) {
                        List<String> teacherOptions = new ArrayList<>();

                        for(Teacher teacher : teachers){
                            teacherOptions.add(teacher.getName());
                        }

                        mEditClassView.fillTeacherOptionsList(teacherOptions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);

                        mEditClassView.showLoadTeachersError();
                    }
                }));
    }

    @Override
    public void verifySaveClass(String className) {

        if(className.isEmpty()) {
            mEditClassView.showRequiredFields();
        }else {
            mEditClassView.showLoadingIndicator(true);

            ClassInfo saveClassInfo;

            if(mLoadedClassInfo == null) {
                saveClassInfo = new ClassInfo(className);
            }else {
                saveClassInfo = mLoadedClassInfo;
                saveClassInfo.setName(className);
            }

            mCompositeDisposable.add(mClassesRepository.save(saveClassInfo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::returnToClasses, this::displaySaveError));
        }

    }

    @Override
    void loadClassInfo(long classId) {
        mCompositeDisposable.add(mClassesRepository.getClassInfo(classId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ClassInfo>() {
                    @Override
                    public void onSuccess(ClassInfo classInfo) {
                        mLoadedClassInfo = classInfo;
                        mEditClassView.fillClassInfo(classInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);

                        mEditClassView.showLoadClassInfoError();
                    }
                }));
    }

    private void returnToClasses() {
        mEditClassView.showLoadingIndicator(false);
        mEditClassView.returnToClassesUi();
    }

    private void displaySaveError(Throwable error) {
        Timber.d(error);

        mEditClassView.showLoadingIndicator(false);
        mEditClassView.showSaveError();
    }
}
