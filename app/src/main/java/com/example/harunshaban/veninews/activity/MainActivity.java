package com.example.harunshaban.veninews.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.harunshaban.veninews.adapter.AppStatus;
import com.example.harunshaban.veninews.rests.APIInterface;
import com.example.harunshaban.veninews.rests.ApiClient;
import com.example.harunshaban.veninews.model.Article;
import com.example.harunshaban.veninews.adapter.MainArticalAdapter;
import com.example.harunshaban.veninews.utils.OnRecyclerViewItemClickListener;
import com.example.harunshaban.veninews.R;
import com.example.harunshaban.veninews.model.ResponeModel;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String API_KEY = "7b3071b23f5c45a79722c732ffe7f5fb";
    private String TC_URL = "https://techcrunch.com/pages/contact-us/";
    private String ABOUT_URL = "https://techcrunch.com/pages/about-techcrunch";
    private DrawerLayout drawerLayout;
    private String TAG = "mylog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        This is from internet it is equivalent with App class
        Made separate because we don`t want to execute the code every tme we open the application

        SugarDb db = new SugarDb(this);
        db.onCreate(db.getDB());
        */
        loadLocale();
        SwipeMenu();
        checkNetwork();
        showData();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.item1:
                    Intent intent_ReadLater = new Intent(".activity.ReadLaterActivity");
                    startActivity(intent_ReadLater);
                break;
            case R.id.item2:
                Intent intent_settings = new Intent(".activity.SettingsActivity");
                //startActivity(intent_settings);
                //TODO start activity to take to take the data from there
                startActivityForResult(intent_settings, 1);
                break;
            case R.id.item3:
                Intent intent_about = new Intent(Intent.ACTION_VIEW, Uri.parse(ABOUT_URL));
                startActivity(intent_about);
                break;
            case R.id.item4:
                Intent intent_terms_conditions = new Intent(Intent.ACTION_VIEW, Uri.parse(TC_URL));
                startActivity(intent_terms_conditions);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //TODO created override to get the result from activity settings
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                String res = data.getStringExtra("Language");
                setLocale(res);
                recreate();
            }
        }
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
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private void SwipeMenu(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawoer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void checkNetwork(){
        if(!AppStatus.getInstance(this).isOnline()){
            Log.v("mainActNetwork", "notConnected");
            Toast.makeText(MainActivity.this, "No internet Access", Toast.LENGTH_LONG).show();
        }
    }
    private void showData(){
        // Second step to create the recycler view to show the data taken
        final RecyclerView mainRecycler = findViewById(R.id.activity_main_tv);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mainRecycler.setLayoutManager(linearLayoutManager);

        // First step to create the response
        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponeModel> call = apiService.getLatestNews("techcrunch", API_KEY);

        //third step
        call.enqueue(new Callback<ResponeModel>() {
            @Override
            public void onResponse(Call<ResponeModel> call, Response<ResponeModel> response) {
                if(response.body().getStatus().equals("ok")){
                    List<Article> articleList = response.body().getArticles();
                    if(articleList.size()>0){
                        final MainArticalAdapter mainArticalAdapter = new MainArticalAdapter(articleList);
                        mainArticalAdapter.setOnRecyclerViewItemClickListener(MainActivity.this);
                        mainRecycler.setAdapter(mainArticalAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponeModel> call, Throwable t) {
                Log.e("on Fail", t.toString());
            }
        });
    }
    public void onItemClick(int position, View view){
        switch (view.getId()){
            case R.id.article_adapter_ll_parent:
                Article article = (Article)view.getTag();
                if(!TextUtils.isEmpty(article.getUrl())){
                    Log.e("clicked url", article.getUrl());
                    Intent webActivity = new Intent(this, WebActivity.class);
                    webActivity.putExtra("url", article.getUrl());
                    startActivity(webActivity);
                }
                break;
        }
    }
}
