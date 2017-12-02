/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers;

public class TeachersState implements TeachersContract.State {
    private final int layoutManagerPosition;

    public TeachersState(int layoutManagerPosition) {
        this.layoutManagerPosition = layoutManagerPosition;
    }

    @Override
    public int getLayoutManagerPosition() {
        return layoutManagerPosition;
    }
}
