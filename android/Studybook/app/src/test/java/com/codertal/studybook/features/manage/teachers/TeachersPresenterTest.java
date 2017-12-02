/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers;

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

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeachersPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TeachersContract.View teachersView;

    @Mock
    private TeachersRepository teachersRepository;

    private TeachersPresenter teachersPresenter;

    private List<Teacher> MANY_TEACHERS;
    private long TEACHER_ID;
    private TeachersState REAL_STATE;

    @Before
    public void setUp(){
        teachersPresenter = new TeachersPresenter(teachersView, teachersRepository);
        MANY_TEACHERS = Arrays.asList(new Teacher("1"), new Teacher("2"),
                new Teacher("3"));
        TEACHER_ID = 1;
        REAL_STATE = new TeachersState(1);

        RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }


    @Test
    public void openAddTeacher_ShouldShowAddTeacherUi(){
        teachersPresenter.openAddTeacher();

        verify(teachersView).showAddTeacherUi();
    }

    @Test
    public void subscribe_WhenManyTeachers_ShouldDisplayTeachers() {
        when(teachersRepository.getAllTeachersAlphabetically()).thenReturn(Single.just(MANY_TEACHERS));

        teachersPresenter.subscribe();

        verify(teachersView).displayTeachers(MANY_TEACHERS);
    }

    @Test
    public void subscribe_WhenEmptyTeachers_ShouldDisplayTeachers() {
        when(teachersRepository.getAllTeachersAlphabetically()).thenReturn(Single.just(EMPTY_LIST));

        teachersPresenter.subscribe();

        verify(teachersView).displayTeachers(EMPTY_LIST);
    }

    @Test
    public void subscribe_WhenDbError_ShouldShowLoadingError() {
        when(teachersRepository.getAllTeachersAlphabetically()).thenReturn(Single.error(new Throwable("db error")));

        teachersPresenter.subscribe();

        verify(teachersView).displayLoadingError();
    }

    @Test
    public void openEditTeacher_ShouldShowEditTeacherUi(){
        teachersPresenter.openEditTeacher(TEACHER_ID);

        verify(teachersView).showEditTeacherUi(TEACHER_ID);
    }

    @Test
    public void restoreState_ShouldRestoreLayoutManagerState(){
        when(teachersRepository.getAllTeachersAlphabetically()).thenReturn(Single.just(MANY_TEACHERS));

        teachersPresenter.restoreState(REAL_STATE);
        teachersPresenter.subscribe();

        verify(teachersView).restoreLayoutManagerPosition(REAL_STATE.getLayoutManagerPosition());
    }

}
