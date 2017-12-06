/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers.editaddteacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codertal.studybook.Henson;
import com.codertal.studybook.R;
import com.codertal.studybook.base.adapter.BaseRecyclerViewAdapter;
import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.teachers.Teacher;
import com.codertal.studybook.data.teachers.TeachersRepository;
import com.codertal.studybook.features.manage.classes.adapter.ClassListAdapter;
import com.codertal.studybook.util.ViewUtils;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import es.dmoral.toasty.Toasty;

public class EditAddTeacherActivity extends AppCompatActivity implements EditAddTeacherContract.View ,
        BaseRecyclerViewAdapter.OnViewHolderClick<ClassInfo> {

    @BindView(R.id.et_teacher_name)
    EditText mEditTeacherName;

    @BindView(R.id.et_teacher_email)
    EditText mEditTeacherEmail;

    @BindView(R.id.fab_save_teacher)
    FloatingActionButton mSaveFab;

    @BindView(R.id.il_teacher_name)
    TextInputLayout mEditTeacherNameLayout;

    @BindView(R.id.il_teacher_email)
    TextInputLayout mEditTeacherEmailLayout;

    @BindView(R.id.rv_classes)
    RecyclerView mClassesRecycler;

    @BindView(R.id.tv_assign_class)
    TextView mAssignTeacherView;

    @Inject
    TeachersRepository mTeachersRepository;

    @Nullable
    @InjectExtra
    Long mTeacherId;

    private EditAddTeacherContract.Presenter mPresenter;
    private ClassListAdapter mClassListAdapter;
    private LinearLayoutManager mLayoutManager;
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
        setUpClassesRecycler();
        mLoadingSave = false;

        mPresenter = new EditAddTeacherPresenter(this, mTeachersRepository);

        //If teacher id given, fill fields with teacher info
        if(mTeacherId != null){
            setTitle(getString(R.string.title_edit_teacher));
            mEditTeacherNameLayout.setHintAnimationEnabled(false);
            mEditTeacherEmailLayout.setHintAnimationEnabled(false);

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
        mEditTeacherEmail.setEnabled(!loading);
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
        mEditTeacherEmail.setText(teacher.getEmail());

        mEditTeacherName.setSelection(teacher.getName().length());
        mEditTeacherEmail.setSelection(teacher.getEmail().length());

        mEditTeacherNameLayout.setHintAnimationEnabled(true);
        mEditTeacherEmailLayout.setHintAnimationEnabled(true);
    }

    @Override
    public void showLoadTeacherError() {
        Toasty.error(this,
                getString(R.string.edit_teacher_unable_to_load),
                Toast.LENGTH_LONG,
                true).show();

        finish();
    }

    @Override
    public void displayClasses(List<ClassInfo> classes) {
        mClassListAdapter.updateItems(classes);
    }

    @Override
    public void showEditClassUi(long classId) {
        Intent editClassIntent = Henson.with(this)
                .gotoEditAddClassActivity()
                .mClassId(classId)
                .build();

        startActivity(editClassIntent);
    }

    @Override
    public void showClassesUi() {
        Intent classesIntent = Henson.with(this)
                .gotoClassesActivity()
                .build();

        startActivity(classesIntent);
    }

    @OnClick(R.id.fab_save_teacher)
    public void onSaveTeacherClick() {
        mPresenter.verifySaveTeacher(mEditTeacherName.getText().toString(),
                mEditTeacherEmail.getText().toString());
    }

    @OnClick(R.id.cv_classes)
    public void onEmptyClassesClick() {
        mPresenter.loadAllClasses();
    }

    @Override
    public void onViewHolderClick(View view, int position, ClassInfo item) {
        mPresenter.loadEditClass(item.getId());
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

    private void setUpClassesRecycler() {
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        mClassesRecycler.setLayoutManager(mLayoutManager);

        mClassesRecycler.setHasFixedSize(true);

        mClassListAdapter = new ClassListAdapter(this, mAssignTeacherView, false);

        mClassesRecycler.setAdapter(mClassListAdapter);
    }
}
