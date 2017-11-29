/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.base;

import android.os.Bundle;

public interface StatefulView<S extends BaseState> {

    void writeToBundle(Bundle outState, S state);

    S readFromBundle(Bundle savedInstanceState);

}
