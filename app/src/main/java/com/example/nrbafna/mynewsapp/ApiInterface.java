package com.example.nrbafna.mynewsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by N.R. BAFNA on 28/03/2017.
 */
public interface ApiInterface {
    @GET("sources")
    Call<Source> getSources();

    @GET("articles")
    Call<NewsSources> getNews(@Query("source") String source,@Query("apiKey") String apiKey);
}
