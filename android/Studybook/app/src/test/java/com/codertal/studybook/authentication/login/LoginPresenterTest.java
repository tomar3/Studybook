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
import com.codertal.studybook.features.authentication.login.domain.LoginResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

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
        loginPresenter = new LoginPresenter(loginView, usersRepository, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
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
    public void loadLogin_ShouldDisableButtons() {
        loginPresenter.loadLogin();

        verify(loginView).enableButtons(false);
    }

    @Test
    public void loadSkipLogin_ShouldDisableButtons() {
        loginPresenter.loadSkipLogin();

        verify(loginView).enableButtons(false);
    }

    @Test
    public void loadCurrentUser_WhenNoCurrentUserOrError_ShouldDoNothing(){
        when(usersRepository.getCurrentUser()).thenReturn(Single.error(new Throwable("null or error")));

        loginPresenter.loadCurrentUser();

        verifyZeroInteractions(loginView);
    }

    @Test
    public void loadCurrentUser_WhenRealUser_ShouldShowDashboardUi(){
        when(usersRepository.getCurrentUser()).thenReturn(Single.just(REAL_USER));

        loginPresenter.loadCurrentUser();

        verify(loginView).showDashboardUi();
    }

    @Test
    public void processLoginResult_WhenLoginCancelled_ShouldOnlyShowCancelMessage(){
        LoginResponse LOGIN_CANCELLED = new LoginResponse(LoginResponse.ResponseCodes.LOGIN_CANCELLED);

        loginPresenter.processLoginResult(LOGIN_CANCELLED);

        verify(loginView, times(1)).enableButtons(true);
        verify(loginView, times(1)).showCancelledMessage();
        verifyNoMoreInteractions(loginView);
    }

    @Test
    public void processLoginResult_WhenNetworkError_ShouldOnlyShowNetworkErrorMessage(){
        LoginResponse NETWORK_ERROR = new LoginResponse(LoginResponse.ResponseCodes.NETWORK_ERROR);

        loginPresenter.processLoginResult(NETWORK_ERROR);

        verify(loginView, times(1)).enableButtons(true);
        verify(loginView, times(1)).showNetworkErrorMessage();
        verifyNoMoreInteractions(loginView);
    }

    @Test
    public void processLoginResult_WhenUnknownError_ShouldOnlyShowUnknownErrorMessage(){
        LoginResponse UNKNOWN_ERROR = new LoginResponse(LoginResponse.ResponseCodes.UNKNOWN_ERROR);

        loginPresenter.processLoginResult(UNKNOWN_ERROR);

        verify(loginView, times(1)).enableButtons(true);
        verify(loginView, times(1)).showUnknownErrorMessage();
        verifyNoMoreInteractions(loginView);
    }

    @Test
    public void processLoginResult_WhenLoginSuccess_ShouldShowDashboardUi(){
        LoginResponse LOGIN_SUCCESS = new LoginResponse(LoginResponse.ResponseCodes.LOGIN_SUCCESS);

        loginPresenter.processLoginResult(LOGIN_SUCCESS);

        verify(loginView).showDashboardUi();
    }

}
