package com.example.harunshaban.veninews;

import android.app.Application;
import android.content.Context;

import com.orm.SugarContext;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(this);
    }
}
