/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers.editaddteacher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.codertal.studybook.R;
import com.codertal.studybook.data.teachers.Teacher;
import com.codertal.studybook.data.teachers.TeachersRepository;
import com.codertal.studybook.util.ViewUtils;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import es.dmoral.toasty.Toasty;

public class EditAddTeacherActivity extends AppCompatActivity implements EditAddTeacherContract.View {

    @BindView(R.id.et_teacher_name)
    EditText mEditTeacherName;

    @BindView(R.id.fab_save_teacher)
    FloatingActionButton mSaveFab;

    @BindView(R.id.il_teacher_name)
    TextInputLayout mEditTeacherNameLayout;

    @Inject
    TeachersRepository mTeachersRepository;

    @Nullable
    @InjectExtra
    Long mTeacherId;

    private EditAddTeacherContract.Presenter mPresenter;
    private boolean mLoadingSave;
    private Runnable mRotateRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_teacher);
        ButterKnife.bind(this);
        Dart.inject(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpRotateRunnable();
        mLoadingSave = false;

        mPresenter = new EditAddTeacherPresenter(this, mTeachersRepository);

        //If teacher id given, fill fields with teacher info
        if(mTeacherId != null){
            setTitle(getString(R.string.title_edit_teacher));
            mEditTeacherNameLayout.setHintAnimationEnabled(false);

            mPresenter.loadTeacher(mTeacherId);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mPresenter.unsubscribe();
    }




    @Override
    public void returnToTeachersUi() {
        finish();
    }

    @Override
    public void showRequiredFields() {
        mEditTeacherName.setError(getString(R.string.required_label));
    }

    @Override
    public void showSaveError() {
        Toasty.error(this,
                getString(R.string.edit_unable_to_save),
                Toast.LENGTH_LONG,
                true).show();
    }

    @Override
    public void showLoadingIndicator(boolean loading) {
        mEditTeacherName.setEnabled(!loading);
        mSaveFab.setEnabled(!loading);

        //Will be used in rotate runnable to know when to stop rotating
        mLoadingSave = loading;

        if(loading) {

            //Rotate saveAndReturnId button
            ViewUtils.rotate360View(mSaveFab, mRotateRunnable);

            //Change saveAndReturnId button image to circle
            mSaveFab.postDelayed(() -> mSaveFab.setImageDrawable(
                    ContextCompat.getDrawable(EditAddTeacherActivity.this, R.drawable.half_circle)),
                    100);
        }
    }

    @Override
    public void fillTeacherInfo(Teacher teacher) {
        mEditTeacherName.setText(teacher.getName());
        mEditTeacherName.setSelection(teacher.getName().length());
        mEditTeacherNameLayout.setHintAnimationEnabled(true);
    }

    @Override
    public void showLoadTeacherError() {
        Toasty.error(this,
                getString(R.string.edit_teacher_unable_to_load),
                Toast.LENGTH_LONG,
                true).show();

        finish();
    }

    @OnClick(R.id.fab_save_teacher)
    public void onSaveTeacherClick() {
        mPresenter.verifySaveTeacher(mEditTeacherName.getText().toString());
    }


    private void setUpRotateRunnable() {
        mRotateRunnable = new Runnable() {
            @Override
            public void run() {

                //If still loading save, keep rotating
                if(mLoadingSave) {
                    ViewUtils.rotate360View(mSaveFab, this);
                }else {
                    //Restore button state
                    mSaveFab.setImageDrawable(
                            ContextCompat.getDrawable(EditAddTeacherActivity.this,
                                    R.drawable.ic_check));
                }
            }
        };
    }
}
