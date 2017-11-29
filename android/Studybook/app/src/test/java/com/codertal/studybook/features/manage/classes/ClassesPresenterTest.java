/*
 * Created by Talab Omar on 11/8/17 12:42 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 12:42 PM
 */

package com.codertal.studybook.features.manage.classes;

import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.ClassesRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClassesPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ClassesContract.View classesView;

    @Mock
    private ClassesRepository classesRepository;

    private ClassesPresenter classesPresenter;

    private List<ClassInfo> MANY_CLASSES;
    private long CLASS_ID;
    private ClassesState REAL_STATE;

    @Before
    public void setUp(){
        classesPresenter = new ClassesPresenter(classesView, classesRepository);
        MANY_CLASSES = Arrays.asList(new ClassInfo("1"), new ClassInfo("2"),
                new ClassInfo("3"));
        CLASS_ID = 1;
        REAL_STATE = new ClassesState(1);

        RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }


    @Test
    public void openAddClass_ShouldShowAddClassUi(){
        classesPresenter.openAddClass();

        verify(classesView).showAddClassUi();
    }

    @Test
    public void subscribe_WhenManyClasses_ShouldDisplayClasses() {
        when(classesRepository.getAllClasses()).thenReturn(Single.just(MANY_CLASSES));

        classesPresenter.subscribe();

        verify(classesView).displayClasses(MANY_CLASSES);
    }

    @Test
    public void subscribe_WhenEmptyClasses_ShouldDisplayClasses() {
        when(classesRepository.getAllClasses()).thenReturn(Single.just(EMPTY_LIST));

        classesPresenter.subscribe();

        verify(classesView).displayClasses(EMPTY_LIST);
    }

    @Test
    public void subscribe_WhenDbError_ShouldShowLoadingError() {
        when(classesRepository.getAllClasses()).thenReturn(Single.error(new Throwable("db error")));

        classesPresenter.subscribe();

        verify(classesView).displayLoadingError();
    }

    @Test
    public void openEditClass_ShouldShowEditClassUi(){
        classesPresenter.openEditClass(CLASS_ID);

        verify(classesView).showEditClassUi(CLASS_ID);
    }

    @Test
    public void restoreState_ShouldRestoreLayoutManagerState(){
        when(classesRepository.getAllClasses()).thenReturn(Single.just(MANY_CLASSES));

        classesPresenter.restoreState(REAL_STATE);
        classesPresenter.subscribe();

        verify(classesView).restoreLayoutManagerPosition(REAL_STATE.getLayoutManagerPosition());
    }

}
