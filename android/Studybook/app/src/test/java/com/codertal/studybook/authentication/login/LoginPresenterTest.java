/*
 * Created by Talab Omar on 10/27/17 4:42 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/27/17 4:42 PM
 */

package com.codertal.studybook.authentication.login;

import com.codertal.studybook.features.authentication.login.LoginContract;
import com.codertal.studybook.features.authentication.login.LoginPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

public class LoginPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private LoginContract.View loginView;

    private LoginPresenter loginPresenter;

    @Before
    public void setUp() {
        loginPresenter = new LoginPresenter(loginView);
    }

    @Test
    public void loadLogin_ShouldShowLoginUi() {
        loginPresenter.loadLogin();

        verify(loginView).showLoginUi();
    }


    @Test
    public void loadSkipLogin_ShouldShowDashboardUi() {
        loginPresenter.loadSkipLogin();

        verify(loginView).showDashboardUi();
    }

}
