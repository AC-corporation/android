package com.example.allclear;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.Utils;

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
