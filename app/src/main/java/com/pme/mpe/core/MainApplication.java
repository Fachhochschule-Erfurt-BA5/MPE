package com.pme.mpe.core;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.pme.mpe.R;
import com.pme.mpe.SplashActivity;
import com.pme.mpe.model.user.User;
import com.pme.mpe.storage.KeyValueStore;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.storage.repository.UserRepository;

public class MainApplication extends Application {

    // """"""""" work with Key Value Store """""""""
    private static final String STORE_KEY_USERNAME ="User Name";
    private static final String STORE_KEY_USER_ID = "User Id";
    boolean isFirstUse = true;

    private KeyValueStore store;

    TasksPackageRepository tasksPackageRepository;


    // if our store is null, will create a new store to save the data
    public KeyValueStore getStore()
    {
        if( this.store == null ) {
            this.store = new KeyValueStore(this);
        }
        return this.store;
    }

    // to get the current username saved in memory, if it's empty "" --> for the First time
    public void isFirstUse() {
        String username = this.getStore().getStringValue(STORE_KEY_USERNAME);

        // isEmpty because the Default Value is "", if no user stored yet
        if(!username.isEmpty()){
            isFirstUse = false;
        }

        this.getStore().writeBoolValue("isFirstUse", isFirstUse);

    }

    // used to store the User name, so that the user doesn't need to login every time he uses the App
    public void putUsername(String username) {
        this.getStore().writeStringValue(STORE_KEY_USERNAME, username);
        isFirstUse = false;
    }

    //used to store the Id of the logged in user to filter the Date from Database
    public void storeUserId(int userId)
    {
        this.getStore().writeIntValue(STORE_KEY_USER_ID, userId);
    }



    public static final String LOG_TAG = "AppClass";



    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "Application created!");

        // test Database
        tasksPackageRepository.getCategoryBlocks();

        isFirstUse();
    }




    





}
