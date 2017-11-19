/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.modules;

import com.codertal.studybook.data.teachers.TeachersRepository;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class TeachersModule {

    @Provides
    TeachersRepository provideTeachersRepository(BoxStore boxStore){
        return new TeachersRepository(boxStore);
    }
}
