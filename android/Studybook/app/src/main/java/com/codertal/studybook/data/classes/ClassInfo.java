/*
 * Created by Talab Omar on 11/11/17 3:05 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/11/17 2:53 PM
 */

package com.codertal.studybook.data.classes;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ClassInfo {
    @Id
    private long id;

    private String name;

    public ClassInfo(String name) {
        this.name = name;
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
}
