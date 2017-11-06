/*
 * Created by Talab Omar on 11/5/17 7:37 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/5/17 7:37 PM
 */

package com.codertal.studybook.base;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class BaseTabFragment extends Fragment {
    @NonNull
    protected String sFragmentTag;

    public String getFragmentTag() {
        return sFragmentTag;
    }
}
