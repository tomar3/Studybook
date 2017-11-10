/*
 * Created by Talab Omar on 11/8/17 2:43 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 2:43 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import android.support.annotation.NonNull;

public class EditAddClassPresenter implements EditAddClassContract.Presenter{

    @NonNull
    EditAddClassContract.View mEditClassView;

    public EditAddClassPresenter(@NonNull EditAddClassContract.View editClassView) {
        this.mEditClassView = editClassView;
    }


    @Override
    public void verifySaveClass(String className) {

        if(className.isEmpty()){
            mEditClassView.showRequiredFields();
        }else{
            //TODO: Save into database


            mEditClassView.returnToClassesUi();
        }

    }
}
