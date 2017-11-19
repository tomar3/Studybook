/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.users;

import com.codertal.studybook.data.users.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Single;

public class UsersRepository {
    private FirebaseAuth mFirebaseAuth;

    public UsersRepository() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }


    public Single<User> getCurrentUser() {
        return Single.fromCallable(() -> {
            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

            if(firebaseUser != null){
                return new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getUid());
            }else {
                return null;
            }
        });
    }

    public boolean containsUserDatabase(String userId){
        return false;
    }
}
