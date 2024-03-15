package com.example.allclear.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    private SharedPreferences preferences;
    private static final String PREFS = "prefs";
    private static final String Access_Token = "Access_Token";
    private static final String Refresh_Token = "Refresh_Token";
    private static final Long User_Id = -1L;
    private static final boolean AUTO_LOGIN = false;

    public void setAutoLogin(Boolean bool) {
        preferences.edit().putBoolean("AUTO_LOGIN", bool).commit();
    }

    public boolean getAutoLogin(Boolean defBool) {
        return preferences.getBoolean("AUTO_LOGIN", defBool);
    }

    public PreferenceUtil(Context context) {
        preferences = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
    }
    public void setUserId(Long value){
        preferences.edit().putLong("User_Id", value).commit();
    }

    public Long getUserId(Long defValue){
        return preferences.getLong("User_Id",defValue);
    }

    public void setAccessToken(String value) {
        preferences.edit().putString(Access_Token, value).commit();
    }

    public String getAccessToken(String defValue) {
        return preferences.getString(Access_Token,defValue);
    }

    public void setRefreshToken(String value) {
        preferences.edit().putString(Refresh_Token, value).commit();
    }

    public String getRefreshToken(String defValue) {
        return preferences.getString(Refresh_Token,defValue);
    }

    public void clearLoginInfo() {
        preferences.edit().clear().apply();
    }
}
