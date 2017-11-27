/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.base;

public interface StatefulPresenter<S extends BaseState> {

    void restoreState(S state);

    S getState();
}
