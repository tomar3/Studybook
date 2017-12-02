/*
 * Created by Talab Omar on 11/5/17 7:05 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/5/17 7:04 PM
 */

package com.codertal.studybook.features.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codertal.studybook.Henson;
import com.codertal.studybook.R;
import com.codertal.studybook.base.BaseTabFragment;
import com.codertal.studybook.features.manage.classes.ClassesActivity;

import java.util.zip.Inflater;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageFragment extends BaseTabFragment implements ManageContract.View{
    private ManageContract.Presenter mPresenter;

    public static ManageFragment newInstance() {
        ManageFragment fragment = new ManageFragment();
        fragment.sFragmentTag = ManageFragment.class.getSimpleName();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage, container, false);
        ButterKnife.bind(this, rootView);

        mPresenter = new ManagePresenter(this);

        return rootView;
    }


    @OnClick(R.id.cv_classes)
    public void onClassesClick(){
        mPresenter.openClasses();
    }

    @OnClick(R.id.cv_teachers)
    public void onTeachersClick(){
        mPresenter.openTeachers();
    }


    @Override
    public void showClassesUi() {
        if(isAdded()) {
            Intent classesIntent = Henson.with(getContext())
                    .gotoClassesActivity()
                    .build();

            startActivity(classesIntent);
        }
    }

    @Override
    public void showTeachersUi() {
        if(isAdded()) {
            Intent classesIntent = Henson.with(getContext())
                    .gotoTeachersActivity()
                    .build();

            startActivity(classesIntent);
        }
    }
}
