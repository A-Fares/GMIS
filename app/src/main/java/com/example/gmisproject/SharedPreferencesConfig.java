package com.example.gmisproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesConfig(Context context) {
        this.context = context;
        sharedPreferences = (SharedPreferences) context.getSharedPreferences(context.getResources().getString(R.string.login_preferences_user), Context.MODE_PRIVATE);
    }

    // Shared Preference User Login

    public void writeUserLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_preferences_status_user), status);
        editor.commit();
    }

    public boolean readUserLoginStatus() {
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_preferences_status_user), false);
        return status;
    }

    // Shared Preference Worker Login


    public void writeWorkerLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_preferences_status_worker), status);
        editor.commit();
    }

    public boolean readWorkerLoginStatus() {
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_preferences_status_worker), false);
        return status;
    }


}
