/*
 * Created by Talab Omar
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.classes.source;

import com.codertal.studybook.data.classes.ClassInfo;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Completable;
import io.reactivex.Single;

public class ClassesRepository {

    private final Box<ClassInfo> classInfoBox;

    @Inject
    public ClassesRepository(BoxStore boxStore){
        classInfoBox = boxStore.boxFor(ClassInfo.class);
    }

    public Completable save(ClassInfo entity) {
        return Completable.fromAction(() -> classInfoBox.put(entity));
    }

    public Single<List<ClassInfo>> getAllClasses() {
        return Single.fromCallable(classInfoBox::getAll);
    }

}
