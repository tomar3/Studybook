package com.codertal.studybook.app;

import android.app.Application;

import com.codertal.studybook.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Set up Timber
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        //Set up LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
