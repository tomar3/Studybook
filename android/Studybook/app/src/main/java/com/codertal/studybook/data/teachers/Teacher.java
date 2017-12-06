/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.teachers;

import com.codertal.studybook.data.BaseData;
import com.codertal.studybook.data.classes.ClassInfo;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Teacher implements BaseData{

    @Id
    private long id;

    private String name;

    private String email;

    @Backlink
    private ToMany<ClassInfo> classes;

    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
        email = "";
        id = 0;
    }

    public Teacher(String name, String email) {
        this.name = name;
        this.email = email;
        id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ToMany<ClassInfo> getClasses() {
        return classes;
    }

    public void setClasses(ToMany<ClassInfo> classes) {
        this.classes = classes;
    }
}
