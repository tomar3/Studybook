/*
 * Created by Talab Omar on 11/7/17 1:32 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/7/17 1:32 PM
 */

package com.codertal.studybook.features.manage.classes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.codertal.studybook.R;
import com.codertal.studybook.data.model.Class;
import com.codertal.studybook.features.manage.classes.adapter.ClassListAdapter;
import com.f2prateek.dart.HensonNavigable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@HensonNavigable
public class ClassesActivity extends AppCompatActivity implements ClassListAdapter.OnClassClickListener {

    @BindView(R.id.rv_classes)
    RecyclerView mClassesRecycler;

    private ClassListAdapter mClassListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpClassesRecycler();
    }

    @OnClick(R.id.fab_add_class)
    public void onAddClassClick(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onClassClick(Class selectedClass) {

    }

    private void setUpClassesRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mClassesRecycler.setLayoutManager(linearLayoutManager);

        mClassesRecycler.setHasFixedSize(true);

        mClassListAdapter = new ClassListAdapter(this);

        mClassesRecycler.setAdapter(mClassListAdapter);
    }
}
