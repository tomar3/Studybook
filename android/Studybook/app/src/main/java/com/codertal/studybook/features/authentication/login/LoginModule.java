/*
 * Created by Talab Omar on 10/28/17 7:30 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/28/17 7:30 PM
 */

package com.codertal.studybook.features.authentication.login;


import com.codertal.studybook.data.users.source.UsersRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    UsersRepository provideUsersRepository() {
        return new UsersRepository();
    }
}
