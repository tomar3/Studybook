/*
 * Created by Talab Omar on 10/29/17 11:40 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/29/17 11:40 AM
 */

package com.codertal.studybook.features.authentication.login.domain;


public class LoginResponse {
    private final int loginResult;

    public LoginResponse(int loginResult) {
        this.loginResult = loginResult;
    }

    public int getLoginResult() {
        return loginResult;
    }

    public class ResponseCodes {
        public static final int LOGIN_SUCCESS = 1;
        public static final int LOGIN_CANCELLED = 2;
        public static final int NETWORK_ERROR = 3;
        public static final int UNKNOWN_ERROR = 4;

    }
}
