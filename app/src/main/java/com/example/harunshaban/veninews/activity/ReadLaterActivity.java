package com.example.harunshaban.veninews.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harunshaban.veninews.R;
import com.example.harunshaban.veninews.adapter.MainArticalAdapter;
import com.example.harunshaban.veninews.adapter.SavedArticalAdapter;
import com.example.harunshaban.veninews.model.Article;
import com.example.harunshaban.veninews.utils.OnRecyclerViewItemClickListener;

import java.util.List;
import java.util.Locale;

public class ReadLaterActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {

    private List savedArticles = Article.listAll(Article.class);
    private Context context = ReadLaterActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_later);
        getD();
    }

    private void getD(){
        final RecyclerView read_later_recView = findViewById(R.id.activity_read_later_tv);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        read_later_recView.setLayoutManager(linearLayoutManager);

        if(savedArticles.size()>0) {
            final SavedArticalAdapter savedArticalAdapter = new SavedArticalAdapter(savedArticles);
            savedArticalAdapter.setOnRecyclerViewItemClickListener(ReadLaterActivity.this);
            read_later_recView.setAdapter(savedArticalAdapter);
        }else
            aDialogReadLater();

    }

    @Override
    public void onItemClick(int adapterPosition, View view) {
        switch (view.getId()){
            case R.id.article_read_later:
                Article article = (Article)view.getTag();
                if(!TextUtils.isEmpty(article.getUrl())){
                    Log.e("clicked url", article.getUrl());
                    Intent webActivity = new Intent(this, WebActivityReadLater.class);
                    webActivity.putExtra("url", article.getUrl());
                    startActivity(webActivity);
                }
                break;
        }
    }

    public void aDialogReadLater(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.aDialogTitle)
                .setMessage(R.string.aDialogMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.aDialogButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //when cliked ok goes to main activity
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
