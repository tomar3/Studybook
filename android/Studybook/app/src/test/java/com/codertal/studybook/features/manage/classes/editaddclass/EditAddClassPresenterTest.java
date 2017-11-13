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
    public void verifySaveClass_WhenAllRequiredFieldsGiven_ShouldReturnToClassesUi() {
        when(classesRepository.save(any(ClassInfo.class))).thenReturn(complete());

        editAddClassPresenter.verifySaveClass("Class Name");

        verify(editAddClassView).returnToClassesUi();
        verify(editAddClassView, times(0)).showRequiredFields();
    }

    //TODO: TEST COMPLETEABLE ERROR

}
