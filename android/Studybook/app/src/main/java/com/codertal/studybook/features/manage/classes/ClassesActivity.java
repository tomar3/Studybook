/*
 * Created by Talab Omar on 11/7/17 1:32 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/7/17 1:32 PM
 */

package com.codertal.studybook.features.manage.classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codertal.studybook.Henson;
import com.codertal.studybook.R;
import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.source.ClassesRepository;
import com.codertal.studybook.features.manage.classes.adapter.ClassListAdapter;
import com.f2prateek.dart.HensonNavigable;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;

@HensonNavigable
public class ClassesActivity extends AppCompatActivity implements ClassesContract.View,
        ClassListAdapter.OnClassClickListener {

    @BindView(R.id.rv_classes)
    RecyclerView mClassesRecycler;

    @BindView(R.id.layout_empty_view)
    ViewGroup mEmptyView;

    @Inject
    ClassesRepository mClassesRepository;

    private ClassesContract.Presenter mPresenter;
    private ClassListAdapter mClassListAdapter;
    private Toast mErrorToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpEmptyView();
        setUpClassesRecycler();

        mErrorToast = Toasty.error(this,
                getString(R.string.class_unable_to_load),
                Toast.LENGTH_LONG,
                true);

        mPresenter = new ClassesPresenter(this, mClassesRepository);
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
    public void showAddClassUi() {
        Intent addClassIntent = Henson.with(this)
                .gotoEditAddClassActivity()
                .build();

        startActivity(addClassIntent);
    }

    @Override
    public void displayClasses(List<ClassInfo> classes) {
        mClassListAdapter.updateData(classes);
    }

    @Override
    public void displayLoadingError() {
        mErrorToast.show();
    }

    @OnClick(R.id.fab_add_class)
    public void onAddClassClick(){
        mPresenter.openAddClass();
    }

    @Override
    public void onClassClick(ClassInfo selectedClass) {

    }

    private void setUpEmptyView(){
        TextView emptyMessage = mEmptyView.findViewById(R.id.tv_empty_message);
        emptyMessage.setText("Click the '+' to add a class");

        ImageView emptyImage = mEmptyView.findViewById(R.id.iv_empty);
        emptyImage.setImageResource(R.drawable.app_logo);
    }

    private void setUpClassesRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mClassesRecycler.setLayoutManager(linearLayoutManager);

        mClassesRecycler.setHasFixedSize(true);

        mClassListAdapter = new ClassListAdapter(this, mEmptyView);

        mClassesRecycler.setAdapter(mClassListAdapter);
    }
}
