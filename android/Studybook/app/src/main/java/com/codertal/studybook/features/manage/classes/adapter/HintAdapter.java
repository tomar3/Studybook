/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.classes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codertal.studybook.R;

import java.util.List;

public class HintAdapter extends ArrayAdapter<String> {
    private Context mContext;


    public HintAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    public HintAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mContext = context;
    }

    public HintAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public HintAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }

    public HintAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public HintAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }

//    @Override
//    public boolean isEnabled(int position){
//        //Disable first item which is used for hint
//        return position != 0;
//    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        if(position == 0){
            //Set the hint text color
            tv.setTextColor(Color.GRAY);
        }
        else if(position == 1){
            //Set the add new text view properties
            Drawable addDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_add);
            DrawableCompat.setTint(addDrawable, ContextCompat.getColor(mContext, R.color.primary_light));

            tv.setTextColor(ContextCompat.getColor(mContext, R.color.primary_light));
            tv.setCompoundDrawablesWithIntrinsicBounds(addDrawable, null, null, null);
           // tv.setCompoundDrawablePadding(16);
        }
        else {
            tv.setTextColor(Color.BLACK);
        }

        return view;
    }
}
