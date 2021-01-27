package com.dicoding.androcoding.blodonapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dicoding.androcoding.blodonapp.Model.login.LoginData;

import java.util.HashMap;

public class SessionManager {
    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_ID ="user_id";
    public static final String USERNAME ="username";

    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    //Memanggil Session
    public void createLoginSession(LoginData user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_ID, user.getUserId());
        editor.putString(USERNAME, user.getUsername());
        editor.commit();
    }

    //Memanggil data session
    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME,null));
        return user;
    }

    //Logout Session
    public void LogoutSession(){
        editor.clear();
        editor.commit();
    }

    public boolean IsLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}
