/*
 * Created by Talab Omar on 10/27/17 4:26 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/27/17 4:16 PM
 */

package com.codertal.studybook.features.authentication.login;

public interface LoginContract {

    interface View{

        void showLoginUi();

        void showDashboardUi();

        void enableButtons(boolean enabled);
    }

    interface Presenter{

        void loadLogin();

        void loadSkipLogin();

        void loadCurrentUser();
    }
}
