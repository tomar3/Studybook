/*
 * Created by Talab Omar on 11/8/17 12:29 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 12:29 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.codertal.studybook.R;
import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.source.ClassesRepository;
import com.f2prateek.dart.HensonNavigable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

@HensonNavigable
public class EditAddClassActivity extends AppCompatActivity implements EditAddClassContract.View {

    @BindView(R.id.et_class_name)
    EditText mEditClassName;

    @Inject
    ClassesRepository mClassesRepository;

    private EditAddClassContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_class);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        if(mClassesRepository == null){
//            Timber.d("CLASS REPO IS NULL");
//        }else {
//            Timber.d("CLASS REPO IS INJECTED");
//        }

        mPresenter = new EditAddClassPresenter(this, mClassesRepository,
                AndroidSchedulers.mainThread());
    }

    @Override
    protected void onStop() {
        super.onStop();

        mPresenter.unsubscribe();
    }

    @Override
    public void returnToClassesUi() {
        finish();
    }

    @Override
    public void showRequiredFields() {
        mEditClassName.setError(getString(R.string.required_label));
    }

    @Override
    public void showSaveError(Throwable error) {

    }


    @OnClick(R.id.fab_save_class)
    public void onSaveClassClick() {
        mPresenter.verifySaveClass(mEditClassName.getText().toString());
    }
}
