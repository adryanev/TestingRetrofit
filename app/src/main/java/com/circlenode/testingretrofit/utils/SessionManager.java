package com.circlenode.testingretrofit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.circlenode.testingretrofit.model.User;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private Context _context;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String ID_USER = "idUser";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String NAMA_LENGKAP = "namaLengkap";
    public static final String NO_HP = "noHP";

    public Context get_context() {
        return _context;
    }

    //constuctor
    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(User user){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(ID_USER,user.getIdUser());
        editor.putString(USERNAME,user.getUsername());
        editor.putString(PASSWORD,user.getPassword());
        editor.putString(NAMA_LENGKAP,user.getNamaLengkap());
        editor.putString(EMAIL,user.getEmail());
        editor.putString(NO_HP,user.getNoHp());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(ID_USER,sharedPreferences.getString(ID_USER,null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME,null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD,null));
        user.put(NAMA_LENGKAP, sharedPreferences.getString(NAMA_LENGKAP,null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL,null));
        user.put(NO_HP, sharedPreferences.getString(NO_HP,null));
        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }
}
