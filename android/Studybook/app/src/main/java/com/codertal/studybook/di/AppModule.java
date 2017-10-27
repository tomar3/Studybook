/*
 * Created by Talab Omar on 10/27/17 6:04 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/27/17 6:04 PM
 */

package com.codertal.studybook.di;

import android.content.Context;

import com.codertal.studybook.app.MainApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Inject application-wide dependencies.
 */
@Module
public class AppModule {

    @Provides
    Context provideContext(MainApplication application) {
        return application.getApplicationContext();
    }

}
