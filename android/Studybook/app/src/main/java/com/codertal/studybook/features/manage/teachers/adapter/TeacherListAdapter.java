/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codertal.studybook.R;
import com.codertal.studybook.base.adapter.BaseRecyclerViewAdapter;
import com.codertal.studybook.data.teachers.Teacher;

import butterknife.BindView;

public class TeacherListAdapter extends BaseRecyclerViewAdapter<Teacher>{

    public TeacherListAdapter(@NonNull OnViewHolderClick<Teacher> onTeacherClickListener, @NonNull ViewGroup emptyView) {
        super(onTeacherClickListener, emptyView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new TeacherViewHolder(createView(viewGroup, viewType));
    }

    @Override
    protected View createView(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return inflater.inflate(R.layout.list_item_teacher, parent, false);
    }

    @Override
    protected void bindView(Teacher currentTeacher, BaseRecyclerViewAdapter.ViewHolder holder) {
        TeacherViewHolder teacherViewHolder = (TeacherViewHolder) holder;

        teacherViewHolder.mTeacherNameView.setText(currentTeacher.getName());
    }


    class TeacherViewHolder extends ViewHolder{
        @BindView(R.id.tv_teacher_name)
        TextView mTeacherNameView;

        TeacherViewHolder(View itemView){
            super(itemView);
        }
    }
}
