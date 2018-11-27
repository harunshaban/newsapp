package com.example.harunshaban.veninews.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.harunshaban.veninews.R;

import java.util.Locale;

import io.paperdb.Paper;

public class SettingsActivity extends AppCompatActivity {

    //decleration for feedback
    private Button btn_feedback;
    private String recipient_email = "miso@venikom.com";
    private String subject = "Feedback news app";

    //decleartion for change language
    private Button btn_changelanguage;

    //list view for language
    String[] name_languages = {"English", "Spanish"};
    Context context1 = SettingsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        feedback();
        changeLanguage();
    }

    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(context1);
                        builder.setTitle("Select Language");
                        //add a list
                        builder.setItems(name_languages, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        setLocale("en");
                                        Toast.makeText(context1, "English selected", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        setLocale("es");
                                        Toast.makeText(context1, "Spanish selected", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }) ;
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
        );
    }
}
