/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codertal.studybook.Henson;
import com.codertal.studybook.R;
import com.codertal.studybook.base.StatefulView;
import com.codertal.studybook.base.adapter.BaseRecyclerViewAdapter;
import com.codertal.studybook.data.teachers.Teacher;
import com.codertal.studybook.data.teachers.TeachersRepository;
import com.codertal.studybook.features.manage.teachers.adapter.TeacherListAdapter;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.HensonNavigable;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import es.dmoral.toasty.Toasty;

@HensonNavigable
public class TeachersActivity extends AppCompatActivity implements TeachersContract.View,
        BaseRecyclerViewAdapter.OnViewHolderClick<Teacher>, StatefulView<TeachersContract.State> {

    private static final String LAYOUT_MANAGER_POSITION_KEY = "LAYOUT_MANAGER_POSITION_KEY";

    @BindView(R.id.rv_teachers)
    RecyclerView mTeachersRecycler;

    @BindView(R.id.layout_empty_view)
    ViewGroup mEmptyView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab_add_teacher)
    FloatingActionButton mAddTeacherFab;

    @Inject
    TeachersRepository mTeachersRepository;

    private TeachersContract.Presenter mPresenter;
    private TeacherListAdapter mTeacherListAdapter;
    private Toast mErrorToast;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);
        ButterKnife.bind(this);
        Dart.inject(this);

        setSupportActionBar(mToolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpEmptyView();
        setUpTeachersRecycler();

        mErrorToast = Toasty.error(this,
                getString(R.string.teacher_unable_to_load),
                Toast.LENGTH_LONG,
                true);

        mPresenter = new TeachersPresenter(this, mTeachersRepository);
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

        if(mErrorToast != null){
            mErrorToast.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        writeToBundle(outState, mPresenter.getState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPresenter.restoreState(readFromBundle(savedInstanceState));
    }

    @Override
    public void writeToBundle(Bundle outState, TeachersContract.State state) {
        outState.putInt(LAYOUT_MANAGER_POSITION_KEY, state.getLayoutManagerPosition());
    }

    @Override
    public TeachersContract.State readFromBundle(Bundle savedInstanceState) {
        int layoutManagerPosition = savedInstanceState.getInt(LAYOUT_MANAGER_POSITION_KEY);

        return new TeachersState(layoutManagerPosition);
    }

    @Override
    public void showAddTeacherUi() {
        Intent addTeacherIntent = Henson.with(this)
                .gotoEditAddClassActivity()
                .build();

        startActivity(addTeacherIntent);
    }

    @Override
    public void displayTeachers(List<Teacher> teachers) {
        mTeacherListAdapter.updateItems(teachers);
    }

    @Override
    public void displayLoadingError() {
        mErrorToast.show();
    }

    @Override
    public void showEditTeacherUi(long teacherId) {
        Intent editClassIntent = Henson.with(this)
                .gotoEditAddClassActivity()
                .mClassId(teacherId)
                .build();

        startActivity(editClassIntent);
    }

    @Override
    public int getLayoutManagerPosition() {
        return mLayoutManager.findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void restoreLayoutManagerPosition(int layoutManagerPosition) {
        mLayoutManager.scrollToPosition(layoutManagerPosition);
    }

    @OnClick(R.id.fab_add_teacher)
    public void onAddTeacherClick(){
        mPresenter.openAddTeacher();
    }

    @Override
    public void onViewHolderClick(View view, int position, Teacher selectedTeacher) {
        mPresenter.openEditTeacher(selectedTeacher.getId());
    }

    private void setUpEmptyView(){
        TextView emptyMessage = mEmptyView.findViewById(R.id.tv_empty_message);
        emptyMessage.setText(getString(R.string.teacher_empty_teachers));

        ImageView emptyImage = mEmptyView.findViewById(R.id.iv_empty);
        emptyImage.setImageResource(R.drawable.ic_school);
    }

    private void setUpTeachersRecycler() {
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mTeachersRecycler.setLayoutManager(mLayoutManager);

        mTeachersRecycler.setHasFixedSize(true);

        mTeacherListAdapter = new TeacherListAdapter(this, mEmptyView);

        mTeachersRecycler.setAdapter(mTeacherListAdapter);

        mTeachersRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mAddTeacherFab.getVisibility() == View.VISIBLE) {
                    mAddTeacherFab.hide();
                } else if (dy < 0 && mAddTeacherFab.getVisibility() != View.VISIBLE) {
                    mAddTeacherFab.show();
                }
            }
        });
    }
}
