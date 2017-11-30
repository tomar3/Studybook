/*
 * Created by Talab Omar on 11/7/17 1:32 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/7/17 1:32 PM
 */

package com.codertal.studybook.features.manage.classes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
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
import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.classes.ClassesRepository;
import com.codertal.studybook.features.manage.classes.adapter.ClassListAdapter;
import com.codertal.studybook.util.ViewUtils;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.HensonNavigable;
import com.f2prateek.dart.InjectExtra;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import es.dmoral.toasty.Toasty;

public class ClassesActivity extends AppCompatActivity implements ClassesContract.View,
        BaseRecyclerViewAdapter.OnViewHolderClick<ClassInfo>, StatefulView<ClassesContract.State> {

    public static final String CLASSES_LIST_TYPE = "CLASSES_LIST_TYPE";
    public static final String TEACHERS_LIST_TYPE = "TEACHERS_LIST_TYPE";

    private static final String LAYOUT_MANAGER_POSITION_KEY = "LAYOUT_MANAGER_POSITION_KEY";

    @BindView(R.id.rv_classes)
    RecyclerView mClassesRecycler;

    @BindView(R.id.layout_empty_view)
    ViewGroup mEmptyView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab_add_class)
    FloatingActionButton mAddClassFab;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @InjectExtra
    String mListType;

    @Inject
    ClassesRepository mClassesRepository;

    private ClassesContract.Presenter mPresenter;
    private ClassListAdapter mClassListAdapter;
    private Toast mErrorToast;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        ButterKnife.bind(this);
        Dart.inject(this);

        setSupportActionBar(mToolbar);

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
    public void writeToBundle(Bundle outState, ClassesContract.State state) {
        outState.putInt(LAYOUT_MANAGER_POSITION_KEY, state.getLayoutManagerPosition());
    }

    @Override
    public ClassesContract.State readFromBundle(Bundle savedInstanceState) {
        int layoutManagerPosition = savedInstanceState.getInt(LAYOUT_MANAGER_POSITION_KEY);

        return new ClassesState(layoutManagerPosition);
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
       mClassListAdapter.updateItems(classes);
       // updateToolbarBehaviour(classes.size());
    }

    @Override
    public void displayLoadingError() {
        mErrorToast.show();
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
    public int getLayoutManagerPosition() {
        return mLayoutManager.findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void restoreLayoutManagerPosition(int layoutManagerPosition) {
        mLayoutManager.scrollToPosition(layoutManagerPosition);
    }

    @OnClick(R.id.fab_add_class)
    public void onAddClassClick(){
        mPresenter.openAddClass();
    }

    @Override
    public void onViewHolderClick(View view, int position, ClassInfo selectedClass) {
        mPresenter.openEditClass(selectedClass.getId());
    }

    private void setUpEmptyView(){
        TextView emptyMessage = mEmptyView.findViewById(R.id.tv_empty_message);
        emptyMessage.setText(getString(R.string.class_empty_classes));

        ImageView emptyImage = mEmptyView.findViewById(R.id.iv_empty);
        emptyImage.setImageResource(R.drawable.ic_school);
    }

    private void setUpClassesRecycler() {
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mClassesRecycler.setLayoutManager(mLayoutManager);

        mClassesRecycler.setHasFixedSize(true);

        mClassListAdapter = new ClassListAdapter(this, mEmptyView);

        mClassesRecycler.setAdapter(mClassListAdapter);

        mClassesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mAddClassFab.getVisibility() == View.VISIBLE) {
                    mAddClassFab.hide();
                } else if (dy < 0 && mAddClassFab.getVisibility() != View.VISIBLE) {
                    mAddClassFab.show();
                }
            }
        });
    }

    private void updateToolbarBehaviour(int dataSize){
        mToolbar.postDelayed(() -> {
            if (mLayoutManager.findLastCompletelyVisibleItemPosition() == dataSize-1) {
                ViewUtils.turnOffToolbarScrolling(mToolbar, mAppBarLayout);
            } else {
                ViewUtils.turnOnToolbarScrolling(mToolbar, mAppBarLayout);
            }
        }, 100);

    }
}
