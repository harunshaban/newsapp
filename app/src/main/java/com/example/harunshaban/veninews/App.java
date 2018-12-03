package com.example.harunshaban.veninews;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.orm.SugarContext;

import java.util.Locale;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }
}

