package com.example.phils.Shareprefered;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.phils.R;

public class AppConfig {
    private Context context;
    private SharedPreferences sharedPreferences;

    public AppConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file_key),Context.MODE_PRIVATE);
    }

    public boolean isUserLogin()
    {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_user_login),false);
    }

    public void updateUserLoginStatus(boolean status)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_user_login),status);
        editor.apply();
    }
    public void saveNameOfUser(String name)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_name_of_user),name);
        editor.apply();
    }
    public String getNameOfUser()
    {
        return sharedPreferences.getString(context.getString(R.string.pref_name_of_user),"Unknown");
    }

    public void saveIdOfUser(String id)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_id_of_user),id);
        editor.apply();
    }
    public String getIdOfUser()
    {
        return sharedPreferences.getString(context.getString(R.string.pref_id_of_user),"same");
    }

    public void saveUserName(String name)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.save_userName),name);
        editor.apply();
    }
    public String getUserName()
    {
        return sharedPreferences.getString(context.getString(R.string.save_userName),"Admin");
    }

    public void SaveLocation(String location)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.save_location),location);
        editor.apply();
    }
    public String getLocation()
    {
        return sharedPreferences.getString(context.getString(R.string.save_location),"padgha");
    }
}
