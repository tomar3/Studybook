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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
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

    @BindView(R.id.fab_save_class)
    FloatingActionButton mSaveFab;

    @Inject
    ClassesRepository mClassesRepository;

    private EditAddClassContract.Presenter mPresenter;
    private boolean mLoadingSave;
    private Runnable mRotateRunnable;


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

        setUpRotateRunnable();
        mLoadingSave = false;

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
    public void showSaveError() {

    }

    @Override
    public void showLoadingIndicator(boolean loading) {
        mEditClassName.setEnabled(!loading);
        mSaveFab.setEnabled(!loading);

        //Will be used in rotate runnable to know when to stop rotating
        mLoadingSave = loading;

        if(loading) {

            //Rotate save button
            rotate360View(mSaveFab, mRotateRunnable);

            //Change save button image to circle
            mSaveFab.postDelayed(() -> mSaveFab.setImageDrawable(
                    ContextCompat.getDrawable(EditAddClassActivity.this, R.drawable.half_circle)),
                    100);
        }
    }

    @OnClick(R.id.fab_save_class)
    public void onSaveClassClick() {
        mPresenter.verifySaveClass(mEditClassName.getText().toString());
    }


    private void setUpRotateRunnable() {
        mRotateRunnable = new Runnable() {
            @Override
            public void run() {

                //If still loading save, keep rotating
                if(mLoadingSave) {
                    rotate360View(mSaveFab, this);
                }else {
                    //Restore button state
                    mSaveFab.setImageDrawable(
                            ContextCompat.getDrawable(EditAddClassActivity.this,
                                    R.drawable.ic_check));

                }
            }
        };
    }


    private void rotate360View(View rotateView, Runnable endAction) {
        rotateView.animate()
                .rotationBy(360)
                .withEndAction(endAction)
                .setDuration(1000)
                .setInterpolator(new LinearInterpolator())
                .start();
    }
}
