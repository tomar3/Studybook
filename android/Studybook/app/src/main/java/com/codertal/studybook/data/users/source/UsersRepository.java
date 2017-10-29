/*
 * Created by Talab Omar on 10/28/17 12:18 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/28/17 12:18 PM
 */

package com.codertal.studybook.data.users.source;

import com.codertal.studybook.data.users.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Single;

public class UsersRepository {
    private FirebaseAuth mFirebaseAuth;

    public UsersRepository() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public User getCurrentUser(){
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            return new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getUid());
        }else {
            return null;
        }
    }
}
