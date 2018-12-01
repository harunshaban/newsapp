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
        loadLocale();

    }
private void setLocale(String lang) {
    Locale locale = new Locale(lang);
    Locale.setDefault(locale);
    Configuration configuration = new Configuration();
    configuration.locale = locale;
    getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

    SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
    editor.putString("My_Lang", lang);
    editor.apply();
}
    //load languages from shared
    public void loadLocale(){
        SharedPreferences pref = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        setLocale(language);
    }
}

