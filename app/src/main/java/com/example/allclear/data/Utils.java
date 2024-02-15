package com.example.allclear.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    private static final String PREFS = "prefs";
    private static final String Access_Token = "Access_Token";
    private static final String Refresh_Token = "Refresh_Token";
    private static final String User_Id = "User_Id";
    private Context mContext;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;
    private static Utils instance;

    public static synchronized Utils init(Context context) {
        if(instance == null)
            instance = new Utils(context);
        return instance;
    }

    private Utils(Context context) {
        mContext = context;
        prefs = mContext.getSharedPreferences(PREFS,Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }
    public void setUserId(Long value){
        prefsEditor.putLong(User_Id, value).commit();
    }

    public Long getUserId(Long defValue){
        return prefs.getLong(User_Id,defValue);
    }

    public void setAccessToken(String value) {
        prefsEditor.putString(Access_Token, value).commit();
    }

    public String getAccessToken(String defValue) {
        return prefs.getString(Access_Token,defValue);
    }

    public void setRefreshToken(String value) {
        prefsEditor.putString(Refresh_Token, value).commit();
    }

    public String getRefreshToken(String defValue) {
        return prefs.getString(Refresh_Token,defValue);
    }

    public void clearToken() {
        prefsEditor.clear().apply();
    }
}