/*
 * Created by Talab Omar on 11/8/17 2:43 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 2:43 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import android.support.annotation.NonNull;

import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.source.ClassesRepository;

public class EditAddClassPresenter implements EditAddClassContract.Presenter{

    @NonNull
    EditAddClassContract.View mEditClassView;

    private ClassesRepository mClassesRepository;

    public EditAddClassPresenter(@NonNull EditAddClassContract.View editClassView,
                                 @NonNull ClassesRepository classesRepository) {
        mEditClassView = editClassView;
        mClassesRepository = classesRepository;
    }


    @Override
    public void verifySaveClass(String className) {

        if(className.isEmpty()){
            mEditClassView.showRequiredFields();
        }else{

            //TODO: Save into database
            ClassInfo classInfo = new ClassInfo(className);
            mClassesRepository.save(classInfo);

            mEditClassView.returnToClassesUi();
        }

    }
}
