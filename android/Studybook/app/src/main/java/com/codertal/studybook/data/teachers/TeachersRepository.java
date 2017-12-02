/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.teachers;


import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.reactivex.Completable;
import io.reactivex.Single;

public class TeachersRepository {

    private final Box<Teacher> teacherBox;

    @Inject
    public TeachersRepository(BoxStore boxStore) {
        teacherBox = boxStore.boxFor(Teacher.class);
    }

    public Single<Long> saveAndReturnId(Teacher teacher) {
        return Single.fromCallable(() -> teacherBox.put(teacher));
    }

    public Completable save(Teacher teacher) {
        return Completable.fromAction(() -> teacherBox.put(teacher));
    }

    public Single<List<Teacher>> getAllTeachersAlphabetically() {
        Query<Teacher> teachersQuery = teacherBox.query().order(Teacher_.name).build();
        return Single.fromCallable(teachersQuery::find);
    }

    public Single<Teacher> getTeacher(long teacherId) {
        return Single.fromCallable(() -> teacherBox.get(teacherId));
    }
}
