package com.android.buffer.fccbengaluru.repository;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by incred on 4/12/17.
 */

public class SharedPreference {
    private SharedPreferences mSharedPreferences;
    private String prefereneName = "fccBengaluruPref";
    private String IsLogin = "isLogin";
    private String customerEmail = "customerEmail";
    private String customerId = "customerId";
    private String fbToken = "fbToken";
    private String gpToken = "gpToken";

    public SharedPreference(final Context context) {
        mSharedPreferences = context.getSharedPreferences(prefereneName,Context.MODE_PRIVATE);
    }

    public void saveEmailLogin(String email,String Id){
        //saving user basic info for authentication
        mSharedPreferences.edit().putString(customerEmail,email).apply();
        mSharedPreferences.edit().putBoolean(IsLogin,true).apply();
        mSharedPreferences.edit().putString(customerId,Id).apply();
    }

    public boolean isLoggedIn(){
        //returning the logged in flag
        return mSharedPreferences.getBoolean(IsLogin,false);
    }

    public void clearLogin(){
        //clear the login details
        mSharedPreferences.edit().clear().apply();;
    }
}
