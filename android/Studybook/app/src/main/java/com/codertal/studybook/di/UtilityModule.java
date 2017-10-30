/*
 * Created by Talab Omar on 10/30/17 4:57 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/30/17 4:57 PM
 */

package com.codertal.studybook.di;

import com.codertal.studybook.util.ClickManager;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilityModule {

    @Provides
    ClickManager provideClickManager() {
        return new ClickManager();
    }

}