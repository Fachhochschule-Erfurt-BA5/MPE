package com.pme.mpe.core;

import android.app.Application;
import android.util.Log;

import com.pme.mpe.activities.CategoryActivity.NewCategoryActivityViewModel;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.user.User;
import com.pme.mpe.storage.repository.UserRepository;

public class MainApplication extends Application {
    public static final String LOG_TAG = "AppClass";



    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "Application created!");

        testDatabase();
    }

    private void testDatabase()
    {
        Log.i(LOG_TAG, "Hello, this a test from the database");

        UserRepository userRepository = new UserRepository(this);

        User DBTrigger = new User("aa", "aa","aa", "aaa", "aaa");
        userRepository.insert(DBTrigger);

    }
}
