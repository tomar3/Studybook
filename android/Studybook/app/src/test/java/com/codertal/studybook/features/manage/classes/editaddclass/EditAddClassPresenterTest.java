/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.source.ClassesRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Completable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.Completable.complete;
import static org.mockito.ArgumentMatchers.any;
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

    private EditAddClassPresenter editAddClassPresenter;

    private final String REAL_CLASS_NAME = "Class name";

    @Before
    public void setUp(){
        editAddClassPresenter = new EditAddClassPresenter(editAddClassView, classesRepository,
                Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
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

}
