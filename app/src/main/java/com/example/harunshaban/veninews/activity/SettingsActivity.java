package com.example.harunshaban.veninews.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.harunshaban.veninews.R;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    //decleration for feedback
    private Button btn_feedback;
    private String recipient_email = "harunshaban@outlook.com";
    private String subject = "Feedback news app";

    //decleartion for change language
    private Button btn_changelanguage;

    Intent i = new Intent();
    Context context = SettingsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadLocale();
        setContentView(R.layout.activity_settings);
        feedback();
        changeLanguage();
    }



    private void feedback(){
        btn_feedback = findViewById(R.id.feedback_button);
        btn_feedback.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient_email});
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        intent.putExtra(Intent.EXTRA_TEXT, "Test text");

                        intent.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent, "Choose e mail app"));
                    }
                }
        );
    }
    private void changeLanguage(){
        btn_changelanguage = findViewById(R.id.changelanguage_button);
        btn_changelanguage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showChangeLanguageDialog();
                    }
                }
        );
    }

    private void showChangeLanguageDialog() {
        final String[] listItem = {"English", "French"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Language");
        builder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    //english
                    Toast.makeText(context, "English selected", Toast.LENGTH_SHORT).show();
                    //setLocale("en");
                    //recreateApp();
                    i.putExtra("Language", "en");
                    setResult(RESULT_OK, i);
                    finish();
                }
                if(which==1){
                    //french
                    Toast.makeText(context, "French selected", Toast.LENGTH_SHORT).show();
                    //setLocale("fr");
                    //recreateApp();
                    i.putExtra("Language", "fr");
                    setResult(RESULT_OK, i);
                    finish();
                }
                //dismiss alert dialog when is selected
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();

    }

    private void recreateApp(){
        Intent goMainActivity = new Intent(this, MainActivity.class);
        goMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goMainActivity);
        finish();
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
