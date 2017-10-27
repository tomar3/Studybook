/*
 * Created by Talab Omar on 10/27/17 6:09 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/27/17 6:09 PM
 */

package com.codertal.studybook.di;

import com.codertal.studybook.app.MainApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Dagger will use this interface to generate the necessary code to perform the dependency injection
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(MainApplication application);
        AppComponent build();
    }

    void inject(MainApplication app);
}
