/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

public class EditAddClassState implements EditAddClassContract.State{

    private final int chosenTeacherIndex;

    public EditAddClassState(int chosenTeacherIndex) {
        this.chosenTeacherIndex = chosenTeacherIndex;
    }

    @Override
    public int getLastTeacherPosition() {
        return chosenTeacherIndex;
    }
}
