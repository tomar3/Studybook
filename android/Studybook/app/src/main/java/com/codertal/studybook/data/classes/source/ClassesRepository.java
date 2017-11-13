/*
 * Created by Talab Omar
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.classes.source;

import com.codertal.studybook.data.classes.ClassInfo;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ClassesRepository {

    private final Box<ClassInfo> classInfoBox;

    @Inject
    public ClassesRepository(BoxStore boxStore){
        classInfoBox = boxStore.boxFor(ClassInfo.class);
    }

    public void save(ClassInfo entity) {
        classInfoBox.put(entity);
    }
}
