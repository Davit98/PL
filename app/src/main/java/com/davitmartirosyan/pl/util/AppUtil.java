package com.davitmartirosyan.pl.util;


import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class AppUtil {

    public static boolean intToBoolean (int b) {
        return (b != 0);
    }

    public static int booleanToInt (boolean b) {
        return (b) ? 1 : 0;
    }

    public static void closeKeyboard(Activity activity) {
        if (activity != null) {
            if (activity.getCurrentFocus() != null) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}
