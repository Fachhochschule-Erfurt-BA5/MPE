package com.pme.mpe.core;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.pme.mpe.R;
import com.pme.mpe.SplashActivity;
import com.pme.mpe.model.user.User;
import com.pme.mpe.storage.KeyValueStore;
import com.pme.mpe.storage.repository.UserRepository;

public class MainApplication extends Application {

    // """"""""" work with Key Value Store """""""""
    private static final String STORE_KEY_USERNAME ="User Name";
    EditText usernameInput;
    boolean isFirstUse = true;

    private KeyValueStore store;

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

        // if not the First use, it will go to the main Activity, otherwise will call the Login Activity
        if(!username.isEmpty()){
            isFirstUse = false;
        }

        this.getStore().writeBoolValue("isFirstUse", isFirstUse);

    }

    public void putUsername(String username) {
        this.getStore().writeStringValue(STORE_KEY_USERNAME, username);
        isFirstUse = false;
    }



    public static final String LOG_TAG = "AppClass";



    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "Application created!");

        isFirstUse();
    }




    





}
