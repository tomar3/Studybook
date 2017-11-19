/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.ClassesRepository;
import com.codertal.studybook.data.teachers.Teacher;
import com.codertal.studybook.data.teachers.TeachersRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.Completable.complete;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditAddClassPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private EditAddClassContract.View editAddClassView;

    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private TeachersRepository teachersRepository;

    private EditAddClassPresenter editAddClassPresenter;

    private String REAL_CLASS_NAME;
    private ClassInfo REAL_CLASS_INFO;
    private long CLASS_ID;
    private List<Teacher> MANY_TEACHERS;

    @Before
    public void setUp(){
        editAddClassPresenter = new EditAddClassPresenter(editAddClassView, classesRepository, teachersRepository);
        REAL_CLASS_NAME = "Class name";
        REAL_CLASS_INFO = new ClassInfo(REAL_CLASS_NAME);
        CLASS_ID = 1;
        MANY_TEACHERS = Arrays.asList(new Teacher("Name1"), new Teacher("Name2"));

        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }


    @Test
    public void verifySaveClass_WhenClassNameNotEntered_ShouldShowRequiredFields() {
        editAddClassPresenter.verifySaveClass("");

        verify(editAddClassView).showRequiredFields();
        verify(editAddClassView, times(0)).returnToClassesUi();
    }

    @Test
    public void verifySaveClass_WhenAllRequiredFieldsGiven_ShouldShowLoadingIndicator() {
        when(classesRepository.save(any(ClassInfo.class))).thenReturn(complete());

        editAddClassPresenter.verifySaveClass(REAL_CLASS_NAME);

        verify(editAddClassView).showLoadingIndicator(true);
    }


    @Test
    public void verifySaveClass_WhenAllRequiredFieldsGiven_ShouldReturnToClassesUi() {
        when(classesRepository.save(any(ClassInfo.class))).thenReturn(complete());

        editAddClassPresenter.verifySaveClass(REAL_CLASS_NAME);

        verify(editAddClassView).returnToClassesUi();
        verify(editAddClassView, times(0)).showRequiredFields();
    }

    @Test
    public void verifySaveClass_WhenAllRequiredFieldsGiven_ShouldSaveClass() {
        when(classesRepository.save(any(ClassInfo.class))).thenReturn(complete());

        editAddClassPresenter.verifySaveClass(REAL_CLASS_NAME);

        verify(classesRepository).save(any(ClassInfo.class));
    }

    @Test
    public void verifySaveClass_WhenClassSaved_ShouldStopShowingLoadingIndicator() {
        when(classesRepository.save(any(ClassInfo.class))).thenReturn(complete());

        editAddClassPresenter.verifySaveClass(REAL_CLASS_NAME);

        verify(editAddClassView).showLoadingIndicator(false);
    }



    @Test
    public void verifySaveClass_WhenDatabaseError_ShouldShowSaveError() {
        when(classesRepository.save(any(ClassInfo.class)))
                .thenReturn(Completable.error(new Throwable("db error")));

        editAddClassPresenter.verifySaveClass(REAL_CLASS_NAME);

        verify(editAddClassView).showSaveError();
        verify(editAddClassView, times(0)).returnToClassesUi();
    }


    @Test
    public void verifySaveClass_WhenDatabaseError_ShouldStopShowingLoading() {
        when(classesRepository.save(any(ClassInfo.class)))
                .thenReturn(Completable.error(new Throwable("db error")));

        editAddClassPresenter.verifySaveClass(REAL_CLASS_NAME);

        verify(editAddClassView).showLoadingIndicator(false);
    }

    @Test
    public void verifyLoadClassInfo_WhenDatabaseSuccess_ShouldFillClassInfo() {
        when(classesRepository.getClassInfo(CLASS_ID)).thenReturn(Single.just(REAL_CLASS_INFO));

        editAddClassPresenter.loadClassInfo(CLASS_ID);

        verify(editAddClassView).fillClassInfo(REAL_CLASS_INFO);
    }

    @Test
    public void verifyLoadClassInfo_WhenDatabaseError_ShouldShowLoadError() {
        when(classesRepository.getClassInfo(CLASS_ID)).thenReturn(Single.error(new Throwable("db error")));

        editAddClassPresenter.loadClassInfo(CLASS_ID);

        verify(editAddClassView).showLoadClassInfoError();
    }

    @Test
    public void subscribe_WhenDatabaseSuccess_ShouldFillTeacherOptionsList() {
        when(teachersRepository.getAllTeachers()).thenReturn(Single.just(MANY_TEACHERS));

        editAddClassPresenter.subscribe();

        verify(editAddClassView).fillTeacherOptionsList(anyList());
    }

    @Test
    public void subscribe_WhenDatabaseError_ShouldShowLoadError() {
        when(teachersRepository.getAllTeachers()).thenReturn(Single.error(new Throwable("db error")));

        editAddClassPresenter.subscribe();

        verify(editAddClassView).showLoadTeachersError();
    }

}
