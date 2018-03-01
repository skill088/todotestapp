package com.projects.vkotov.todotestapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by skill on 01.03.2018.
 */

public class Prefs {

    private final static String TOKEN = "TOKEN";

    private static SharedPreferences getInstance(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getToken(Context context) {
        return getInstance(context).getString(TOKEN, null);
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences.Editor edit = getInstance(context).edit();
        edit.putString(TOKEN, token);
        edit.apply();
    }
}
