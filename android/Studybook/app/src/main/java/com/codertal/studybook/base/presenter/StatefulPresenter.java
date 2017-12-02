/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.base.presenter;

import com.codertal.studybook.base.BaseState;

public interface StatefulPresenter<S extends BaseState> {

    void restoreState(S state);

    S getState();
}
