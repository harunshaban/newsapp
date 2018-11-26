package com.example.harunshaban.veninews.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AppStatus {

    private static AppStatus instance = new AppStatus();
    static Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean conneted = false;

    public static AppStatus getInstance(Context ctx){
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try{
            connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            conneted = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return conneted;
        }catch (Exception e){
            Log.v("connectivity", e.toString());
        }
        return conneted;
    }
}
