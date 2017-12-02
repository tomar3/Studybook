/*
 * Created by Talab Omar on 10/31/17 1:16 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/31/17 1:16 PM
 */

package com.codertal.studybook.features.loading;


import com.codertal.studybook.base.presenter.SubscribablePresenter;

public interface LoadingContract {

    interface View {

        void showDashboardUi();

    }


    abstract class Presenter extends SubscribablePresenter {


    }
}
