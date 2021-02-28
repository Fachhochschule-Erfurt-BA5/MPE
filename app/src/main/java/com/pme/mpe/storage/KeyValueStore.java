package com.pme.mpe.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class KeyValueStore {
    private static String KEY_VALUE_STORE_FILE_NAME = "TODO_app_kv_store";
    private static final int DEFAULT_INT_VALUE = -1;

    // Ref needed to access SharedPreferences
    private Application app;

    public KeyValueStore(Application application) {
        this.app = application;
    }

    private SharedPreferences getPreferences()
    {
        return this.app.getSharedPreferences( KEY_VALUE_STORE_FILE_NAME, Context.MODE_PRIVATE);
    }

    // to write the name of the User and his Id
    public void writeIntValue(String key, int value )
    {
        this.getPreferences().edit().putInt(key, value).apply();
    }

    // to get the id of the User
    public int getIntValue( String key )
    {
        return this.getPreferences().getInt( key, DEFAULT_INT_VALUE );
    }

}
