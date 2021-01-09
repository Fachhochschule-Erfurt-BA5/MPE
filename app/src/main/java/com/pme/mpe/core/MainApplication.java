package com.pme.mpe.core;

import android.app.Application;

public class MainApplication extends Application {
    public static final String LOG_TAG = "AppClass";

    @Override
    public void onCreate() {
        super.onCreate();

        testDatabase();
    }

    private void testDatabase() {

    }

}
