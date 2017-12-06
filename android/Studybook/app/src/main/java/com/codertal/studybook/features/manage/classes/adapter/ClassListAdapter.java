/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.classes.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codertal.studybook.R;
import com.codertal.studybook.base.adapter.BaseRecyclerViewAdapter;
import com.codertal.studybook.data.classes.ClassInfo;

import butterknife.BindView;

public class ClassListAdapter extends BaseRecyclerViewAdapter<ClassInfo>{
    private boolean mBigClassItemLayout;

    public ClassListAdapter(@NonNull OnViewHolderClick<ClassInfo> onClassClickListener,
                            @NonNull View emptyView, boolean bigClassItemLayout) {
        super(onClassClickListener, emptyView);

        mBigClassItemLayout = bigClassItemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ClassViewHolder(createView(viewGroup, viewType));
    }

    @Override
    protected View createView(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(mBigClassItemLayout) {
            return inflater.inflate(R.layout.list_item_class_big, parent, false);
        }else {
            return inflater.inflate(R.layout.list_item_class_small, parent, false);
        }
    }

    @Override
    protected void bindView(ClassInfo currentClass, BaseRecyclerViewAdapter.ViewHolder holder) {
        ClassViewHolder classViewHolder = (ClassViewHolder) holder;

        classViewHolder.mClassNameView.setText(currentClass.getName());
    }


    class ClassViewHolder extends ViewHolder{
        @BindView(R.id.tv_class_name)
        TextView mClassNameView;

        ClassViewHolder(View itemView){
            super(itemView);
        }
    }
}
