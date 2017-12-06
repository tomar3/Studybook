/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.classes;

import android.support.annotation.NonNull;

import com.codertal.studybook.data.teachers.Teacher;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.relation.ToOne;
import io.reactivex.Completable;
import io.reactivex.Single;

public class ClassesRepository {

    private final Box<ClassInfo> classInfoBox;
    private final Box<Teacher> teacherBox;

    @Inject
    public ClassesRepository(BoxStore boxStore){
        classInfoBox = boxStore.boxFor(ClassInfo.class);
        teacherBox = boxStore.boxFor(Teacher.class);
    }

    public Completable save(ClassInfo classInfo) {
        return Completable.fromAction(() -> classInfoBox.put(classInfo));
    }

    public Single<List<ClassInfo>> getAllClassesAlphabetically() {
        Query<ClassInfo> classesQuery = classInfoBox.query().order(ClassInfo_.name).build();
        return Single.fromCallable(classesQuery::find);
    }

    public Single<ClassInfo> getClassInfo(long classId) {
        return Single.fromCallable(() -> classInfoBox.get(classId));
    }

    public long getClassTeacherId(ClassInfo classInfo) {
        return classInfo.getTeacher().getTargetId();
    }

    public void assignTeacherToClass(ClassInfo classInfo, Teacher newTeacher) {
        ToOne<Teacher> teacherOfClass = classInfo.getTeacher();

        //Assign new teacher to the class (can be null)
        teacherOfClass.setTarget(newTeacher);
        classInfoBox.put(classInfo);
    }

    public boolean classHasTeacher(ClassInfo classInfo) {
        return !classInfo.getTeacher().isNull();
    }

}
