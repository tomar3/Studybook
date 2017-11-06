/*
 * Created by Talab Omar on 11/5/17 7:59 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/5/17 7:59 PM
 */

package com.codertal.studybook.features.homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codertal.studybook.R;
import com.codertal.studybook.base.BaseTabFragment;

public class HomeworkFragment extends BaseTabFragment {

    public static HomeworkFragment newInstance() {
        HomeworkFragment fragment = new HomeworkFragment();
        fragment.sFragmentTag = HomeworkFragment.class.getSimpleName();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homework, container, false);
    }
}
