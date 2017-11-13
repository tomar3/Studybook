/*
 * Created by Talab Omar on 11/9/17 8:31 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/9/17 8:31 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import com.codertal.studybook.data.classes.source.ClassesRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        editAddClassPresenter = new EditAddClassPresenter(editAddClassView, classesRepository);
    }


    @Test
    public void verifySaveClass_WhenClassNameNotEntered_ShouldShowRequiredFields(){
        editAddClassPresenter.verifySaveClass("");

        verify(editAddClassView).showRequiredFields();
        verify(editAddClassView, times(0)).returnToClassesUi();
    }


    @Test
    public void verifySaveClass_WhenAllRequiredFieldsGiven_ShouldReturnToClassesUi(){
        editAddClassPresenter.verifySaveClass("Class Name");

        verify(editAddClassView).returnToClassesUi();
        verify(editAddClassView, times(0)).showRequiredFields();
    }

}
