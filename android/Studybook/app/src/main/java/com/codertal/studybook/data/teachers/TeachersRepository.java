/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.teachers;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Completable;
import io.reactivex.Single;

public class TeachersRepository {

    private final Box<Teacher> teacherBox;

    @Inject
    public TeachersRepository(BoxStore boxStore) {
        teacherBox = boxStore.boxFor(Teacher.class);
    }

    public Completable save(Teacher teacher) {
        return Completable.fromAction(() -> teacherBox.put(teacher));
    }

    public Single<List<Teacher>> getAllTeachers() {
        return Single.fromCallable(teacherBox::getAll);
    }
}
