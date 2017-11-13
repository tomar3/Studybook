/*
 * Created by Talab Omar on 10/29/17 4:13 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/29/17 4:13 PM
 */

package com.codertal.studybook.base;


import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseRxPresenter {
    @NonNull
    protected CompositeDisposable mCompositeDisposable;

    public BaseRxPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public void unsubscribe(){
        mCompositeDisposable.clear();
    }
}
