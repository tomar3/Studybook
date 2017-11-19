/*
 * Created by Talab Omar
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.modules;

import com.codertal.studybook.data.classes.ClassesRepository;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class ClassesModule {

    @Provides
    ClassesRepository provideClassesRepository(BoxStore boxStore){
        return new ClassesRepository(boxStore);
    }
}
