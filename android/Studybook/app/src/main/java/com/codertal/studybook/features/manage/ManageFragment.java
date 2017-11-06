/*
 * Created by Talab Omar on 11/5/17 7:05 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/5/17 7:04 PM
 */

package com.codertal.studybook.features.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codertal.studybook.R;
import com.codertal.studybook.base.BaseTabFragment;

public class ManageFragment extends BaseTabFragment {

    public static ManageFragment newInstance() {
        ManageFragment fragment = new ManageFragment();
        fragment.sFragmentTag = ManageFragment.class.getSimpleName();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }
}
