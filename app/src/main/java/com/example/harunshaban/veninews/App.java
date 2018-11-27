package com.example.harunshaban.veninews;

import android.app.Application;
import android.content.Context;

import com.example.harunshaban.veninews.Helper.LocaleHelper;
import com.orm.SugarContext;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}
