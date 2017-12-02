/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
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

public class EditAddClassPresenter extends EditAddClassContract.Presenter {

    private static final int TEACHER_INDEX_OFFSET = 2;

    @NonNull
    private EditAddClassContract.View mEditClassView;

    private ClassesRepository mClassesRepository;
    private TeachersRepository mTeachersRepository;
    private ClassInfo mLoadedClassInfo;
    private List<Teacher> mTeacherOptions;
    private int mChosenTeacherPosition;
    private long mSavedTeacherId;
    private boolean mRestoreState;


    public EditAddClassPresenter(@NonNull EditAddClassContract.View editClassView,
                                 @NonNull ClassesRepository classesRepository,
                                 @NonNull TeachersRepository teachersRepository) {
        mEditClassView = editClassView;
        mClassesRepository = classesRepository;
        mTeachersRepository = teachersRepository;
        mChosenTeacherPosition = 0;
        mSavedTeacherId = -1;
        mRestoreState = false;
    }


    @Override
    public void subscribe() {
       loadAllTeachers();
    }

    @Override
    public void restoreState(EditAddClassContract.State state) {
        mChosenTeacherPosition = state.getLastTeacherPosition();
        mRestoreState = true;
    }

    @Override
    public EditAddClassContract.State getState() {
        return new EditAddClassState(mEditClassView.getSelectedTeacherPosition());
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

            if(mChosenTeacherPosition == 0) {
                mClassesRepository.assignTeacherToClass(saveClassInfo, null);
            }else {
                mClassesRepository.assignTeacherToClass(saveClassInfo,
                        mTeacherOptions.get(mChosenTeacherPosition - TEACHER_INDEX_OFFSET));
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

                        if(mClassesRepository.classHasTeacher(mLoadedClassInfo)){
                            mSavedTeacherId = mClassesRepository.getClassTeacherId(mLoadedClassInfo);
                        }

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

    @Override
    void saveNewTeacher(String teacherName) {
        mCompositeDisposable.add(mTeachersRepository.saveAndReturnId(new Teacher(teacherName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Long>() {

                    @Override
                    public void onSuccess(Long savedTeacherId) {
                        mSavedTeacherId = savedTeacherId;
                        loadAllTeachers();
                        mEditClassView.showTeacherSaveSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);

                        mEditClassView.showTeacherSaveError();
                    }
                }));
    }

    private void loadAllTeachers() {
        mCompositeDisposable.add(mTeachersRepository.getAllTeachersAlphabetically()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Teacher>>() {
                    @Override
                    public void onSuccess(List<Teacher> teachers) {
                        mTeacherOptions = teachers;
                        List<String> teacherOptions = new ArrayList<>();

                        int chosenTeacherPosition = -1;

                        for (int i = 0; i < teachers.size(); i++) {
                            Teacher currentTeacher = teachers.get(i);

                            if(mSavedTeacherId != -1 && currentTeacher.getId() == mSavedTeacherId){
                                chosenTeacherPosition = i;
                                mSavedTeacherId = -1;
                            }

                            teacherOptions.add(currentTeacher.getName());
                        }

                        mEditClassView.fillTeacherOptionsList(teacherOptions);
                        selectTeacherPosition(chosenTeacherPosition);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);

                        mEditClassView.showLoadTeachersError();
                    }
                }));
    }

    private void selectTeacherPosition(int chosenTeacherPosition) {
        if(chosenTeacherPosition != -1 && !mRestoreState) {
            mEditClassView.selectTeacherPosition(chosenTeacherPosition + TEACHER_INDEX_OFFSET);
        }else if(mRestoreState) {
            mEditClassView.selectTeacherPosition(mChosenTeacherPosition);
            mRestoreState = false;
        }
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

    //Only used for unit testing
    public int getChosenTeacherPosition() {
        return mChosenTeacherPosition;
    }
}
