/*
 * Created by Talab Omar on 11/8/17 12:42 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 12:42 PM
 */

package com.codertal.studybook.features.manage.classes;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;

public class ClassesPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ClassesContract.View classesView;

    private ClassesPresenter classesPresenter;

    @Before
    public void setUp(){
        classesPresenter = new ClassesPresenter(classesView);
    }


    @Test
    public void openAddClass_ShouldShowAddClassUi(){
        classesPresenter.openAddClass();

        verify(classesView).showAddClassUi();
    }

}
