/*
 * Created by Talab Omar on 11/11/17 3:05 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/11/17 2:53 PM
 */

package com.codertal.studybook.data.classes;

import com.codertal.studybook.data.teachers.Teacher;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class ClassInfo {
    @Id
    private long id;

    private String name;

    private ToOne<Teacher> teacher;

    public ClassInfo() {
    }

    public ClassInfo(String name) {
        this.name = name;
        id = 0;
    }

    public ClassInfo(String name, Teacher teacher) {
        this.name = name;
        this.teacher.setTarget(teacher);
        id = 0; //ObjectBox will assign unique id
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToOne<Teacher> getTeacher() {
        return teacher;
    }

    public void setTeacher(ToOne<Teacher> teacher) {
        this.teacher = teacher;
    }
}
