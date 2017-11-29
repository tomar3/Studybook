/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.classes;

public class ClassesState implements ClassesContract.State {
    private final int layoutManagerPosition;

    public ClassesState(int layoutManagerPosition) {
        this.layoutManagerPosition = layoutManagerPosition;
    }

    @Override
    public int getLayoutManagerPosition() {
        return layoutManagerPosition;
    }
}
