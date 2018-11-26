package com.example.harunshaban.veninews.rests;

import com.example.harunshaban.veninews.model.ResponeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("top-headlines")
    Call<ResponeModel>getLatestNews(@Query("sources")String source, @Query("apiKey") String apiKey);

}
