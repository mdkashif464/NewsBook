package com.example.kashif.newsbook.ApiClasses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by KASHIF on 2/28/2017.
 */

public interface NewsApi {

    @GET("sources")
    public Call<SourceResponse> fetchsourceLists(@Query("category") String category, @Query("language") String language, @Query("country") String country);

    @GET("articles")
    public Call<ArticleResponse> showArticlesList(@Query("apiKey") String apikey, @Query("source") String sourceId, @Query("sortBy") String sortingOrder);
}
