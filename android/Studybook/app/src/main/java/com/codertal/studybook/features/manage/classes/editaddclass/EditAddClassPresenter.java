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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class EditAddClassPresenter extends EditAddClassContract.Presenter{
    private static final int TEACHER_INDEX_OFFSET = 2;

    @NonNull
    private EditAddClassContract.View mEditClassView;

    private ClassesRepository mClassesRepository;
    private TeachersRepository mTeachersRepository;
    private ClassInfo mLoadedClassInfo;
    private List<Teacher> mTeacherOptions;
    private int mChosenTeacherPosition;
    private long mSavedClassId;


    public EditAddClassPresenter(@NonNull EditAddClassContract.View editClassView,
                                 @NonNull ClassesRepository classesRepository,
                                 @NonNull TeachersRepository teachersRepository) {
        mEditClassView = editClassView;
        mClassesRepository = classesRepository;
        mTeachersRepository = teachersRepository;
        mChosenTeacherPosition = 0;
    }


    @Override
    public void subscribe() {
       loadAllTeachers(false);
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

    @Override
    void loadAddNewTeacher() {
        mEditClassView.selectTeacherPosition(mChosenTeacherPosition);
        mEditClassView.showAddTeacherDialog();
    }

    @Override
    void saveTeacherPosition(int teacherPosition) {
        mChosenTeacherPosition = teacherPosition;
    }

    //TODO: REMOVE THIS, CHECK IF STILL NOT NEEDED
    @Override
    void loadPreviousTeacherPosition() {
        mEditClassView.selectTeacherPosition(mChosenTeacherPosition);
    }

    @Override
    void saveNewTeacher(String teacherName) {
        mCompositeDisposable.add(mTeachersRepository.save(new Teacher(teacherName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Long>() {

                    @Override
                    public void onSuccess(Long savedClassId) {
                        mSavedClassId = savedClassId;
                        loadAllTeachers(true);
                        mEditClassView.showTeacherSaveSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);

                        mEditClassView.showTeacherSaveError();
                    }
                }));
    }

    private void loadAllTeachers(boolean selectTeacherPosition) {
        mCompositeDisposable.add(mTeachersRepository.getAllTeachersAlphabetically()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Teacher>>() {
                    @Override
                    public void onSuccess(List<Teacher> teachers) {
                        mTeacherOptions = teachers;
                        List<String> teacherOptions = new ArrayList<>();

                        int chosenTeacherPosition = 0;

                        for (int i = 0; i < teachers.size(); i++) {
                            Teacher currentTeacher = teachers.get(i);

                            if(selectTeacherPosition && currentTeacher.getId() == mSavedClassId){
                                chosenTeacherPosition = i;
                            }

                            teacherOptions.add(currentTeacher.getName());
                        }


                        mEditClassView.fillTeacherOptionsList(teacherOptions);

                        if(selectTeacherPosition){
                            mEditClassView.selectTeacherPosition(chosenTeacherPosition + TEACHER_INDEX_OFFSET);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);

                        mEditClassView.showLoadTeachersError();
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

    public int getChosenTeacherPosition() {
        return mChosenTeacherPosition;
    }
}
