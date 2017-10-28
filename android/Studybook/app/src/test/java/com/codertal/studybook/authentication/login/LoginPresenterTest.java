/*
 * Created by Talab Omar on 10/27/17 4:42 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/27/17 4:42 PM
 */

package com.codertal.studybook.authentication.login;

import com.codertal.studybook.data.users.User;
import com.codertal.studybook.data.users.source.UsersRepository;
import com.codertal.studybook.features.authentication.login.LoginContract;
import com.codertal.studybook.features.authentication.login.LoginPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

public class LoginPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private LoginContract.View loginView;

    @Mock
    private UsersRepository usersRepository;

    private LoginPresenter loginPresenter;
    private final User REAL_USER = new User("name", "email", "userId");

    @Before
    public void setUp() {
        loginPresenter = new LoginPresenter(loginView, usersRepository);
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

    @Test
    public void loadCurrentUser_WhenNoCurrentUser_ShouldDoNothing(){
        when(usersRepository.getCurrentUser()).thenReturn(null);

        loginPresenter.loadCurrentUser();

        verifyZeroInteractions(loginView);
    }

    @Test
    public void loadCurrentUser_WhenRealUser_ShouldShowDashboardUi(){
        when(usersRepository.getCurrentUser()).thenReturn(REAL_USER);

        loginPresenter.loadCurrentUser();

        verify(loginView).showDashboardUi();
    }

}
