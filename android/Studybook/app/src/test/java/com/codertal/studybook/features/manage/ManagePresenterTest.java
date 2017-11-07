/*
 * Created by Talab Omar on 11/7/17 1:50 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/7/17 1:50 PM
 */

package com.codertal.studybook.features.manage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

public class ManagePresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ManageContract.View manageView;

    private ManagePresenter managePresenter;

    @Before
    public void setUp(){
        managePresenter = new ManagePresenter(manageView);
    }


    @Test
    public void openClasses_ShouldShowClassUi(){
        managePresenter.openClasses();

        verify(manageView).showClassesUi();
    }
}
