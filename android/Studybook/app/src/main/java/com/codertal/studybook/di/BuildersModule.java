/*
 * Created by Talab Omar on 10/27/17 6:06 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/27/17 6:06 PM
 */

package com.codertal.studybook.di;

import com.codertal.studybook.features.authentication.login.LoginActivity;
import com.codertal.studybook.features.authentication.login.LoginModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLoginActivity();

}