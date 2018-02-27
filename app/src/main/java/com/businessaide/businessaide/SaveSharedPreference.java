package com.businessaide.businessaide;

/**
 * Created by ashwinkulkarni on 27/02/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String PREF_USERNAME = "username";

    static SharedPreferences getSP(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String usrname)
    {
        SharedPreferences.Editor editor  = getSP(ctx).edit();
        editor.putString(PREF_USERNAME, usrname);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSP(ctx).getString(PREF_USERNAME, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSP(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
