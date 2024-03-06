package com.example.allclear;

import android.app.Application;

import com.example.allclear.data.PreferenceUtil;

public class MyApplication extends Application {
    private static PreferenceUtil preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new PreferenceUtil(getApplicationContext());

    }

    public static PreferenceUtil getPreferences() {
        return preferences;
    }
}
