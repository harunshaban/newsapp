package com.example.harunshaban.veninews.activity;

import android.content.Context;
import android.content.Intent;
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

public class ReadLaterActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {

    private List savedArticles = Article.listAll(Article.class);

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
        }

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
}
