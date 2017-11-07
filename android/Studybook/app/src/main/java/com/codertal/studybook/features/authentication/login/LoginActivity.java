/*
 * Created by Talab Omar on 10/26/17 1:38 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/26/17 1:18 PM
 */

package com.codertal.studybook.features.authentication.login;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;

import com.codertal.studybook.BuildConfig;
import com.codertal.studybook.Henson;
import com.codertal.studybook.R;
import com.codertal.studybook.data.users.source.UsersRepository;
import com.codertal.studybook.features.authentication.login.domain.LoginResponseAdapter;
import com.codertal.studybook.features.dashboard.DashboardActivity;
import com.codertal.studybook.features.loading.LoadingActivity;
import com.codertal.studybook.util.ClickManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    @BindView(R.id.cl_login)
    View mLoginLayout;

    @BindView(R.id.btn_login)
    AppCompatButton mLoginButton;

    @BindView(R.id.btn_skip_login)
    AppCompatButton mSkipLoginButton;

    @Inject
    UsersRepository mUsersRepository;

    @Inject
    ClickManager mClickManager;

    private static final int RC_SIGN_IN = 11;

    private LoginContract.Presenter mPresenter;


    //LIFECYCLE METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        //TODO: remove once done with login screen
        //FirebaseAuth.getInstance().signOut();

        mPresenter = new LoginPresenter(this, mUsersRepository, AndroidSchedulers.mainThread(),
                mClickManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            mPresenter.processLoginResult(
                    LoginResponseAdapter.activityResultToLoginResponse(resultCode,
                    IdpResponse.fromResultIntent(data)));
        }
    }


    //VIEW HANDLES

    @OnClick(R.id.btn_login)
    public void onLoginClick(View view) {
        mPresenter.loadLogin(view.getId());
    }

    @OnClick(R.id.btn_skip_login)
    public void onSkipLoginClick(View view) {
        mPresenter.loadSkipLogin(view.getId());
    }


    // CONTRACT METHODS //

    @Override
    public void showLoginUi() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.app_logo)
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void showDashboard() {
        Intent dashboardIntent = Henson.with(this)
                .gotoDashboardActivity()
                .build();

        startActivity(dashboardIntent);
        finish();
    }

    @Override
    public void showNetworkErrorMessage() {
        Snackbar.make(mLoginLayout, getString(R.string.login_network_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showUnknownErrorMessage() {
        Snackbar.make(mLoginLayout, getString(R.string.login_unknown_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showCancelledMessage() {
        Snackbar.make(mLoginLayout, getString(R.string.login_cancelled), Snackbar.LENGTH_LONG).show();
    }
}
