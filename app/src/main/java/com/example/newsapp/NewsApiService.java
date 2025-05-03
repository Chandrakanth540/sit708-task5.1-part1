package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    @GET("v2/everything")
    Call<NewsApiResponse> getRelatedNews(
            @Query("q") String query,
            @Query("apiKey") String apiKey,
            @Query("page") int page
    );
}
