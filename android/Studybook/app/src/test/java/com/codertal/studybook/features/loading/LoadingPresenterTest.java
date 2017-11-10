/*
 * Created by Talab Omar on 10/31/17 2:14 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/31/17 2:14 PM
 */

package com.codertal.studybook.features.loading;

import com.codertal.studybook.data.database.DatabaseRepository;
import com.codertal.studybook.data.users.source.UsersRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static com.codertal.studybook.util.UserUtils.UserConstants.TEMP_USER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoadingPresenterTest {

//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//    @Mock
//    private LoadingContract.View loadingView;
//
//    @Mock
//    private UsersRepository usersRepository;
//
//    @Mock
//    private DatabaseRepository databaseRepository;
//
//    private LoadingPresenter loadingPresenterWithTempUser;
//
//    private LoadingPresenter loadingPresenterWithRealUser;
//
//    private static final String REAL_USER = "REAL_USER";
//
//    @Before
//    public void setUp(){
//        loadingPresenterWithTempUser = new LoadingPresenter(loadingView, TEMP_USER, usersRepository,
//                                                            databaseRepository);
//        loadingPresenterWithRealUser = new LoadingPresenter(loadingView, REAL_USER, usersRepository,
//                                                            databaseRepository);
//    }
//
//
//    @Test
//    public void subscribe_WhenExistingTempUser_ShouldShowDashboardUi(){
//        when(usersRepository.containsUserDatabase(TEMP_USER)).thenReturn(true);
//
//        loadingPresenterWithTempUser.subscribe();
//
//        verify(loadingView).showDashboardUi();
//        verify(databaseRepository, never()).createNewDatabase(anyString());
//    }
}
