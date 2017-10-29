/*
 * Created by Talab Omar on 10/29/17 11:49 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/29/17 11:49 AM
 */

package com.codertal.studybook.features.authentication.login.domain;

import android.support.annotation.Nullable;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import static android.app.Activity.RESULT_OK;
import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.LOGIN_CANCELLED;
import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.LOGIN_SUCCESS;
import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.NETWORK_ERROR;
import static com.codertal.studybook.features.authentication.login.domain.LoginResponse.ResponseCodes.UNKNOWN_ERROR;

public class LoginResponseAdapter {

    public static LoginResponse activityResultToLoginResponse(int resultCode, @Nullable IdpResponse response){
        // Login success
        if (resultCode == RESULT_OK) {
            return new LoginResponse(LOGIN_SUCCESS);
        } else {
            // Login in failed

            if (response == null) {
                // User cancelled
                return new LoginResponse(LOGIN_CANCELLED);
            }

            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                return new LoginResponse(NETWORK_ERROR);
            }


            return new LoginResponse(UNKNOWN_ERROR);
        }
    }
}
