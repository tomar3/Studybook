/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.teachers;

import com.codertal.studybook.data.classes.ClassInfo;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Teacher {

    @Id
    private long id;

    private String name;

    @Backlink
    private ToMany<ClassInfo> classes;

    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
        id = 0;
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

    public ToMany<ClassInfo> getClasses() {
        return classes;
    }

    public void setClasses(ToMany<ClassInfo> classes) {
        this.classes = classes;
    }
}
