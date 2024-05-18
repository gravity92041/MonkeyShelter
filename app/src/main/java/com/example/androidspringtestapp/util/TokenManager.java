package com.example.androidspringtestapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String SHARED_PREFS_NAME="my_shared_prefs";
    private static final String TOKEN_KEY = "jwt-token";
    private SharedPreferences sharedPreferences;

    public TokenManager(Context context){
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE);
    }

    public void saveToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY,token);
        editor.apply();
    }
    public String getToken(){
        return sharedPreferences.getString(TOKEN_KEY,null);
    }


}
