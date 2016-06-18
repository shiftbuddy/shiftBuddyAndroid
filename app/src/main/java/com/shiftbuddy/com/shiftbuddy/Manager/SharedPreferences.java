package com.shiftbuddy.com.shiftbuddy.Manager;

import android.content.Context;

/**
 * @(#) ShiftBuddy
 * <p>
 * Copyright (C) ShiftBuddy, 2016
 * All rights reserved.
 * <p>
 * This software is the proprietary information of
 * shiftbuddy ("Confidential Information").
 * Author : Dinesh Vaithyalingam Gangatharan
 */

public class SharedPreferences {

    public static void storePref(String key, String value, Context context) {
        context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE).edit().putString(key, value)
                .commit();
    }

    public static String getPrefString(String key, String defValue, Context context) {
        if (context != null) {
            return context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE).getString(key, defValue);
        } else {
            return defValue;
        }
    }
}
