/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {
    private List<T> items;
    private ViewGroup emptyView;
    private OnViewHolderClick<T> listener;

    public interface OnViewHolderClick<T> {
        void onViewHolderClick(View view, int position, T item);
    }


    protected abstract View createView(ViewGroup viewGroup, int viewType);

    protected abstract void bindView(T item, BaseRecyclerViewAdapter.ViewHolder viewHolder);

    public BaseRecyclerViewAdapter(OnViewHolderClick<T> listener, ViewGroup emptyView) {
        this.listener = listener;
        this.emptyView = emptyView;
        items = new ArrayList<>();
    }

    @Override
    public abstract ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType);

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.ViewHolder holder, int position) {
        bindView(items.get(position), holder);
    }

    @Override
    public int getItemCount() {
        if(items.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
        }

        return items.size();
    }


    public void updateItems(List<T> list) {
        items = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onViewHolderClick(view, getAdapterPosition(), items.get(getAdapterPosition()));
            }
        }
    }
}
