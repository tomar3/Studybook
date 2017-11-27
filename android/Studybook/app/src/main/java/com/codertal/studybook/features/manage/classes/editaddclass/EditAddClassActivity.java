/*
 * Created by Talab Omar on 11/8/17 12:29 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/8/17 12:29 PM
 */

package com.codertal.studybook.features.manage.classes.editaddclass;

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
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codertal.studybook.R;
import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.ClassesRepository;
import com.codertal.studybook.data.teachers.TeachersRepository;
import com.codertal.studybook.features.manage.classes.adapter.HintAdapter;
import com.codertal.studybook.util.dialog.DialogUtils;
import com.codertal.studybook.util.dialog.TextSubmitListener;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import es.dmoral.toasty.Toasty;

public class EditAddClassActivity extends AppCompatActivity implements EditAddClassContract.View {
    private static final int ADD_TEACHER_INDEX = 1;
    private static final String LAST_TEACHER_INDEX_KEY = "LAST_TEACHER_INDEX_KEY";

    @BindView(R.id.et_class_name)
    EditText mEditClassName;

    @BindView(R.id.fab_save_class)
    FloatingActionButton mSaveFab;

    @BindView(R.id.il_class_name)
    TextInputLayout mEditClassNameLayout;

    @BindView(R.id.spinner_teacher)
    Spinner mTeacherSpinner;

    @Inject
    ClassesRepository mClassesRepository;

    @Inject
    TeachersRepository mTeachersRepository;

    @Nullable
    @InjectExtra
    Long mClassId;

    private EditAddClassContract.Presenter mPresenter;
    private boolean mLoadingSave;
    private Runnable mRotateRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_class);
        ButterKnife.bind(this);
        Dart.inject(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpRotateRunnable();
        mLoadingSave = false;

        mPresenter = new EditAddClassPresenter(this, mClassesRepository, mTeachersRepository);

        //If class id given, fill fields with class info
        if(mClassId != null){
            setTitle(getString(R.string.title_edit_class));
            mEditClassNameLayout.setHintAnimationEnabled(false);

            mPresenter.loadClassInfo(mClassId);
        }

        if(savedInstanceState != null) {
            mPresenter.restoreState(readFromBundle(savedInstanceState));
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        writeToBundle(outState, mPresenter.getState());
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
        Toasty.error(this,
                getString(R.string.edit_unable_to_save),
                Toast.LENGTH_LONG,
                true).show();
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

    @Override
    public void fillClassInfo(ClassInfo classInfo) {
        mEditClassName.setText(classInfo.getName());
        mEditClassNameLayout.setHintAnimationEnabled(true);
    }

    @Override
    public void showLoadClassInfoError() {
        Toasty.error(this,
                getString(R.string.edit_unable_to_load),
                Toast.LENGTH_LONG,
                true).show();

        finish();
    }

    @Override
    public void showLoadTeachersError() {
        Toasty.warning(this,
                getString(R.string.edit_unable_to_load),
                Toast.LENGTH_LONG,
                true).show();

        setUpTeacherSpinner(new ArrayList<>());
    }

    @Override
    public void fillTeacherOptionsList(List<String> teacherOptionsList) {
        setUpTeacherSpinner(teacherOptionsList);
    }

    @Override
    public void showAddTeacherDialog() {
        TextSubmitListener saveTeacherListener = inputText -> mPresenter.saveNewTeacher(inputText);

        ViewGroup parentView = findViewById(android.R.id.content);
        View dialogLayout = LayoutInflater.from(this)
                .inflate(R.layout.dialog_text_input, parentView, false);


        DialogUtils.displayTextInputDialog(this, getString(R.string.edit_teacher_add_new),
                getString(R.string.edit_teacher_add_message), getString(R.string.edit_teacher_name_hint),
                dialogLayout, saveTeacherListener, null);
    }

    @Override
    public void selectTeacherPosition(int teacherPosition) {
        mTeacherSpinner.setSelection(teacherPosition);
    }

    @Override
    public void showTeacherSaveSuccess() {
        Toasty.success(this, getString(R.string.edit_teacher_added)).show();
    }

    @Override
    public void showTeacherSaveError() {
        Toasty.error(this, getString(R.string.edit_teacher_add_error)).show();
    }

    @Override
    public int getSelectedTeacherPosition() {
        return mTeacherSpinner.getSelectedItemPosition();
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


    private void setUpTeacherSpinner(List<String> teacherOptions) {
        teacherOptions.add(0, getString(R.string.edit_teacher_none));
        teacherOptions.add(ADD_TEACHER_INDEX, getString(R.string.edit_teacher_add_new));

        HintAdapter teacherDataAdapter = new HintAdapter(this,
                android.R.layout.simple_spinner_item, teacherOptions);

        teacherDataAdapter.setDropDownViewResource(R.layout.spinner_item);

        mTeacherSpinner.setAdapter(teacherDataAdapter);

        mTeacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == ADD_TEACHER_INDEX ){
                    mPresenter.loadAddNewTeacher();
                }else {
                    mPresenter.saveTeacherPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void writeToBundle(Bundle outState, EditAddClassContract.State state) {
        outState.putInt(LAST_TEACHER_INDEX_KEY, state.getLastTeacherPosition());
    }

    private EditAddClassContract.State readFromBundle(Bundle savedInstanceState) {
        int lastTeacherPosition = savedInstanceState.getInt(LAST_TEACHER_INDEX_KEY);

        return new EditAddClassState(lastTeacherPosition);
    }
}
