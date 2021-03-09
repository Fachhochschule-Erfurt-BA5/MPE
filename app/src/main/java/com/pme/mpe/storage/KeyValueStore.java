package com.pme.mpe.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class KeyValueStore {
    private static String KEY_VALUE_STORE_FILE_NAME = "TODO_app_kv_store";
    private static final String DEFAULT_STRING_VALUE = "";
    private static final boolean DEFAULT_BOOL_VALUE = false;
    private static final int DEFAULT_INT_VALUE = -10;

    // Ref needed to access SharedPreferences
    private Application app;

    public KeyValueStore(Application application) {
        this.app = application;
    }

    private SharedPreferences getPreferences()
    {
        return this.app.getSharedPreferences( KEY_VALUE_STORE_FILE_NAME, Context.MODE_PRIVATE);
    }


    // to write the Username
    public void writeStringValue(String key, String value )
    {
        this.getPreferences().edit().putString(key, value).apply();
    }

    // to get the Username
    public String getStringValue( String key )
    {
        return this.getPreferences().getString( key, DEFAULT_STRING_VALUE );
    }

    public void writeBoolValue(String key, boolean value )
    {
        this.getPreferences().edit().putBoolean(key, value).apply();
    }

    // to get the Username
    public boolean getBoolValue( String key )
    {
        return this.getPreferences().getBoolean( key, DEFAULT_BOOL_VALUE );
    }

    public void writeIntValue(String key, int intValue)
    {
        this.getPreferences().edit().putInt( key, intValue);
    }
    public int getIntValue(String key)
    {
        return this.getPreferences().getInt(key, DEFAULT_INT_VALUE);
    }

}
