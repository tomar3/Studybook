/*
 * Created by Talab Omar on 10/31/17 1:21 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/31/17 1:21 PM
 */

package com.codertal.studybook.features.loading;

import android.support.annotation.NonNull;

import com.codertal.studybook.data.database.DatabaseRepository;
import com.codertal.studybook.data.users.source.UsersRepository;

import static com.codertal.studybook.util.UserUtils.UserConstants.TEMP_USER;

public class LoadingPresenter extends LoadingContract.Presenter{
    @NonNull
    private LoadingContract.View mLoadingView;

    private final String mUserId;
    private final UsersRepository mUsersRepository;
    private final DatabaseRepository mDatabaseRepository;

    public LoadingPresenter(@NonNull LoadingContract.View loadingView, @NonNull String userId,
                            @NonNull UsersRepository usersRepository,
                            @NonNull DatabaseRepository databaseRepository) {
        mLoadingView = loadingView;
        mUserId = userId;
        mUsersRepository = usersRepository;
        mDatabaseRepository = databaseRepository;
    }

    @Override
    public void subscribe() {
        if(mUserId.equals(TEMP_USER)){
//            if(mUsersRepository.containsUserDatabase(mUserId)){
//                //Continue to dashboard with this database
//            }else {
//                //Create database and continue
//            }


        }else {
            //Firebase User
        }
    }

    private void initializeNewDatabase(){

    }
}
