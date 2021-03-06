package com.davitmartirosyan.pl.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtil {
    // ===========================================================
    // Constants
    // ===========================================================
    private static final String LOG_TAG = NetworkUtil.class.getSimpleName();
    // ===========================================================
    // Fields
    // ===========================================================
    private static NetworkUtil sInstance;
    // ===========================================================
    // Constructors
    // ===========================================================
    private NetworkUtil() {
    }
    public static NetworkUtil getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkUtil();
        }
        return sInstance;
    }
    // ===========================================================
    // Getter & Setter
    // ===========================================================
    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================
    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================
    // ===========================================================
    // Methods
    // ===========================================================
    public boolean isConnected(Context context) {
        Log.d("testt","isConnected");
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}